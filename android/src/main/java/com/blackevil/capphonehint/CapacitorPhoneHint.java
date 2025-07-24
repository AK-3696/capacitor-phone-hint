package com.blackevil.capphonehint;

import com.getcapacitor.Logger;

public class CapacitorPhoneHint {

    public String echo(String value) {
        Logger.info("Echo", value);
        return value;
    }
}
