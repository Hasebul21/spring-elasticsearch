package com.example.elasticsearch.demo.repo;

import com.example.elasticsearch.demo.entity.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends ElasticsearchRepository<Post, String> {
    // Custom query methods can be defined here
    // For example, findByTitle or findByContent
}
