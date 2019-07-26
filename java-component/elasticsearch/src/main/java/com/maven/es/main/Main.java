package com.maven.es.main;

import com.maven.es.base.ElasticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by jm on 2019/1/2.
 * Copyright (c) 2018,jeff.zhew01@gmail.com All Rights Reserved.
 */
public class Main {
    private static Map<String, Integer> maps = new ConcurrentHashMap<>();
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        String clusterName = args[0];
        String clusterNodes = args[1];
        ElasticService elasticService = ElasticService.getInstance(clusterName, clusterNodes);

        List<String> resIds = new ArrayList<>();
        String res_index = "owl_hijack_res";
        String res_type = "res";
        elasticService.getIdList(res_index, res_type, resIds);
        int count = 0;
        for(String s: resIds){
            maps.put(res_index+","+res_type+","+s, count++);
        }

        List<String> pageIds = new ArrayList<>();
        String page_index = "owl_hijack_page";
        String page_type = "page";
        elasticService.getIdList(page_index, page_type, pageIds);
        for(String s: pageIds){
            maps.put(page_index+","+page_type+","+s, count++);
        }

        LOG.info("start");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Iterator<Map.Entry<String, Integer>> entries = maps.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, Integer> entry = entries.next();
                    String[] key = entry.getKey().split(",");
                    elasticService.delete(key[0], key[1], key[2]);
                    maps.remove(entry.getKey());
                }
                LOG.info("end");
            }
        });
    }
}
