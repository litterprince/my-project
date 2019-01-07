/*
 * Copyright (C) 2016 jumei, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.maven.es.base;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.*;

/**
 * Function:ES 工具包<br>
 *
 * @Date :2017年1月13日<br>
 * @Author :timothy.yue12@gmail.com<br>
 * @Copyright Copyright (c) 2016, JuMei All Rights Reserved.<br>
 */
public class ElasticService {
    private static final Logger LOG = LoggerFactory.getLogger(ElasticService.class);
    private static final String CLUSTER_NAME = "cluster.name";
    private static final String CLIENT_TRANSPORT_SNIFF = "client.transport.sniff";
    private Calendar calendar = Calendar.getInstance();
    private Settings settings;
    private TransportClient client;
    private static ElasticService instance = null;

    private ElasticService(String clusterName, String clusterNodes) {
        try {
            // 指定集群名称&探测集群中机器状态
            settings = Settings.settingsBuilder().put(CLUSTER_NAME, clusterName).put(CLIENT_TRANSPORT_SNIFF, true).build();
            // 创建客户端，所有的操作都由客户端开始
            client = TransportClient.builder().settings(settings).build();

            String[] nodes = clusterNodes.trim().split(",");
            if (nodes == null || nodes.length <= 0) {
                throw new RuntimeException("ElasticService Constructor property clusterNodes format error! ");
            }

            for (String node : nodes) {
                String[] nodeIpPort = node.trim().split(":");
                String nodeIp = nodeIpPort[0];
                int nodePort = Integer.parseInt(nodeIpPort[1]);
                if (StringUtils.isBlank(nodeIp) || nodePort <= 0) {
                    throw new RuntimeException("ElasticService Constructor property nodeIp|nodePort format error! ");
                }
                // 添加可以操作的ES数据传输地址
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(nodeIp), nodePort));
            }
        } catch (Exception e) {
            LOG.error("init elastic error:" + e.getMessage(), e);
        }
    }

    public static ElasticService getInstance(String clusterName, String clusterNodes) {
        if(instance == null){
            synchronized(ElasticService.class) {
                if(instance==null) {
                    instance = new ElasticService(clusterName, clusterNodes);
                }
            }
        }
        return instance;
    }

    /**
     * ES 保存数据<br>
     *
     * @param data<br>
     */
    public void save(String index, String type, Map<String, Object> data, String time) {
        long start = System.currentTimeMillis();
        save(index, type, data);
        LOG.info("es upload success, type: " + index + ", time: " + time + ", elapsed time: " + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * ES 保存数据<br>
     *
     * @param index
     * @param type
     * @param data
     */
    public void save(String index, String type, Map<String, Object> data) {
        // 获取索引
        String getCurrentIndex = getESIndex(index, calendar.get(Calendar.WEEK_OF_YEAR));
        long start = System.currentTimeMillis();
        IndexResponse indexResponse = client.prepareIndex(getCurrentIndex, type).setId(getUUID()).setSource(data).get();
        if (indexResponse.isCreated()) {
            if (data.get("date") != null) {
                LOG.info("es upload success, type: " + index + ", time: " + data.get("date"));
            } else if (data.get("time") != null) {
                LOG.info("es upload success, type: " + index + ", time: " + data.get("time") + ", elapsed time: " + (System.currentTimeMillis() - start) + " ms");
            } else {
                LOG.info("es upload search success, type: " + index);
            }
        } else {
            LOG.error("es upload failed, type: " + index + ", msg: " + JSON.toJSONString(data), new RuntimeException("es save error."));
        }
    }

    private String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private String getESIndex(String index, Integer cycle) {
        Date date = new Date();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return index + "_" + year + "_" + cycle;
    }

    public void delete(String indexName, String typeName, String id){
        DeleteResponse response = client.prepareDelete(indexName, typeName, id).get();
        if(response.isFound()) {
            String index = response.getIndex();
            String type = response.getType();
            long version = response.getVersion();
            LOG.info(index + " : " + type + ": " + id + ": " + version);
        }
    }

    public void getIdList(String indexName, String typeName, List<String> ids){
        RangeQueryBuilder rqb = QueryBuilders.rangeQuery("timeStamp").lte(1545926399000l);
        QueryBuilder qb = QueryBuilders.boolQuery().should(rqb);
        SortBuilder sortBuilder = SortBuilders.fieldSort("timeStamp");
        sortBuilder.order(SortOrder.DESC);
        SearchRequestBuilder sv = client.prepareSearch(indexName).setTypes(typeName).setQuery(qb).addSort(sortBuilder).setFrom(0).setSize(10000);
        //System.out.println(sv.toString());
        SearchResponse response = sv.get();
        SearchHits searchHits = response.getHits();
        for (SearchHit hit : searchHits.getHits()) {
            String id = hit.getId();
            if(!ids.contains(id)) {
                ids.add(id);
            }
        }
    }
}
