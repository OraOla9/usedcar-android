package com.oraola.usedcar.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.oraola.usedcar.bean.Car;
import com.oraola.usedcar.helper.CarSoldHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CarSoldManager {
    private static final int DB_VERSION = 2;
    public static final String TABLE_NAME = "car_sold";

    private SQLiteDatabase db;

    public CarSoldManager(Context context){
        CarSoldHelper carSoldHelper = CarSoldHelper.getInstance(context,DB_VERSION);
        db = carSoldHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<Car> findAllSoldCars(){
        db.beginTransaction();
        try {
            List<Car> cars = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
            while (cursor.moveToNext()){
                Car car = new Car();
                car.setCarId(cursor.getInt(cursor.getColumnIndex("carId")));
                car.setEngineNumber(cursor.getString(cursor.getColumnIndex("engineNumber")));
                car.setBrand(cursor.getString(cursor.getColumnIndex("brand")));
                car.setModel(cursor.getString(cursor.getColumnIndex("model")));
                car.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
                car.setAcquisitionPrice(cursor.getDouble(cursor.getColumnIndex("acquisitionPrice")));
                car.setAcquisitionDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndex("acquisition_date"))));
                car.setSalePrice(cursor.getDouble(cursor.getColumnIndex("sale_price")));
                car.setSaleDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndex("sale_data"))));
                cars.add(car);
            }
            cursor.close();
            db.setTransactionSuccessful();
            return cars;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            db.endTransaction();
        }
    }

    @SuppressLint("Range")
    public Car findSoldCarByCarId(int carid){
        db.beginTransaction();
        try {
            Car car = new Car();
            Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE carId = (?)",new String[]{Integer.toString(carid)});
            if (cursor.moveToNext()){
                car.setCarId(cursor.getInt(cursor.getColumnIndex("carId")));
                car.setEngineNumber(cursor.getString(cursor.getColumnIndex("engineNumber")));
                car.setBrand(cursor.getString(cursor.getColumnIndex("brand")));
                car.setModel(cursor.getString(cursor.getColumnIndex("model")));
                car.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
                car.setAcquisitionPrice(cursor.getDouble(cursor.getColumnIndex("acquisitionPrice")));
                car.setSalePrice(cursor.getDouble(cursor.getColumnIndex("sale_price")));
                car.setSaleDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndex("sale_data"))));
                Log.d("datadatatdtatdat",cursor.getString(cursor.getColumnIndex("acquisition_date")));

                car.setAcquisitionDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndex("acquisition_date"))));
            }
            cursor.close();
            db.setTransactionSuccessful();
            return car;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            db.endTransaction();
        }
    }


    public void addCar(List<Car> cars){
        try {
            //dropValues();
            for (Car car:cars){
                //carId INTEGER PRIMARY KEY,
                // engineNumber VARCHAR NOT NULL,
                // brand VARCHAR NOT NULL,
                // model VARCHAR NOT NULL,
                // grade VARCHAR NOT NULL ,
                // acquisitionPrice DECIMAL(10, 2) NOT NULL ,
                // acquisition_date DATE NOT NULL
                //sale_price DECIMAL(10, 2) NOT NULL
                // sale_data VARCHAR NOT NULL
                db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES(?,?,?,?,?,?,?,?,?)",new Object[]{car.getCarId(),car.getEngineNumber(),car.getBrand(),car.getModel(),car.getGrade(),car.getAcquisitionPrice(),new SimpleDateFormat("yyyy-MM-dd").format(car.getAcquisitionDate()),car.getSalePrice(),new SimpleDateFormat("yyyy-MM-dd").format(car.getSaleDate())});
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    private void dropValues(){
        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM " + TABLE_NAME);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void closeDB(){
        db.close();
    }


}
