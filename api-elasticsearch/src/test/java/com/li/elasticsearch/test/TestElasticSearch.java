package com.li.elasticsearch.test;

import com.li.elasticsearch.util.EsClientUtil;
import org.junit.Test;

public class TestElasticSearch {

    @Test
    public void testEsClient() {

        System.out.println(EsClientUtil.getInstance().getEsClient());
    }
}
