package no.iegget.blog.repository;

import no.iegget.blog.model.BlogCategory;
import no.iegget.blog.model.BlogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BlogRepository extends JpaRepository<BlogEntry, Long> {

    Collection<BlogEntry> findByAccountUsername(String username);

    BlogEntry findByTitle(String title);

    List<BlogEntry> findAllByCategory(BlogCategory blogCategory);

    BlogEntry findBySlug(String slug);

}