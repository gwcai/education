package com.genius.coder.education.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genius.coder.base.util.SpringBeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/8
 */
public class HttpsClientUtil {
    /**
     * buildSSLCloseableHttpClient:(设置允许所有主机名称都可以，忽略主机名称验证)
     * @author xbq
     * @return
     * @throws Exception
     */
    private static CloseableHttpClient buildSSLCloseableHttpClient() throws Exception {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            // 信任所有
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        // ALLOW_ALL_HOSTNAME_VERIFIER:这个主机名验证器基本上是关闭主机名验证的,实现的是一个空操作，并且不会抛出javax.net.ssl.SSLException异常。
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    public static JsonNode get(String url) throws Exception {
        CloseableHttpClient client = buildSSLCloseableHttpClient();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse execute = client.execute(get);
        return getJsonNode(execute);
    }

    public static JsonNode post(String url, String body) throws Exception {
        return post(url, body, null);
    }


    public static JsonNode post(String url, String body, String contentType) throws Exception {
        CloseableHttpClient client = buildSSLCloseableHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
        if (StringUtils.isNotBlank(contentType)) {
            httpPost.addHeader("content-type", contentType);
        }
        CloseableHttpResponse execute = client.execute(httpPost);
        return getJsonNode(execute);
    }

    private static JsonNode getJsonNode(CloseableHttpResponse execute) throws Exception {
        HttpEntity entity = execute.getEntity();
        String res = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        return objectMapper().readTree(res);
    }

    private static ObjectMapper objectMapper() {
        return SpringBeanUtil.getBean(ObjectMapper.class);
    }

}
