package com.li.elasticsearch.query;

import com.li.elasticsearch.util.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedDoubleTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedMax;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class AggregationQuery {

    public static void main(String[] args) throws Exception {

        RestHighLevelClient esClient = EsClientUtil.getInstance().getEsClient();
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 分组查询
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("terms_price").field("price");
        builder.aggregation(termsAggregationBuilder);

        // 分组查询，求最大值
        MaxAggregationBuilder maxAggregationBuilder = AggregationBuilders.max("max_price").field("price");
        builder.aggregation(maxAggregationBuilder);

        searchRequest.source(builder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        ParsedDoubleTerms termsCategoryAggregation = searchResponse.getAggregations().get("terms_price");
        System.out.println(termsCategoryAggregation.getBuckets());

        ParsedMax maxPriceAggregation = searchResponse.getAggregations().get("max_price");
        System.out.println(maxPriceAggregation.getValue());
        esClient.close();
    }
}
