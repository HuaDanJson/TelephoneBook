package com.cqnu.sunxt.telephonebook.utils;


import android.text.TextUtils;

public class StringUtils {

    public static String trim(String data) {
        if (TextUtils.isEmpty(data)) {
            return data;
        } else {
            return data.replaceAll(" ", "");
        }
    }
}
