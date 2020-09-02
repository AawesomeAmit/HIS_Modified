package com.his.android.Activity.UploadMultipleImg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.provider.Settings;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    static ProgressDialog progressDialog;

    public static void logoutUser(Context context) {
      /*  SharedPrefManager userSessionManager = SharedPrefManager.getInstance(context);
        userSessionManager.setLoggedIn(false);
        Intent intent = new Intent(context, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);*/
    }

    public static long convertDateToMillis(String givenDateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        long timeInMilliseconds = 0;

        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timeInMilliseconds;
    }

    public static String formatDateForProblem(String inpDate) {
        Date date = null;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(inpDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
// format the java.util.Date object to the desired format
        String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);

        return formattedDate;
    }


    public static String formatDate(String inpDate) {
        Date date = null;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(inpDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
// format the java.util.Date object to the desired format
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        return formattedDate;
    }

    public static String formatDateForDDMMYY(String inpDate) {
        Date date = null;

        try {
            date = new SimpleDateFormat("dd-MM-yyyy").parse(inpDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
// format the java.util.Date object to the desired format
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        return formattedDate;
    }

    public static void formatTime(int hour, int minutes, TextView tvMain, TextView tvAmPm) {

        String timeSet = "";
        if (hour > 12) {
            hour -= 12;
            timeSet = "PM";
        } else if (hour == 0) {
            hour += 12;
            timeSet = "AM";
        } else if (hour == 12){
            timeSet = "PM";
        }else{
            timeSet = "AM";
        }

        String min = "";
        if (minutes < 10)
            min = "0" + minutes ;
        else
            min = String.valueOf(minutes);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hour).append(':')
                .append(min).toString();

        // Append in a StringBuilder
        String bTime = new StringBuilder().append(timeSet).toString();

        tvMain.setText(aTime);
        tvAmPm.setText(bTime);

        //return aTime;
    }


/*
    public static boolean isCurrentDate(String dateStr){
        //String dateStr = "04/05/2010";

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

// and get that as a Date
        Date dateSpecified = c.getTime();


    }
*/

    public static boolean checkPreviousDate(String currentDate) {

        boolean check = false;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date strDate = sdf.parse(currentDate);
            if (System.currentTimeMillis() < strDate.getTime()) {
                check = true;
            }else {
                check = false;
            }
        }catch (Exception e){
            check = false;
            e.printStackTrace();
        }

        return check;

    }

    public static void showRequestDialog(Activity activity) {

        if (!((Activity) activity).isFinishing()) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setCancelable(true);
               // progressDialog.setMessage(activity.getString(R.string.loading));
                progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                progressDialog.show();
            }
        }
    }

    public static void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

   /*  public static boolean isNetworkConnected(Context context) {

       ConnectionDetector connectionDetector = new ConnectionDetector(context);

        if (connectionDetector.isConnected()) {
            return true;
        }else {
            return false;
        }

    }*/


        public static String getMimeType(String url) {
            String type = null;
            String extension = MimeTypeMap.getFileExtensionFromUrl(url);
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            }
            return type;

    }

    public static String getDeviceID(Context ctx) {
        return Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
