package com.truongbn.security.service.impl;

import com.truongbn.security.dao.ProductDto;
import com.truongbn.security.entities.Product;
import com.truongbn.security.repository.ProductRepository;
import com.truongbn.security.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product savedProduct = modelMapper.map(productDto, Product.class);
        System.out.println("dto to entity: "+savedProduct);
        Product saved = productRepository.save(savedProduct);
        System.out.println("saved entity: "+saved);
        ProductDto returnedMap = modelMapper.map(saved, ProductDto.class);
        return returnedMap;
    }
}
