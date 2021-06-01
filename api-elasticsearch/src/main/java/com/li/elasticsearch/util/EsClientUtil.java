package com.li.elasticsearch.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class EsClientUtil {

    private static final String HOSTNAME = "10.10.0.26";

    private static final Integer PORT = 9200;

    private static EsClientUtil instance = null;

    private EsClientUtil() {}

    public static EsClientUtil getInstance() {
        if (instance == null) {
            synchronized (EsClientUtil.class) {
                if (instance == null) {
                    instance = new EsClientUtil();
                }
            }
        }

        return instance;
    }

    public RestHighLevelClient getEsClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(HOSTNAME, PORT)));
    }
}
