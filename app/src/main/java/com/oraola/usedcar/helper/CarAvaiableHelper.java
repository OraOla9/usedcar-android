package com.oraola.usedcar.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class CarAvaiableHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "oraolausedcar.db";
    private static final int DB_VERSION = 2;
    public static final String TABLE_NAME = "car_available";

    public static CarAvaiableHelper getInstance(Context context, int version){
        return new CarAvaiableHelper(context,DB_NAME,null,version);
    }

    public CarAvaiableHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (carId INTEGER PRIMARY KEY, engineNumber VARCHAR NOT NULL,brand VARCHAR NOT NULL,model VARCHAR NOT NULL,grade VARCHAR NOT NULL ,acquisitionPrice DECIMAL(10, 2) NOT NULL ,acquisition_date VARCHAR NOT NULL);";
        Log.d("okokoka","ss");
        sqLiteDatabase.execSQL(sql);
        Log.d("okokokb","tt");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
