package com.seshutechie.taxii2lib.test;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.TaxiiLib;
import com.seshutechie.taxii2lib.http.HttpUtil;
import com.seshutechie.taxii2lib.stix.model.StixCollection;
import com.seshutechie.taxii2lib.stix.model.StixDiscovery;
import com.seshutechie.taxii2lib.stix.model.StixManifestResult;
import com.seshutechie.taxii2lib.stix.model.StixObjectResult;
import com.seshutechie.taxii2lib.util.JsonUtil;

import java.util.List;

public class SomeDirtyTest {
    public static void main(String[] args) throws Exception {
        testHttpDiscovery();
//        testTaxiiDiscovery();
//        testGetCollections();
//        testCollectionDetails();
//        testObjects();
//        testObjectById();
//        testManifest();
    }

    private static void testManifest() throws TaxiiAppException {
        TaxiiLib taxiiLib = new TaxiiLib();
        taxiiLib.setBasicAuthorization("guest", "guest");
        StixManifestResult manifest = taxiiLib.getManifestAsObject("https://limo.anomali.com/api/v1/taxii2/feeds/", "68", 0, 10);
        String json = JsonUtil.objectToJson(manifest);
        System.out.println(JsonUtil.prettyJson(json));

        taxiiLib.setPageSize(2);
        manifest = taxiiLib.getNextManifestAsObject();
        json = JsonUtil.objectToJson(manifest);
        System.out.println(JsonUtil.prettyJson(json));
    }

    private static void testObjectById() throws TaxiiAppException {
        TaxiiLib taxiiLib = new TaxiiLib();
        taxiiLib.setBasicAuthorization("guest", "guest");
        StixObjectResult objects = taxiiLib.getObjectByIdAsObject("https://limo.anomali.com/api/v1/taxii2/feeds",
                "68", "relationship--21842707-0f15-43bf-bc42-2bceadf2cfa2");
        String json = JsonUtil.objectToJson(objects);
        System.out.println(JsonUtil.prettyJson(json));
    }

    private static void testObjects() throws TaxiiAppException {
        TaxiiLib taxiiLib = new TaxiiLib();
        taxiiLib.setBasicAuthorization("guest", "guest");
        StixObjectResult objects = taxiiLib.getObjectsAsObjects("https://cti-taxii.mitre.org/stix/", "2f669986-b40b-4423-b720-4396ca6a462b");
        String json = JsonUtil.objectToJson(objects);
        System.out.println(JsonUtil.prettyJson(json));
    }

    private static void testCollectionDetails() throws TaxiiAppException {
        TaxiiLib taxiiLib = new TaxiiLib();
        taxiiLib.setBasicAuthorization("guest", "guest");
        StixCollection collection = taxiiLib.getCollectionDetailsAsObject("https://cti-taxii.mitre.org/stix/", "2f669986-b40b-4423-b720-4396ca6a462b");
        String json = JsonUtil.objectToJson(collection);
        System.out.println(json);
        System.out.println(JsonUtil.prettyJson(json));
    }

    private static void testGetCollections() throws TaxiiAppException {
        TaxiiLib taxiiLib = new TaxiiLib();
        taxiiLib.setBasicAuthorization("guest", "guest");
        List<StixCollection> collections = taxiiLib.getCollectionsAsObject("https://cti-taxii.mitre.org/stix/");
        System.out.println(JsonUtil.objectToJson(collections));
//        System.out.println(JsonUtil.prettyJson(JsonUtil.objectToJson(collections)));
    }

    private static void testTaxiiDiscovery() throws TaxiiAppException {
        TaxiiLib taxiiLib = new TaxiiLib();
        taxiiLib.setBasicAuthorization("guest", "guest");
        StixDiscovery discoveryObject = taxiiLib.getDiscoveryAsObject("https://cti-taxii.mitre.org/taxii");
        System.out.println(JsonUtil.objectToJson(discoveryObject));
    }

    private static void testHttpDiscovery() throws TaxiiAppException {
        String response = HttpUtil.doDiscovery("https://cti-taxii.mitre.org/taxii", "guest", "guest");
        System.out.println(JsonUtil.prettyJson(response));
    }
}
