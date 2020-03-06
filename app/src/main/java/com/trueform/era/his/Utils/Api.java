package com.trueform.era.his.Utils;

import com.trueform.era.his.Model.PatientDetailRDA;
import com.trueform.era.his.Model.VitalChart;
import com.trueform.era.his.Response.ActivityDataResp;
import com.trueform.era.his.Response.ActivityResp;
import com.trueform.era.his.Response.AddInvestigationResp;
import com.trueform.era.his.Response.AnalyzingGraphResp;
import com.trueform.era.his.Response.AngioPatientDetailResp;
import com.trueform.era.his.Response.AngioReportResp;
import com.trueform.era.his.Response.AngioplastyResp;
import com.trueform.era.his.Response.AttribValueResp;
import com.trueform.era.his.Response.BillDetailsResp;
import com.trueform.era.his.Response.CalciumDynamicReportResp;
import com.trueform.era.his.Response.CalciumPatientDataResp;
import com.trueform.era.his.Response.CalciumWardListResp;
import com.trueform.era.his.Response.CalculatorResp;
import com.trueform.era.his.Response.CheckPidResp;
import com.trueform.era.his.Response.ControlBySubDeptResp;
import com.trueform.era.his.Response.DepartmentResp;
import com.trueform.era.his.Response.DieteticsPatientResp;
import com.trueform.era.his.Response.DischargeTypeResp;
import com.trueform.era.his.Response.DiseaseListResp;
import com.trueform.era.his.Response.DiseasePatientListResp;
import com.trueform.era.his.Response.EmployeeShiftResp;
import com.trueform.era.his.Response.FluidListResp;
import com.trueform.era.his.Response.FoodDetailResp;
import com.trueform.era.his.Response.FoodListRes;
import com.trueform.era.his.Response.FreqUnitResp;
import com.trueform.era.his.Response.GetAllMedicineByAlphabetRes;
import com.trueform.era.his.Response.GetAllSuggestedProblemRes;
import com.trueform.era.his.Response.GetAllSymptomsRes;
import com.trueform.era.his.Response.GetAttributeResp;
import com.trueform.era.his.Response.GetBodyOrganRegionRes;
import com.trueform.era.his.Response.GetCalculatorListResp;
import com.trueform.era.his.Response.GetIcdCodeResp;
import com.trueform.era.his.Response.GetIdealNutrientIntakeResp;
import com.trueform.era.his.Response.GetIntakeOuttakeResp;
import com.trueform.era.his.Response.GetNutrientAverageDeficiencyResp;
import com.trueform.era.his.Response.GetNutrientByPrefixTextResp;
import com.trueform.era.his.Response.GetNutrientInBodyResp;
import com.trueform.era.his.Response.GetNutrientLevelImmediateEffectByFoodTimeIdResp;
import com.trueform.era.his.Response.GetNutrientSearchListResp;
import com.trueform.era.his.Response.GetPatientDetailsByPidResp;
import com.trueform.era.his.Response.GetPatientListResp;
import com.trueform.era.his.Response.GetPrescriptionResponse;
import com.trueform.era.his.Response.GetQuestionnaireList;
import com.trueform.era.his.Response.GetRequiredSupplementByFoodTimeIdResp;
import com.trueform.era.his.Response.GraphColorcodeRes;
import com.trueform.era.his.Response.IcuPatientListResp;
import com.trueform.era.his.Response.InsertResponse;
import com.trueform.era.his.Response.IntakePrescriptionResp;
import com.trueform.era.his.Response.InvestigationChartResp;
import com.trueform.era.his.Response.InvestigationResultNotificationResp;
import com.trueform.era.his.Response.IpdPatientListResp;
import com.trueform.era.his.Response.LoginResp;
import com.trueform.era.his.Response.MealResp;
import com.trueform.era.his.Response.MedicineListResp;
import com.trueform.era.his.Response.MedicineSearchResp;
import com.trueform.era.his.Response.MemberIdResp;
import com.trueform.era.his.Response.NotificationCountResp;
import com.trueform.era.his.Response.NutriAnalyzerResp;
import com.trueform.era.his.Response.NutriAnlzrFoodListDtlRes;
import com.trueform.era.his.Response.NutrientBindRes;
import com.trueform.era.his.Response.NutrientResponseValue;
import com.trueform.era.his.Response.ObservationGraphResp;
import com.trueform.era.his.Response.ParameterScoreResp;
import com.trueform.era.his.Response.PatientBillResp;
import com.trueform.era.his.Response.PatientDetailFoodNutrientResp;
import com.trueform.era.his.Response.PatientDetailGraphResp;
import com.trueform.era.his.Response.PatientDiagnosisDetailsByPID;
import com.trueform.era.his.Response.PatientDiagnosisResultResp;
import com.trueform.era.his.Response.PatientNutrientGraphResp;
import com.trueform.era.his.Response.PatientPerformanceListResp;
import com.trueform.era.his.Response.PhysioPatientListResp;
import com.trueform.era.his.Response.PrescribedMedResp;
import com.trueform.era.his.Response.ProbAttribResp;
import com.trueform.era.his.Response.ProblemDataResp;
import com.trueform.era.his.Response.ProblemListResp;
import com.trueform.era.his.Response.QuestionnaireResp;
import com.trueform.era.his.Response.RangeHistoryResp;
import com.trueform.era.his.Response.RangeMasterResp;
import com.trueform.era.his.Response.RdaNotificationResp;
import com.trueform.era.his.Response.ResearchDiseaseNotificationResp;
import com.trueform.era.his.Response.SetIDResp;
import com.trueform.era.his.Response.SubDeptCalReportResp;
import com.trueform.era.his.Response.SubHeadIDResp;
import com.trueform.era.his.Response.SupplementDetailResp;
import com.trueform.era.his.Response.SupplementListResp;
import com.trueform.era.his.Response.UnitResp;
import com.trueform.era.his.Response.UpdatePatientPidResp;
import com.trueform.era.his.Response.UserMedicationReportResp;
import com.trueform.era.his.Response.VersionCheckResp;
import com.trueform.era.his.Response.ViewIntkAllPriortyNutriRes;
import com.trueform.era.his.Response.ViewNotificationResp;
import com.trueform.era.his.Response.ViewProgressResp;
import com.trueform.era.his.Response.VitalAutoCompleteResp;
import com.trueform.era.his.Response.VitalList;
import com.trueform.era.his.Response.VitalListResp;
import com.trueform.era.his.Response.VitalRangeHistoryResp;

import org.json.JSONArray;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {
    @FormUrlEncoded//205
    @POST("Login/LoginAuthontication")
    Call<LoginResp> loginAuthontication(@Field("Username") String Username, @Field("Password") String Password);

    @FormUrlEncoded
    @POST("Dashboard/GetsubDepertmentByHID")
    Call<SubHeadIDResp> getsubDepertmentByHID(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("headID") String headID, @Field("who") Integer who);

    @FormUrlEncoded
    @POST("Dashboard/GetControlsBySubDept")
    Call<ControlBySubDeptResp> getControlsBySubDept(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("subDeptID") Integer subDeptID, @Field("headID") Integer headID, @Field("who") Integer who);

    @FormUrlEncoded
    @POST("Dashboard/GetIPDPatientList")
    Call<IpdPatientListResp> getIPDPatientList(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("subDeptID") Integer subDeptID, @Field("who") Integer who);

    @FormUrlEncoded
    @POST("Dashboard/GetPhysiotherapyPanelPatientList")
    Call<PhysioPatientListResp> getPhysiotherapyPatientList(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("subDeptID") Integer subDeptID, @Field("who") Integer who);

    @FormUrlEncoded
    @POST("Dashboard/GetICUPatientList")
    Call<IcuPatientListResp> getICUPatientList(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("subDeptID") Integer subDeptID, @Field("wardID") Integer wardID, @Field("who") Integer who);

    //@FormUrlEncoded
    @POST("PatientMonitor/GetIntakeOutputList")
//PatientMonitor/GetIntakeOutputList
    Call<FluidListResp> intakeOutType(@Header("accessToken") String accessToken, @Header("userID") String userID);

    //@FormUrlEncoded
    @POST("Prescription/GetVitalMaster")
    Call<List<VitalList>> getVitalMaster(
            @Header("accessToken") String accessToken, @Header("userID") String userID);

    @POST("DateWiseReports/InitDischargeDetailsList")
    Call<DischargeTypeResp> initDischargeDetailsList(@Header("accessToken") String accessToken, @Header("userID") String userID);

    @POST("DateWiseReports/InitControlsSubDept")
    Call<SubDeptCalReportResp> initControlsSubDept(@Header("accessToken") String accessToken, @Header("userID") String userID);

    @FormUrlEncoded
    @POST("PatientMonitor/GetIntakeFoodData")
    Call<FoodDetailResp> getIntakeFoodData(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("PatientPhysicalActivity/GetPatientPhysicalActivityList")
    Call<ActivityDataResp> getPatientPhysicalActivityList(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("Graph/GetPatientAnalyzingGraph")
    Call<AnalyzingGraphResp> getPatientAnalyzingGraph(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("userID") String userID, @Field("vitalIdSearch") String vitalIdSearch, @Field("date") String date, @Field("subDeptID") Integer subDeptID, @Field("timeFrom") String timeFrom, @Field("toHour") Integer toHour, @Field("nutrientIDSearch") String nutrientIDSearch, @Field("queryType") String queryType, @Field("isFoodIntake") String isFoodIntake, @Field("isInvestigation") String isInvestigation, @Field("isActivity") String isActivity, @Field("isProblem") String isProblem, @Field("isIO") String isIO, @Field("isIntakeMedicine") String isIntakeMedicine);

    @FormUrlEncoded
    @POST("PatientPhysicalActivity/GetPatientComplainList")
    Call<ProblemDataResp> getPatientComplainList(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("Graph/GetPatientDetailGraph")
    Call<PatientDetailGraphResp> getPatientDetailGraph(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("date") String date, @Field("PID") Integer PID, @Field("subDeptID") Integer subDeptID);

    @FormUrlEncoded
    @POST("PatientPhysicalActivity/GetProblemAttribute")
    Call<ProbAttribResp> getProblemAttribute(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("problemID") Integer problemID);

    @FormUrlEncoded
    @POST("Radiology/GetAngioReportList")
    Call<AngioReportResp> getAngioReportList(@Header("accessToken") String accessToken, @Header("userID") Integer userID, @Field("PID") Integer PID, @Field("headID") Integer headID, @Field("userID") Integer userID1);

    @FormUrlEncoded
    @POST("CalciumReport/GetWardBySubDepartment")
    Call<CalciumWardListResp> getWardBySubDepartment(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("userID") String userID1);

    @FormUrlEncoded
    @POST("DateWiseReports/GetCalciumPatientHourlyReport")
    Call<CalciumPatientDataResp> getCalciumPatientHourlyReport(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("fromdate") String fromdate, @Field("todate") String todate, @Field("catIds") Integer catIds, @Field("subDeptID") String subDeptID);

    @FormUrlEncoded
    @POST("CalciumReport/GetPatientCalciumHourlyReport")
    Call<CalciumDynamicReportResp> getPatientCalciumHourlyReport(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("date") String date, @Field("toDate") String toDate, @Field("dischargeID") Integer dischargeID, @Field("subDeptId") String subDeptId, @Field("wardID") String wardID);

    @FormUrlEncoded
    @POST("PatientPhysicalActivity/GetProblemAttributeValue")
    Call<AttribValueResp> getProblemAttributeValue(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("attributeID") Integer attributeID);

    @FormUrlEncoded
    @POST("PatientPhysicalActivity/DeletePatientComplain")
    Call<ResponseBody> deletePatientComplain(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("patientComplainAttributeID") Integer patientComplainAttributeID, @Field("patientComplainID") Integer patientComplainID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("UserIntake.asmx/GetNutriAnalyserValuesByFoodTimeIdPID")
    Call<PatientDetailFoodNutrientResp> getNutriAnalyserValuesByFoodTimeIdPID(@Header("Token") String accessToken, @Field("PID") Integer PID, @Field("foodTimeID") String foodTimeID, @Field("intakeDate") String intakeDate, @Field("memberId") String memberId, @Field("nutrientList") String nutrientList, @Field("userLoginID") Integer userLoginID);

    @POST("UserIntake.asmx/GetNutriAnalyserFoodListByFoodTimeId")
    @FormUrlEncoded
    Call<FoodListRes> getFoodListFoodTimeId(
            @Header("token") String token,
            @Field("memberId") String memberid,
            @Field("foodTimeId") String foodTimeId,
            @Field("intakeDate") String intakeDate,
            @Field("userLoginId") String userLoginId
    );

    @FormUrlEncoded
    @POST("UserIntake.asmx/GetNutrientListByIntake")
    Call<NutriAnlzrFoodListDtlRes> viewFoodDtl(
            @Header("token") String token,
            @Field("memberId") String memberid,
            @Field("dietId") String dietId,
            @Field("dietType") String dietType,
            @Field("userLoginId") String userLoginId
    );

    @POST("UserIntake.asmx/GetNutrientLevelImmediateEffectByFoodTimeId")
    @FormUrlEncoded
    Call<GetNutrientLevelImmediateEffectByFoodTimeIdResp> GetNutrientLevelImmediateEffectByFoodTimeId(
            @Header("token") String token,
            @Field("memberId") String memberId,
            @Field("foodTimeId") String foodTimeId,
            @Field("intakeDate") String intakeDate,
            @Field("userLoginId") String userLoginId
    );

    @POST("UserIntake.asmx/GetRequiredSupplementByFoodTimeId")
    @FormUrlEncoded
    Call<GetRequiredSupplementByFoodTimeIdResp> GetRequiredSupplementByFoodTimeId(
            @Header("token") String token,
            @Field("memberId") String memberId,
            @Field("foodTimeId") String foodTimeId,
            @Field("intakeDate") String intakeDate,
            @Field("userLoginId") String userLoginId
    );

    @FormUrlEncoded
    @POST("Graph/GetAutoCompleteProblem")
    Call<ProblemListResp> getAutoCompleteProblem(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("searchKey") String searchKey, @Field("who") Integer who);

    @FormUrlEncoded
    @POST("getBodyOrganRegion")
    Call<GetBodyOrganRegionRes> getBodyOrganRegion(@Header("x-access-Token") String token, @Field("userId") String userId, @Field("bodyRegionId") String bodyRegionId);

    @FormUrlEncoded
    @POST("getAllSymptoms")
    Call<GetAllSymptomsRes> getAllSymptoms(@Header("x-access-token") String token, @Field("userId") String userId, @Field("regionOrganId") String regionOrganId);

    @FormUrlEncoded
    @POST("getAttributeList")
    Call<GetAttributeResp> getAttributeList(@Header("x-access-token") String token, @Field("userId") String userId, @Field("problemId") Integer problemId);

    @FormUrlEncoded
    @POST("getAllSuggestedProblem")
    Call<GetAllSuggestedProblemRes> getAllSuggestedProblem(@Header("x-access-token") String token, @Field("userId") String userId);

    @FormUrlEncoded
    @POST("PatientPhysicalActivity/SavePatientPhysicalActivity")
    Call<ResponseBody> savePatientPhysicalActivity(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("ListPatientPhysicalActivity") JSONArray ListPatientPhysicalActivity, @Field("PID") Integer PID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("PatientPhysicalActivity/DeletePatientPhysicalActivity")
    Call<ResponseBody> deletePatientPhysicalActivity(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("physicalActivityID") Integer physicalActivityID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("PatientPhysicalActivity/GetPatientActivityMaster")
    Call<ActivityResp> getPatientActivityMaster(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("Dashboard/RemovePatientFromPhysiotherapyPanel")
    Call<ResponseBody> removePatientFromPhysiotherapyPanel(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("subDeptID") Integer subDeptID, @Field("id") Integer id, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("Graph/GetPatientIntakeNutritionGraph")
    Call<PatientNutrientGraphResp> getPatientIntakeNutritionGraph(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("PID") Integer PID, @Field("date") String date, @Field("nutrientId") String nutrientId);

    @FormUrlEncoded
    @POST("Dashboard/SavePatientPhysiotherapyPanel")
    Call<ResponseBody> savePatientPhysiotherapyPanel(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("headID") Integer headID, @Field("PID") String PID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("PatientMonitor/GetIntakeOutputData")
    Call<GetIntakeOuttakeResp> getIntakeOutputData(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID1,
            @Field("PID") Integer PID,
            @Field("headID") Integer headID,
            @Field("intakeOralDate") String intakeOralDate,
            @Field("ipNo") String ipNo,
            @Field("outputDate") String outputDate,
            @Field("subDeptID") Integer subDeptID,
            @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("RecipeMaster.asmx/GetFoodListByPrefixText")
    Call<MealResp> getFoodListByPrefixText(@Header("accessToken") String accessToken, @Field("memberId") Integer memberId, @Field("prefixText") String prefixText, @Field("userLoginID") Integer userLoginID);

    @FormUrlEncoded
    @POST("Universal.asmx/GetFoodUnitByFoodId")
    Call<UnitResp> getFoodUnitByFoodId(@Header("accessToken") String accessToken, @Field("foodId") Integer foodId);

    @FormUrlEncoded
    @POST("Graph/GetAutoCompleteNutrition")
    Call<NutrientBindRes> getAutoCompleteNutrition(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("searchKey") String searchKey);

    @FormUrlEncoded
    @POST("SupplementIntake.asmx/GetSupplementList")
    Call<SupplementListResp> getSupplementList(@Header("Token") String accessToken, @Field("memberID") Integer memberID, @Field("userLoginID") Integer userLoginID);

    @FormUrlEncoded
    @POST("PatientMonitor/SaveIntakeData")
    Call<ResponseBody> saveIntakeData(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("headID") Integer headID, @Field("ipNo") String ipNo, @Field("advBy") Integer advBy, @Field("intakeOralDate") String intakeOralDate, @Field("intakeQty") String intakeQty, @Field("intakeType") String intakeType, @Field("intakeUnit") String intakeUnit, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("PatientMonitor/SaveOutputData")
    Call<ResponseBody> saveOutputData(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("headID") Integer headID, @Field("ipNo") String ipNo, @Field("outputDate") String outputDate, @Field("outputQty") String outputQty, @Field("outputType") Integer outputType, @Field("outputUnit") Integer outputUnit, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("supplementIntake.asmx/AddIntakeDetails")
    Call<InsertResponse> addMedicineIntakeDetails(@Header("Token") String accessToken, @Field("memberID") Integer memberID, @Field("medicineID") Integer medicineID, @Field("brandID") Integer brandID, @Field("doseStrength") String doseStrength, @Field("doseQuantity") String doseQuantity, @Field("doseUnitID") Integer doseUnitID, @Field("intakeTime") String intakeTime, @Field("intakeDate") String intakeDate, @Field("userLoginId") Integer userLoginId);

    @FormUrlEncoded
    @POST("UserIntake.asmx/SaveIntakeData")
    Call<InsertResponse> saveIntakeData(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("advBy") String advBy, @Field("doseStrength") Integer doseStrength, @Field("doseUnitID") Integer doseUnitID, @Field("foodId") Integer foodId, @Field("foodQuantity") String foodQuantity, @Field("foodTime") String foodTime, @Field("foodUnitId") Integer foodUnitId, @Field("intakeDate") String intakeDate, @Field("intakeDateTime") String intakeDateTime, @Field("intakeOralDate") String intakeOralDate, @Field("intakeQty") Integer intakeQty, @Field("intakeTime") String intakeTime, @Field("intakeType") Integer intakeType, @Field("intakeUnit") Integer intakeUnit, @Field("memberId") Integer memberId, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID, @Field("userLoginID") Integer userLoginID);

    @FormUrlEncoded
    @POST("UserIntake.asmx/AddIntakeDetails")
    Call<InsertResponse> addIntakeDetails(@Header("Token") String accessToken, @Field("foodId") Integer foodId, @Field("foodQuantity") String foodQuantity, @Field("foodTime") String foodTime, @Field("foodUnitId") String foodUnitId, @Field("intakeDate") String intakeDate, @Field("memberId") String memberId, @Field("userLoginID") Integer userLoginID);

    @FormUrlEncoded
    @POST("familymember.asmx/GetUserProfileByPID")
    Call<MemberIdResp> getUserProfileByPID(@Header("Token") String accessToken, @Field("PID") String PID);

    @FormUrlEncoded
    @POST("Questionnaire/GetQuestionnaireList")
    Call<QuestionnaireResp> getQuestionnaireList(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("setID") Integer setID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("PatientMonitor/GetIntakeSupplimentData")
    Call<SupplementDetailResp> getIntakeSupplimentData(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("Questionnaire/SaveAnswerList")
    Call<ResponseBody> saveAnswerList(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("answerList") JSONArray answerList, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("Calculator/GetParameterScoreValue")
    Call<ParameterScoreResp> getParameterScoreValue(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("id") Integer id, @Field("parameterID") Integer parameterID, @Field("parameterValue") String parameterValue);

    @FormUrlEncoded
    @POST("Questionnaire/GetSetList")
    Call<SetIDResp> getSetList(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("headID") Integer headID);

    @FormUrlEncoded
    @POST("autoVisitRevisit/checkCRNo")
    Call<CheckPidResp> checkCRNo(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("Investigation/GetPatientBill")
    Call<PatientBillResp> getPatientBill(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @POST("Prescription/GetInvestigationUnitFrequency")
    Call<AddInvestigationResp> getInvestigationUnitFrequency(@Header("accessToken") String accessToken, @Header("userID") String userID);

    @FormUrlEncoded
    @POST("Investigation/ViewBillDetails")
    Call<BillDetailsResp> viewBillDetails(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("billNo") String billNo, @Field("PID") Integer PID, @Field("id") String id, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("OGraph/GetVitalGraph")
    Call<ObservationGraphResp> getVitalGraph(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("PID") Integer PID, @Field("fromDate") String fromDate, @Field("headID") Integer headID, @Field("hours") Integer hours, @Field("vitalType") Integer vitalType);

    @FormUrlEncoded
    @POST("PatientRange/GetPageLoadMaster")
    Call<RangeMasterResp> getPageLoadMaster(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("Graph/GetVitalGraph")
    Call<RangeMasterResp> getVitalGraphs(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("date") String date, @Field("vitalIdSearch") String vitalIdSearch, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("PatientRange/GetPatientRangeHistory")
    Call<RangeHistoryResp> getPatientRangeHistory(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("PID") Integer PID, @Field("subDeptID") Integer subDeptID);

    @FormUrlEncoded
    @POST("PatientRange/GetVitalRangeHistory")
    Call<VitalRangeHistoryResp> getVitalRangeHistory(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("PID") Integer PID, @Field("subDeptID") Integer subDeptID);

    @FormUrlEncoded
    @POST("PatientRange/UpdateVitalRange")
    Call<ResponseBody> updateVitalRange(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("ID") Integer ID);

    @FormUrlEncoded
    @POST("Questionnaire/DeleteQuestionnaireInput")
    Call<ResponseBody> deleteQuestionnaireInput(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("pid") Integer pid, @Field("answerMainID") Integer answerMainID);

    @FormUrlEncoded
    @POST("Radiology/GetAngioTemplate")
    Call<AngioplastyResp> getAngioTemplate(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("headID") Integer headID, @Field("userID") Integer userID1, @Field("itemID") String itemID);

    @FormUrlEncoded
    @POST("PatientRange/UpdatePatientRange")
    Call<ResponseBody> updatePatientRange(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("ID") Integer ID);

    @FormUrlEncoded
    @POST("PatientRange/SavePatientRange")
    Call<ResponseBody> savePatientRange(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("ipNo") String ipNo, @Field("diagID") String diagID, @Field("drID") Integer drID, @Field("maxRange") String maxRange, @Field("minRange") String minRange, @Field("subDeptID") Integer subDeptID, @Field("typeID") String typeID, @Field("unitID") String unitID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("PatientRange/SaveVitalRange")
    Call<ResponseBody> saveVitalRange(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("drID") Integer drID, @Field("maxRange") String maxRange, @Field("minRange") String minRange, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID, @Field("vitalID") String vitalID);

    @FormUrlEncoded
    @POST("IntakePrescription/GetintakePrescription")
    Call<GetPrescriptionResponse> getintakePrescription(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("headID") Integer headID, @Field("ipNo") String ipNo, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("Questionnaire/GetQuestionnaireInputReport")
    Call<GetQuestionnaireList> GetQuestionnaireInputReport(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("date") String date, @Field("pid") Integer pid, @Field("setID") Integer setID);

    @FormUrlEncoded
    @POST("Radiology/SaveAngioResult")
    Call<ResponseBody> saveAngioResult(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("headID") String headID, @Field("userID") Integer userID1, @Field("billNo") String billNo, @Field("itemID") String itemID, @Field("resultRemark") String resultRemark, @Field("result") String result, @Field("cathID") String cathID, @Field("patientType") String patientType);

    @FormUrlEncoded
    @POST("Prescription/getIPDCurrentHistory")
    Call<PrescribedMedResp> getCurrentPrescripttionHistory(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("headID") Integer headID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("IntakePrescription/GetgetintakeList")
    Call<IntakePrescriptionResp> getgetintakeList(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("headID") Integer headID, @Field("ipNo") String ipNo, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("Prescription/GetMedicineBySearch")
    Call<MedicineSearchResp> getMedicineBySearch(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("searchKey") String searchKey, @Field("who") Integer who);

    @FormUrlEncoded
    @POST("Prescription/updateMedication")
    Call<ResponseBody> updateMedication(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("id") Integer id, @Field("isStop") Integer isStop, @Field("status") Integer status);

    @FormUrlEncoded
    @POST("MedicationPackageMaster/GetMedicationList")
    Call<MedicineListResp> getMedicationList(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("diseaseId") Integer diseaseId, @Field("subDeptId") Integer subDeptId, @Field("userId") Integer userId, @Field("packageName") String packageName);

    @FormUrlEncoded
    @POST("Investigation/GetGraphByTestId")
    Call<InvestigationChartResp> getGraphByTestId(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("PID") Integer PID, @Field("billNo") Integer billNo, @Field("id") Integer id, @Field("searchKey") String searchKey);

    @POST("Calculator/GetCalculatorList")
    Call<GetCalculatorListResp> getCalculatorList(@Header("accessToken") String accessToken, @Header("userID") String userID);

    @FormUrlEncoded
    @POST("Calculator/GetCalculatorFormula")
    Call<CalculatorResp> getCalculatorFormula(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("PID") Integer PID, @Field("id") Integer id);

    @FormUrlEncoded
    @POST("Report/GetUserMedicationReport")
    Call<UserMedicationReportResp> getUserMedicationReport(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("PID") Integer PID, @Field("userid") String userid, @Field("headID") Integer headID, @Field("subDeptID") Integer subDeptID, @Field("timePeriod") String timePeriod);

    @FormUrlEncoded
    @POST("Prescription/GetDiagnosisByPID")
    Call<PatientDiagnosisDetailsByPID> getDiagnosisByPID(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("headID") Integer headID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("Prescription/GetVitalChart")
    Call<List<VitalChart>> getVitalChart(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("vitalDate") String vitalDate, @Field("headID") Integer headID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("Prescription/GetPatientVitalList")
    Call<VitalListResp> getPatientVitalList(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("headID") Integer headID, @Field("subDeptID") Integer subDeptID, @Field("userID") Integer userID, @Field("vitalType") Integer vitalType);

    @POST("DischargeCard/GetUnitFrequencyDosages")
    Call<FreqUnitResp> getUnitFrequencyDosages(@Header("accessToken") String accessToken, @Header("userID") String userID);

    @FormUrlEncoded
    @POST("Prescription/SavePrescriptionAndInvistigation")
        //Call<ResponseBody> savePrescriptionAndInvistigation(@Header("accessToken") String accessToken, @Body String body);
    Call<ResponseBody> savePrescriptionAndInvistigation(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("PID") Integer PID, @Field("headID") Integer headID, @Field("prescription") JSONArray prescription, @Field("investigation") JSONArray investigation, @Field("subDeptID") Integer subDeptID, @Field("consultantName") Integer consultantName, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("CheckVersion/CheckVersion")
    Call<VersionCheckResp> checkVersion(@Field("appVersion") String appVersion);

    //@FormUrlEncoded
    @POST("familymember.asmx/GetNutritionalPanelPatientList")
    Call<DieteticsPatientResp> getNutritionalPanelPatientList(
            @Header("token") String token,
            @Header("userID") String userID);

    @FormUrlEncoded
    @POST("Prescription/GetProgressHistory")
    Call<ViewProgressResp> getProgressHistory(
            @Header("accessToken") String accessToken,
            @Header("userID") Integer userID1,
            @Field("PID") Integer PID,
            @Field("headID") Integer headID,
            @Field("ipNo") String ipNo,
            @Field("subDeptID") Integer subDeptID,
            @Field("userID") Integer userID,
            @Field("entryDate") String entryDate,
            @Field("pdmID") Integer pdmID
    );

    @FormUrlEncoded
    @POST("Radiology/GetPatientDetails")
    Call<AngioPatientDetailResp> getPatientDetails(
            @Header("accessToken") String accessToken,
            @Header("userID") Integer userID1,
            @Field("headID") Integer headID,
            @Field("billNo") String billNo,
            @Field("userID") Integer userID
    );

    @FormUrlEncoded
    @POST("Login/logOut")
    Call<ResponseBody> logOut(@Field("Token") String accessToken, @Header("userID") String userID1, @Field("deviceToken") String deviceToken, @Field("userID") String userID);

    @FormUrlEncoded
    @POST("familymember.asmx/RemovePatientFromNutritionalPanel")
    Call<ResponseBody> removePatientFromNutritionalPanel(@Header("Token") String accessToken, @Field("memberId") Integer memberId, @Field("userLoginId") Integer userLoginId);

    @POST("UserIntake.asmx/GetNutriAnalyserValuesByFoodTimeId")
    @FormUrlEncoded
    Call<NutrientResponseValue> getNutriAnalyserValuesByFoodTimeId(@Header("token") String token, @Field("memberId") Integer memberid, @Field("foodTimeId") String foodTimeId, @Field("intakeDate") String intakeDate, @Field("nutrientList") String nutrientList, @Field("userLoginId") Integer userLoginId);

    @FormUrlEncoded
    @POST("UserNotification/InsertFCMDeviceToken")
    Call<ResponseBody> insertFCMDeviceToken(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("deviceToken") String deviceToken, @Field("userID") String userID);

    @FormUrlEncoded
    @POST("Report/GetUserWiseResearchDiseaseList")
    Call<DiseaseListResp> getUserWiseResearchDiseaseList(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("userID") String userID);

    @FormUrlEncoded
    @POST("Graph/GetAutoCompleteVital")
    Call<VitalAutoCompleteResp> getAutoCompleteVital(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("searchKey") String searchKey);

    @FormUrlEncoded
    @POST("Report/GetResearchDiseasePatientList")
    Call<DiseasePatientListResp> getResearchDiseasePatientList(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("userNotificationAssignID") Integer userNotificationAssignID, @Field("queryDate") String queryDate, @Field("userID") String userID);

    @FormUrlEncoded
    @POST("UserNotification/GetNotificationList")
    Call<List<ViewNotificationResp>> getNotificationList(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("userID") Integer userID);

    @POST("EmployeeShiftInfo/GetDepartmentList")
    Call<DepartmentResp> getDepartmentList(@Header("accessToken") String accessToken, @Header("userID") String userID);

    @FormUrlEncoded
    @POST("EmployeeShiftInfo/GetEmployeeList")
    Call<EmployeeShiftResp> getEmployeeList(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("deptID") Integer deptID , @Field("DeptName") String DeptName);

    @FormUrlEncoded
    @POST("UserNotification/GetUnReadNotificationCount")
    Call<List<NotificationCountResp>> getUnReadNotificationCount(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("userID") Integer userID);

    @FormUrlEncoded
    @POST("UserNotification/GetResearchDiseaseNotificationDetail")
    Call<List<ResearchDiseaseNotificationResp>> getResearchDiseaseNotificationDetail(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("notificationID") Integer notificationID, @Field("userID") String userID1);

    @FormUrlEncoded
    @POST("UserNotification/GetInvestigationResultNotificationDetail")
    Call<List<InvestigationResultNotificationResp>> getInvestigationResultNotificationDetail(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("notificationID") Integer notificationID, @Field("userID") String userID1);

    @FormUrlEncoded
    @POST("UserNotification/GetRDAChangeNotificationDetail")
    Call<RdaNotificationResp> getRDAChangeNotificationDetail(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("notificationID") Integer notificationID, @Field("userID") String userID1);

    @FormUrlEncoded
    @POST("UserNotification/GetAdviceNotificationDetail")
    Call<RdaNotificationResp> getAdviceNotificationDetail(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("notificationID") Integer notificationID, @Field("userID") String userID1);

    @FormUrlEncoded
    @POST("UserNotification/GetSymptomDueToNutrientPercentageNotificationDetail")
    Call<List<PatientDetailRDA>> getSymptomDueToNutrientPercentageNotificationDetail(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("notificationID") Integer notificationID, @Field("userID") String userID1);

    @FormUrlEncoded
    @POST("UserNotification/GetChangedRDANotificationDetail")
    Call<List<PatientDetailRDA>> getChangedRDANotificationDetail(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("notificationID") Integer notificationID, @Field("userID") String userID1);

    @FormUrlEncoded
    @POST("UserNotification/ChangeRDAThroughNotification")
    Call<ResponseBody> changeRDAThroughNotification(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("notificationID") Integer notificationID, @Field("userID") String userID1, @Field("rdaPercentage") String rdaPercentage, @Field("rdaChangeToDate ") String rdaChangeToDate);

    @POST("PatientDashboard/GetPatientPerformanceList")
    Call<PatientPerformanceListResp> getPatientPerformanceList(@Header("accessToken") String accessToken, @Header("userID") String userID);

    @FormUrlEncoded
    @POST("Prescription/GetICDCode")
    Call<GetIcdCodeResp> getICDCode(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID,
            @Field("searchKey") String searchKey,
            @Field("who") String who

    );

    @FormUrlEncoded
    @POST("PatientDashboard/GetPatientDiagnosisResult")
    Call<PatientDiagnosisResultResp> getPatientDiagnosisResult(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID,
            @Field("pmID") String pmID);

    @FormUrlEncoded
    @POST("Graph/GetNutrientInBody")
    Call<GetNutrientInBodyResp> getNutrientInBody(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID,
            @Field("pId") String pId);

    @POST("UserIntake.asmx/GetAchievedRDAPercentageColor")
    Call<GraphColorcodeRes> getColor();

    @POST("UserIntake.asmx/GetAllNutrientValuesCombinedByFoodTimeId")
    @FormUrlEncoded
    Call<NutriAnalyzerResp> GetAllNutrientValuesCombinedByFoodTimeId(
            @Header("token") String token,
            @Field("memberId") String memberId,
            @Field("foodTimeId") String foodTimeId,
            @Field("intakeDate") String intakeDate,
            @Field("userLoginId") String userLoginId
    );

    // Api use to View Intake All Prority Nutrients
    @POST("UserIntake.asmx/GetPriorityNutrientValuesByFoodTimeId")
    @FormUrlEncoded
    Call<ViewIntkAllPriortyNutriRes> viewIntkPriortyNutri(
            @Header("token") String token,
            @Field("memberId") String memberId,
            @Field("foodTimeId") String foodTimeId,
            @Field("intakeDate") String intakeDate,
            @Field("userLoginId") String userLoginId

    );

    @FormUrlEncoded
    @POST("getAllMedicineByAlphabet")
    Call<GetAllMedicineByAlphabetRes> getAllMedicineByAlphabet(@Header("x-access-Token") String token, @Field("userId") String userId);

    @FormUrlEncoded
    @POST("PatientEntryByGuard/GetPatientDetailsByPID")
    Call<List<GetPatientDetailsByPidResp>> getPatientDetailsByPID(
            @Header("accessToken") String token,
            @Header("userId") String userId,
            @Field("PID") Integer PID);

   /* @FormUrlEncoded
    @POST("PatientEntryByGuard/SavePatientEntryByGuard")
    Call<JSONObject> savePatientEntryByGuard(
            @Header("accessToken") String token,
            @Header("userID") String userID,
            @Field("userID") String userID_,
            @Field("PID") String PID,
            @Field("patientName") String patientName,
            @Field("age") String age,
            @Field("ageUnit") String ageUnit,
            @Field("gender") String gender,
            @Field("problem") String problem,
            @Field("patientImage") String patientImage
    );*/

    @Multipart
    @POST("UploadImageByGaurd.ashx")
    Call<String> savePatientEntryByGuard(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID_,
            @Part("userID") RequestBody userID,
            @Part("PID") RequestBody PID,
            @Part("patientName") RequestBody patientName,
            @Part("age") RequestBody age,
            @Part("ageUnit") RequestBody ageUnit,
            @Part("gender") RequestBody gender,
            @Part("problem") RequestBody problem,
            @Part MultipartBody.Part[] patientImage
    );

    @FormUrlEncoded
    @POST("PatientEntryByGuard/GetPatientList")
    Call<GetPatientListResp> getPatientList(
            @Header("accessToken") String token,
            @Header("userId") String userId,
            @Field("fromDate") String fromDate
    );

    @FormUrlEncoded
    @POST("PatientEntryByGuard/UpdatePatientPID")
    Call<UpdatePatientPidResp> updatePatientPID(
            @Header("accessToken") String token,
            @Header("userId") String userId,
            @Field("userId") String userId_,
            @Field("PID") String PID,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("UserIntake.asmx/GetNutrientByPrefixText")
    Call<GetNutrientByPrefixTextResp> getNutrientByPrefixText(
            @Header("token") String token,
            @Field("prefixText") String prefixText
    );

    @FormUrlEncoded
    @POST("DeficientNutrientReport.asmx/GetNutrientAverageDeficiencyBetweenTwoDates")
    Call<GetNutrientAverageDeficiencyResp> getNutrientAverageDeficiency(
            @Header("token") String token,
            @Field("memberId") String memberId,
            @Field("userLoginID") String userLoginID,
            @Field("intakeFromDate") String intakeFromDate,
            @Field("intakeToDate") String intakeToDate
    );

    @FormUrlEncoded
    @POST("IdealNutrientGraph.asmx/GetIdealNutrientIntakeGraphData")
    Call<GetIdealNutrientIntakeResp> getIdealNutrientIntakeGraphData(
            @Header("token") String token,
            @Field("memberId") String memberId,
            @Field("userLoginID") String userLoginID,
            @Field("nutrientID") String nutrientID
    );

    @FormUrlEncoded
    @POST("IdealNutrientGraph.asmx/GetNutrientSerachList")
    Call<GetNutrientSearchListResp> getNutrientSearchList(
            @Header("token") String token,
            @Field("memberId") String memberId,
            @Field("userLoginID") String userLoginID,
            @Field("searchText") String searchText
    );


}