package com.example.elasticsearch.demo.service;

import co.elastic.clients.elasticsearch._types.SortOptions;
import com.example.elasticsearch.demo.entity.Post;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostService {

    private static final String PRODUCT_INDEX = "posts";

    private ElasticsearchOperations elasticsearchOperations;

    public PostService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public void findProductsByBrand(final String value) {

        Query query = Query.of(q -> q
                .match(m -> m
                        .field("title")
                        .query(value)
                )
        );

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(query)
                .build();

        SearchHits<Post> productHits =
                elasticsearchOperations
                        .search(searchQuery,
                                Post.class,
                                IndexCoordinates.of(PRODUCT_INDEX));

        productHits.forEach(hit -> {
            Post post = hit.getContent();
            System.out.println(post);
        });
    }

    public void findPostGreaterThanTenLike(){
        Query query = Query.of(q->
                q.range( r ->
                        r.number(n-> n
                                .field("postLike")
                                .gte(10.0)
                        )

                )
        );

        NativeQuery searchQuery = NativeQuery.builder().withQuery(query).build();
        SearchHits<Post> postList = elasticsearchOperations
                .search(searchQuery, Post.class, IndexCoordinates.of(PRODUCT_INDEX));

        System.out.println("Total Hits: " + postList.getTotalHits());
        postList.forEach(hit-> System.out.println(hit.getContent()));
    }

    public void findPostAfterDate(){
        Query query = Query.of(q-> q
                      .range(r -> r
                              .date(d -> d
                                      .field("createdDate")
                                      .gt("2023-12-05T09:30:00Z")
                              )
                      )
        );
        Sort sort = Sort.by(Sort.Order.asc("createdDate"));
        elasticQuery(query, sort);
    }


    public void findPostZeroDisLike(){
        Query query = Query.of(q-> q
                .term(t -> t
                        .field("postDislike")
                        .value(0)
                )
        );
        Sort sort = Sort.by(Sort.Order.desc("postLike"));
        elasticQuery(query, sort);
    }

    private void elasticQuery(Query query, Sort sort){
        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(query)
                .withSort(sort)
                .build();

        SearchHits<Post> searchHits = elasticsearchOperations.search(
                nativeQuery,
                Post.class,
                IndexCoordinates.of(PRODUCT_INDEX)
        );

        System.out.println(searchHits.getTotalHits());

        searchHits.forEach(hit -> {
            Post post = hit.getContent();
            System.out.println(post);
        });
    }
}
