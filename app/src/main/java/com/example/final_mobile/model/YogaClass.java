package com.example.final_mobile.model;

public class YogaClass {
    public static final String TABLE_NAME = "yoga_class";
    public static final String COLUMN_CLASS_INSTANCE_ID = "yoga_class_id";
    public static final String COLUMN_CLASS_INSTANCE_COURSE_ID = "yoga_class_course_id";
    public static final String COLUMN_CLASS_INSTANCE_TEACHER_NAME = "yoga_class_teacher_name";
    public static final String COLUMN_CLASS_INSTANCE_CLASS_DATE = "yoga_class_date";
    public static final String COLUMN_CLASS_INSTANCE_COMMENT = "yoga_class_comment";

    private int yoga_class_id;
    private int yoga_class_course_id;
    private String yoga_class_teacher_name;
    private String yoga_class_date;
    private String yoga_class_comment;

    public YogaClass() {
    }

    public YogaClass(int yoga_class_id, int yoga_class_course_id, String yoga_class_teacher_name, String yoga_class_date, String yoga_class_comment) {
        this.yoga_class_id = yoga_class_id;
        this.yoga_class_course_id = yoga_class_course_id;
        this.yoga_class_teacher_name = yoga_class_teacher_name;
        this.yoga_class_date = yoga_class_date;
        this.yoga_class_comment = yoga_class_comment;
    }

    public int getYoga_class_id() {
        return yoga_class_id;
    }

    public void setYoga_class_id(int yoga_class_id) {
        this.yoga_class_id = yoga_class_id;
    }

    public int getYoga_class_course_id() {
        return yoga_class_course_id;
    }

    public void setYoga_class_course_id(int yoga_class_course_id) {
        this.yoga_class_course_id = yoga_class_course_id;
    }

    public String getYoga_class_teacher_name() {
        return yoga_class_teacher_name;
    }

    public void setYoga_class_teacher_name(String yoga_class_teacher_name) {
        this.yoga_class_teacher_name = yoga_class_teacher_name;
    }

    public String getYoga_class_date() {
        return yoga_class_date;
    }

    public void setYoga_class_date(String yoga_class_date) {
        this.yoga_class_date = yoga_class_date;
    }

    public String getYoga_class_comment() {
        return yoga_class_comment;
    }

    public void setYoga_class_comment(String yoga_class_comment) {
        this.yoga_class_comment = yoga_class_comment;
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_CLASS_INSTANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CLASS_INSTANCE_COURSE_ID + " INTEGER,"
                    + COLUMN_CLASS_INSTANCE_TEACHER_NAME + " TEXT,"
                    + COLUMN_CLASS_INSTANCE_CLASS_DATE + " TEXT,"
                    + COLUMN_CLASS_INSTANCE_COMMENT + " TEXT,"
                    + "FOREIGN KEY(" + COLUMN_CLASS_INSTANCE_COURSE_ID + ") REFERENCES " + YogaCourse.TABLE_NAME + "(" + YogaCourse.COLUMN_ID + ")"
                    + ")";
}
