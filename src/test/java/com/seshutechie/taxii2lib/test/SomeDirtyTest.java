package com.seshutechie.taxii2lib.test;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.TaxiiLib;
import com.seshutechie.taxii2lib.http.HttpUtil;
import com.seshutechie.taxii2lib.stix.model.StixCollection;
import com.seshutechie.taxii2lib.stix.model.StixDiscovery;
import com.seshutechie.taxii2lib.util.JsonUtil;

import java.util.List;

public class SomeDirtyTest {
    public static void main(String[] args) throws Exception {
//        testHttpDiscovery();
//        testTaxiiDiscovery();
        testGetCollections();
    }

    private static void testGetCollections() throws TaxiiAppException {
        TaxiiLib taxiiLib = new TaxiiLib();
        taxiiLib.setBasicAuthorization("guest", "guest");
        List<StixCollection> collections = taxiiLib.getCollectionsObject("https://cti-taxii.mitre.org/stix/");
        System.out.println(JsonUtil.objectToJson(collections));
//        System.out.println(JsonUtil.prettyJson(JsonUtil.objectToJson(collections)));
    }

    private static void testTaxiiDiscovery() throws TaxiiAppException {
        TaxiiLib taxiiLib = new TaxiiLib();
        taxiiLib.setBasicAuthorization("guest", "guest");
        StixDiscovery discoveryObject = taxiiLib.getDiscoveryObject("https://cti-taxii.mitre.org/taxii");
        System.out.println(JsonUtil.objectToJson(discoveryObject));
    }

    private static void testHttpDiscovery() throws TaxiiAppException {
        String response = HttpUtil.doDiscovery("https://cti-taxii.mitre.org/taxii", "guest", "guest");
        System.out.println(JsonUtil.prettyJson(response));
    }
}
