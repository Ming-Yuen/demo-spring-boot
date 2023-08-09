package com.demo.admin.controller;

import com.demo.admin.dto.ProductEnquiryRequest;
import com.demo.admin.entity.Product;
import com.demo.admin.service.ProductService;
import com.demo.common.controller.ControllerPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ControllerPath.product)
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping(path = ControllerPath.enquiry, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> enquiry(@Valid @RequestBody ProductEnquiryRequest request){
        return productService.enquiry(request);
    }
    @PostMapping(path = "importDate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void importData() throws InterruptedException {
        productService.importDate();
    }
}
