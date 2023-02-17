package com.mphstar.androidnative.belajar.sqllite;

import android.provider.BaseColumns;

public class DatabaseDataDiri {
    private DatabaseDataDiri(){};
    public static class DataDiri implements BaseColumns {
        public static final String TABLE_NAME = "datadiri";
        public static final String COLUMN_NAME_NAMA = "nama";
        public static final String COLUMN_NAME_ALAMAT = "alamat";
        public static final String COLUMN_NAME_HOBI = "hobi";
    }
    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DataDiri.TABLE_NAME + " (" +
                    DataDiri._ID + " INTEGER PRIMARY KEY," +
                    DataDiri.COLUMN_NAME_NAMA + " TEXT," +
                    DataDiri.COLUMN_NAME_ALAMAT + " TEXT, " +
                    DataDiri.COLUMN_NAME_HOBI + " TEXT)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DataDiri.TABLE_NAME;
}
