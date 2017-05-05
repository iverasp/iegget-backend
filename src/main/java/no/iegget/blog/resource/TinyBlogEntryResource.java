package no.iegget.blog.resource;

import no.iegget.blog.controller.BlogRestController;
import no.iegget.blog.model.BlogEntry;
import no.iegget.blog.model.TinyBlogEntry;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class TinyBlogEntryResource extends ResourceSupport {

    private final BlogEntry blogEntry;

    public TinyBlogEntryResource(BlogEntry blogEntry) {
        this.blogEntry = blogEntry;
        this.blogEntry.setTiny();
        this.add(linkTo(methodOn(BlogRestController.class)
                .readBlogEntry(blogEntry.getSlug())).withSelfRel());
    }

    public BlogEntry getBlogEntry() {
        return blogEntry;
    }
}
