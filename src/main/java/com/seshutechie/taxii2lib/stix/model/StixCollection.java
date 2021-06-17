package com.seshutechie.taxii2lib.stix.model;

import com.google.gson.annotations.SerializedName;

public class StixCollection {
    private String id;
    private String title;
    private String description;

    @SerializedName("can_read")
    private boolean canRead;

    @SerializedName("can_write")
    private boolean canWrite;

    @SerializedName("media_types")
    private String[] mediaTypes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public boolean isCanRead() {
        return canRead;
    }

    public void setCanRead(boolean canRead) {
        this.canRead = canRead;
    }

    public boolean isCanWrite() {
        return canWrite;
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }

    public String[] getMediaTypes() {
        return mediaTypes;
    }

    public void setMediaTypes(String[] mediaTypes) {
        this.mediaTypes = mediaTypes;
    }
}
