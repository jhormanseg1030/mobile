package com.app.backend.Service;

import com.app.backend.models.Subcategory;
import com.app.backend.repository.CategoryRepository;
import com.app.backend.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubcategoryService{

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    public List<Subcategory> finAll(){
        return subcategoryRepository.findAll();
    }

    public List<Subcategory> findByCategoryId(Long id){
        return subcategoryRepository.findByCategoryId(id);
    }

    public Subcategory findById(Long id){
        return subcategoryRepository.findById(id).orElse(() -> new RuntimeException("Subcategoria no encontrada"));
    }

    public Subcategory create(Subcategory subcategory){
        return categoryRepository.save(subcategory);
    }

    public Subcategory update(Long id, Subcategory subcategoryDetails){
        Subcategory  subcategory = findById(id);
        subcategory.setName(subcategoryDetails.getName());
        subcategory.setDescription(subcategoryDetails.getDescription());
        subcategory.setActive(subcategoryDetails.getActive());
        subcategory.setCategory(subcategoryDetails.getCategory());
        return subcategoryRepository.save(subcategory);
    }

    public void delete(Long id){
        Subcategory subcategory = findById(id);
        subcategoryRepository.delete(subcategory);
    }
}