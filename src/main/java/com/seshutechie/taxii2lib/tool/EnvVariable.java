package com.seshutechie.taxii2lib.tool;

public enum EnvVariable {
    USER("user"),
    PASSWORD("password"),
    PAGE_SIZE("page-size"),
    FROM("from"),
    API_ROOT("api-root"),
    COLLECTION_ID("collection-id");

    public String name;

    EnvVariable(String name) {
        this.name = name;
    }

    public static EnvVariable getInstance(String name) {
        EnvVariable envVariable = null;
        if(name != null) {
            for(EnvVariable var : EnvVariable.values()) {
                if(var.name.equals(name)) {
                    envVariable = var;
                    break;
                }
            }
        }
        return envVariable;
    }
}
