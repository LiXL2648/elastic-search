package com.li.elasticsearch.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.li.elasticsearch.entity.Shopping;
import com.li.elasticsearch.util.EsClientUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.math.BigDecimal;

public class BatchDocument {

    public static void main(String[] args) throws Exception {

        RestHighLevelClient esClient = EsClientUtil.getInstance().getEsClient();
        BulkRequest bulkRequest = new BulkRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        bulkRequest.add(new IndexRequest().index("shopping").id("7").source(objectMapper.writeValueAsString(
                new Shopping("iPhone 12", "Apple", "https://item.jd.com/100020961240.html", new BigDecimal("6299.00"))), XContentType.JSON));
        bulkRequest.add(new IndexRequest().index("shopping").id("8").source(objectMapper.writeValueAsString(
                new Shopping("iPhone 11 Prod Max", "Apple", "https://item.jd.com/100020961240.html", new BigDecimal("7299.00"))), XContentType.JSON));
        bulkRequest.add(new IndexRequest().index("shopping").id("9").source(objectMapper.writeValueAsString(
                new Shopping("iPhone X", "Apple", "https://item.jd.com/100020961240.html", new BigDecimal("8499.00"))), XContentType.JSON));
        BulkResponse bulkResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.hasFailures());
        esClient.close();
    }
}
