package com.seshutechie.taxii2lib.stix.model;

import java.util.List;

public class StixCollectionResp {
    List<StixCollection> collections;

    public List<StixCollection> getCollections() {
        return collections;
    }

    public void setCollections(List<StixCollection> collections) {
        this.collections = collections;
    }
}
