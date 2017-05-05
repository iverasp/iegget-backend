package no.iegget.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import no.iegget.model.Account;
import no.iegget.utils.Slug;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BlogEntry {

    @JsonIgnore
    @ManyToOne
    private Account account;

    @Id
    @GeneratedValue
    private Long id;

    private String slug;

    private Date timeCreated;

    private Date timeEdited;

    private String title;

    private String summary;

    @Lob
    private String contents;

    private String feauteredPhotoUri;

    @ManyToOne
    private BlogCategory category;

    public BlogEntry(){}

    public BlogEntry(Account account, String title) {
        this.account = account;
        this.title = title;
    }

    public BlogEntry(Account account, String title, String summary, String contents, BlogCategory category) {
        this.account = account;
        this.title = title;
        this.summary = summary;
        this.contents = contents;
        this.category = category;
        this.slug = Slug.makeSlug(title);
    }

    @PrePersist
    protected void onCreate() {
        timeCreated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        timeEdited = new Date();
    }

    public void setTiny() {
        this.contents = null;
    }

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public Date getTimeEdited() {
        return timeEdited;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getSummary() {
        return summary;
    }

    public BlogCategory getCategory() {
        return category;
    }

    public String getAuthor() {
        return account.getFullName();
    }

    public String getSlug() {
        return slug;
    }

    @Override
    public String toString() {
        return "BlogEntry{" +
                "account=" + account +
                ", id=" + id +
                ", slug='" + slug + '\'' +
                ", timeCreated=" + timeCreated +
                ", timeEdited=" + timeEdited +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", contents='" + contents + '\'' +
                ", category=" + category +
                '}';
    }
}
