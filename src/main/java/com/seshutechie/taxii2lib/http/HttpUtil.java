package com.seshutechie.taxii2lib.http;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.stix.StixConstants;
import com.seshutechie.taxii2lib.stix.model.ItemRange;
import com.seshutechie.taxii2lib.util.EncodeUtil;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    private static final String AUTH_BASIC = "Basic ";
    private static final String URL_SEPARATOR = "/";
    private static final String ITEMS = "items ";

    public static String doDiscovery(String url, String user, String password) throws TaxiiAppException {
        HttpGet httpGet = new HttpGet(url);
        updateCommonHeaders(httpGet, StixConstants.HTTP_ACCEPT_TAXII, user, password);
        return getData(httpGet);
    }

    public static void updateCommonHeaders(HttpGet httpGet, String accept, String user, String password) {
        httpGet.setHeader(HttpHeaders.ACCEPT, accept);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, getAuthorizationString(user, password));
        httpGet.setHeader(StixConstants.HTTP_HEADER_VERSION, StixConstants.STIX_VERSION);
    }

    public static void updateRangeHeaders(HttpGet httpGet, int from, int to) {
        if (from >= 0 && to >= from) {
            httpGet.setHeader(HttpHeaders.RANGE, getRangeString(from, to));
        }
    }

    private static String getRangeString(int from, int to) {
        return ITEMS + from + "-" + to;
    }

    private static String getAuthorizationString(String user, String password) {
        return AUTH_BASIC + EncodeUtil.base64Encode(user + ":" + password);
    }

    public static String getCollections(String apiRoot, String user, String password) throws TaxiiAppException {
        HttpGet httpGet = new HttpGet(apiRoot + StixConstants.SUFFIX_COLLECTIONS);
        updateCommonHeaders(httpGet, StixConstants.HTTP_ACCEPT_TAXII, user, password);
        return getData(httpGet);
    }

    public static String getCollectionDetails(String apiRoot, String collectionId, String user, String password)
            throws TaxiiAppException {
        HttpGet httpGet = new HttpGet(apiRoot + StixConstants.SUFFIX_COLLECTIONS + URL_SEPARATOR + collectionId);
        updateCommonHeaders(httpGet, StixConstants.HTTP_ACCEPT_TAXII, user, password);
        return getData(httpGet);
    }

    public static String getObjects(String apiRoot, String collectionId, Map<String, String> params,
                                    String user, String password, ItemRange itemRange) throws TaxiiAppException {
        HttpGet httpGet = new HttpGet(getObjectsUrl(apiRoot, collectionId, params));
        updateCommonHeaders(httpGet, StixConstants.HTTP_ACCEPT_STIX, user, password);
        if (itemRange != null) {
            updateRangeHeaders(httpGet, itemRange.getFrom(), itemRange.getTo());
        }
        Map<String, String> responseHeaderMap = prepareObjectsResponseHeaderMap();
        String body = getData(httpGet, responseHeaderMap);
        if (itemRange != null) {
            String value = responseHeaderMap.get(HttpHeaders.CONTENT_RANGE);
            updateItemRange(itemRange, value);
        }
        return body;
    }

    private static String getObjectsUrl(String apiRoot, String collectionId, Map<String, String> params) throws TaxiiAppException {
        try {
            String url = apiRoot + StixConstants.SUFFIX_COLLECTIONS + URL_SEPARATOR + collectionId + StixConstants.SUFFIX_OBJECTS;
            URIBuilder builder = new URIBuilder(url);
            if (!params.isEmpty()) {
                params.forEach(builder::addParameter);
                url = builder.build().toString();
            }
            return url;
        } catch (URISyntaxException e) {
            throw new TaxiiAppException(e);
        }
    }

    public static String getObject(String apiRoot, String collectionId, String objectId, String user, String password)
            throws TaxiiAppException {
        HttpGet httpGet = new HttpGet(apiRoot + StixConstants.SUFFIX_COLLECTIONS + URL_SEPARATOR + collectionId +
                StixConstants.SUFFIX_OBJECTS + URL_SEPARATOR + objectId);
        updateCommonHeaders(httpGet, StixConstants.HTTP_ACCEPT_STIX, user, password);
        return getData(httpGet);
    }

    public static String getManifest(String apiRoot, String collectionId, String user, String password, ItemRange itemRange)
            throws TaxiiAppException {
        HttpGet httpGet = new HttpGet(apiRoot + StixConstants.SUFFIX_COLLECTIONS + URL_SEPARATOR + collectionId +
                StixConstants.SUFFIX_MANIFEST);
        updateCommonHeaders(httpGet, StixConstants.HTTP_ACCEPT_STIX, user, password);
        if (itemRange != null) {
            updateRangeHeaders(httpGet, itemRange.getFrom(), itemRange.getTo());
        }
        Map<String, String> responseHeaderMap = prepareObjectsResponseHeaderMap();
        String body = getData(httpGet, responseHeaderMap);
        if (itemRange != null) {
            String value = responseHeaderMap.get(HttpHeaders.CONTENT_RANGE);
            updateItemRange(itemRange, value);
        }
        return body;
    }

    private static void updateItemRange(ItemRange itemRange, String value) {
        if (value != null) {//Ex: items 0-0/11645
            String range = value.substring(ITEMS.length()).replace('/', '-');
            String[] values = range.split("-");
            if (values.length > 0) {
                itemRange.setFrom(Integer.parseInt(values[0]));
            }
            if (values.length > 1) {
                itemRange.setTo(Integer.parseInt(values[1]));
            }
            if (values.length > 2) {
                itemRange.setTotal(Integer.parseInt(values[2]));
            }
        }
    }

    private static Map<String, String> prepareObjectsResponseHeaderMap() {
        Map<String, String> map = new HashMap<>();
        map.put(HttpHeaders.CONTENT_RANGE, null);
        return map;
    }

    private static String getData(HttpGet httpGet) throws TaxiiAppException {
        return getData(httpGet, null);
    }

    private static String getData(HttpGet httpGet, Map<String, String> responseHeaders) throws TaxiiAppException {
        String result = null;

        try {
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setCookieSpec(CookieSpecs.STANDARD)
                            .build())
                    .build();
            CloseableHttpResponse response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= HttpStatus.SC_MULTIPLE_CHOICES) { //Any error code >= 300
                throw new TaxiiAppException(response.getStatusLine().toString());
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
            if (responseHeaders != null) {
                for (String headerName : responseHeaders.keySet()) {
                    Header header = response.getFirstHeader(headerName);
                    if (header != null) {
                        responseHeaders.put(headerName, header.getValue());
                    }
                }
            }
            httpclient.close();
        } catch (IOException e) {
            throw new TaxiiAppException(e);
        }

        return result;
    }

    /*
     * Used for dirty testing
     */
    public static void main(String[] args) {
        ItemRange itemRange = new ItemRange(-1, -1);
        HttpUtil.updateItemRange(itemRange, "items 0-0/1234");
        System.out.printf("%d to %d of %d\n", itemRange.getFrom(), itemRange.getTo(), itemRange.getTotal());
    }
}
