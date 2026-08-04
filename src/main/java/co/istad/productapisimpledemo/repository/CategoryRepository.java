package co.istad.productapisimpledemo.repository;

import co.istad.productapisimpledemo.entity.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Boolean existsByName(String name);
    List<Category> findByNameContainingIgnoreCase(String name);
    // pagination
    List<Category> findByParentCategoryIsNull(Sort sort );
}
