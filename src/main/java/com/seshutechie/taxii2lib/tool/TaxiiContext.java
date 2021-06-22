package com.seshutechie.taxii2lib.tool;

import com.seshutechie.taxii2lib.TaxiiLib;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TaxiiContext {
    private static volatile TaxiiContext context = null;

    private Map<String, Object> contextMap;
    private TaxiiLib taxiiLib;

    private TaxiiContext() {
        contextMap = new HashMap<>();
    }

    public static TaxiiContext getContext() {
        if(context == null) {
            synchronized (TaxiiContext.class) {
                if(context == null) {
                    context = new TaxiiContext();
                }
            }
        }
        return context;
    }

    public void addValue(String name, Object value) {
        contextMap.put(name, value);
    }

    public Object getValue(String name) {
        return contextMap.get(name);
    }

    public TaxiiLib getTaxiiLib() {
        return taxiiLib;
    }

    public void setTaxiiLib(TaxiiLib taxiiLib) {
        this.taxiiLib = taxiiLib;
    }

    public Set<String> getKeys() {
        return Collections.unmodifiableSet(contextMap.keySet());
    }

    public boolean hasValue(String name) {
        return contextMap.containsKey(name);
    }
}
