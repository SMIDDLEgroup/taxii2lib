package com.seshutechie.taxii2lib.stix.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StixObjectResult {
    private String id;
    private List<StixObject> objects;
    @SerializedName("spec_version")
    private String specVersion;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<StixObject> getObjects() {
        return objects;
    }

    public void setObjects(List<StixObject> objects) {
        this.objects = objects;
    }

    public String getSpecVersion() {
        return specVersion;
    }

    public void setSpecVersion(String specVersion) {
        this.specVersion = specVersion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
