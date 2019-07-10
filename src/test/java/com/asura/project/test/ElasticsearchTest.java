package com.asura.project.test;

import com.alibaba.fastjson.JSONObject;
import com.asura.elastic.client.sdk.ElasticService;
import org.junit.Test;

public class ElasticsearchTest {


    public void printSplit() {
        System.out.println("####################################");
    }

    @Test
    public void testStatic() throws InterruptedException {
        String idx_name = "idx_goods";

        JSONObject res1 = ElasticService.searchAll(idx_name, 0, 100);
        System.out.println(res1.toJSONString());
        printSplit();

        /*JSONObject res2 = ElasticService.clearIndex(idx_name);
        System.out.println(res2.toJSONString());
        printSplit();

        // 清空数据需要等一段时间才能生效
        Thread.sleep(2000);
        JSONObject res3 = ElasticService.searchAll(idx_name, 0, 100);
        System.out.println(res3.toJSONString());
        printSplit();*/

        /*String data = "[{ \"id\": \"1\", \"name\": \"苹果\",    \"price\": 100, \"number\": \"600101\", \"type\": \"A\", \"status\": \"N\", \"spec\": \"\"},{ \"id\": \"2\", \"name\": \"苹果\",    \"price\": 100, \"number\": \"600102\", \"type\": \"A\", \"status\": \"D\", \"spec\": \"\"},{ \"id\": \"3\", \"name\": \"西瓜\",    \"price\": 100, \"number\": \"600103\", \"type\": \"B\", \"status\": \"N\", \"spec\": \"水果\"}]";
        JSONArray dataList = JSONArray.parseArray(data);
        JSONObject res4 = ElasticService.batchInsert(idx_name, dataList);
        System.out.println(res4.toJSONString());
        printSplit();

        Thread.sleep(2000);
        JSONObject res5 = ElasticService.searchAll(idx_name,0, 100);
        System.out.println(res5.toJSONString());
        printSplit();*/

        JSONObject res6 = ElasticService.searchByKeyword(idx_name, "果", "spec");
        System.out.println(res6.toJSONString());
        printSplit();
    }

}
