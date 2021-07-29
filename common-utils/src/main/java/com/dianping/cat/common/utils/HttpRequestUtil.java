package com.dianping.cat.common.utils;

import com.dianping.cat.Cat;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unidal.lookup.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author mort
 * @Description
 * @date 2021/1/14
 **/
public class HttpRequestUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    public static final String CHARSET = "UTF-8";


    public static String doGet(Map<String, String> params, String url, Header header) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response;
            URIBuilder uriBuilder = new URIBuilder(url);
            List<NameValuePair> list = new LinkedList<>();
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    list.add(new BasicNameValuePair(entry.getKey(), value));
                }
                uriBuilder.setParameters(list);
            }
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            if(null != header) {
                httpGet.setHeader(header);
            }
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, CHARSET);
        } catch (Exception e) {
            Cat.logError(e);
            logger.error("error ", e);
        }
        return null;
    }

    public static String doPost(Map<String, String> params, String body, String url, Header header) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response;
            URIBuilder uriBuilder = new URIBuilder(url);
            List<NameValuePair> list = new LinkedList<>();
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    list.add(new BasicNameValuePair(entry.getKey(), value));
                }
                uriBuilder.setParameters(list);
            }
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            if (null != header) {
                httpPost.setHeader(header);
            }
            if(StringUtils.isNotEmpty(body)){
                httpPost.setEntity(new StringEntity(body, CHARSET));
            }
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, CHARSET);
        } catch (Exception e) {
            Cat.logError(e);
            logger.error("error ", e);
        }
        return null;
    }
}
