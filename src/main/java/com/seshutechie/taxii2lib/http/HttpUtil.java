package com.seshutechie.taxii2lib.http;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.stix.StixConstants;
import com.seshutechie.taxii2lib.util.EncodeUtil;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpUtil {

    private static final String AUTH_BASIC = "Basic ";

    public static String doDiscovery(String url, String user, String password) throws TaxiiAppException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        updateDiscoveryHeaders(httpGet, user, password);
        String result = null;

        try {
            CloseableHttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            throw new TaxiiAppException(e);
        }
        return result;
    }

    public static void updateDiscoveryHeaders(HttpGet httpGet, String user, String password) {
        httpGet.setHeader(HttpHeaders.ACCEPT, StixConstants.HTTP_ACCEPT_VALUE);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, getAuthorizationString(user, password));
        httpGet.setHeader(StixConstants.HTTP_HEADER_VERSION, StixConstants.SITX_VERSION);
    }

    private static String getAuthorizationString(String user, String password) {
        return AUTH_BASIC + EncodeUtil.base64Encode(user + ":" + password);
    }
}
