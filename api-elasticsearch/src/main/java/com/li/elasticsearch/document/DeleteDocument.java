package com.li.elasticsearch.document;

import com.li.elasticsearch.util.EsClientUtil;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

public class DeleteDocument {

    public static void main(String[] args) throws Exception {

        RestHighLevelClient esClient = EsClientUtil.getInstance().getEsClient();
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index("shopping").id("7");
        DeleteResponse deleteResponse = esClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.getResult());
        esClient.close();
    }
}
