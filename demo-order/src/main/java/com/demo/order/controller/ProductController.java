package com.demo.order.controller;

import com.demo.common.controller.ControllerPath;
import com.demo.common.dto.ApiResponse;
import com.demo.common.dto.ProductEnquiryRequest;
import com.demo.common.vo.ProductUpdateRequest;
import com.demo.order.entity.Product;
import com.demo.order.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResponse update(@Valid @RequestBody List<ProductUpdateRequest> request){
        productService.productUpdateRequest(request);
        return new ApiResponse();
    }
}