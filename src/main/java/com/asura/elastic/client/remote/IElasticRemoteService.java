package com.asura.elastic.client.remote;

import feign.Headers;
import feign.RequestLine;

/**
 * Elastic 基础接口
 *
 * @author Henry
 */
public interface IElasticRemoteService {

    /**
     * 批量导入 / 重建索引
     *
     * @param body
     * @return
     */
    @Headers("Content-Type: application/json")
    @RequestLine("POST /_bulk")
    String batchInsert(String body);

    /**
     * 清空文档
     *
     * @param body
     * @return
     */
    @Headers("Content-Type: application/json")
    @RequestLine("POST /_doc/_delete_by_query")
    String clear(String body);

    /**
     * 搜索所有文档
     *
     * @param body
     * @return
     */
    @Headers("Content-Type: application/json")
    @RequestLine("POST /_doc/_search")
    String searchAll(String body);

    /**
     * 关键字搜索
     * 中文分词
     *
     * @param Body
     * @return
     */
    @Headers("Content-Type: application/json")
    @RequestLine("POST /_doc/_search")
    String searchByKeyword(String Body);

}
