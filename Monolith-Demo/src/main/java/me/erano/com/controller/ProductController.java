package me.erano.com.controller;

import me.erano.com.dto.ProductRequest;
import me.erano.com.dto.ProductResponse;
import me.erano.com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        return new ResponseEntity<ProductResponse>(productService.createProductResponse(productRequest), HttpStatus.CREATED);
    }

}
