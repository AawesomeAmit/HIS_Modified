package com.trueform.era.his.Activity.UploadMultipleImg;

import com.trueform.era.his.Activity.UploadMultipleImg.DischargePatient.GetDischargeTypeRes;
import com.trueform.era.his.Activity.UploadMultipleImg.EmployeeOnDuty.CasualityAreaRes;
import com.trueform.era.his.Activity.UploadMultipleImg.EmployeeOnDuty.MedicosDepartmentRes;
import com.trueform.era.his.Activity.UploadMultipleImg.EmployeeOnDuty.MedicosDoctorRes;
import com.trueform.era.his.Activity.UploadMultipleImg.EmployeeOnDuty.NonMedicoEmployeeRes;
import com.trueform.era.his.Activity.UploadMultipleImg.EmployeeOnDuty.NonMedicosDepartmentRes;
import com.trueform.era.his.Activity.UploadMultipleImg.TransferPatient.BedResp;
import com.trueform.era.his.Activity.UploadMultipleImg.TransferPatient.ConsultantRes;
import com.trueform.era.his.Activity.UploadMultipleImg.TransferPatient.GetDepartmentRes;
import com.trueform.era.his.Response.DietImageResp;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    //Add Self Assign no repeat task
    @Multipart
    @POST("UploadPatientDocument.ashx")
    Call<String> addImage(
            @Header("accessToken") String accessToken,
            @Header("userID") String userId,
            @Part MultipartBody.Part[] fileName,
            @Part("PID") RequestBody PID,
            @Part("userID") RequestBody userID,
            @Part("fileDateTime") RequestBody fileDateTime

    );
    @Multipart
    @POST("UserDateWiseDietImage.ashx")
    Call<String> UserDateWiseDietImage(
            @Header("accessToken") String accessToken,
            @Header("userID") String userId,
            @Part MultipartBody.Part[] dietImage,
            @Part("PID") RequestBody PID,
            @Part("userID") RequestBody userID,
            @Part("dietTime") RequestBody dietTime

    );

    //get Image
    @POST("api/PatientDocument/GetPatientAllDocumentList")
    @FormUrlEncoded
    Call<GetPatientDocRes> getPatientDoc(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID,
            @Field("PID") String PID
    );

    //get Department
    @POST("api/DoctorsDailyDuty/GetMedicosDepartmentList")
    Call<MedicosDepartmentRes> getMedicosDepartment(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID
    );
    //get Casuality Area List
    @POST("api/DoctorsDailyDuty/GetCasualityAreaList")
    Call<CasualityAreaRes> getCasualityAreaList(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID
    );

    //get medicos doctor list
    @POST("api/DoctorsDailyDuty/MedicosDoctorList")
    @FormUrlEncoded
    Call<MedicosDoctorRes> getMedicosDoctorList(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID,
            @Field("subDepartmentID") String subDepartmentID,
            @Field("dateFrom") String dateFrom,
            @Field("casualityAreaID") String casualityAreaID
    );

    //Get non medicos department list
    @POST("Api/Employee/GetDepartment")
    Call<NonMedicosDepartmentRes> getNonMedicosDepartment(
            @Header("accessToken") String accessToken
    );

    //get Non Medicos on duty employee list
    @POST("Api/Attendance/getEmpoyeeListOnDuty")
    @FormUrlEncoded
    Call<NonMedicoEmployeeRes> getNonMedicosEmployeeList(
            @Header("accessToken") String accessToken,
            @Field("deptID") String deptID,
            @Field("companyID") String companyID,
            @Field("fromDate") String fromDate
    );

    //Get Discharge Type
    @POST("api/DischargeCard/GetDischargeType")
    Call<GetDischargeTypeRes> getDischargeType(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID
    );

    //hit discharge patient
    @POST("api/DischargeCard/saveDischarge")
    @FormUrlEncoded
    Call<Universalres> hitDischargePatient(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID,
            @Field("PID") String PID,
            @Field("consultantName") String consultantName,
            @Field("dischargeTypeID") String dischargeTypeID,
            @Field("ipNo") String ipNo,
            @Field("nextVisitDate") String nextVisitDate,
            @Field("subDeptID") String subDeptID,
            @Field("userID") String userId,
            @Field("DischargePatientDetails") String DischargePatientDetails

    );



    ///////////****************   Transfer Patients Apis    ******************///////

    //Get deparetment List
    @POST("api/AdmitPatient/GetsubDepertment")
    Call<GetDepartmentRes> getDepartmentList(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID
    );

    //Get Consultant and Ward list
    @POST("api/AdmitPatient/GetDoctorBySubDept")
    @FormUrlEncoded
    Call<ConsultantRes> getConsultantAndDepartmentList(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID,
            @Field("subDeptID") String subDeptID
    );
    //Transfer Patient
    @POST("api/Dashboard/SendPatientReq")
    @FormUrlEncoded
    Call<Universalres> hitTransferPatient(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID,
            @Field("PID") String PID,
            @Field("consultantName") String consultantName,
            @Field("crNo") String crNo,
            @Field("ipNo") String ipNo,
            @Field("region") String region,
            @Field("subDeptID") String subDeptID,
            @Field("transferType") String transferType,
            @Field("wardID") String wardID,
            @Field("who") String who
    );

    @POST("api/AdmitPatient/GetWardBedNo")
    @FormUrlEncoded
    Call<BedResp> GetWardBedNo(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID,
            @Field("headID") String headID,
            @Field("wardID") String wardID,
            @Field("subDeptID") String subDeptID
    );
    @POST("api/Dashboard/AcceptPatient")
    @FormUrlEncoded
    Call<Universalres> AcceptPatient(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID,
            @Field("PID") String PID,
            @Field("userID") String userID1,
            @Field("subDeptID") String subDeptID,
            @Field("crNo") String crNo,
            @Field("ipNo") String ipNo,
            @Field("consultantName") String consultantName,
            @Field("wardID") String wardID,
            @Field("bedID") String bedID
    );

    @FormUrlEncoded
    @POST("api/UserDateWiseDietImage/GetPatientDietImage")
    Call<DietImageResp> getPatientDietImage(
            @Header("accessToken") String token,
            @Header("userId") String userId,
            @Field("PID") String PID
    );

}
