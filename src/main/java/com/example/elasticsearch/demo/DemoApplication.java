package com.example.elasticsearch.demo;


import com.example.elasticsearch.demo.entity.Post;
import com.example.elasticsearch.demo.repo.PostRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private RestHighLevelClient client;


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Post post = new Post();
		post.setId("1");
		post.setTitle("Elasticsearch with Spring Boot");
		post.setContent("This is a demo post for Elasticsearch with Spring Boot.");
		post.setCreatedAt("2023-10-01T10:00:00Z");

		postRepo.save(post);

		System.out.println(searchByName("Elasticsearch with Spring Boot"));
	}

	public List<Post> searchByName(String keyword) throws IOException {
		SearchRequest searchRequest = new SearchRequest("posts");

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.matchQuery("title", keyword));
		searchRequest.source(sourceBuilder);

		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

		List<Post> products = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();

		for (SearchHit hit : response.getHits().getHits()) {
			Post p = mapper.readValue(hit.getSourceAsString(), Post.class);
			products.add(p);
		}

		return products;
	}
}
