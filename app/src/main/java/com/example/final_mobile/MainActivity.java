package com.example.final_mobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_mobile.adapter.CourseAdapter;
import com.example.final_mobile.database.DatabaseHelper;
import com.example.final_mobile.model.YogaCourse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<YogaCourse> courseList;
    private DatabaseHelper db;
    private CourseAdapter courseAdapter;
    //giao diện
    private RecyclerView recyclerView;
    private ImageView btn_search;


    public void changeToClassActivity(int course_id){
        Intent intent = new Intent(MainActivity.this, YogaClassMainActivity.class);
        intent.putExtra("course_id", course_id);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //set up tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Courses List");
        //List
        recyclerView = findViewById(R.id.recycler_view_courses);
        //Database
        db = new DatabaseHelper(MainActivity.this);
        courseList = db.getAllCourse();

        courseAdapter = new CourseAdapter(this, courseList,MainActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(courseAdapter);

        //search
        btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchClassActivity.class);
                startActivity(intent);

            }
        });


        //Floating button
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAndEditCourses(false, null, -1);
            }
        });
    }

    public void addAndEditCourses(final boolean isUpdated,final YogaCourse course,final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.add_course,null);
        //AlertDialog.Builder
        AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alerDialogBuilder.setView(view);


        TextView courseTitle = view.findViewById(R.id.new_course_title);
        final EditText newCourseName = view.findViewById(R.id.add_course_name);
//        final EditText newDayOfTheWeek = view.findViewById(R.id.add_day_of_the_week);
        final Spinner newDayOfTheWeek = view.findViewById(R.id.add_day_of_the_week);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.day_of_the_week, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newDayOfTheWeek.setAdapter(adapter);
        final EditText newTimeOfCourse = view.findViewById(R.id.add_time_of_course);
        final EditText newCapacity = view.findViewById(R.id.add_capacity);
        final EditText newDuration = view.findViewById(R.id.add_duration);
        final Spinner newType = view.findViewById(R.id.add_type_of_class);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.type_of_class, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newType.setAdapter(adapter1);
        final EditText newPrice = view.findViewById(R.id.add_price_per_class);
        final EditText newDescription = view.findViewById(R.id.add_description);

        courseTitle.setText(!isUpdated ? "Add New course" : "Edit course");


        if (isUpdated && course != null){
            newCourseName.setText(course.getCourse_name());
//            newDayOfTheWeek.setText(course.getDay_of_the_week());
            newTimeOfCourse.setText(course.getTime_of_course());
            newCapacity.setText(String.valueOf(course.getCapacity()));
            newDuration.setText(String.valueOf(course.getDuration()));
//            newType.setText(course.getType_of_class());
            newPrice.setText(String.valueOf(course.getPrice_per_class()));
            newDescription.setText(course.getDescription());
        }

        alerDialogBuilder.setCancelable(false)
                .setPositiveButton(isUpdated ? "Update" : "Save", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (TextUtils.isEmpty(newCourseName.getText().toString())){
                            Toast.makeText(MainActivity.this, "Please Enter a Name", Toast.LENGTH_SHORT).show();
                            return;
                        }
//                        else if (TextUtils.isEmpty(newDayOfTheWeek.getText().toString())){
//                            Toast.makeText(MainActivity.this, "Please Enter a Day", Toast.LENGTH_SHORT).show();
//                            return;
//
//                        }
                        else if (TextUtils.isEmpty(newTimeOfCourse.getText().toString())){
                            Toast.makeText(MainActivity.this, "Please Enter a Time", Toast.LENGTH_SHORT).show();
                            return;

                        }
                        else if (TextUtils.isEmpty(newCapacity.getText().toString())){
                            Toast.makeText(MainActivity.this, "Please Enter a Capacity", Toast.LENGTH_SHORT).show();
                            return;

                        }
                        else if (TextUtils.isEmpty(newDuration.getText().toString())){
                            Toast.makeText(MainActivity.this, "Please Enter a Duration", Toast.LENGTH_SHORT).show();
                            return;

                        }

                        else if (TextUtils.isEmpty(newPrice.getText().toString())){
                            Toast.makeText(MainActivity.this, "Please Enter a Price", Toast.LENGTH_SHORT).show();
                            return;

                        }
                        else if (TextUtils.isEmpty(newDescription.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Please Enter a Description", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else{

                            //alertDialog.dismiss();
                            dialogInterface.dismiss();
                        }

                        if (isUpdated && course != null){
                            try {
                                String course_name = newCourseName.getText().toString();
                                String day_of_week = newDayOfTheWeek.getSelectedItem().toString();
                                String time_of_course = newTimeOfCourse.getText().toString();
                                int capacity = Integer.parseInt(newCapacity.getText().toString());
                                int duration = Integer.parseInt(newDuration.getText().toString());
                                String type_of_class = newType.getSelectedItem().toString();
                                double price_per_class = Double.parseDouble(newPrice.getText().toString());
                                String description = newDescription.getText().toString();

                                UpdateCourse(course_name, day_of_week,
                                        time_of_course, capacity,
                                        duration, type_of_class,
                                        price_per_class, description ,position);
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Invalid value", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            try {
                                String course_name = newCourseName.getText().toString();
                                String day_of_week = newDayOfTheWeek.getSelectedItem().toString();
                                String time_of_course = newTimeOfCourse.getText().toString();
                                int capacity = Integer.parseInt(newCapacity.getText().toString());
                                int duration = Integer.parseInt(newDuration.getText().toString());
                                String type_of_class = newType.getSelectedItem().toString();
                                double price_per_class = Double.parseDouble(newPrice.getText().toString());
                                String description = newDescription.getText().toString();

                                CreateCourse(course_name, day_of_week,
                                        time_of_course, capacity,
                                        duration, type_of_class,
                                        price_per_class, description);
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Invalid value", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                })
                //.setNegativeButton("Delete",
                .setNegativeButton(isUpdated ? "Delete" : "Cancle",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (isUpdated){
                                    DeleteCourse(course, position);
                                }else{
                                    dialogInterface.cancel();
                                }
                            }
                        }
                );
        if (isUpdated) {
            alerDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel(); // Hoặc bất kỳ hành động nào khác bạn muốn
                }
            });
        }
        final AlertDialog alertDialog = alerDialogBuilder.create();
        alertDialog.show();

    }

    private void DeleteCourse(YogaCourse course, int position) {
        courseList.remove(position);
        db.deleteCourse(course);
        courseAdapter.notifyDataSetChanged();
    }

    private void UpdateCourse(String courseName, String dayOfTheWeek, String timeOfCourse, int capacity, int duration, String typeOfClass, double pricePerClass, String description, int position){

        YogaCourse course = courseList.get(position);
        course.setCourse_name(courseName);
        course.setDay_of_the_week(dayOfTheWeek);
        course.setTime_of_course(timeOfCourse);
        course.setCapacity(capacity);
        course.setDuration(duration);
        course.setType_of_class(typeOfClass);
        course.setPrice_per_class(pricePerClass);
        course.setDescription(description);

        db.updateCourse(course);

        courseList.set(position, course);
        courseAdapter.notifyDataSetChanged();
    }

    private void CreateCourse(String courseName, String dayOfTheWeek, String timeOfCourse, int capacity, int duration, String typeOfClass, double pricePerClass, String description){

        long id = db.insertCourse(courseName, dayOfTheWeek, timeOfCourse, capacity, duration, typeOfClass, pricePerClass, description);
        YogaCourse course = db.getCourse(id);

        if (course != null){
            courseList.add(0, course);
            courseAdapter.notifyDataSetChanged();
        }

    }

}