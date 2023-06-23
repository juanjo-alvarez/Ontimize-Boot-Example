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
    @Value("${aap.files.path}")
    private String path;

    @Autowired
    IProductService productService;

    @Override
    public IProductService getService() {
        return productService;
    }

    @PostMapping(value = "upload")
    public ResponseEntity upload(@RequestParam("name") String[] names, @RequestParam("file") MultipartFile[] files, @RequestParam(name="data",required = false) String data) {

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
            Map mProductId = (Map) extraData.get(ProductFileDao.ATTR_PRODUCT_ID);
            productId = (Integer) mProductId.get("value");
            String directory = path+productId;
            try {
                Files.createDirectories(Paths.get(directory));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for(MultipartFile file:files){
                String filePath = directory+"\\"+file.getOriginalFilename();
                File newFile = new File(filePath);
                Map<String,Object> fileResult = new HashMap<>();
                try {
                    if(newFile.exists()){
                        fileResult.put(NAME,file.getOriginalFilename());
                        fileResult.put(STATUS,"ALREADY_EXIST");
                    }else{
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
