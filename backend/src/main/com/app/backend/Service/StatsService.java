package com.app.backend.Service;

import com.app.backend.repository.UserRepository;
import com.app.backend.repository.CategoryRepository;
import com.app.backend.repository.SubcategoryRepository;
import com.app.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Map<String, Long> getStats(){
        Map<String, Long> stats = new HashMap<>();
        stats.put("users", userRepository.count());
        stats.put("categories", categoryRepository.count());
        stats.put("subcategories", subCategoryRepository.count());
        stats.put("products", productRepository.count());
        return stats;
    }
}