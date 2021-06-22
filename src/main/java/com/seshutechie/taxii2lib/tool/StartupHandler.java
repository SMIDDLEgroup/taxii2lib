package com.seshutechie.taxii2lib.tool;

import com.seshutechie.taxii2lib.TaxiiLib;

public class StartupHandler {
    public static final void setDefaultContext() {
        TaxiiContext context = TaxiiContext.getContext();
        TaxiiLib taxiiLib = new TaxiiLib();
        context.setTaxiiLib(taxiiLib);

        context.addValue(EnvVariable.USER.name, ContextConstants.DEFAULT_USER);
        context.addValue(EnvVariable.PASSWORD.name, ContextConstants.DEFAULT_PASSWORD);
        context.addValue(EnvVariable.PAGE_SIZE.name, ContextConstants.DEFAULT_PAGE_SIZE);
    }
}
