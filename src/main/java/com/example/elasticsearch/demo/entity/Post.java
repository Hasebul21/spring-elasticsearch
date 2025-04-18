package com.example.elasticsearch.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "posts")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Post {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Text, name = "creatorName")
    private String creatorName;

    @Field(type = FieldType.Integer)
    private int postLike;

    @Field(type = FieldType.Integer)
    private int postDislike;

    @Field(type = FieldType.Date, name = "createdDate")
    private String createdDate;

    // 👉 Constructors

    public Post() {
    }

    public Post(String id, String title, String content, String creatorName, int postLike, int postDislike, String createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creatorName = creatorName;
        this.postLike = postLike;
        this.postDislike = postDislike;
        this.createdDate = createdDate;
    }

    // 👉 Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public int getPostLike() {
        return postLike;
    }

    public void setPostLike(int postLike) {
        this.postLike = postLike;
    }

    public int getPostDislike() {
        return postDislike;
    }

    public void setPostDislike(int postDislike) {
        this.postDislike = postDislike;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}

