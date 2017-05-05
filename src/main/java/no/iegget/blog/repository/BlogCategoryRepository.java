package no.iegget.blog.repository;

import no.iegget.blog.model.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Long> {

    BlogCategory findByNameIgnoreCase(String name);

}
