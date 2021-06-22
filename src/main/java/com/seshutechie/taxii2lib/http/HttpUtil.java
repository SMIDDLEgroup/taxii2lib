package com.seshutechie.taxii2lib.http;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.stix.StixConstants;
import com.seshutechie.taxii2lib.util.EncodeUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpUtil {

    private static final String AUTH_BASIC = "Basic ";
    private static final String URL_SEPARATOR = "/";

    public static String doDiscovery(String url, String user, String password) throws TaxiiAppException {
        HttpGet httpGet = new HttpGet(url);
        updateCommonHeaders(httpGet, user, password);
        return getData(httpGet);
    }

    public static void updateCommonHeaders(HttpGet httpGet, String user, String password) {
        httpGet.setHeader(HttpHeaders.ACCEPT, StixConstants.HTTP_ACCEPT_VALUE);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, getAuthorizationString(user, password));
        httpGet.setHeader(StixConstants.HTTP_HEADER_VERSION, StixConstants.SITX_VERSION);
    }

    private static String getAuthorizationString(String user, String password) {
        return AUTH_BASIC + EncodeUtil.base64Encode(user + ":" + password);
    }

    public static String getCollections(String apiRoot, String user, String password) throws TaxiiAppException {
        HttpGet httpGet = new HttpGet(apiRoot + StixConstants.SUFFIX_COLLECTIONS);
        updateCommonHeaders(httpGet, user, password);
        return getData(httpGet);
    }

    public static String getCollectionDetails(String apiRoot, String collectionId, String user, String password) throws TaxiiAppException {
        HttpGet httpGet = new HttpGet(apiRoot + StixConstants.SUFFIX_COLLECTIONS + URL_SEPARATOR + collectionId);
        updateCommonHeaders(httpGet, user, password);
        return getData(httpGet);
    }

    private static String getData(HttpGet httpGet) throws TaxiiAppException {
        String result = null;

        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
            httpclient.close();
        } catch (IOException e) {
            throw new TaxiiAppException(e);
        }

        return result;
    }
}
