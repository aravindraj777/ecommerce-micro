package com.microsecond.productservice.service;

import com.microsecond.productservice.dto.ProductRequest;
import com.microsecond.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    void createProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();
}
