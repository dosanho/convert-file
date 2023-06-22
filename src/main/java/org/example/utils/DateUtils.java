package org.example.utils;

import org.example.common.Const;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Timestamp parseStringToTimestamp(String strDate) {
        try {
            Date date = new SimpleDateFormat(Const.DATETIME_FORMAT_2).parse(strDate);
            return new Timestamp(date.getTime());
        } catch (Exception exception) {
            return null;
        }
    }
    public static Timestamp parseStringToTimestamp(String strDate, String format) {
        try {
            Date date = new SimpleDateFormat(format).parse(strDate);
            return new Timestamp(date.getTime());
        } catch (Exception exception) {
            return null;
        }
    }
}
