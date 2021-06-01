package com.li.elasticsearch.document;

import com.li.elasticsearch.util.EsClientUtil;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

public class GetDocument {

    public static void main(String[] args) throws Exception {

        RestHighLevelClient esClient = EsClientUtil.getInstance().getEsClient();
        GetRequest getRequest = new GetRequest();
        getRequest.index("shopping").id("7");
        GetResponse getResponse = esClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());
        esClient.close();
    }
}
