package com.ambit.database;

public class DBUtility {
    public static final String DATABASE_NAME = "TINKER_DB";
    public static final String TABLE_LOGIN = "USER_LOGIN";

    // USER_LOGIN TABLE FIELD
    public static final String USER_ID = "user_id";
    public static final String FB_ID = "facebook_id";
    public static final String FULL_NAME = "full_name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String DOB = "dob";
    public static final String GENDER = "gender";
    public static final String COUNTRY_ID = "country_id";
    public static final String COUNTRY_NAME = "country_name";
    public static final String PROFILE_PHOTO_LARGE = "profile_large";
    public static final String PROFILE_PHOTO_THUMB = "profile_thumb";

    public static final String CREATE_LOGIN_TABLE = "CREATE TABLE IF NOT EXISTS " + DBUtility.TABLE_LOGIN + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FB_ID + " TEXT, "
            + FULL_NAME + " TEXT, "
            + EMAIL + " TEXT, "
            + PHONE + " TEXT, "
            + DOB + " TEXT, "
            + GENDER + " TEXT, "
            + COUNTRY_ID + " INTEGER, "
            + COUNTRY_NAME + " TEXT, "
            + PROFILE_PHOTO_LARGE + " TEXT, "
            + PROFILE_PHOTO_THUMB + " TEXT " + ")";

}
