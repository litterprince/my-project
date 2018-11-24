package com.maven;

import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.script.Script;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
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
     * 查看集群信息
     */
    @Test
    public void testEsInfo() {
        List<DiscoveryNode> nodes = client.connectedNodes();
        for (DiscoveryNode node : nodes) {
            System.out.println(node.getHostAddress());
        }
    }

    /**
     * 进行连接测试
     * @throws Exception
     */
    @Test
    public void testInsert() throws Exception {
        Map<String,Object> json = new HashMap<String, Object>();
        json.put("user", "kimchy");
        json.put("postDate", new Date());
        json.put("message", "trying out elasticsearch");
        // 存json入索引中
        IndexResponse response = client.prepareIndex(indexName, typeName, "1").setSource(json).get();
        // 结果获取
        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        long version = response.getVersion();
        boolean created = response.isCreated();
        System.out.println(index + " : " + type + ": " + id + ": " + version + ": " + created);
    }

    /**
     * get API 获取指定文档信息
     */
    @Test
    public void testGet() {
        GetResponse response = client.prepareGet(indexName, typeName, "bcc3f55d88464344adea6b3d3e3db8e6")
                .setOperationThreaded(false)    // 线程安全
                .get();
        System.out.println(response.getSourceAsString());
    }

    /**
     * 测试 delete api
     */
    @Test
    public void testDelete() {
        DeleteResponse response = client.prepareDelete(indexName, typeName, "1").get();
        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        long version = response.getVersion();
        System.out.println(index + " : " + type + ": " + id + ": " + version);
    }

    /**
     * 测试update api, 使用client
     * @throws Exception
     */
    @Test
    public void testUpdate() throws Exception {
        // 使用Script对象进行更新
        UpdateResponse response = client.prepareUpdate(indexName, typeName, "1")
                .setScript(new Script("hits._source.gender = \"male\"")).get();

        // 使用XContFactory.jsonBuilder() 进行更新
        UpdateResponse response1 = client.prepareUpdate(indexName, typeName, "1")
                .setDoc(XContentFactory.jsonBuilder()
                .startObject()
                .field("gender", "malelelele")
                .endObject()).get();

        // 使用updateRequest对象及script
        UpdateRequest updateRequest = new UpdateRequest(indexName, typeName, "1")
                .script(new Script("ctx._source.gender=\"male\""));
        UpdateResponse response2 = client.update(updateRequest).get();

        // 使用updateRequest对象及documents进行更新
        UpdateResponse response3 = client.update(new UpdateRequest(indexName, typeName, "1")
                .doc(XContentFactory.jsonBuilder()
                .startObject()
                .field("gender", "male")
                .endObject()
            )).get();
        System.out.println(response.getIndex());
    }

    /**
     * 测试upsert方法
     * @throws Exception
     *
     */
    @Test
    public void testUpsert() throws Exception {
        // 设置查询条件, 查找不到则添加生效
        IndexRequest indexRequest = new IndexRequest(indexName, typeName, "1")
                .source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("name", "qergef")
                        .field("gender", "malfdsae")
                        .endObject());
        // 设置更新, 查找到更新下面的设置
        UpdateRequest upsert = new UpdateRequest(indexName, typeName, "1")
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("user", "wenbronk")
                        .endObject())
                .upsert(indexRequest);

        client.update(upsert).get();
    }

    /**
     * 测试multi get api
     * 从不同的index, type, 和id中获取
     */
    @Test
    public void testMultiGet() {
        MultiGetResponse multiGetResponse = client.prepareMultiGet()
                .add(indexName, typeName, "1")
                .add(indexName, typeName, "2", "3", "4")
                .add("anothoer", "type", "foo")
                .get();

        for (MultiGetItemResponse itemResponse : multiGetResponse) {
            GetResponse response = itemResponse.getResponse();
            if (response.isExists()) {
                String sourceAsString = response.getSourceAsString();
                System.out.println(sourceAsString);
            }
        }
    }

    /**
     * bulk 批量执行
     * 一次查询可以update 或 delete多个document
     */
    @Test
    public void testBulk() throws Exception {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        bulkRequest.add(client.prepareIndex(indexName, typeName, "1")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()));
        bulkRequest.add(client.prepareIndex("twitter", "tweet", "2")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "another post")
                        .endObject()));
        BulkResponse response = bulkRequest.get();
        System.out.println(response.getHeaders());
    }

    /**
     * 使用bulk processor
     * @throws Exception
     */
    @Test
    public void testBulkProcessor() throws Exception {
        // 创建BulkPorcessor对象
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            public void beforeBulk(long paramLong, BulkRequest paramBulkRequest) {
                // TODO Auto-generated method stub
            }

            // 执行出错时执行
            public void afterBulk(long paramLong, BulkRequest paramBulkRequest, Throwable paramThrowable) {
                // TODO Auto-generated method stub
            }

            public void afterBulk(long paramLong, BulkRequest paramBulkRequest, BulkResponse paramBulkResponse) {
                // TODO Auto-generated method stub
            }
        })
        // 1w次请求执行一次bulk
        .setBulkActions(10000)
        // 1gb的数据刷新一次bulk
        .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
        // 固定5s必须刷新一次
        .setFlushInterval(TimeValue.timeValueSeconds(5))
        // 并发请求数量, 0不并发, 1并发允许执行
        .setConcurrentRequests(1)
        // 设置退避, 100ms后执行, 最大请求3次
        .setBackoffPolicy(
                BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
        .build();

        // 添加单次请求
        bulkProcessor.add(new IndexRequest(indexName, typeName, "1"));
        bulkProcessor.add(new DeleteRequest(indexName, typeName, "2"));

        // 关闭
        bulkProcessor.awaitClose(10, TimeUnit.MINUTES);
        // 或者
        bulkProcessor.close();
    }
}
