package com.asura.elastic.client.sdk;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.elastic.client.config.ElasticConfig;
import com.asura.elastic.client.remote.IElasticRemoteService;
import com.asura.elastic.client.utils.FeignUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Elastic 基础服务
 *
 * @author henry
 */
public class ElasticService {

    private static final String URL_ES = ElasticConfig.URL_ES_TEST;

    /**
     * 批量插入 / 重建索引
     *
     * @param idx_name  索引名称
     * @param data      需要导入的数据
     * @return
     */
    public static JSONObject batchInsert(String idx_name, JSONArray data) {
        if (StringUtils.isBlank(idx_name)) {
            return new JSONObject().fluentPut("msg", "idx_name 不能为空");
        }
        if (null == data || 0 == data.size()) {
            return new JSONObject().fluentPut("msg", "插入的数据不能为空");
        }

        JSONObject idx_base = new JSONObject().fluentPut("_index", idx_name);
        JSONObject idx_info = new JSONObject().fluentPut("index", idx_base);

        StringBuffer body = new StringBuffer();
        for (int i = 0; i < data.size(); i++) {
            body.append(idx_info)
              .append("\n")
              .append(data.getString(i))
              .append("\n");
        }

        String url = URL_ES + "/" + idx_name;
        String res = FeignUtil.getTarget(IElasticRemoteService.class, url)
                              .batchInsert(body.toString());
        return handleResult(res);
    }

    /**
     * 清空索引
     *
     * @param idx_name  索引名称
     * @return
     */
    public static JSONObject clearIndex(String idx_name) {
        if (StringUtils.isBlank(idx_name)) {
            return new JSONObject().fluentPut("msg", "idx_name 不能为空");
        }

        String body = "{ \"query\": { \"match_all\": {} } }";
        String url = URL_ES + "/" + idx_name;
        String res = FeignUtil.getTarget(IElasticRemoteService.class, url).clear(body);

        return handleResult(res);
    }

    /**
     * 查询全部
     *
     * @param idx_name  索引名称
     * @param from      开始位置
     * @param size      页大小
     * @return
     */
    public static JSONObject searchAll(String idx_name, int from, int size) {
        if (StringUtils.isBlank(idx_name)) {
            return new JSONObject().fluentPut("msg", "idx_name 不能为空");
        }

        from = from < 0 ? 0 : from;
        size = size <= 0 ? 100 : size;

        JSONObject condition = new JSONObject().fluentPut("match_all", new JSONObject());
        JSONObject body = new JSONObject().fluentPut("query", condition)
                                          .fluentPut("from", from)
                                          .fluentPut("size", size);

        String url = URL_ES + "/" + idx_name;
        String res = FeignUtil.getTarget(IElasticRemoteService.class, url)
                              .searchAll(body.toJSONString());
        return handleResult(res);

    }


    /**
     * 关键字搜索
     *
     * @param idx_name      索引名称
     * @param keyword       关键字
     * @param field_name    字段名
     * @return
     */
    public static JSONObject searchByKeyword(String idx_name, String keyword, String field_name) {
        if (StringUtils.isBlank(idx_name)) {
            return new JSONObject().fluentPut("msg", "idx_name 不能为空");
        }
        if (StringUtils.isBlank(keyword) || StringUtils.isBlank(field_name)) {
            return new JSONObject().fluentPut("msg", "keyword、field_name 不能为空");
        }

        JSONObject condition_inner = new JSONObject().fluentPut(field_name, keyword);
        JSONObject condition_outer = new JSONObject().fluentPut("match", condition_inner);
        JSONObject body = new JSONObject().fluentPut("query", condition_outer)
                                          .fluentPut("from", 0).fluentPut("size", 100);

        String url = URL_ES + "/" + idx_name;
        String res = FeignUtil.getTarget(IElasticRemoteService.class, url)
                .searchByKeyword(body.toJSONString());
        return handleResult(res);

    }

    private static JSONObject handleResult(String res) {
        JSONObject jsonRes;
        try {
            jsonRes = JSONObject.parseObject(res);
        } catch (Exception e) {
            String msg = "数据格式异常：" + e.getMessage();
            jsonRes = new JSONObject().fluentPut("code", "FAIL")
                                       .fluentPut("msg", msg);
        }
        return jsonRes;
    }

}
