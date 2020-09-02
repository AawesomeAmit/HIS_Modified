package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.MedId;
import com.his.android.Model.NotificatonForSave;
import com.his.android.Model.ShowNotificaton;

import java.util.List;

public class InteractionNotificationResp {
    @SerializedName("medId")
    @Expose
    public List<MedId> medId = null;
    @SerializedName("notificatonForSave")
    @Expose
    public List<NotificatonForSave> notificatonForSave = null;
    @SerializedName("showNotificaton")
    @Expose
    public List<ShowNotificaton> showNotificaton = null;
}
