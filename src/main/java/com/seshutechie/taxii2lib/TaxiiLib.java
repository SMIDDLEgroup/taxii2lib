package com.seshutechie.taxii2lib;

import com.seshutechie.taxii2lib.http.HttpUtil;
import com.seshutechie.taxii2lib.stix.model.StixDiscovery;
import com.seshutechie.taxii2lib.util.JsonUtil;

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
     */
    public String getDiscovery(String discoveryRoot) throws TaxiiAppException {
        return HttpUtil.doDiscovery(discoveryRoot, user, password);
    }

    /**
     * Does the discovery on given url and returns discovery details as an Object
     *
     * @param discoveryRoot URL to get discovery details from (ex: https://cti-taxii.mitre.org/taxii)
     * @return STIX discovery details
     */
    public StixDiscovery getDiscoveryObject(String discoveryRoot) throws TaxiiAppException {
        String discovery = getDiscovery(discoveryRoot);
        return (StixDiscovery) JsonUtil.jsonToObject(discovery, StixDiscovery.class);
    }
}
