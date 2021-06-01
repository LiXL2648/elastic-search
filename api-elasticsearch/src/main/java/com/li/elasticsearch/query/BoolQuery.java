package com.li.elasticsearch.query;

import com.li.elasticsearch.util.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class BoolQuery {

    public static void main(String[] args) throws Exception {

        RestHighLevelClient esClient = EsClientUtil.getInstance().getEsClient();

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 组合查询
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("category", "华为");
        MatchQueryBuilder matchQueryBuilder1 = QueryBuilders.matchQuery("category", "Apple");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().should(matchQueryBuilder).should(matchQueryBuilder1);
        builder.query(boolQueryBuilder);

        // 范围查询
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("price").gte(5999.0d).lte(8499.0d);
        builder.query(rangeQueryBuilder);

        searchRequest.source(builder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        searchResponse.getHits().forEach(s -> System.out.println(s.getSourceAsString()));
        esClient.close();
    }
}
