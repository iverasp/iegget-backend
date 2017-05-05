package no.iegget.blog.controller;

import no.iegget.blog.model.BlogCategory;
import no.iegget.blog.model.BlogEntry;
import no.iegget.blog.repository.BlogCategoryRepository;
import no.iegget.blog.repository.BlogRepository;
import no.iegget.blog.resource.BlogEntryResource;
import no.iegget.blog.resource.TinyBlogEntryResource;
import no.iegget.eat.model.Recipe;
import no.iegget.eat.repository.RecipeRepository;
import no.iegget.eat.resource.RecipeResource;
import no.iegget.exception.UserNotFoundException;
import no.iegget.repository.AccountRepository;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog")
public class BlogRestController {

    private final AccountRepository accountRepository;
    private final BlogRepository blogRepository;
    private final BlogCategoryRepository blogCategoryRepository;

    BlogRestController(AccountRepository accountRepository, BlogRepository blogRepository, BlogCategoryRepository blogCategoryRepository) {
        this.accountRepository = accountRepository;
        this.blogRepository = blogRepository;
        this.blogCategoryRepository = blogCategoryRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    List<BlogEntry> readBlogEntries(@RequestParam(required = false, value = "category") String category) {
        System.out.println("Get blog entries");
        if (category != null) {
            BlogCategory blogCategory = blogCategoryRepository.findByNameIgnoreCase(category);
            return blogRepository.findAllByCategory(blogCategory);
        }
        return blogRepository.findAll();
    }

    @RequestMapping("/categories")
    List<BlogCategory> readBlogCategories() {
        return blogCategoryRepository.findAll();
    }

    /*
    @RequestMapping(method = RequestMethod.GET)
    Resources<TinyBlogEntryResource> readBlogEntries() {

        List<TinyBlogEntryResource> blogEntryResourceList = blogRepository
                .findAll()
                .stream()
                .map(TinyBlogEntryResource::new)
                .collect(Collectors.toList());

        return new Resources<>(blogEntryResourceList);
    }
    */

    @RequestMapping(method = RequestMethod.GET, value = "/{slug}")
    public BlogEntry readBlogEntry(@PathVariable String slug) {
        BlogEntry entry = this.blogRepository.findBySlug(slug);
        if (entry != null) System.out.println(entry.getTitle());
        return entry;
    }

}
