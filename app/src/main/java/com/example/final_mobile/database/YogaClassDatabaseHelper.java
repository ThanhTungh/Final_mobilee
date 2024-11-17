package com.example.final_mobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.final_mobile.model.YogaClass;
import com.example.final_mobile.model.YogaCourse;

import java.util.ArrayList;

public class YogaClassDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "class_db";

    public YogaClassDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(YogaClass.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + YogaClass.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public ArrayList<YogaClass> getAllClasses(int course_id){
        ArrayList<YogaClass> classes = new ArrayList<>();


        String selectQuery = "SELECT * FROM " + YogaClass.TABLE_NAME +
                " WHERE " + YogaClass.COLUMN_CLASS_INSTANCE_COURSE_ID + " = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(course_id)});

        if (cursor.moveToFirst()){
            do{
                YogaClass yogaClass = new YogaClass();
                yogaClass.setYoga_class_id(cursor.getInt(cursor.getColumnIndexOrThrow(YogaClass.COLUMN_CLASS_INSTANCE_ID)));
                yogaClass.setYoga_class_course_id(cursor.getInt(cursor.getColumnIndexOrThrow(YogaClass.COLUMN_CLASS_INSTANCE_COURSE_ID)));
                yogaClass.setYoga_class_teacher_name(cursor.getString(cursor.getColumnIndexOrThrow(YogaClass.COLUMN_CLASS_INSTANCE_TEACHER_NAME)));
                yogaClass.setYoga_class_date(cursor.getString(cursor.getColumnIndexOrThrow(YogaClass.COLUMN_CLASS_INSTANCE_CLASS_DATE)));
                yogaClass.setYoga_class_comment(cursor.getString(cursor.getColumnIndexOrThrow(YogaClass.COLUMN_CLASS_INSTANCE_COMMENT)));

                classes.add(yogaClass);

            }while(cursor.moveToNext());
        }

        db.close();

        return classes;
    }

    public long insertClass(int yoga_class_course_id, String yoga_class_teacher_name, String yoga_class_date, String yoga_class_comment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(YogaClass.COLUMN_CLASS_INSTANCE_COURSE_ID, yoga_class_course_id);
        values.put(YogaClass.COLUMN_CLASS_INSTANCE_TEACHER_NAME, yoga_class_teacher_name);
        values.put(YogaClass.COLUMN_CLASS_INSTANCE_CLASS_DATE, yoga_class_date);
        values.put(YogaClass.COLUMN_CLASS_INSTANCE_COMMENT, yoga_class_comment);

        long id = db.insert(YogaClass.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public int updateClass(YogaClass yogaClass){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(YogaClass.COLUMN_CLASS_INSTANCE_COURSE_ID, yogaClass.getYoga_class_course_id());
        values.put(YogaClass.COLUMN_CLASS_INSTANCE_TEACHER_NAME, yogaClass.getYoga_class_teacher_name());
        values.put(YogaClass.COLUMN_CLASS_INSTANCE_CLASS_DATE, yogaClass.getYoga_class_date());
        values.put(YogaClass.COLUMN_CLASS_INSTANCE_COMMENT, yogaClass.getYoga_class_comment());

        int rowsUpdated = db.update(YogaClass.TABLE_NAME, values, YogaClass.COLUMN_CLASS_INSTANCE_ID + " =? ",
                new String[]{String.valueOf(yogaClass.getYoga_class_id())});

        db.close(); // Close the database

        return rowsUpdated;

    }

    public void deleteClass(YogaClass yogaClass){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(YogaClass.TABLE_NAME, YogaClass.COLUMN_CLASS_INSTANCE_ID + " = ?",
                new String[]{String.valueOf(yogaClass.getYoga_class_id())}
        );
        db.close();
    }
    //Get all class by teacher name
    public ArrayList<YogaClass> getAllClassesByTeacherName(String teacherName){
        ArrayList<YogaClass> classes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + YogaClass.TABLE_NAME +
                " WHERE " + YogaClass.COLUMN_CLASS_INSTANCE_TEACHER_NAME + " LIKE ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{"%" + teacherName + "%"});

        if (cursor.moveToFirst()){
            do{
                YogaClass yogaClass = new YogaClass();
                yogaClass.setYoga_class_id(cursor.getInt(cursor.getColumnIndexOrThrow(YogaClass.COLUMN_CLASS_INSTANCE_ID)));
                yogaClass.setYoga_class_course_id(cursor.getInt(cursor.getColumnIndexOrThrow(YogaClass.COLUMN_CLASS_INSTANCE_COURSE_ID)));
                yogaClass.setYoga_class_teacher_name(cursor.getString(cursor.getColumnIndexOrThrow(YogaClass.COLUMN_CLASS_INSTANCE_TEACHER_NAME)));
                yogaClass.setYoga_class_date(cursor.getString(cursor.getColumnIndexOrThrow(YogaClass.COLUMN_CLASS_INSTANCE_CLASS_DATE)));
                yogaClass.setYoga_class_comment(cursor.getString(cursor.getColumnIndexOrThrow(YogaClass.COLUMN_CLASS_INSTANCE_COMMENT)));

                classes.add(yogaClass);

            }while(cursor.moveToNext());
        }

        db.close();

        return classes;
    }

    public YogaClass getClass(long id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(YogaClass.TABLE_NAME,
                new String[]{
                        YogaClass.COLUMN_CLASS_INSTANCE_ID,
                        YogaClass.COLUMN_CLASS_INSTANCE_COURSE_ID,
                        YogaClass.COLUMN_CLASS_INSTANCE_TEACHER_NAME,
                        YogaClass.COLUMN_CLASS_INSTANCE_CLASS_DATE,
                        YogaClass.COLUMN_CLASS_INSTANCE_COMMENT},
                YogaClass.COLUMN_CLASS_INSTANCE_ID + "=?",
                new String[]{
                        String.valueOf(id)
                },
                null,
                null,
                null,
                null);

        if (cursor !=null)
            cursor.moveToFirst();

        YogaClass yogaClass = new YogaClass(
                cursor.getInt(cursor.getColumnIndexOrThrow(YogaClass.COLUMN_CLASS_INSTANCE_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(YogaClass.COLUMN_CLASS_INSTANCE_COURSE_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(YogaClass.COLUMN_CLASS_INSTANCE_TEACHER_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(YogaClass.COLUMN_CLASS_INSTANCE_CLASS_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(YogaClass.COLUMN_CLASS_INSTANCE_COMMENT))
        );

        cursor.close();
        return yogaClass;

    }

}
