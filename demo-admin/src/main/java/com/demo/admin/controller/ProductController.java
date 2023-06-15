package com.demo.admin.controller;

import com.demo.admin.dto.ProductEnquiryRequest;
import com.demo.admin.entity.Product;
import com.demo.admin.service.ProductService;
import com.demo.common.controller.ControllerPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ControllerPath.menu)
public class ProductController {
    @Autowired
    private ProductService productService;
    public List<Product> enquiry(ProductEnquiryRequest request){
        return productService.enquiry(request);
    }
}
