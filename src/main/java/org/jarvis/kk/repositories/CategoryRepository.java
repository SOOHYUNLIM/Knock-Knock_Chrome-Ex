package org.jarvis.kk.repositories;

import org.jarvis.kk.dto.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CategoryDTORepository
 */
public interface CategoryRepository extends JpaRepository<Category, String> {

    
}