package com.demo.product.controller;

import com.demo.common.controller.ControllerPath;
import com.demo.common.dto.ApiResponse;
import com.demo.product.dto.ProductEnquiryRequest;
import com.demo.product.entity.Product;
import com.demo.product.service.ProductService;
import com.demo.product.vo.ProductUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ControllerPath.product)
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping(path = ControllerPath.QUERY, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> enquiry(@Valid @RequestBody ProductEnquiryRequest request){
        return productService.enquiry(request);
    }

    @PostMapping(path = ControllerPath.UPDATE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse update(@Valid @RequestBody ProductUpdateRequest[] request){
        productService.update(request);
        return new ApiResponse();
    }
}
