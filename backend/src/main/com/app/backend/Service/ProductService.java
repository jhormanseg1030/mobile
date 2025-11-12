package com.app.backend.Service;

import com.app.backend.models.Product;
import com.app.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service

public class ProductService{
    @Autowired
    private ProductService productService;

    public List<Product> finAll(){
        return productRepository.findAll();
    }

    public List<Product> findByCategoryId(Long id){
        return productRepository.findByCategoryId(id);
    }

    public List<Product> findBySubcategoryId(Long id){
        return productRepository.findBySubcategoryId(id);
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElse(()-> new RuntimeException("Producto no encontrado"));
    }

    public Product create(Subcategory subcategory){
        return productRepository.save(subcategory);
    }

    public Product update(Long id, Subcategory subcategoryDetails){
        Product  product = findById(id);
        product.setName(subcategoryDetails.getName());
        product.setDescription(subcategoryDetails.getDescription());
        product.setPrice(subcategoryDetails.getPrice());
        product.setStock(subcategoryDetails.getStock());
        product.setActive(subcategoryDetails.getActive());
        product.setCategory(subcategoryDetails.getCategory());
        product.setSubcategory(subcategoryDetails.getSubcategory());
        return productRepository.save(product);
    }

    public void delete(Long id){
        Product product = findById(id);
        productRepository.delete(product);
    }
}