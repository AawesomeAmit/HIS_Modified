package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificatonForSave {
    @SerializedName("medID")
    @Expose
    public Integer medID;
    @SerializedName("medName")
    @Expose
    public String medName;
    @SerializedName("interactionID")
    @Expose
    public Integer interactionID;
    @SerializedName("interactionName")
    @Expose
    public String interactionName;
    @SerializedName("groupMedID")
    @Expose
    public Integer groupMedID;
    @SerializedName("groupMedName")
    @Expose
    public String groupMedName;
    @SerializedName("regionID")
    @Expose
    public Integer regionID;
    @SerializedName("regionName")
    @Expose
    public String regionName;
    @SerializedName("notificationType")
    @Expose
    public Integer notificationType;
    @SerializedName("notification")
    @Expose
    public String notification;
}
