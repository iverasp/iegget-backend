package no.iegget.blog.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class BlogCategory {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String photoUri;

    @OneToMany
    private Set<BlogEntry> blogEntries;

    public BlogCategory(){}

    public BlogCategory(String name, String photoUri) {
        this.name = name;
        this.photoUri = photoUri;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUri() {
        return photoUri;
    }
}
