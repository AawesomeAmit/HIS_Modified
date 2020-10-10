package com.his.android.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    private static ProgressDialog progressDialog;


    @SuppressLint("SimpleDateFormat")
    public static String formatDate(String inpDate) {
        Date date = null;

        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(inpDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        @SuppressLint("SimpleDateFormat") String formattedDate = new SimpleDateFormat("MMM dd, yyyy").format(date);

        return formattedDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDateNew(String inpDate) {
        Date date = null;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(inpDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        @SuppressLint("SimpleDateFormat") String formattedDate = new SimpleDateFormat("MMM dd, yyyy").format(date);

        return formattedDate;
    }

    public static String getCurrentDate(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String formatTime(int hour, int minutes) {

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
                .append(min).append(" ").append(timeSet).toString();

        return aTime;
    }

    public static String formatTimeNew(String time) {

        String newTime = "";
        try {
            String _24HourTime = time;
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            System.out.println(_24HourDt);
            System.out.println(_12HourSDF.format(_24HourDt));

            newTime = _12HourSDF.format(_24HourDt);
        }catch (Exception e){
            e.printStackTrace();
        }


        return newTime;
    }


    public static Date getYesterdayDate() {

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();

    }


    public static void showRequestDialog(Context activity) {
        if (!((Activity) activity).isFinishing()) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setCancelable(true);
                progressDialog.setMessage("Please wait...");
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

    public static void showRequestDialog1(Context activity) {
        if (!((Activity) activity).isFinishing()) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                progressDialog.show();
            }
        }
    }

    public static String print(String mString) {
        Log.d("mString ",mString);
        return mString;
    }


}
