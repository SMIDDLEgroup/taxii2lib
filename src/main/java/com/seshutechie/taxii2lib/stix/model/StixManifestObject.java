package com.seshutechie.taxii2lib.stix.model;

import com.google.gson.annotations.SerializedName;

public class StixManifestObject {
    @SerializedName("date_added")
    private String date_added;
    private String id;
    @SerializedName("media_types")
    private String[] mediaTypes;
    private String[] versions;

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getMediaTypes() {
        return mediaTypes;
    }

    public void setMediaTypes(String[] mediaTypes) {
        this.mediaTypes = mediaTypes;
    }

    public String[] getVersions() {
        return versions;
    }

    public void setVersions(String[] versions) {
        this.versions = versions;
    }
}
