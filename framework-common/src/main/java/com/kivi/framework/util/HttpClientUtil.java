package com.kivi.framework.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kivi.framework.exception.AppException;
import com.kivi.framework.util.kit.StrKit;

@SuppressWarnings( "deprecation" )
public class HttpClientUtil {
    protected static final Logger      log                  = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final RequestConfig config;

    // 连接时间为10秒
    private static final RequestConfig configForNotify;

    public static final String         DEFAULT_SEND_CHARSET = "UTF-8";

    public static final String         DEFAULT_RES_CHARSET  = "UTF-8";

    private static CloseableHttpClient poolHttpClient       = null;

    private final static Object        syncLock             = new Object();

    static {
        config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(60000).build();
    }

    static {
        configForNotify = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
    }

    public static String doGet( Map<String, String> params, String url ) {
        return doGet(params, url, DEFAULT_SEND_CHARSET, DEFAULT_RES_CHARSET);
    }

    public static String doPost( Map<String, String> params, String url ) {
        return doPost(params, url, DEFAULT_SEND_CHARSET, DEFAULT_RES_CHARSET);
    }

    public static String doPostWithRetry( Map<String, String> params, String url ) {
        return doPost(params, url, DEFAULT_SEND_CHARSET, DEFAULT_RES_CHARSET);
    }

    public static String doPostWithPooling( Map<String, String> params, String url ) {
        return doPostPooling(params, url, DEFAULT_SEND_CHARSET, DEFAULT_RES_CHARSET);
    }

    public static String doPostByContentType( Map<String, String> params, String url, String contentType ) {
        return doPostByCodeAndContentType(params, url, DEFAULT_SEND_CHARSET, DEFAULT_RES_CHARSET, contentType);
    }

    public static byte[] doPostBytesForDownload( Map<String, String> params, String url ) {
        return doPostBytesForDownload(params, url, DEFAULT_SEND_CHARSET, DEFAULT_RES_CHARSET);
    }

    public static String doJSONPost( String json, String url ) {
        return doJSONPost(json, url, DEFAULT_SEND_CHARSET, DEFAULT_RES_CHARSET);
    }

    public static String doDualSSLLoginAndDownLoad( String originUrl, String indexUrl, String loginurl,
            Map<String, String> loginParams, String downloadurl, Map<String, String> downloadParams,
            String keyStorePath, String keyStorePass ) {
        CloseableHttpClient httpClient = getDualSSLConnection(keyStorePath, keyStorePass);
        CloseableHttpResponse loginResp = null, response = null;
        if (StrKit.isBlank(loginurl) || StrKit.isBlank(downloadurl)) {
            return null;
        }
        try {

            List<NameValuePair> pairs = null;
            if (loginParams != null && !loginParams.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(loginParams.size());
                for (Map.Entry<String, String> entry : loginParams.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }

            HttpPost loginPost = new HttpPost(loginurl);
            loginPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            loginPost.addHeader("Origin", originUrl);
            loginPost.addHeader("Referer", indexUrl);

            if (pairs != null && pairs.size() > 0) {
                loginPost.setEntity(new UrlEncodedFormEntity(pairs, DEFAULT_SEND_CHARSET));
            }
            loginResp = httpClient.execute(loginPost);
            int statusCode = loginResp.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new AppException("HttpClient,error status code :" + statusCode);
            }

            pairs.clear();
            if (downloadParams != null && !downloadParams.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(downloadParams.size());
                for (Map.Entry<String, String> entry : downloadParams.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost downloandPost = new HttpPost(downloadurl);
            downloandPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            downloandPost.addHeader("Origin", originUrl);
            downloandPost.addHeader("Referer", loginurl);
            downloandPost.addHeader("Connection", "close");

            if (pairs != null && pairs.size() > 0) {
                downloandPost.setEntity(new UrlEncodedFormEntity(pairs, DEFAULT_SEND_CHARSET));
            }
            response = httpClient.execute(downloandPost);
            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new AppException("HttpClient,error status code :" + statusCode);
            }

            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, DEFAULT_RES_CHARSET);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        }
        catch (Exception e) {
            throw new AppException(e);
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                }
        }
    }

    public static String doDualSSLPost( Map<String, String> params, String url, String keyStorePath,
            String keyStorePass ) {
        return doDualSSLPost(params, url, DEFAULT_SEND_CHARSET, DEFAULT_RES_CHARSET, keyStorePath, keyStorePass);
    }

    public static String doPostContent( String dataContent, String contentType, String contentCharset,
            String resCharset,
            String url ) {
        CloseableHttpClient httpClient = getSingleSSLConnection(config);
        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }
        try {
            HttpPost httpPost = new HttpPost(url);

            httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpPost.addHeader("Content-Type", contentType);
            httpPost.addHeader("Connection", "close");
            HttpEntity reqentity = new StringEntity(dataContent, ContentType.create(contentType, contentCharset));
            httpPost.setEntity(reqentity);

            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new AppException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, resCharset == null ? DEFAULT_RES_CHARSET : resCharset);
            }
            EntityUtils.consume(entity);

            return result;
        }
        catch (Exception e) {
            throw new AppException(e);
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                }
        }
    }

    /**
     * 通知超时时间为10秒
     * 
     * @param dataContent
     * @param contentType
     * @param contentCharset
     * @param resCharset
     * @param url
     * @return
     */
    public static String doPostContentForNotify( String dataContent, String contentType, String contentCharset,
            String resCharset, String url ) {
        CloseableHttpClient httpClient = getSingleSSLConnection(configForNotify);
        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }
        try {
            HttpPost httpPost = new HttpPost(url);

            httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpPost.addHeader("Content-Type", contentType);
            httpPost.addHeader("Connection", "close");
            HttpEntity reqentity = new StringEntity(dataContent, ContentType.create(contentType, contentCharset));
            httpPost.setEntity(reqentity);

            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new AppException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, resCharset == null ? DEFAULT_RES_CHARSET : resCharset);
            }
            EntityUtils.consume(entity);

            return result;
        }
        catch (Exception e) {
            throw new AppException(e);
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                }
        }
    }

    public static String doPostContentForNetPayNotify( String dataContent, String contentType, String contentCharset,
            String resCharset, String url ) {
        CloseableHttpClient httpClient = getCustomizedSingleSSLConn(configForNotify);
        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }
        try {
            HttpPost httpPost = new HttpPost(url);

            httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpPost.addHeader("Content-Type", contentType);
            httpPost.addHeader("Connection", "close");
            HttpEntity reqentity = new StringEntity(dataContent, ContentType.create(contentType, contentCharset));
            httpPost.setEntity(reqentity);

            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new AppException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, resCharset == null ? DEFAULT_RES_CHARSET : resCharset);
            }
            EntityUtils.consume(entity);

            return result;
        }
        catch (Exception e) {
            throw new AppException(e);
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                }
        }
    }

    /**
     * 發送報文返回文件类型，需要自己处理响应内容
     * 
     * @param dataContent
     * @param contentType
     * @param contentCharset
     * @param resCharset
     * @param url
     * @return
     */
    public static CloseableHttpResponse doPostContentForDownload( String dataContent, String contentType,
            String contentCharset, String resCharset, String url ) {
        CloseableHttpClient httpClient = getSingleSSLConnection(configForNotify);
        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }
        try {
            HttpPost httpPost = new HttpPost(url);

            httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpPost.addHeader("Content-Type", contentType);
            httpPost.addHeader("Connection", "close");
            HttpEntity reqentity = new StringEntity(dataContent, ContentType.create(contentType, contentCharset));
            httpPost.setEntity(reqentity);

            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new AppException("HttpClient,error status code :" + statusCode);
            }
            return response;
        }
        catch (Exception e) {
            throw new AppException(e);
        }
        finally {
        }
    }

    /**
     * HTTP Get 获取内容
     * 
     * @param params
     *            请求的参数
     * @param url
     *            请求的url地址 ?之前的地址
     * @param reqCharset
     *            编码格式
     * @param resCharset
     *            编码格式
     * @return 页面内容
     */
    public static String doGet( Map<String, String> params, String url, String reqCharset, String resCharset ) {
        CloseableHttpClient httpClient = getSingleSSLConnection(config);
        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(
                        new UrlEncodedFormEntity(pairs, reqCharset == null ? DEFAULT_SEND_CHARSET : reqCharset));
            }
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpGet.addHeader("Connection", "close");

            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new AppException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, resCharset == null ? DEFAULT_RES_CHARSET : resCharset);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        }
        catch (Exception e) {
            throw new AppException(e);
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                }
        }
    }

    /**
     * HTTP Get 获取内容
     * 
     * @param params
     *            请求的参数
     * @param url
     *            请求的url地址 ?之前的地址
     * @param reqCharset
     *            编码格式
     * @param resCharset
     *            编码格式
     * @param connectTimeout
     *            连接超时时间
     * @param socketTimeout
     *            响应超时时间
     * @return 页面内容
     */
    public static String doGetForSelfSetting( Map<String, String> params, String url, String reqCharset,
            String resCharset, Integer connectTimeout, Integer socketTimeout ) {
        RequestConfig configSelf = RequestConfig.custom().setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout).build();
        CloseableHttpClient httpClient = getSingleSSLConnection(configSelf);
        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(
                        new UrlEncodedFormEntity(pairs, reqCharset == null ? DEFAULT_SEND_CHARSET : reqCharset));
            }
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpGet.addHeader("Connection", "close");

            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new AppException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, resCharset == null ? DEFAULT_RES_CHARSET : resCharset);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        }
        catch (Exception e) {
            throw new AppException(e);
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                }
        }
    }

    /**
     * HTTP Post 获取内容
     * 
     * @param params
     *            请求的参数
     * @param url
     *            请求的url地址 ?之前的地址
     * @param reqCharset
     *            编码格式
     * @param resCharset
     *            编码格式
     * @return 页面内容
     */
    public static String doPost( Map<String, String> params, String url, String reqCharset, String resCharset ) {
        CloseableHttpClient httpClient = getSingleSSLConnection(config);

        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpPost.addHeader("Connection", "close");
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(
                        new UrlEncodedFormEntity(pairs, reqCharset == null ? DEFAULT_SEND_CHARSET : reqCharset));
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                log.error("状态码非200, 为:" + statusCode);
                httpPost.abort();
                throw new AppException("状态码" + statusCode, "HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, resCharset == null ? DEFAULT_RES_CHARSET : resCharset);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        }
        catch (AppException e) {
            throw e;
        }
        catch (Exception e) {
            throw new AppException(e);
            // TODO LOG
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                }
        }
    }

    /**
     * 使用PoolingHttpClientConnectionManager管理连接
     * 
     * @param params
     * @param url
     * @param reqCharset
     * @param resCharset
     * @return
     */
    public static String doPostPooling( Map<String, String> params, String url, String reqCharset, String resCharset ) {
        CloseableHttpClient httpClient = getPoolingHttpClient(config, url);

        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpPost.addHeader("Connection", "close");
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(
                        new UrlEncodedFormEntity(pairs, reqCharset == null ? DEFAULT_SEND_CHARSET : reqCharset));
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                log.error("状态码非200, 为:" + statusCode);
                httpPost.abort();
                throw new AppException("状态码" + statusCode, "HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, resCharset == null ? DEFAULT_RES_CHARSET : resCharset);
            }
            EntityUtils.consume(entity);
            // response.close();
            return result;
        }
        catch (AppException e) {
            throw e;
        }
        catch (Exception e) {
            log.error("http异常", e);
            throw new AppException(e);
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 用于 EPCC
     * 
     * @param url
     * @param headers
     * @param dataContent
     * @return
     * @throws Exception
     */
    public static String doPostPooling( String url, Map<String, String> headers, String dataContent ) throws Exception {

        return doPostPooling(url, headers, dataContent, "application/xml;charset=utf-8", DEFAULT_SEND_CHARSET,
                DEFAULT_RES_CHARSET);
    }

    public static String doPostPooling( String url, Map<String, String> headers, String dataContent, String contentType,
            String reqCharset, String resCharset ) throws Exception {

        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }

        try {
            CloseableHttpClient httpClient = getPoolingHttpClient(config, url);

            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", contentType);

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }

            HttpEntity reqentity = new StringEntity(dataContent, ContentType.APPLICATION_XML);
            httpPost.setEntity(reqentity);

            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                log.error("状态码非200, 为:" + statusCode);
                httpPost.abort();
                throw new Exception("状态码" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, resCharset == null ? DEFAULT_RES_CHARSET : resCharset);
            }
            EntityUtils.consume(entity);
            // response.close();
            return result;
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * HTTP Post 获取内容
     * 
     * @param params
     *            请求的参数
     * @param url
     *            请求的url地址 ?之前的地址
     * @param reqCharset
     *            编码格式
     * @param resCharset
     *            编码格式
     * @return 流
     */
    public static byte[] doPostBytesForDownload( Map<String, String> params, String url, String reqCharset,
            String resCharset ) {
        CloseableHttpClient httpClient = getSingleSSLConnection(config);

        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpPost.addHeader("Connection", "close");
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(
                        new UrlEncodedFormEntity(pairs, reqCharset == null ? DEFAULT_SEND_CHARSET : reqCharset));
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                log.error("状态码非200, 为:" + statusCode);
                httpPost.abort();
                throw new AppException("状态码" + statusCode, "HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            byte[] result = null;
            if (entity != null) {
                result = EntityUtils.toByteArray(entity);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        }
        catch (AppException e) {
            throw e;
        }
        catch (Exception e) {
            throw new AppException(e);
            // TODO LOG
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                }
        }
    }

    public static String doPostWithRetry( Map<String, String> params, String url, String reqCharset,
            String resCharset ) {
        CloseableHttpClient httpClient = getSingleSSLConnection(config);

        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpPost.addHeader("Connection", "close");
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(
                        new UrlEncodedFormEntity(pairs, reqCharset == null ? DEFAULT_SEND_CHARSET : reqCharset));
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new AppException("", "HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, resCharset == null ? DEFAULT_RES_CHARSET : resCharset);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        }
        catch (AppException e) {
            throw e;
        }
        catch (Exception e) {
            throw new AppException(e);
            // TODO LOG
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                }
        }
    }

    /**
     * HTTP Post 获取内容
     * 
     * @param params
     *            请求的参数
     * @param url
     *            请求的url地址 ?之前的地址
     * @param reqCharset
     *            编码格式
     * @param resCharset
     *            编码格式
     * @return 页面内容
     */
    public static String doPostByCodeAndContentType( Map<String, String> params, String url, String reqCharset,
            String resCharset, String contentType ) {
        CloseableHttpClient httpClient = getSingleSSLConnection(config);
        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            // httpPost.addHeader("User-Agent","Mozilla/4.0 (compatible; MSIE
            // 6.0; Windows NT 5.1)");
            // httpPost.addHeader("Connection" , "close");
            // httpPost.addHeader("Content-Length" , length);
            httpPost.addHeader("Content-Type", contentType);
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(
                        new UrlEncodedFormEntity(pairs, reqCharset == null ? DEFAULT_SEND_CHARSET : reqCharset));
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new AppException("", "HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, resCharset == null ? DEFAULT_RES_CHARSET : resCharset);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        }
        catch (AppException e) {
            log.info("1", e);
            throw e;
        }
        catch (Exception e) {
            log.info("2", e);
            throw new AppException("ER001", e);
            // TODO LOG
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                }
        }
    }

    /**
     * HTTP Post 获取内容
     * 
     * @param params
     *            请求的参数
     * @param url
     *            请求的url地址 ?之前的地址
     * @param reqCharset
     *            编码格式
     * @param resCharset
     *            编码格式
     * @return 页面内容
     */
    public static String doPost( Map<String, String> params, String url, String reqCharset, String resCharset,
            String contentType ) {
        CloseableHttpClient httpClient = getSingleSSLConnection(config);
        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpPost.addHeader("Content-Type", contentType);
            httpPost.addHeader("Connection", "close");
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(
                        new UrlEncodedFormEntity(pairs, reqCharset == null ? DEFAULT_SEND_CHARSET : reqCharset));
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new AppException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, resCharset == null ? DEFAULT_RES_CHARSET : resCharset);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        }
        catch (Exception e) {
            throw new AppException(e);
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                }
        }
    }

    /**
     * HTTP Post 获取内容
     * 
     * @param params
     *            请求的参数
     * @param url
     *            请求的url地址 ?之前的地址
     * @param reqCharset
     *            编码格式
     * @param resCharset
     *            编码格式
     * @return 页面内容
     */
    public static String doDualSSLPost( Map<String, String> params, String url, String reqCharset, String resCharset,
            String keyStorePath, String keyStorePass ) {
        CloseableHttpClient httpClient = getDualSSLConnection(keyStorePath, keyStorePass);
        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpPost.addHeader("Connection", "close");
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(
                        new UrlEncodedFormEntity(pairs, reqCharset == null ? DEFAULT_SEND_CHARSET : reqCharset));
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new AppException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, resCharset == null ? DEFAULT_RES_CHARSET : resCharset);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        }
        catch (Exception e) {
            throw new AppException(e);
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                }
        }
    }

    /**
     * 创建双向ssl的连接
     * 
     * @param keyStorePath
     * @param keyStorePass
     * @return
     * @throws AppException
     */
    private static CloseableHttpClient getDualSSLConnection( String keyStorePath, String keyStorePass )
            throws AppException {
        CloseableHttpClient httpClient = null;
        try {
            File file = new File(keyStorePath);
            URL sslJksUrl = file.toURI().toURL();
            KeyStore keyStore = KeyStore.getInstance("jks");
            InputStream is = null;
            try {
                is = sslJksUrl.openStream();
                keyStore.load(is, keyStorePass != null ? keyStorePass.toCharArray() : null);
            }
            finally {
                if (is != null)
                    is.close();
            }
            SSLContext sslContext = new SSLContextBuilder()
                    .loadKeyMaterial(keyStore, keyStorePass != null ? keyStorePass.toCharArray() : null)
                    .loadTrustMaterial(null, new TrustStrategy() {
                        @Override
                        public boolean isTrusted( X509Certificate[] paramArrayOfX509Certificate, String paramString )
                                throws CertificateException {
                            return true;
                        }
                    }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(config).build();
            return httpClient;
        }
        catch (Exception e) {
            throw new AppException(e);
        }

    }

    /**
     * 创建单向ssl的连接
     * 
     * @return
     * @throws AppException
     */
    private static CloseableHttpClient getSingleSSLConnection( RequestConfig config ) throws AppException {
        CloseableHttpClient httpClient = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted( X509Certificate[] paramArrayOfX509Certificate, String paramString )
                        throws CertificateException {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(config).build();
            return httpClient;
        }
        catch (Exception e) {
            throw new AppException(e);
        }

    }

    private static CloseableHttpClient getCustomizedSingleSSLConn( RequestConfig config ) throws AppException {
        CloseableHttpClient httpClient = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted( X509Certificate[] paramArrayOfX509Certificate, String paramString )
                        throws CertificateException {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    new String[] { "SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2" }, null,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(config).build();
            return httpClient;
        }
        catch (Exception e) {
            throw new AppException(e);
        }

    }

    /**
     * 使用PoolingHttpClientConnectionManager的连接池
     * 
     * @param config
     * @return
     * @throws AppException
     */
    public static CloseableHttpClient getPoolingHttpClient( RequestConfig config, String url ) {

        try {
            URL u = new URL(url);
            String hostname = u.getHost();
            int port = u.getPort();
            if (port == -1) {
                port = "https".equals(u.getProtocol()) ? 443 : 80;
            }

            if (poolHttpClient == null) {
                synchronized (syncLock) {
                    if (poolHttpClient == null) {
                        poolHttpClient = createPoolingConnection(config, 400, 40, 100, hostname, port);
                    }
                }
            }
        }
        catch (MalformedURLException e) {
            throw new AppException("URL地址不正确", e);
        }
        catch (Exception e) {
            throw new AppException("获取poolhttpclient异常", e);
        }

        return poolHttpClient;
    }

    private static CloseableHttpClient createPoolingConnection( RequestConfig config, int maxTotal, int maxPerRoute,
            int maxRoute, String hostname, int port ) throws AppException {
        CloseableHttpClient httpClient = null;
        try {
            ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted( X509Certificate[] paramArrayOfX509Certificate, String paramString )
                        throws CertificateException {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
                    .register("http", plainsf).register("https", sslsf).build();
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
            // 将最大连接数增加
            cm.setMaxTotal(maxTotal);
            // 将每个路由基础的连接增加
            cm.setDefaultMaxPerRoute(maxPerRoute);
            HttpHost httpHost = new HttpHost(hostname, port);
            // 将目标主机的最大连接数增加
            cm.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);

            // 请求重试处理
            HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
                @Override
                public boolean retryRequest( IOException exception, int executionCount, HttpContext context ) {
                    if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                        return false;
                    }
                    if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                        return true;
                    }
                    if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                        return false;
                    }
                    if (exception instanceof InterruptedIOException) {// 超时
                        return false;
                    }
                    if (exception instanceof UnknownHostException) {// 目标服务器不可达
                        return false;
                    }
                    if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                        return false;
                    }
                    if (exception instanceof SSLException) {// SSL握手异常
                        return false;
                    }

                    HttpClientContext clientContext = HttpClientContext.adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    // 如果请求是幂等的，就再次尝试
                    if (!(request instanceof HttpEntityEnclosingRequest)) {
                        return true;
                    }
                    return false;
                }
            };

            httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(config)
                    .setRetryHandler(httpRequestRetryHandler).build();
            return httpClient;
        }
        catch (Exception e) {
            throw new AppException(e);
        }

    }

    /**
     * HTTP Post 获取内容
     * 
     * @param json
     *            请求json串
     * @param url
     *            请求的url地址
     * @param reqCharset
     *            编码格式
     * @param resCharset
     *            编码格式
     * @return 页面内容
     */
    public static String doJSONPost( String json, String url, String reqCharset, String resCharset ) {
        CloseableHttpClient httpClient = getSingleSSLConnection(config);

        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }
        try {
            StringEntity s = new StringEntity(json);
            s.setContentEncoding(reqCharset);
            s.setContentType("application/json");
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpPost.addHeader("Connection", "close");
            httpPost.setEntity(s);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                log.error("状态码非200, 为:" + statusCode);
                httpPost.abort();
                throw new AppException("状态码" + statusCode, "HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, resCharset == null ? DEFAULT_RES_CHARSET : resCharset);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        }
        catch (AppException e) {
            throw e;
        }
        catch (Exception e) {
            throw new AppException(e);
            // TODO LOG
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                }
        }
    }

    public static boolean doPostForBill( String url, String path ) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(60000).build();
        CloseableHttpClient httpClient = getSingleSSLConnection(config);
        CloseableHttpResponse ponse = null;
        if (StrKit.isBlank(url)) {
            return false;
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpPost.addHeader("Connection", "close");

            ponse = httpClient.execute(httpPost);
            int statusCode = ponse.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                log.error("状态码非200, 为:" + statusCode);
                httpPost.abort();
                throw new AppException("状态码" + statusCode, "HttpClient,error status code :" + statusCode);
            }

            InputStream is = ponse.getEntity().getContent();

            OutputStream out = new FileOutputStream(path, true);
            byte[] buffer = new byte[1024];
            int temp = 0;
            while ((temp = is.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, temp);
            }
            is.close();
            is.close();
            out.close();

        }
        catch (AppException e) {
            throw e;
        }
        catch (Exception e) {
            throw new AppException(e);
            // TODO LOG
        }
        finally {
            if (ponse != null)
                try {
                    ponse.close();
                }
                catch (IOException e) {
                }
        }
        return true;
    }

    /**
     * HTTP Post 获取内容
     * 
     * @param params
     *            请求的参数
     * @param url
     *            请求的url地址 ?之前的地址
     * @param reqCharset
     *            编码格式
     * @param resCharset
     *            编码格式
     * @return 页面内容
     */
    public static String doPostWithObject( Map<String, Object> params, String url, String reqCharset,
            String resCharset ) {
        CloseableHttpClient httpClient = getSingleSSLConnection(config);

        CloseableHttpResponse response = null;
        if (StrKit.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String value = entry.getValue().toString();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpPost.addHeader("Connection", "close");
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(
                        new UrlEncodedFormEntity(pairs, reqCharset == null ? DEFAULT_SEND_CHARSET : reqCharset));
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                log.error("状态码非200, 为:" + statusCode);
                httpPost.abort();
                throw new AppException("状态码" + statusCode, "HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, resCharset == null ? DEFAULT_RES_CHARSET : resCharset);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        }
        catch (AppException e) {
            throw e;
        }
        catch (Exception e) {
            throw new AppException(e);
            // TODO LOG
        }
        finally {
            if (response != null)
                try {
                    response.close();
                }
                catch (IOException e) {
                }
        }
    }

}
