package com.seshutechie.taxii2lib;

import com.seshutechie.taxii2lib.http.HttpUtil;
import com.seshutechie.taxii2lib.stix.model.StixCollection;
import com.seshutechie.taxii2lib.stix.model.StixCollectionResp;
import com.seshutechie.taxii2lib.stix.model.StixDiscovery;
import com.seshutechie.taxii2lib.util.JsonUtil;

import java.util.List;

/**
 * Main library class to access TAXII API.
 *
 */
public class TaxiiLib {
    private String user;
    private String password;

    /**
     * Sets basic authorization for all TAXII API
     * @param user user
     * @param password password
     */
    public void setBasicAuthorization(String user, String password) {
        this.user = user;
        this.password = password;
    }

    /**
     * Does the discovery on given url and returns the raw response
     *
     * @param discoveryRoot URL to get discovery details from (ex: https://cti-taxii.mitre.org/taxii)
     * @return STIX discovery details as String
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public String getDiscovery(String discoveryRoot) throws TaxiiAppException {
        return HttpUtil.doDiscovery(discoveryRoot, user, password);
    }

    /**
     * Does the discovery on given url and returns discovery details as an Object
     *
     * @param discoveryRoot URL to get discovery details from (ex: https://cti-taxii.mitre.org/taxii)
     * @return STIX discovery details
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public StixDiscovery getDiscoveryObject(String discoveryRoot) throws TaxiiAppException {
        String discovery = getDiscovery(discoveryRoot);
        return (StixDiscovery) JsonUtil.jsonToObject(discovery, StixDiscovery.class);
    }

    /**
     * Find the list of collections from a given STIX API root, and return as a raw string
     * You can use discovery and pick default api_root from the response.
     *
     * @param apiRoot API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
     * @return a raw string of collection objects
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public String getCollections(String apiRoot) throws TaxiiAppException {
        return HttpUtil.getCollections(apiRoot, user, password);
    }

    /**
     * Find the list of collections from a given STIX API root, and return as a list of objects
     * You can use discovery and pick default api_root from the response.
     *
     * @param apiRoot API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
     * @return a list of collection objects
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public List<StixCollection> getCollectionsObject(String apiRoot) throws TaxiiAppException {
        String collections = getCollections(apiRoot);
        StixCollectionResp collectionResp = (StixCollectionResp) JsonUtil.jsonToObject(collections, StixCollectionResp.class);
        return collectionResp.getCollections();
    }
}
