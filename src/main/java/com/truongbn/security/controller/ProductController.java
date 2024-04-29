package com.truongbn.security.controller;

import com.truongbn.security.dao.ProductDto;
import com.truongbn.security.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/addResource")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){
        System.out.println("ProductDTO: "+productDto);
        ProductDto savedProduct = productService.addProduct(productDto);
       return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
}
