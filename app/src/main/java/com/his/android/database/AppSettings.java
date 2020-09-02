package com.his.android.database;

import android.app.Activity;

public final class AppSettings extends OSettings {
    public static final String PREFS_MAIN_FILE             = "PREFS_MMMBEACH_FILE";
    public static final String accessToken                   = "accessToken";
    public static final String userId = "userId";
    public static final String name = "name";
    public static final String email = "email";
    public static final String phoneNumber = "phoneNumber";
    public static final String photoUrl = "photoUrl";
    public static final String latitude = "latitude";
    public static final String longitude = "longitude";
    public static final String guideId = "guideId";
    public static final String guideName = "guideName";
    public static final String beachId = "beachId";
    public static final String beachName = "beachName";
    public static final String click = "click";
    public static final String service = "service";
    public static final String massage_type = "massage_type";
    public static final String location = "location";
    public static final String time = "time";
    public static final String rate = "rate";
    public static final String length = "length";
    public static final String serviceIndex = "serviceIndex";
    public static final String moodIndex = "moodIndex";
    public static final String locationIndex = "locationIndex";
    public static final String timeIndex = "timeIndex";
    public static final String dayIndex = "dayIndex";
    public static final String lengthIndex = "lengthIndex";
    public static final String date = "date";
    public static final String pageCheck = "pageCheck";
    public static final String address = "address";
    public static final String country = "Limassol";
    public static final String count = "count";
    public static final String discountPercentage = "discountPercentage";
    public static final String nextPageCheck = "nextPageCheck";

    public AppSettings(Activity mActivity) {
        super(mActivity);
    }
}
