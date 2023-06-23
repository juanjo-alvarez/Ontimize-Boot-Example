package com.campusdual.springontimize.ws.core.rest;

import com.campusdual.springontimize.api.core.service.IProductService;
import com.campusdual.springontimize.model.core.dao.ProductFileDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.server.rest.ORestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductsRestController extends ORestController<IProductService> {

    public static final String STATUS = "status";
    public static final String NAME = "name";

    //the parameter in the yml where put the files in local path
    @Value("${aap.files.path}")
    private String path;

    @Autowired
    IProductService productService;

    @Override
    public IProductService getService() {
        return productService;
    }

    /**
     * this methos id called from the o-file-input component.
     *
     * @param names the names of the files
     * @param files the content of the files
     * @param data String with JSON estructure with extra info abount the files, id_product description or another info to insert in the files table
     * @return String with JSON estructure
     */
    @PostMapping(value = "upload")
    public ResponseEntity upload(@RequestParam("name") String[] names, @RequestParam("file") MultipartFile[] files, @RequestParam(name="data",required = false) String data) {

        //cast the data to a map object
        HashMap<String, Object> extraData = new HashMap<>();
        if (data != null) {
            try {
                extraData = new ObjectMapper().readValue(data, HashMap.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        Integer productId = null;
        EntityResult result = new EntityResultMapImpl();
        if(extraData.get(ProductFileDao.ATTR_PRODUCT_ID) instanceof Map){
            //get the product associated
            Map mProductId = (Map) extraData.get(ProductFileDao.ATTR_PRODUCT_ID);
            productId = (Integer) mProductId.get("value");
            //the directory is related to the product
            String directory = path+productId;
            try {
                //create the directory if not exists
                Files.createDirectories(Paths.get(directory));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for(MultipartFile file:files){
                //for each file sote the file locally
                String filePath = directory+"\\"+file.getOriginalFilename();
                File newFile = new File(filePath);
                Map<String,Object> fileResult = new HashMap<>();
                try {
                    if(newFile.exists()){
                        //if exist not remplace and return the aready exists state
                        fileResult.put(NAME,file.getOriginalFilename());
                        fileResult.put(STATUS,"ALREADY_EXIST");
                    }else{
                        //write the file in the disk
                        file.transferTo(newFile);
                        Map<String,Object> attrMap = new HashMap();
                        attrMap.put(ProductFileDao.ATTR_PRODUCT_ID,productId);
                        attrMap.put(ProductFileDao.ATTR_NAME,file.getOriginalFilename());
                        attrMap.put(ProductFileDao.ATTR_PATH,filePath);
                        EntityResult fileInsert = productService.fileInsert(attrMap);
                        if(fileInsert.isWrong()){
                            fileResult.put(NAME,file.getOriginalFilename());
                            fileResult.put(STATUS,"ERROR_ON_INSERT");
                        }else{
                            fileResult.put(NAME,file.getOriginalFilename());
                            fileResult.put(STATUS,"OK");
                        }
                    }
                } catch (IOException e) {
                    fileResult.put(NAME,file.getOriginalFilename());
                    fileResult.put(STATUS,"ERROR_ON_WRITE_FILE");
                }
                result.addRecord(fileResult);
            }
        }

        return new ResponseEntity<EntityResult>(result,HttpStatus.OK);
    }
}
