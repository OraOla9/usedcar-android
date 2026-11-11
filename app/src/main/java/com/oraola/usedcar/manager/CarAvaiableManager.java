package com.oraola.usedcar.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.oraola.usedcar.bean.Car;
import com.oraola.usedcar.helper.CarAvaiableHelper;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CarAvaiableManager{
    private static final int DB_VERSION = 2;
    public static final String TABLE_NAME = "car_available";

    private SQLiteDatabase db;

    public CarAvaiableManager(Context context) {
        CarAvaiableHelper carAvaiableHelper = CarAvaiableHelper.getInstance(context,DB_VERSION);
        db = carAvaiableHelper.getWritableDatabase();
    }


    @SuppressLint("Range")
    public List<Car> findAllAvailableCars(){
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
                cars.add(car);
            }
            cursor.close();
            db.setTransactionSuccessful();
            Log.d("qqqqq",cars.toString());
            return cars;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            db.endTransaction();
        }
    }

    @SuppressLint("Range")
    public List<Car> findAvailableCarByBrand(String brand){
        db.beginTransaction();
        try {
            List<Car> cars = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE brand LIKE ?",new String[]{"%"+brand+"%"});
            Log.d("sql",cursor.toString());
            while (cursor.moveToNext()){
                Car car = new Car();
                car.setCarId(cursor.getInt(cursor.getColumnIndex("carId")));
                car.setEngineNumber(cursor.getString(cursor.getColumnIndex("engineNumber")));
                car.setBrand(cursor.getString(cursor.getColumnIndex("brand")));
                car.setModel(cursor.getString(cursor.getColumnIndex("model")));
                car.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
                car.setAcquisitionPrice(cursor.getDouble(cursor.getColumnIndex("acquisitionPrice")));
                car.setAcquisitionDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndex("acquisition_date"))));
                cars.add(car);
            }
            Log.d("1111111111111",cars.toString());
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
    public List<Car> findAvailableCarByModel(String model){
        db.beginTransaction();
        try {
            List<Car> cars = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE model LIKE ?",new String[]{"%"+model+"%"});
            Log.d("sql2",cursor.toString());
            while (cursor.moveToNext()){
                Car car = new Car();
                car.setCarId(cursor.getInt(cursor.getColumnIndex("carId")));
                car.setEngineNumber(cursor.getString(cursor.getColumnIndex("engineNumber")));
                car.setBrand(cursor.getString(cursor.getColumnIndex("brand")));
                car.setModel(cursor.getString(cursor.getColumnIndex("model")));
                car.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
                car.setAcquisitionPrice(cursor.getDouble(cursor.getColumnIndex("acquisitionPrice")));
                car.setAcquisitionDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndex("acquisition_date"))));
                cars.add(car);
            }
            Log.d("222222222222222222",cars.toString());
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
    public Car findAvailableCarByCarId(int carid){
        db.beginTransaction();
        try {
            Car car = new Car();
            Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE carId = ?",new String[]{Integer.toString(carid)});
            if (cursor.moveToNext()){
                car.setCarId(cursor.getInt(cursor.getColumnIndex("carId")));
                car.setEngineNumber(cursor.getString(cursor.getColumnIndex("engineNumber")));
                car.setBrand(cursor.getString(cursor.getColumnIndex("brand")));
                car.setModel(cursor.getString(cursor.getColumnIndex("model")));
                car.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
                car.setAcquisitionPrice(cursor.getDouble(cursor.getColumnIndex("acquisitionPrice")));
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
            dropValues();
            for (Car car:cars){
                //carId INTEGER PRIMARY KEY,
                // engineNumber VARCHAR NOT NULL,
                // brand VARCHAR NOT NULL,
                // model VARCHAR NOT NULL,
                // grade VARCHAR NOT NULL ,
                // acquisitionPrice DECIMAL(10, 2) NOT NULL ,
                // acquisition_date DATE NOT NULL
                db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES(?,?,?,?,?,?,?)",new Object[]{car.getCarId(),car.getEngineNumber(),car.getBrand(),car.getModel(),car.getGrade(),car.getAcquisitionPrice(),new SimpleDateFormat("yyyy-MM-dd").format(car.getAcquisitionDate())});
            }

        } catch (Exception e) {
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
