package com.seshutechie.taxii2lib.stix.model;

import com.google.gson.annotations.SerializedName;

public class StixObject {
    private String created;
    private String description;
    private String id;
    private String[] labels;
    private String modified;
    private String name;
    @SerializedName("object_marking_refs")
    private String[] objectMakingRefs;
    private String pattern;
    private String type;
    @SerializedName("valid_from")
    private String validFrom;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getObjectMakingRefs() {
        return objectMakingRefs;
    }

    public void setObjectMakingRefs(String[] objectMakingRefs) {
        this.objectMakingRefs = objectMakingRefs;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }
}
