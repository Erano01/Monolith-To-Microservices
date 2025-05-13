package me.erano.com.service;

import me.erano.com.dto.ProductRequest;
import me.erano.com.dto.ProductResponse;
import me.erano.com.model.Product;
import me.erano.com.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ProductResponse createProductResponse(ProductRequest productRequest){
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest){
        return productRepository.findById(id)
                .map(existenceProduct -> {
                    updateProductFromRequest(existenceProduct, productRequest);
                    Product savedProduct = productRepository.save(existenceProduct);
                    return mapToProductResponse(savedProduct);
        });
    }

    private Product updateProductFromRequest(Product product, ProductRequest productRequest){
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        return product;
    }
    private ProductResponse mapToProductResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory(),
                product.getImageUrl(),
                product.getActive()
        );
    }

}
