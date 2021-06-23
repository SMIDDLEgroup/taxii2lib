package com.seshutechie.taxii2lib.util;

import com.seshutechie.taxii2lib.stix.model.ItemRange;

public class CommonUtil {
    public static String getLastPageStatus(ItemRange itemRange) {
        String status = null;
        if (itemRange != null) {
            status = "Items from " + itemRange.getFrom() + " to " + itemRange.getTo() + " been fetched. Total: " + itemRange.getTotal();
        }
        return status;
    }
}
