package viewitdoit.anchorsmedia.com.viewitdoit.utils;

import android.util.Log;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DateUtil {

    private static final String TAG = "DateUtil";

    final static SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
    final public static SimpleDateFormat fullMonthDateFormat = new SimpleDateFormat(("MMMM dd, yyyy"));
    final public static SimpleDateFormat dateOnlyFormat = new SimpleDateFormat(("yyyy-MM-dd"));


    static String formatAvailability(JSONObject result) {

        String shiftStart = "08:00 AM";
        String shiftEnd = "5:00 PM";
        String startWindow = "30min";
        String endWindow = "30min";

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        Date shiftStartDate = null;
        try {
            shiftStartDate = stringToDate(result.optString("shiftStart"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (shiftStartDate != null) {
            shiftStart = timeFormat.format(shiftStartDate);
        }
        Date shiftEndDate = null;
        try {
            shiftEndDate = stringToDate(result.optString("shiftEnd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (shiftEndDate != null) {
            shiftEnd = timeFormat.format(shiftEndDate);
        }
        if (!Objects.equals(result.optString("startWindow"), "")) {
            startWindow = result.optString("startWindow") + "min";
        }
        if (!Objects.equals(result.optString("endWindow"), "")) {
            endWindow = result.optString("endWindow") + "min";
        }
        return shiftStart + " - " + shiftEnd + " +/- " +
                startWindow + "/" + endWindow;
    }


    public static String dateToString(Date date) {
        String response = null;
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            response = dateFormat.format(date);
        }
        return response;
    }

    public static String dateToString(Date date, SimpleDateFormat dateFormat) {
        String response = null;
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        }
        if (date != null) {
            response = dateFormat.format(date);
        }
        return response;
    }

    public static Date stringToDate(String dateString) throws Exception {
        Date response = null;
        if (dateString != null && !"".equals(dateString)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            if (!"".equals(dateString)) {
                try {
                    response = dateFormat.parse(dateString);
                } catch (ParseException e) {
                    Log.e(TAG, "stringToDate failed: " + e.toString());
                    throw e;
                }
            }
        }
        return response;
    }

    public static Date stringToDate(String dateString, SimpleDateFormat dateFormat) throws Exception {
        Date response = null;
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        }
        if (dateString != null) {
            try {
                response = dateFormat.parse(dateString);
            } catch (ParseException e) {
                Log.e(TAG, "stringToDate failed: " + e.toString());
                throw e;
            }
        }
        return response;
    }

    public static String formatDateString(String dateString, String dateFormat) throws Exception {
        String response = dateString;
        Date date = null;
        if (dateString != null) {
            date = stringToDate(dateString);
        }
        if (date != null && dateFormat != null) {
            response = dateToString(date, new SimpleDateFormat(dateFormat));
        }
        return response;
    }

    public static Boolean isUpcoming(String dateString) throws Exception {
            return DateUtil.isUpcoming(DateUtil.stringToDate(dateString));
    }

    public static Boolean isUpcoming(Date date){
        if(new Date().after(date)){
            return true;
        }
        return false;
    }
}
