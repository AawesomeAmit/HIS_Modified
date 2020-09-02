package com.his.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.his.android.Model.AdmittedPatientICU;
import com.his.android.Model.PatientVitalGraph;
import com.his.android.Model.SubDept;
import com.his.android.Model.VitalList;
import com.his.android.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseController {
    public static SQLiteDatabase myDataBase;
    private static DataBaseHelper myDataBaseHelper;

    public static void openDataBase(Context mContext) {
        if (myDataBaseHelper == null) {
            myDataBaseHelper = new DataBaseHelper(mContext);
        }
        if (myDataBase == null) {
            myDataBase = myDataBaseHelper.getWritableDatabase();
        }
    }

    public static void removeTable(String tableName) {
        myDataBase.delete(tableName, null, null);
    }

    public static long insertData(ContentValues values, String tableName) {
        long id = -1;
        try {
            id = myDataBase.insert(tableName, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.print("errorInInsertion " + e.toString());
        }
        Utils.print("====insertData " + id);

        return id;
    }

    public static long insertUpdateData(ContentValues values, String tableName, String columnName, String uniqueId) {
        try {

            if (checkRecordExist(tableName, columnName, uniqueId)) {
                Log.d("update-data",values.toString());
                Log.d("unique-id",uniqueId);
                return (long) myDataBase.update(tableName, values, columnName + "='" + uniqueId + "'", null);
            }
            Log.d("insert-data",values.toString());
            Log.d("unique-id",uniqueId);
            return myDataBase.insert(tableName, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    public static void updateEqual(ContentValues mContentValues, String tableName, String columnName, String columnValue) {
        myDataBase.update(tableName, mContentValues, columnName + " = '" + columnValue + "'", null);
    }

    public static void updateNotEqual(ContentValues mContentValues, String tableName, String columnName, String columnValue) {
        myDataBase.update(tableName, mContentValues, columnName + " != '" + columnValue + "'", null);
    }

    public static long insertUpdateNotData(ContentValues values, String tableName, String columnName, String uniqueId) {
        try {
            if (checkRecordExist(tableName, columnName, uniqueId)) {
                return (long) myDataBase.update(tableName, values, columnName + "!='" + uniqueId + "'", null);
            }
            return myDataBase.insert(tableName, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static long insertUpdateDataMultiple(ContentValues values, String tableName, String where) {
        try {
            if (checkRecordExistMultiple(tableName, where)) {
                return (long) myDataBase.update(tableName, values, where, null);
            }
            return myDataBase.insert(tableName, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean checkRecordExist(String tableName, String columnName, String uniqueId) {
        boolean status = false;
        Cursor cursor = myDataBase.query(tableName, null, columnName + "='" + uniqueId + "'", null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                status = true;
            }
            cursor.close();
        }
        return status;
    }

    public static boolean checkRecordExistWhere(String tableName, String where) {
        boolean status = false;
        Cursor cursor = myDataBase.query(tableName, null, where, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                status = true;
            }
            cursor.close();
        }
        return status;
    }

    public static boolean checkRecordExistMultiple(String tableName, String where) {
        boolean status = false;
        Cursor cursor = myDataBase.query(tableName, null, where, null, null, null, null);
        Utils.print("isDataExit-SubCategory" + cursor.getCount());
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                status = true;
            }
            cursor.close();
        }
        return status;
    }

    public static long isDataExit(String tableName) {
        long cnt = DatabaseUtils.queryNumEntries(myDataBase, tableName);
        Utils.print("isDataExit " + cnt);
        Log.d("isDataExit", String.valueOf(cnt));
        return cnt;
    }

    public static boolean deleteRow(String tableName, String keyName, String keyValue) {
        try {

            Log.v("##delete:",keyName+":"+keyValue);

            return myDataBase.delete(tableName, new StringBuilder().append(keyName).append("=").append("'"+keyValue+"'").toString(), null) > 0;


//            return myDataBase.delete(tableName, keyName+"="+keyValue, null) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public static int delete(String tableName, String where, String[] args) {
        return myDataBase.delete(tableName, where, args);
    }

    public static ArrayList<JSONObject> fetchAllDataFromValues(String tableName) {
        String query = "SELECT * FROM " + tableName;
        Utils.print("===fetchAllDataFromValues" + query);
        Cursor cursor = myDataBase.rawQuery(query, null);
        ArrayList<JSONObject> arrayList = new ArrayList();
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                JSONObject jsonObject = new JSONObject();
                for (int j = 0; j < cursor.getColumnCount(); j++) {
                    try {
                        jsonObject.put(cursor.getColumnName(j), cursor.getString(j));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                arrayList.add(jsonObject);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }
    
    public static ArrayList<HashMap<String, String>> getICDList() {

        ArrayList<HashMap<String, String>> CatList = new ArrayList();
        CatList.clear();
        Cursor cur;
        String query="";

       // query = "SELECT * from "+TableTest.test +" where "+ TableTest.testColumn.city +" = '"+ city +"'  GROUP BY id order by "+  filter +"  LIMIT "+ limit +" , "+ 20;
        query = "SELECT * from "+ TableMedicineList.icd_list +" order by icdId";//" asc LIMIT "+ limit +" , "+ 2000;
        cur = myDataBase.rawQuery(query,null);

        Log.d("getStepData-query1", query);
        Log.d("getStepData-cur", String.valueOf(cur.getCount()));

        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                HashMap<String, String> hashlist = new HashMap();
                hashlist.put("icdId", cur.getString(cur.getColumnIndex(TableMedicineList.icdListColumn.icdId.toString())));
                hashlist.put("icdCode", cur.getString(cur.getColumnIndex(TableMedicineList.icdListColumn.icdCode.toString())));
                CatList.add(hashlist);
                cur.moveToNext();
            }
        }
        cur.close();
        return CatList;
    }

//    public static ArrayList<HashMap<String, String>> getICUAdmittedPatientList() {
    public static List<AdmittedPatientICU> getICUAdmittedPatientList(String subDeptID, String headId_subDeptId) {

        List<AdmittedPatientICU> list = new ArrayList<>();
       // CatList.clear();
        Cursor cur;
        String query="";

       // query = "SELECT * from "+TableTest.test +" where "+ TableTest.testColumn.city +" = '"+ city +"'  GROUP BY id order by "+  filter +"  LIMIT "+ limit +" , "+ 20;
        //query = "SELECT * from "+ TableICUAdmittedPatientList.icu_admitted_patient_list;//" asc LIMIT "+ limit +" , "+ 2000;
        query = "SELECT * from "+ TableICUAdmittedPatientList.icu_admitted_patient_list+" where "+ TableICUAdmittedPatientList.icuPatientColumn.subDeptID +" = '"+ subDeptID +"'"+
        " and "+ TableICUAdmittedPatientList.icuPatientColumn.headId_subDeptId +" = '"+ headId_subDeptId +"'";
        cur = myDataBase.rawQuery(query,null);

        Log.d("getStepData-query1", query);
        Log.d("getStepData-cur", String.valueOf(cur.getCount()));

        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                AdmittedPatientICU admittedPatientICUe = new AdmittedPatientICU(null,null,null,null,null,null);
                admittedPatientICUe.setPmid(Integer.valueOf(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.pmid.toString()))));
                admittedPatientICUe.setWardID(Integer.valueOf(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.wardID.toString()))));

                if(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.previousWardID.toString())) != null){
                    admittedPatientICUe.setPreviousWardID(Integer.valueOf(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.previousWardID.toString()))));
                }else {
                    admittedPatientICUe.setPreviousWardID(null);
                }

                admittedPatientICUe.setBedName(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.bedName.toString())));
                admittedPatientICUe.setPid(Integer.valueOf(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.pid.toString()))));
                admittedPatientICUe.setCrNo(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.crNo.toString())));
                admittedPatientICUe.setIpNo(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.ipNo.toString())));
                admittedPatientICUe.setUserID(Integer.valueOf(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.userID.toString()))));
                admittedPatientICUe.setSubDeptID(Integer.valueOf(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.subDeptID.toString()))));
                admittedPatientICUe.setSubDepartmentName(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.subDepartmentName.toString())));
                admittedPatientICUe.setStatus(Integer.valueOf(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.status.toString()))));
                admittedPatientICUe.setConsultantName(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.consultantName.toString())));
                admittedPatientICUe.setPname(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.pname.toString())));
                admittedPatientICUe.setSex(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.sex.toString())));
                admittedPatientICUe.setGender(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.gender.toString())));
                admittedPatientICUe.setAge(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.age.toString())));
                admittedPatientICUe.setAgeUnit(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.ageUnit.toString())));
                admittedPatientICUe.setAddress(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.address.toString())));
                admittedPatientICUe.setFatherName(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.fatherName.toString())));
                admittedPatientICUe.setPhoneNo(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.phoneNo.toString())));
                admittedPatientICUe.setRelation(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.relation.toString())));
                admittedPatientICUe.setAdmitDate(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.admitDate.toString())));
                admittedPatientICUe.setDischargeDate(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.dischargeDate.toString())));
                admittedPatientICUe.setDdate(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.ddate.toString())));
                admittedPatientICUe.setDischargeStatus(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.dischargeStatus.toString())));
                admittedPatientICUe.setPreviousWardName(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.previousWardName.toString())));
                admittedPatientICUe.setNotificationCount(Integer.valueOf(cur.getString(cur.getColumnIndex(TableICUAdmittedPatientList.icuPatientColumn.notificationCount.toString()))));

                list.add(admittedPatientICUe);
                cur.moveToNext();
            }
        }
        cur.close();
        return list;
    }


    public static List<SubDept> getSubDeptList(String headID) {

      //  ArrayList<HashMap<String, String>> CatList = new ArrayList();
        List<SubDept> list = new ArrayList<>();
       // CatList.clear();
        Cursor cur;
        String query="";

       // query = "SELECT * from "+TableTest.test +" where "+ TableTest.testColumn.city +" = '"+ city +"'  GROUP BY id order by "+  filter +"  LIMIT "+ limit +" , "+ 20;

       // query = "SELECT * from " + TableSubDeptList.sub_dept_list;//" asc LIMIT "+ limit +" , "+ 2000;

        query = "SELECT * from " + TableSubDeptList.sub_dept_list +" where "+ TableSubDeptList.subDeptListColumn.headID +" = '"+ headID +"'";

        cur = myDataBase.rawQuery(query,null);

        Log.d("getStepData-query1", query);
        Log.d("getStepData-cur", String.valueOf(cur.getCount()));

        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {

                SubDept subDept = new SubDept(null,null);
                subDept.setId(Integer.valueOf(cur.getString(cur.getColumnIndex(TableSubDeptList.subDeptListColumn.id.toString()))));
                subDept.setHeadID(Integer.valueOf(cur.getString(cur.getColumnIndex(TableSubDeptList.subDeptListColumn.headID.toString()))));
                subDept.setBgColor(cur.getString(cur.getColumnIndex(TableSubDeptList.subDeptListColumn.bgColor.toString())));
                subDept.setSubDepartmentName(cur.getString(cur.getColumnIndex(TableSubDeptList.subDeptListColumn.subDepartmentName.toString())));

                //CatList.add(hashlist);
                list.add(subDept);
                cur.moveToNext();
            }
        }
        cur.close();
        //return CatList;
        return list;
    }

    public static List<VitalList> getPatientVitalList(String pid_headID) {

      //  ArrayList<HashMap<String, String>> CatList = new ArrayList();
        List<VitalList> list = new ArrayList<>();
       // CatList.clear();
        Cursor cur;
        String query = "";

       // query = "SELECT * from "+TableTest.test +" where "+ TableTest.testColumn.city +" = '"+ city +"'  GROUP BY id order by "+  filter +"  LIMIT "+ limit +" , "+ 20;

       // query = "SELECT * from " + TableSubDeptList.sub_dept_list;//" asc LIMIT "+ limit +" , "+ 2000;

        query = "SELECT * from " + TablePatientVitalList.patient_vital_list +" where "+ TablePatientVitalList.patientVitalListColumn.pId_headId +" = '"+ pid_headID +"'";

        cur = myDataBase.rawQuery(query,null);

        Log.d("getStepData-query1", query);
        Log.d("getStepData-cur", String.valueOf(cur.getCount()));

        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {

                VitalList vitalList = new VitalList();
                vitalList.setVmID(Integer.valueOf(cur.getString(cur.getColumnIndex(TablePatientVitalList.patientVitalListColumn.vmID.toString()))));
                vitalList.setVitalName((cur.getString(cur.getColumnIndex(TablePatientVitalList.patientVitalListColumn.vitalName.toString()))));
                vitalList.setVitalUnit(cur.getString(cur.getColumnIndex(TablePatientVitalList.patientVitalListColumn.vitalUnit.toString())));
                vitalList.setVitalValue(cur.getString(cur.getColumnIndex(TablePatientVitalList.patientVitalListColumn.vitalValue.toString())));

                list.add(vitalList);
                cur.moveToNext();
            }
        }
        cur.close();
        //return CatList;
        return list;
    }

    public static List<PatientVitalGraph> getPatientVitalGraph(String pid_headID, String createdDate) {

      //  ArrayList<HashMap<String, String>> CatList = new ArrayList();
        List<PatientVitalGraph> list = new ArrayList<>();
       // CatList.clear();
        Cursor cur;
        String query = "";

       // query = "SELECT * from "+TableTest.test +" where "+ TableTest.testColumn.city +" = '"+ city +"'  GROUP BY id order by "+  filter +"  LIMIT "+ limit +" , "+ 20;

       // query = "SELECT * from " + TableSubDeptList.sub_dept_list;//" asc LIMIT "+ limit +" , "+ 2000;

        query = "SELECT * from " + TablePatientVitalGraph.patient_vital_graph +" where "
                + TablePatientVitalGraph.patientVitalGraphColumn.pId_headId +" = '"+ pid_headID +"'"
                + " and " + TablePatientVitalGraph.patientVitalGraphColumn.createdDate +" = '"+ createdDate +"'";

        cur = myDataBase.rawQuery(query,null);

        Log.d("getStepData-query1", query);
        Log.d("getStepData-cur", String.valueOf(cur.getCount()));

        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {

                PatientVitalGraph vitalGraph = new PatientVitalGraph();
                vitalGraph.setbP_Dias((cur.getString(cur.getColumnIndex(TablePatientVitalGraph.patientVitalGraphColumn.bp_dias.toString()))));
                vitalGraph.setbP_Sys((cur.getString(cur.getColumnIndex(TablePatientVitalGraph.patientVitalGraphColumn.bp_sys.toString()))));
                vitalGraph.setHeartRate(cur.getString(cur.getColumnIndex(TablePatientVitalGraph.patientVitalGraphColumn.heartRate.toString())));
                vitalGraph.setHr(Integer.parseInt(cur.getString(cur.getColumnIndex(TablePatientVitalGraph.patientVitalGraphColumn.hr.toString()))));
                vitalGraph.setPulse(cur.getString(cur.getColumnIndex(TablePatientVitalGraph.patientVitalGraphColumn.pulse.toString())));
                vitalGraph.setRespRate(cur.getString(cur.getColumnIndex(TablePatientVitalGraph.patientVitalGraphColumn.respRate.toString())));
                vitalGraph.setSpo2(cur.getString(cur.getColumnIndex(TablePatientVitalGraph.patientVitalGraphColumn.spo2.toString())));
                vitalGraph.setX(Integer.parseInt(cur.getString(cur.getColumnIndex(TablePatientVitalGraph.patientVitalGraphColumn.x.toString()))));
                vitalGraph.setCreatedDate(cur.getString(cur.getColumnIndex(TablePatientVitalGraph.patientVitalGraphColumn.createdDate.toString())));

                list.add(vitalGraph);
                cur.moveToNext();
            }
        }
        cur.close();
        //return CatList;
        return list;
    }

    public static int getAllICDListCount() {

        Cursor cur = myDataBase.rawQuery("SELECT * from "+ TableMedicineList.icd_list, null);
        int count =  cur.getCount();
        return count;

    }

    public static int getAllICUPatientListCount() {

        Cursor cur = myDataBase.rawQuery("SELECT * from "+ TableICUAdmittedPatientList.icu_admitted_patient_list, null);
        int count =  cur.getCount();
        return count;

    }

}
