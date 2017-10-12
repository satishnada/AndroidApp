package com.ambit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.ambit.model.LoginRequestResponse;
import com.ambit.model.UserData;

public class DBHelper extends SQLiteOpenHelper {

    private static String TAG = DBHelper.class.getSimpleName();
    public static DBHelper dbHelper;

    public DBHelper(Context context) {
        super(context, DBUtility.DATABASE_NAME, null, 2);
    }

    public static DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBUtility.CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DBUtility.TABLE_LOGIN);
        onCreate(db);
    }

    public void truncateData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + DBUtility.TABLE_LOGIN); //delete all rows in a table
        //db.close();
    }

    public int getDbCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBUtility.TABLE_LOGIN, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

//    public void insertUserLoginData(UserData userData) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        Log.i(TAG, "USER DATA INSERT");
//        values.put(DBUtility.USER_ID, userData.getUser_id());
//        values.put(DBUtility.FB_ID, userData.getFacebookId());
//        values.put(DBUtility.FULL_NAME, userData.getFull_name());
//        values.put(DBUtility.EMAIL, userData.getEmail());
//        values.put(DBUtility.PHONE, userData.getPhone());
//        values.put(DBUtility.DOB, userData.getDob());
//        values.put(DBUtility.GENDER, userData.getGender());
//        values.put(DBUtility.COUNTRY_ID, userData.getCountry_id());
//        values.put(DBUtility.COUNTRY_NAME, userData.getCountry_name());
//        values.put(DBUtility.PROFILE_PHOTO_LARGE, userData.getProfile_photo_large());
//        values.put(DBUtility.PROFILE_PHOTO_THUMB, userData.getProfile_photo_thumb());
//        db.insert(DBUtility.TABLE_LOGIN, null, values);
//    }

//    public UserData getUserLoginData() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from " + DBUtility.TABLE_LOGIN, null);
//        Log.e("db size userdata", cursor.getCount() + "");
//        LoginRequestResponse.UserData userLoginData = null;
//        if (cursor.moveToFirst()) {
//            userLoginData = new LoginRequestResponse().new UserData();
//            userLoginData.setUser_id(cursor.getInt(cursor.getColumnIndex(DBUtility.USER_ID)));
//            userLoginData.setFacebookId(cursor.getString(cursor.getColumnIndex(DBUtility.FB_ID)));
//            userLoginData.setFull_name(cursor.getString(cursor.getColumnIndex(DBUtility.FULL_NAME)));
//            userLoginData.setEmail(cursor.getString(cursor.getColumnIndex(DBUtility.EMAIL)));
//            userLoginData.setPhone(cursor.getString(cursor.getColumnIndex(DBUtility.PHONE)));
//            userLoginData.setDob(cursor.getString(cursor.getColumnIndex(DBUtility.DOB)));
//            userLoginData.setGender(cursor.getString(cursor.getColumnIndex(DBUtility.GENDER)));
//            userLoginData.setCountry_id(cursor.getInt(cursor.getColumnIndex(DBUtility.COUNTRY_ID)));
//            userLoginData.setCountry_name(cursor.getString(cursor.getColumnIndex(DBUtility.COUNTRY_NAME)));
//            userLoginData.setProfile_photo_thumb(cursor.getString(cursor.getColumnIndex(DBUtility.PROFILE_PHOTO_THUMB)));
//            userLoginData.setProfile_photo_large(cursor.getString(cursor.getColumnIndex(DBUtility.PROFILE_PHOTO_LARGE)));
//        }
//        cursor.close();
//        return userLoginData;
//    }

}
