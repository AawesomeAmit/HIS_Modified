package com.his.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.his.android.R;

public class DataBaseHelper extends SQLiteOpenHelper {
    DataBaseHelper(Context context) {
        super(context, context.getString(R.string.database_name), null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableMedicineList.SQL_CREATE_ICD_LIST);
        db.execSQL(TableICUAdmittedPatientList.SQL_CREATE_ICU_ADMITTED_PATIENT_LIST);
        db.execSQL(TablePatientVitalGraph.SQL_CREATE_PATIENT_VITAL_GRAPH);
        db.execSQL(TableSubDeptList.SQL_CREATE_SUB_DEPT_LIST);
        db.execSQL(TablePatientVitalList.SQL_CREATE_PATIENT_VITAL_LIST);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TableMedicineList.icd_list);
            db.execSQL("DROP TABLE IF EXISTS " + TableICUAdmittedPatientList.icu_admitted_patient_list);
            db.execSQL("DROP TABLE IF EXISTS " + TableSubDeptList.sub_dept_list);
            db.execSQL("DROP TABLE IF EXISTS " + TablePatientVitalGraph.patient_vital_graph);
            db.execSQL("DROP TABLE IF EXISTS " + TablePatientVitalList.patient_vital_list);
            onCreate(db);
        }
    }

    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
}
