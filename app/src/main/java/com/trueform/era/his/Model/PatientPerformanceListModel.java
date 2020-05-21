package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientPerformanceListModel {

    private boolean isSelected = false;
    @SerializedName("pid")
    @Expose
    private Integer pid;
    @SerializedName("ipNo")
    @Expose
    private String ipNo;
    @SerializedName("patientName")
    @Expose
    private String patientName;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("admitDateTime")
    @Expose
    private String admitDateTime;
    @SerializedName("districtName")
    @Expose
    private String districtName;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("consultantName")
    @Expose
    private String consultantName;
    @SerializedName("wardName")
    @Expose
    private String wardName;
    @SerializedName("pmID")
    @Expose
    private Integer pmID;
    @SerializedName("userStatus")
    @Expose
    private String userStatus;
    @SerializedName("diagnosis")
    @Expose
    private String diagnosis;
    @SerializedName("vitalDetail")
    @Expose
    private String vitalDetail;
    @SerializedName("intakeDetail")
    @Expose
    private String intakeDetail;

    @SerializedName("outputDetail")
    @Expose
    private String outputDetail;
    @SerializedName("toDo")
    @Expose
    private Integer toDo;

    public Integer getToDo() {
        return toDo;
    }
/* @SerializedName("pulse")
    @Expose
    private String pulse;
    @SerializedName("bP_Sys")
    @Expose
    private String bPSys;
    @SerializedName("bP_Dias")
    @Expose
    private String bPDias;
    @SerializedName("respRate")
    @Expose
    private String respRate;
    @SerializedName("temperature")
    @Expose
    private String temperature;
    @SerializedName("spo2")
    @Expose
    private String spo2;
    @SerializedName("intake")
    @Expose
    private String intake;
    @SerializedName("output")
    @Expose
    private String output;
    @SerializedName("heartRate")
    @Expose
    private String heartRate;*/

    public Integer getPid() {
        return pid;
    }

    public String getIpNo() {
        return ipNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getAdmitDateTime() {
        return admitDateTime;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public String getWardName() {
        return wardName;
    }

    public Integer getPmID() {
        return pmID;
    }

    public void setPmID(Integer pmID) {
        this.pmID = pmID;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getVitalDetail() {
        return vitalDetail;
    }

    public String getIntakeDetail() {
        return intakeDetail;
    }

    public String getOutputDetail() {
        return outputDetail;
    }

/*    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getBPSys() {
        return bPSys;
    }

    public void setBPSys(String bPSys) {
        this.bPSys = bPSys;
    }

    public String getBPDias() {
        return bPDias;
    }

    public void setBPDias(String bPDias) {
        this.bPDias = bPDias;
    }

    public String getRespRate() {
        return respRate;
    }

    public void setRespRate(String respRate) {
        this.respRate = respRate;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getSpo2() {
        return spo2;
    }

    public void setSpo2(String spo2) {
        this.spo2 = spo2;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }*/

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
