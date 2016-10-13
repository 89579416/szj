package com.szj.library.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MasterFan on 2015/7/8.
 * description:
 */
public class MatcherUtil {
    public static boolean isPhone(String phone) {
        String check = "^(13[0, 1, 2,3,4,5,6,7,8,9]|15[0,8,9,1,2,3,4,5,6,7]|18[1,2,3,5,7,8,9]|17[7,8])\\d{8}$"; //手机验证
        Pattern p = Pattern.compile(check);
        Matcher m = p.matcher(phone);
        return m.find();
    }
}
