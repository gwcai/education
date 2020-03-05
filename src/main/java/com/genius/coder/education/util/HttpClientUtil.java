package com.genius.coder.education.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genius.coder.base.util.SpringBeanUtil;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class HttpClientUtil {

    protected final static CloseableHttpClient CLOSEABLE_HTTP_CLIENT;

    static {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
        CLOSEABLE_HTTP_CLIENT = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setMaxConnTotal(20).build();
    }

    public static JsonNode get(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse execute = CLOSEABLE_HTTP_CLIENT.execute(get);
        return getJsonNode(execute);
    }

    public static JsonNode post(String url, String body) throws IOException {
        return post(url, body, null);
    }


    public static JsonNode post(String url, String body, String contentType) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
        if (StringUtils.isNotBlank(contentType)) {
            httpPost.addHeader("content-type", contentType);
        }
        CloseableHttpResponse execute = CLOSEABLE_HTTP_CLIENT.execute(httpPost);
        return getJsonNode(execute);
    }

    private static JsonNode getJsonNode(CloseableHttpResponse execute) throws IOException {
        HttpEntity entity = execute.getEntity();
        String res = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        return objectMapper().readTree(res);
    }

    private static ObjectMapper objectMapper() {
        return SpringBeanUtil.getBean(ObjectMapper.class);
    }

}
