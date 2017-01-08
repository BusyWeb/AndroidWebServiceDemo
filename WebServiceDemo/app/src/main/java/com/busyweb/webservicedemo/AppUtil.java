package com.busyweb.webservicedemo;

import java.util.Date;

/**
 * Created by BusyWeb on 1/7/2017.
 */

public class AppUtil {

    public static boolean isObjectNullOrEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            String val = (String)object;
            if (val == null || val.equalsIgnoreCase("") || val.length() < 1) {
                return true;
            } else {
                return false;
            }
        }
        if (object instanceof Integer) {
            Integer valInt = (Integer)object;
            if (valInt == null || valInt < 0) {
                return true;
            } else {
                return false;
            }
        }
        if (object instanceof java.util.Date) {
            Date valDate = (Date)object;
            if (valDate == null || valDate.toString().length() < 1) {
                return true;
            } else {
                return false;
            }
        }
        if (object instanceof Boolean) {
            Boolean valBool = (Boolean)object;
            if (valBool == null) {
                return true;
            } else {
                return valBool;
            }
        }
        return true;
    }


}
