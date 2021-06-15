package com.seshutechie.taxii2lib.stix.model;

import com.google.gson.annotations.SerializedName;

public class StixDiscovery {
    private String title;
    private String description;
    private String contact;
    @SerializedName("default")
    private String defaultApiRoot;
    private String[] api_roots;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String[] getApi_roots() {
        return api_roots;
    }

    public void setApi_roots(String[] api_roots) {
        this.api_roots = api_roots;
    }

    public String getDefaultApiRoot() {
        return defaultApiRoot;
    }

    public void setDefaultApiRoot(String defaultApiRoot) {
        this.defaultApiRoot = defaultApiRoot;
    }
}
