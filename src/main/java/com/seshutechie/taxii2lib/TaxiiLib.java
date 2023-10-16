package com.seshutechie.taxii2lib;

import com.seshutechie.taxii2lib.http.HttpUtil;
import com.seshutechie.taxii2lib.stix.model.ItemRange;
import com.seshutechie.taxii2lib.stix.model.StixCollection;
import com.seshutechie.taxii2lib.stix.model.StixCollectionResult;
import com.seshutechie.taxii2lib.stix.model.StixDiscovery;
import com.seshutechie.taxii2lib.stix.model.StixManifestResult;
import com.seshutechie.taxii2lib.stix.model.StixObjectResult;
import com.seshutechie.taxii2lib.util.JsonUtil;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main library class to access TAXII API.
 */
public class TaxiiLib {
    private static final String ADDED_AFTER_PARAM = "added_after";
    private static final String TYPE_PARAM = "match[type]";
    private static final int DEFAULT_PAGE_SIZE = 1000;
    private final Map<String, String> params = new HashMap<>();
    private String user;
    private String password;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private String apiRoot;
    private String collectionId;
    private ItemRange lastPage;

    /**
     * Sets basic authorization for all TAXII API
     *
     * @param user     user
     * @param password password
     */
    public void setBasicAuthorization(String user, String password) {
        this.user = user;
        this.password = password;
    }

    /**
     * Sets date after what should be fetched collections objects. Removes addedAfter from query if null is passed
     *
     * @param addedAfter epoch of local date
     */
    public void setAddedAfter(Long addedAfter) {
        if (addedAfter != null) {
            String formattedUtcTime = Instant.ofEpochMilli(addedAfter).toString();
            params.put(ADDED_AFTER_PARAM, formattedUtcTime);
        } else {
            params.remove(ADDED_AFTER_PARAM);
        }
    }

    /**
     * Sets date after what should be fetched collections objects. Removes types from query if null is passed
     *
     * @param types list of collections object types, separated with coma, that will be returned with getObjects and
     *              getObjectsAsObjects methods.
     */
    public void setObjectTypes(String types) {
        if (types != null) {
            params.put(TYPE_PARAM, types);
        } else {
            params.remove(TYPE_PARAM);
        }
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
    public StixDiscovery getDiscoveryAsObject(String discoveryRoot) throws TaxiiAppException {
        String discovery = getDiscovery(discoveryRoot);
        return JsonUtil.jsonToObject(discovery, StixDiscovery.class);
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
    public List<StixCollection> getCollectionsAsObject(String apiRoot) throws TaxiiAppException {
        String collections = getCollections(apiRoot);
        StixCollectionResult collectionResult = JsonUtil.jsonToObject(collections, StixCollectionResult.class);
        return collectionResult.getCollections();
    }

    /**
     * Get the collection details from a given STIX API root, and return as a raw string
     *
     * @param apiRoot      API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
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
     * @param apiRoot      API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
     * @param collectionId collection id
     * @return a raw string of collection details
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public StixCollection getCollectionDetailsAsObject(String apiRoot, String collectionId) throws TaxiiAppException {
        String collection = HttpUtil.getCollectionDetails(apiRoot, collectionId, user, password);
        return JsonUtil.jsonToObject(collection, StixCollection.class);
    }

    /**
     * Get the STIX objects using API root and connection id. It return as a raw string.
     * It always get the first page of the objects list.
     *
     * @param apiRoot      API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
     * @param collectionId collection id
     * @return a raw json string of STIX objects
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public String getObjects(String apiRoot, String collectionId) throws TaxiiAppException {
        return getObjects(apiRoot, collectionId, -1, -1);
    }

    /**
     * Get the STIX objects using API root and connection id. It return as a raw string.
     * It always get the first page of the objects list.
     *
     * @param apiRoot      API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
     * @param collectionId collection id
     * @param from         starting index of the object. Whole list starts from 0 index.
     *                     If from is -1 then it does not use range to fetch it is same as getObjects method with out from and pageSize.
     * @param pageSize     number of objects to fetch. if pageSize is -1 then page size is picked from prior call of setPageSize or default size.
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
        String objects = HttpUtil.getObjects(apiRoot, collectionId, params, user, password, itemRange);
        this.lastPage = itemRange;
        return objects;
    }

    /**
     * Get the STIX objects using API root and connection id. It return as a raw string.
     * It always get the first page of the objects list.
     *
     * @param apiRoot      API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
     * @param collectionId collection id
     * @return STIX objects wrapped in {@link com.seshutechie.taxii2lib.stix.model.StixObjectResult StixObjectResults}
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public StixObjectResult getObjectsAsObjects(String apiRoot, String collectionId) throws TaxiiAppException {
        String objects = getObjects(apiRoot, collectionId);
        return JsonUtil.jsonToObject(objects, StixObjectResult.class);
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
        if (lastPage != null) {
            if (lastPage.getTo() < lastPage.getTotal()) {
                int size = pageSize < 0 ? this.pageSize : pageSize;
                int to = lastPage.getTo() + size;
                if (to >= lastPage.getTotal()) {
                    to = lastPage.getTotal() - 1;
                }
                ItemRange itemRange = new ItemRange(lastPage.getTo() + 1, to);
                objects = HttpUtil.getObjects(apiRoot, collectionId, params, user, password, itemRange);
                this.lastPage = itemRange;
            }
        } else {
            throw new TaxiiAppException("No previous page found. You might need to call getObjects first.");
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
        String objects = getNextObjects();
        return JsonUtil.jsonToObject(objects, StixObjectResult.class);
    }

    /**
     * Get the STIX object details using given API root, connection id and object id, and return as a raw string.
     *
     * @param apiRoot      API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
     * @param collectionId collection id
     * @param objectId     object id
     * @return a raw json string of STIX objects
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public String getObjectById(String apiRoot, String collectionId, String objectId) throws TaxiiAppException {
        return HttpUtil.getObject(apiRoot, collectionId, objectId, user, password);
    }

    /**
     * Get the STIX object details using given API root, connection id and object id, and return as a raw string.
     *
     * @param apiRoot      API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
     * @param collectionId collection id
     * @param objectId     object id
     * @return a raw json string of STIX objects
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public StixObjectResult getObjectByIdAsObject(String apiRoot, String collectionId, String objectId) throws TaxiiAppException {
        String object = getObjectById(apiRoot, collectionId, objectId);
        return JsonUtil.jsonToObject(object, StixObjectResult.class);
    }

    /**
     * Get the STIX collection manifest using API root and connection id. It return as a raw string.
     * It always get the first page of the objects list.
     *
     * @param apiRoot      API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
     * @param collectionId collection id
     * @param from         starting index of the object. Whole list starts from 0 index.
     *                     If from is -1 then it does not use range to fetch it is same as getObjects method with out from and pageSize.
     * @param pageSize     number of objects to fetch. if pageSize is -1 then page size is picked from prior call of setPageSize or default size.
     * @return a raw json string of collection manifest
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public String getManifest(String apiRoot, String collectionId, int from, int pageSize) throws TaxiiAppException {
        this.apiRoot = apiRoot;
        this.collectionId = collectionId;
        this.lastPage = null;
        int size = pageSize < 0 ? this.pageSize : pageSize;
        int to = (from >= 0) ? from + size - 1 : -1;
        ItemRange itemRange = new ItemRange(from, to);
        String manifest = HttpUtil.getManifest(apiRoot, collectionId, user, password, itemRange);
        this.lastPage = itemRange;
        return manifest;
    }

    /**
     * Get the STIX collection manifest using API root and connection id. It return as an object.
     * It always get the first page of the objects list.
     *
     * @param apiRoot      API Root URL to get collections list (ex: https://cti-taxii.mitre.org/stix/)
     * @param collectionId collection id
     * @param from         starting index of the object. Whole list starts from 0 index.
     *                     If from is -1 then it does not use range to fetch it is same as getObjects method with out from and pageSize.
     * @param pageSize     number of objects to fetch. if pageSize is -1 then page size is picked from prior call of setPageSize or default size.
     * @return StixManifestResult object
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public StixManifestResult getManifestAsObject(String apiRoot, String collectionId, int from, int pageSize) throws TaxiiAppException {
        String manifest = getManifest(apiRoot, collectionId, from, pageSize);
        return JsonUtil.jsonToObject(manifest, StixManifestResult.class);
    }

    /**
     * Gets next page of manifest, if available.
     * It should be called only after initial {@link #getManifest(String, String, int, int) getManifest}/{@link #getManifestAsObject(String, String, int, int) getManifestAsObject}
     * method been called or {@link #getNextManifest(int) getNextManifest}/{@link } been called.
     *
     * @param pageSize number of required objects. If this is zero then it uses earlier set pageSize or default size.
     * @return a raw json string of STIX manifest
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public String getNextManifest(int pageSize) throws TaxiiAppException {
        String objects = null;
        if (lastPage != null) {
            if (lastPage.getTo() < lastPage.getTotal() - 1) {
                int size = pageSize < 0 ? this.pageSize : pageSize;
                int to = lastPage.getTo() + size;
                if (to >= lastPage.getTotal()) {
                    to = lastPage.getTotal() - 1;
                }
                ItemRange itemRange = new ItemRange(lastPage.getTo() + 1, to);
                objects = HttpUtil.getManifest(apiRoot, collectionId, user, password, itemRange);
                this.lastPage = itemRange;
            }
        } else {
            throw new TaxiiAppException("No previous page details found. You might need to call getManifest before this");
        }
        return objects;
    }

    /**
     * Gets next page of manifest, if available.
     * It should be called only after initial {@link #getManifest(String, String, int, int) getManifest}/{@link #getManifestAsObject(String, String, int, int) getManifestAsObject}
     * method been called or {@link #getNextManifest(int) getNextManifest}/{@link #getNextManifestAsObject() getNextManifestAsObject} been called.
     *
     * @return a raw json string of STIX manifest
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public String getNextManifest() throws TaxiiAppException {
        return getNextManifest(-1);
    }

    /**
     * Gets next page of manifest, if available.
     * It should be called only after initial {@link #getManifest(String, String, int, int) getManifest}/{@link #getManifestAsObject(String, String, int, int) getManifestAsObject}
     * method been called or {@link #getNextManifest(int) getNextManifest}/{@link #getNextManifestAsObject() getNextManifestAsObject} been called.
     *
     * @return StixManifestResult object
     * @throws TaxiiAppException error condition is raised as TaxiiAppException
     */
    public StixManifestResult getNextManifestAsObject() throws TaxiiAppException {
        String manifest = getNextManifest();
        return JsonUtil.jsonToObject(manifest, StixManifestResult.class);
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
        if (lastPage != null) {
            itemRange = new ItemRange(lastPage.getFrom(), lastPage.getTo(), lastPage.getTotal());
        }
        return itemRange;
    }

    /**
     * Gives boolean value hasNextPage
     *
     * @return boolean value that represents presence of next page.
     */
    public boolean hasNextPage() {
        return lastPage.getTo() != 0 && lastPage.getTo() + 1 != lastPage.getTotal();
    }
}
