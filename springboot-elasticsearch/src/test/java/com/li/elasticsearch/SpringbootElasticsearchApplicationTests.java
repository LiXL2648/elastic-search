package com.li.elasticsearch;

import com.li.elasticsearch.entity.Product;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SpringbootElasticsearchApplicationTests {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private ElasticsearchRestTemplate esRestTemplate;

    // 查看所有文档
    public void test() {
        
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
