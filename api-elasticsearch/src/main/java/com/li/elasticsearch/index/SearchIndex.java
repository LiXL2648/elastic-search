package com.li.elasticsearch.index;

import com.li.elasticsearch.util.EsClientUtil;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

public class SearchIndex {

    public static void main(String[] args) throws Exception {

        RestHighLevelClient esClient = EsClientUtil.getInstance().getEsClient();
        GetIndexRequest indexRequest = new GetIndexRequest("student");
        GetIndexResponse indexResponse = esClient.indices().get(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.getAliases());
        System.out.println(indexResponse.getSettings());
        System.out.println(indexResponse.getMappings());
        esClient.close();
    }
}
