package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.MedId;
import com.trueform.era.his.Model.NotificatonForSave;
import com.trueform.era.his.Model.ShowNotificaton;

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
