package com.example.final_mobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.final_mobile.model.YogaClass;
import com.example.final_mobile.model.YogaCourse;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "yoga_course_db";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(YogaCourse.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + YogaCourse.TABLE_NAME);

        onCreate(sqLiteDatabase);

    }
    public YogaCourse getCourse(long id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(YogaCourse.TABLE_NAME,
                new String[]{
                        YogaCourse.COLUMN_ID,
                        YogaCourse.COLUMN_NAME,
                        YogaCourse.COLUMN_DAY_OF_THE_WEEK,
                        YogaCourse.COLUMN_TIME_OF_COURSE,
                        YogaCourse.COLUMN_CAPACITY,
                        YogaCourse.COLUMN_DURATION,
                        YogaCourse.COLUMN_TYPE_OF_CLASS,
                        YogaCourse.COLUMN_PRICE_PER_CLASS,
                        YogaCourse.COLUMN_DESCRIPTION},
                YogaCourse.COLUMN_ID + "=?",
                new String[]{
                        String.valueOf(id)
                },
                null,
                null,
                null,
                null);

        if (cursor !=null)
            cursor.moveToFirst();

        YogaCourse course = new YogaCourse(
                cursor.getInt(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_DAY_OF_THE_WEEK)),
                cursor.getString(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_TIME_OF_COURSE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_CAPACITY)),
                cursor.getInt(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_DURATION)),
                cursor.getString(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_TYPE_OF_CLASS)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_PRICE_PER_CLASS)),
                cursor.getString(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_DESCRIPTION))
        );

        cursor.close();
        return course;

    }

    public ArrayList<YogaCourse> getAllCourse(){
        ArrayList<YogaCourse> courses = new ArrayList<>();
//        Select all courses from table
        String selectQuery = "SELECT * FROM " +YogaCourse.TABLE_NAME + " ORDER BY "+
                YogaCourse.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        //cursor.moveToFirst() return true
        if (cursor.moveToFirst()){
            do{
                //create new course object
                YogaCourse course = new YogaCourse();
                //set all properties of course object
                course.setId(cursor.getInt(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_ID)));
                course.setCourse_name(cursor.getString(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_NAME)));
                course.setDay_of_the_week(cursor.getString(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_DAY_OF_THE_WEEK)));
                course.setTime_of_course(cursor.getString(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_TIME_OF_COURSE)));
                course.setCapacity(cursor.getInt(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_CAPACITY)));
                course.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_DURATION)));
                course.setType_of_class(cursor.getString(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_TYPE_OF_CLASS)));
                course.setPrice_per_class(cursor.getDouble(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_PRICE_PER_CLASS)));
                course.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(YogaCourse.COLUMN_DESCRIPTION)));

                courses.add(course);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return courses;
    }
    public long insertCourse(String name, String day_of_the_week, String time_of_course, int capacity, int duration, String type_of_class, double price_per_class, String description){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(YogaCourse.COLUMN_NAME, name);
        values.put(YogaCourse.COLUMN_DAY_OF_THE_WEEK, day_of_the_week);
        values.put(YogaCourse.COLUMN_TIME_OF_COURSE, time_of_course);
        values.put(YogaCourse.COLUMN_CAPACITY, capacity);
        values.put(YogaCourse.COLUMN_DURATION, duration);
        values.put(YogaCourse.COLUMN_TYPE_OF_CLASS, type_of_class);
        values.put(YogaCourse.COLUMN_PRICE_PER_CLASS, price_per_class);
        values.put(YogaCourse.COLUMN_DESCRIPTION, description);

        long id = db.insert(YogaCourse.TABLE_NAME, null, values);

        db.close();

        return id;
    }
    public int updateCourse(YogaCourse course){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(YogaCourse.COLUMN_NAME, course.getCourse_name());
        values.put(YogaCourse.COLUMN_DAY_OF_THE_WEEK, course.getDay_of_the_week());
        values.put(YogaCourse.COLUMN_TIME_OF_COURSE, course.getTime_of_course());
        values.put(YogaCourse.COLUMN_CAPACITY, course.getCapacity());
        values.put(YogaCourse.COLUMN_DURATION, course.getDuration());
        values.put(YogaCourse.COLUMN_TYPE_OF_CLASS, course.getType_of_class());
        values.put(YogaCourse.COLUMN_PRICE_PER_CLASS, course.getPrice_per_class());
        values.put(YogaCourse.COLUMN_DESCRIPTION, course.getDescription());


        int rowsUpdated = db.update(YogaCourse.TABLE_NAME, values, YogaCourse.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(course.getId())});

        db.close(); // Close the database

        return rowsUpdated;

    }

    public void deleteCourse(YogaCourse course){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(YogaCourse.TABLE_NAME, YogaCourse.COLUMN_ID + " = ?",
                new String[]{String.valueOf(course.getId())}
        );
        db.close();
    }

}
