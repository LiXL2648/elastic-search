package com.li.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.li.elasticsearch.entity.Product;
import com.li.elasticsearch.entity.Users;
import com.li.elasticsearch.repository.ProductRepository;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SpringbootElasticsearchApplicationTests {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() {

        Product product = new Product(1L, "iphone 12", "apple", "http://ip:port/image1.jsp", new BigDecimal("6799.99"));
        productRepository.save(product);
    }

    @Test
    public void testFindAll() {
        Iterable<Product> all = productRepository.findAll();
        all.forEach(System.out::println);
    }

    @Test
    public void testSaveUser() throws Exception {
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("users").id("2");
        Users users = new Users(null, "lilx", 2);
        String json = objectMapper.writeValueAsString(users);
        indexRequest.source(json, XContentType.JSON);
        IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.getResult());
    }

    @Test
    public void testQueryUser() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("users");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        searchResponse.getHits().forEach(h -> System.out.println(h.getSourceAsString()));
    }

    @Test
    public void testCreateIndex() {
        System.out.println("创建索引");
    }

    @Test
    public void testDeleteIndex() throws IOException {
        System.out.println("删除索引");
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("product");
        AcknowledgedResponse response = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    @Test
    public void contextLoads() {
        System.out.println(restHighLevelClient);
    }

}
