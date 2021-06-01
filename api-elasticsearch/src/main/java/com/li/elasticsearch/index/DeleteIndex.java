package com.li.elasticsearch.index;

import com.li.elasticsearch.util.EsClientUtil;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

public class DeleteIndex {

    public static void main(String[] args) throws Exception {

        RestHighLevelClient esClient = EsClientUtil.getInstance().getEsClient();
        DeleteIndexRequest indexRequest = new DeleteIndexRequest("student");
        AcknowledgedResponse response = esClient.indices().delete(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
        esClient.close();
    }
}
