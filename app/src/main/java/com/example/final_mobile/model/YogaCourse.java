package com.example.final_mobile.model;

public class YogaCourse {
    public static final String TABLE_NAME = "yoga_course";
    public static final String COLUMN_ID = "yoga_course_id";
    public static final String COLUMN_NAME = "course_name";
    public static final String COLUMN_DAY_OF_THE_WEEK = "day_of_the_week";
    public static final String COLUMN_TIME_OF_COURSE = "time_of_course";
    public static final String COLUMN_CAPACITY = "capacity";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_TYPE_OF_CLASS = "type_of_class";
    public static final String COLUMN_PRICE_PER_CLASS = "price_per_class";
    public static final String COLUMN_DESCRIPTION = "description";

    private int id;
    private String course_name;
    private String day_of_the_week;
    private String time_of_course;
    private int capacity;
    private int duration;
    private String type_of_class;
    private double price_per_class;
    private String descriptions;

    public YogaCourse(int id, String course_name, String day_of_the_week, String time_of_course, int capacity, int duration, String type_of_class, double price_per_class, String descriptions) {
        this.id = id;
        this.course_name = course_name;
        this.day_of_the_week = day_of_the_week;
        this.time_of_course = time_of_course;
        this.capacity = capacity;
        this.duration = duration;
        this.type_of_class = type_of_class;
        this.price_per_class = price_per_class;
        this.descriptions = descriptions;
    }

    public YogaCourse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getDay_of_the_week() {
        return day_of_the_week;
    }

    public void setDay_of_the_week(String day_of_the_week) {
        this.day_of_the_week = day_of_the_week;
    }

    public String getTime_of_course() {
        return time_of_course;
    }

    public void setTime_of_course(String time_of_course) {
        this.time_of_course = time_of_course;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getType_of_class() {
        return type_of_class;
    }

    public void setType_of_class(String type_of_class) {
        this.type_of_class = type_of_class;
    }

    public double getPrice_per_class() {
        return price_per_class;
    }

    public void setPrice_per_class(double price_per_class) {
        this.price_per_class = price_per_class;
    }

    public String getDescription() {
        return descriptions;
    }

    public void setDescription(String descriptions) {
        this.descriptions = descriptions;
    }


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_DAY_OF_THE_WEEK + " TEXT,"
                    + COLUMN_TIME_OF_COURSE + " TEXT,"
                    + COLUMN_CAPACITY + " INTEGER,"
                    + COLUMN_DURATION + " INTEGER,"
                    + COLUMN_TYPE_OF_CLASS + " TEXT,"
                    + COLUMN_PRICE_PER_CLASS + " REAL,"
                    + COLUMN_DESCRIPTION + " TEXT"
                    + ")";
}
