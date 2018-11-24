package com.maven;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by jm on 2018/11/24.
 * Copyright (c) 2018,jeff.zhew01@gmail.com All Rights Reserved.
 */
public class SearchTest {
    private TransportClient client;
    private String indexName = "accounts";
    private String typeName = "person";

    @Before
    public void before() {
        // 通过setting对象指定集群配置信息, 配置的集群名
        Settings settings = Settings.settingsBuilder().put("cluster.name", "es-owl-2.3.3") // 设置集群名
                //.put("client.transport.sniff", true) // 开启嗅探 , 开启后会一直连接不上, 原因未知
                //.put("network.host", "192.168.50.37")
                .put("client.transport.ignore_cluster_name", true) // 忽略集群名字验证, 打开后集群名字不对也能连接上
                //.put("client.transport.nodes_sampler_interval", 5) //报错,
                //.put("client.transport.ping_timeout", 5) // 报错, ping等待时间,
                .build();
        client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("172.20.4.204", 9300)))
                .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("172.20.4.201", 9300)))
                .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("172.20.4.202", 9300)));
        // 默认5s
        // 多久打开连接, 默认5s
        System.out.println("success connect");
    }

    /**
     * 相对于sql 的 select * from accounts.person  where id=1 ;
     */
    @Test
    public void testGet() {
        GetResponse response = client.prepareGet(indexName, typeName, "1").setOperationThreaded(false).get();
        System.out.println(response.getSourceAsString());
    }

    /**
     * 各种查询
     * must = and   相较于sql  select * from accounts.person where title='JAVA开发工程师' and age=30
     * should = or  相较于sql  select * from accounts.person where user='kimchy14' or user='kimchy15'
     * 包含查询 相当于sql  select age from accounts.person where user in ('kimchy14','kimchy15','kimchy16')
     * 分页查询 setFrom(0).setSize(100)
     */
    @Test
    public void testSearch() {
        //条件组合查询(must = and,should = or)
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("user", "kimchy14");
        TermQueryBuilder queryBuilder1 = QueryBuilders.termQuery("user", "kimchy15");
        QueryBuilder qb = QueryBuilders.boolQuery().should(queryBuilder).should(queryBuilder1);

        //查询所有（match_all）
        QueryBuilder qb0 = QueryBuilders.matchAllQuery();

        //范围查询
        RangeQueryBuilder rqb = QueryBuilders.rangeQuery("age").gte(30);
        QueryBuilder qb1 = QueryBuilders.boolQuery().should(rqb);

        //包含查询
        QueryBuilder qb2_1 = QueryBuilders.termQuery("user", "kimchy14");
        QueryBuilder qb2_2 = QueryBuilders.termsQuery("user", "kimchy14", "kimchy15", "kimchy16");

        //按id查询
        QueryBuilder qb3 = QueryBuilders.idsQuery(0 + "");

        //按通配符查询
        QueryBuilder qb4 = QueryBuilders.wildcardQuery("user", "k*hy17*");

        //termQuery, matchQuery, matchPhraseQuery, multiMatchQuery
        QueryBuilder qb5_1 = QueryBuilders.termQuery("user", "乔约翰史密斯");//完全匹配
        MatchQueryBuilder qb5_2 = QueryBuilders.matchQuery("user", "乔约翰史密斯");//先分词然后检索
        MatchQueryBuilder qb5_3 = QueryBuilders.matchPhraseQuery("user", "乔约翰史密斯");//精确检索
        QueryBuilder qb5_4 = QueryBuilders.multiMatchQuery("changge","address","interests");

        //前缀查询
        QueryBuilder qb6 = QueryBuilders.prefixQuery("name", "zhao");

        //类型查询（type）
        QueryBuilder qb7 = QueryBuilders.typeQuery("blog");

        //模糊查询（fuzzy）
        QueryBuilder qb8 = QueryBuilders.fuzzyQuery("interests", "chagge");

        SortBuilder sortBuilder = SortBuilders.fieldSort("age");
        sortBuilder.order(SortOrder.DESC);
        SearchRequestBuilder sv = client.prepareSearch(indexName).setTypes(typeName).setQuery(qb).addSort(sortBuilder).setFrom(0).setSize(100);
        System.out.println(sv.toString());
        SearchResponse response = sv.get();
        SearchHits searchHits = response.getHits();
        println(searchHits);
    }

    @Test
    public void testTimeRange() {
        SearchRequestBuilder builder = client.prepareSearch(indexName).setTypes(typeName);
        RangeQueryBuilder qb = QueryBuilders.rangeQuery("time").from("2016-7-21 00:00:01").to("2016-7-21 00:00:03");
        SearchResponse response = builder.setQuery(qb).setFrom(0).setSize(50).setExplain(true).execute().actionGet();
        SearchHits hits = response.getHits();
        for (int i = 0; i < hits.getHits().length; i++) {
            System.out.println(hits.getHits()[i].getSourceAsString());
        }
    }

    /**
     * 计算出count max min avg sum   相当于sql  select count(age) ageAgg form player.person
     */
    @Test
    public void testAgg() {
        //统计, select count(age) ageAgg from accounts.person
        SearchResponse resp = client.prepareSearch(indexName).setTypes(typeName).addAggregation(AggregationBuilders.stats("ageAgg").field("age")).get();
        Stats ageAgg = resp.getAggregations().get("ageAgg");
        System.out.println("最小值：" + ageAgg.getMin());
        System.out.println("最大值：" + ageAgg.getMax());
        System.out.println("平均值：" + ageAgg.getAvg());
        System.out.println("和：" + ageAgg.getSum());
        System.out.println("总数：" + ageAgg.getCount());

        //统计最小值, select min(age) ageAgg from accounts.person
        SearchRequestBuilder builder = client.prepareSearch(indexName).setTypes(typeName);
        builder.addAggregation(AggregationBuilders.min("ageAgg").field("age"));//min max avg sum count方法
        SearchResponse response = builder.execute().actionGet();
        Min minAgg = response.getAggregations().get("ageAgg");//Min Max Avg Sum ValueCount类
        double value = minAgg.getValue();
        System.out.println("最小值：" + value);

        //分组聚合, select count(age) ageAgg from accounts.person group by age
        AggregationBuilder agg1 = AggregationBuilders.terms("ageAgg").field("age");
        SearchResponse response1 = client.prepareSearch(indexName).setTypes(typeName).addAggregation(agg1).execute().actionGet();
        Terms terms = response1.getAggregations().get("ageAgg");
        for (Terms.Bucket entry : terms.getBuckets()) {
            System.out.println(entry.getKey() + ":" + entry.getDocCount());
        }

        //过滤聚合, select count(age) filter from accounts.person where age=20
        //过滤的条件可以自己定义
        QueryBuilder query = QueryBuilders.termQuery("age", 20);
        AggregationBuilder agg2 = AggregationBuilders.filter("filter").filter(query);
        SearchResponse response2 = client.prepareSearch(indexName).setTypes(typeName).addAggregation(agg2).execute().actionGet();
        Filter filter = response2.getAggregations().get("filter");
        System.out.println(filter.getDocCount());

        //为空或null聚合, select * from accounts.person where age is null
        AggregationBuilder agg3 = AggregationBuilders.missing("missing").field("age");
        SearchResponse response3 = client.prepareSearch(indexName).setTypes(typeName).addAggregation(agg3).execute().actionGet();
        Aggregation aggregation = response3.getAggregations().get("missing");
        System.out.println(aggregation.toString());

    }

    private void println(SearchHits searchHits){
        for (SearchHit hit : searchHits.getHits()) {
            //整个文档输出
            System.out.println(hit.getSourceAsString());
            //文档中每个字段数据
            Map<String, Object> map = hit.getSource();
            for (String key : map.keySet()) {
                System.out.println(key + "=" + map.get(key));
            }
        }
    }
}
