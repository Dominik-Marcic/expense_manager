package com.devot.expense_manager.dataloader;

import com.devot.expense_manager.model.Category;
import com.devot.expense_manager.model.Role;
import com.devot.expense_manager.repository.CategoryRepository;
import com.devot.expense_manager.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;

    public InitialDataLoader(RoleRepository roleRepository, CategoryRepository categoryRepository) {
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
    }

    private void initializeRoles() {
        if (roleRepository.findByName("USER").isEmpty()) {
            Role role = new Role();
            role.setName("USER");
            roleRepository.save(role);
        }

        List<String> predefinedCategories = Arrays.asList("Food", "Car", "Other");

        for (String categoryName : predefinedCategories) {
            if (categoryRepository.findByName(categoryName) == null) {
                Category category = new Category();
                category.setName(categoryName);
                categoryRepository.save(category);
            }
        }
    }
}
