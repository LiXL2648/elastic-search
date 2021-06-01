package com.li.elasticsearch.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.li.elasticsearch.entity.Shopping;
import com.li.elasticsearch.util.EsClientUtil;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.math.BigDecimal;

public class CreateDocument {

    public static void main(String[] args) throws Exception {

        RestHighLevelClient esClient = EsClientUtil.getInstance().getEsClient();
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("shopping").id("11");
        Shopping shopping = new Shopping("Apple 8", "Apple", "http://www.gulixueyuan.com/apple12.jpg",
                new BigDecimal("5299.00"));
        ObjectMapper objectMapper = new ObjectMapper();
        String shoppingJson = objectMapper.writeValueAsString(shopping);
        indexRequest.source(shoppingJson, XContentType.JSON);
        IndexResponse indexResponse = esClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.getResult());

        esClient.close();
    }
}
