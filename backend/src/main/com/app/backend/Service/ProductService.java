package com.app.backend.Service;

import com.app.backend.models.Product;
import com.app.backend.models.Category;
import com.app.backend.models.Subcategory;
import com.app.backend.repository.ProductRepository;
import com.app.backend.repository.CategoryRepository;
import com.app.backend.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service

public class ProductService{
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private SubcategoryRepository subcategoryRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public List<Product> findByCategoryId(Long id){
        return productRepository.findByCategoryId(id);
    }

    public List<Product> findBySubcategoryId(Long id){
        return productRepository.findBySubcategoryId(id);
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Product create(Product product){
        // Si category tiene ID pero es un objeto incompleto, buscar el completo
        if(product.getCategory() != null && product.getCategory().getId() != null){
            product.setCategory(categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada")));
        }
        
        // Si subcategory tiene ID pero es un objeto incompleto, buscar el completo
        if(product.getSubcategory() != null && product.getSubcategory().getId() != null){
            product.setSubcategory(subcategoryRepository.findById(product.getSubcategory().getId())
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada")));
        }
        
        return productRepository.save(product);
    }

    public Product update(Long id, Product productDetails){
        Product product = findById(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());
        product.setActive(productDetails.getActive());
        
        // Si se envía category con ID, resolver la entidad completa
        if(productDetails.getCategory() != null && productDetails.getCategory().getId() != null){
            product.setCategory(categoryRepository.findById(productDetails.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada")));
        } else if(productDetails.getCategory() != null){
            product.setCategory(productDetails.getCategory());
        }
        
        // Si se envía subcategory con ID, resolver la entidad completa
        if(productDetails.getSubcategory() != null && productDetails.getSubcategory().getId() != null){
            product.setSubcategory(subcategoryRepository.findById(productDetails.getSubcategory().getId())
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada")));
        } else if(productDetails.getSubcategory() != null){
            product.setSubcategory(productDetails.getSubcategory());
        }
        
        return productRepository.save(product);
    }

    public void delete(Long id){
        Product product = findById(id);
        productRepository.delete(product);
    }
}