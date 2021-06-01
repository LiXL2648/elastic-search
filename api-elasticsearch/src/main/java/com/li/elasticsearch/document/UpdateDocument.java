package com.li.elasticsearch.document;

import com.li.elasticsearch.util.EsClientUtil;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class UpdateDocument {

    public static void main(String[] args) throws Exception {

        RestHighLevelClient esClient = EsClientUtil.getInstance().getEsClient();
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("shopping").id("10");
        // updateRequest.doc(XContentType.JSON, "price", new BigDecimal("6199.00"));
        updateRequest.doc(XContentType.JSON, "title", "iPhone 11");
        UpdateResponse updateResponse = esClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse.getResult());
        esClient.close();
    }
}
