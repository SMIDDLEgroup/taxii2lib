package com.seshutechie.taxii2lib;

import com.seshutechie.taxii2lib.http.HttpUtil;
import com.seshutechie.taxii2lib.stix.model.*;
import com.seshutechie.taxii2lib.util.JsonUtil;

import java.util.List;

/**
 * Main library class to access TAXII API.
 *
 */
public class TaxiiLib {
    private static int DEFAULT_PAGE_SIZE = 1000;
    private String user;
    private String password;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private String apiRoot;
    private String collectionId;
    private ItemRange lastPage;

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
     * Get the list of collections from a given STIX API root, and return as a raw string
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
     * Get the list of collections from a given STIX API root, and return as a list of objects
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

    /**
     * Get the collection details from a given STIX API root, and return as a raw string
     *
     * @param apiRoot API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
     * @param collectionId collection id
     * @return a raw string of collection details
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public String getCollectionDetails(String apiRoot, String collectionId) throws TaxiiAppException {
        return HttpUtil.getCollectionDetails(apiRoot, collectionId, user, password);
    }

    /**
     * Get the collection details from a given STIX API root, and return as an object
     *
     * @param apiRoot API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
     * @param collectionId collection id
     * @return a raw string of collection details
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public StixCollection getCollectionDetailsObject(String apiRoot, String collectionId) throws TaxiiAppException {
        String collection = HttpUtil.getCollectionDetails(apiRoot, collectionId, user, password);
        return (StixCollection) JsonUtil.jsonToObject(collection, StixCollection.class);
    }

    /**
     * Get the STIX objects from a given API root, and return as a raw string.
     * It always get the first page of the objects list.
     *
     * @param apiRoot API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
     * @param collectionId collection id
     * @return a raw json string of STIX objects
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public String getObjects(String apiRoot, String collectionId) throws TaxiiAppException {
        return getObjects(apiRoot, collectionId, -1, -1);
    }

    /**
     * Get the STIX objects from a given API root, and return as a raw string.
     * It always get the first page of the objects list.
     *
     * @param apiRoot API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
     * @param collectionId collection id
     * @param from starting index of the object. Whole list starts from 0 index.
     *             If from is -1 then it does not use range to fetch it is same as getObjects method with out from and pageSize.
     * @param pageSize number of objects to fetch. if pageSize is -1 then page size is picked from prior call of setPageSize or default size.
     * @return a raw json string of STIX objects
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public String getObjects(String apiRoot, String collectionId, int from, int pageSize) throws TaxiiAppException {
        this.apiRoot = apiRoot;
        this.collectionId = collectionId;
        this.lastPage = null;
        int size = pageSize < 0 ? this.pageSize : pageSize;
        int to = (from >= 0) ? from + size - 1 : -1;
        ItemRange itemRange = new ItemRange(from, to);
        String objects = HttpUtil.getObjects(apiRoot, collectionId, user, password, itemRange);
        this.lastPage = itemRange;
        return objects;
    }

    /**
     * Get the STIX objects from a given API root, and return as a raw string.
     * It always get the first page of the objects list.
     *
     * @param apiRoot API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
     * @param collectionId collection id
     * @return STIX objects wrapped in {@link com.seshutechie.taxii2lib.stix.model.StixObjectResult StixObjectResults}
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public StixObjectResult getObjectsAsObjects(String apiRoot, String collectionId) throws TaxiiAppException {
        String collection = getObjects(apiRoot, collectionId);
        return (StixObjectResult) JsonUtil.jsonToObject(collection, StixObjectResult.class);
    }

    /**
     * Gets next page of objects, if available.
     * It should be called only after initial {@link #getObjects(String, String) getObject} method been called or getNextObjects been called.
     *
     * @param pageSize number of required objects. If this is zero then it uses earlier set pageSize or default size.
     * @return a raw json string of STIX objects
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public String getNextObjects(int pageSize) throws TaxiiAppException {
        String objects = null;
        if(lastPage != null) {
            if(lastPage.getTo() < lastPage.getTotal()) {
                int size = pageSize < 0 ? this.pageSize : pageSize;
                int to = lastPage.getTo() + size - 1;
                if (to >= lastPage.getTotal()) {
                    to = lastPage.getTotal() - 1;
                }
                ItemRange itemRange = new ItemRange(lastPage.getTo() + 1, to);
                objects = HttpUtil.getObjects(apiRoot, collectionId, user, password, itemRange);
                this.lastPage = itemRange;
            }
        } else {
            throw new TaxiiAppException("No previous call found for getObjects. You might");
        }
        return objects;
    }

    /**
     * Gets next page of objects, if available.
     * It should be called only after initial {@link #getObjects(String, String) getObject} method been called or getNextObjects been called.
     *
     * @return a raw json string of STIX objects
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public String getNextObjects() throws TaxiiAppException {
        return getNextObjects(-1);
    }

    /**
     * Gets next page of objects, if available.
     * It should be called only after initial {@link #getObjectsAsObjects(String, String)}  getObject/getObjectsAsObjects} method been called or
     * {@link #getNextObjectsAsObject() getNextObjects/getNextObjectsAsObject}  been called.
     *
     * @return STIX objects wrapped in {@link com.seshutechie.taxii2lib.stix.model.StixObjectResult StixObjectResults}
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public StixObjectResult getNextObjectsAsObject() throws TaxiiAppException {
        String collection = getNextObjects();
        return (StixObjectResult) JsonUtil.jsonToObject(collection, StixObjectResult.class);
    }

    /**
     * Get page size for retrieving objects. Default page size is 1000.
     *
     * @return page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Set page size for retrieving objects. Default page size is 1000.
     * If page size set to -1 then it uses default page size.
     *
     * @param pageSize number of object to be retrieved on each request.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gives last page status after getObjects or nextObjects been called.
     *
     * @return ItemRange object gives from, to and total objects from last fetch of objects. Returns null if no prior call objects.
     */
    public ItemRange getLastPage() {
        ItemRange itemRange = null;
        if(lastPage != null) {
            itemRange = new ItemRange(lastPage.getFrom(), lastPage.getTo(), lastPage.getTotal());
        }
        return itemRange;
    }
}
