package com.li.elasticsearch.query;

import com.li.elasticsearch.util.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

public class PagingQuery {

    public static void main(String[] args) throws Exception {

        RestHighLevelClient esClient = EsClientUtil.getInstance().getEsClient();

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("shopping");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 全文检索
        searchSourceBuilder.query(QueryBuilders.matchQuery("category", "Apple"));
        // 分页查询
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(2);
        // 排序
        searchSourceBuilder.sort("price", SortOrder.ASC);
        // 过滤字段
        String[] includes = {};
        String[] excludes = {"category"};
        searchSourceBuilder.fetchSource(includes, excludes);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        searchResponse.getHits().forEach(s -> System.out.println(s.getSourceAsString()));
        esClient.close();
    }
}
