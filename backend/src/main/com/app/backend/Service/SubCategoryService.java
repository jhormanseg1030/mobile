package com.app.backend.Service;

import com.app.backend.models.Subcategory;
import com.app.backend.models.Category;
import com.app.backend.repository.CategoryRepository;
import com.app.backend.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubCategoryService{

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Subcategory> findAll(){
        return subcategoryRepository.findAll();
    }

    public List<Subcategory> findByCategoryId(Long id){
        return subcategoryRepository.findByCategoryId(id);
    }

    public Subcategory findById(Long id){
        return subcategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Subcategoria no encontrada"));
    }

    public Subcategory create(Subcategory subcategory){
        // Si category tiene ID pero es un objeto incompleto, buscar el completo
        if(subcategory.getCategory() != null && subcategory.getCategory().getId() != null){
            subcategory.setCategory(categoryRepository.findById(subcategory.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada")));
        }
        return subcategoryRepository.save(subcategory);
    }

    public Subcategory update(Long id, Subcategory subcategoryDetails){
        Subcategory subcategory = findById(id);
        subcategory.setName(subcategoryDetails.getName());
        subcategory.setDescription(subcategoryDetails.getDescription());
        subcategory.setActive(subcategoryDetails.getActive());
        
        // Si se envía category con ID, resolver la entidad completa
        if(subcategoryDetails.getCategory() != null && subcategoryDetails.getCategory().getId() != null){
            subcategory.setCategory(categoryRepository.findById(subcategoryDetails.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada")));
        } else if(subcategoryDetails.getCategory() != null){
            subcategory.setCategory(subcategoryDetails.getCategory());
        }
        
        return subcategoryRepository.save(subcategory);
    }

    public void delete(Long id){
        Subcategory subcategory = findById(id);
        subcategoryRepository.delete(subcategory);
    }
}