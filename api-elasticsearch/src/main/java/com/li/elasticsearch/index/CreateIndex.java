package com.li.elasticsearch.index;


import com.li.elasticsearch.util.EsClientUtil;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

public class CreateIndex {

    public static void main(String[] args) throws Exception {

        RestHighLevelClient esClient = EsClientUtil.getInstance().getEsClient();
        CreateIndexRequest indexRequest = new CreateIndexRequest("student");
        CreateIndexResponse indexResponse = esClient.indices().create(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.isAcknowledged());
        esClient.close();
    }
}
