package com.example.final_mobile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

    private CourseAdapter courseAdapter;
    private ArrayList<YogaCourse> courseList  = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Courses List");

        recyclerView = findViewById(R.id.recycler_view_courses);
        db = new DatabaseHelper(this);

        courseList.addAll(db.getAllCourse());

        courseAdapter = new CourseAdapter(this, courseList,MainActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(courseAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
        final EditText newDayOfTheWeek = view.findViewById(R.id.add_day_of_the_week);
        final EditText newTimeOfCourse = view.findViewById(R.id.add_time_of_course);
        final EditText newCapacity = view.findViewById(R.id.add_capacity);
        final EditText newDuration = view.findViewById(R.id.add_duration);
        final EditText newType = view.findViewById(R.id.add_type_of_class);
        final EditText newPrice = view.findViewById(R.id.add_price_per_class);
        final EditText newDescription = view.findViewById(R.id.add_description);

        courseTitle.setText(!isUpdated ? "Add New course" : "Edit course");


        if (isUpdated && course != null){
            newCourseName.setText(course.getCourse_name());
            newDayOfTheWeek.setText(course.getDay_of_the_week());
            newTimeOfCourse.setText(course.getTime_of_course());
            newCapacity.setText(String.valueOf(course.getCapacity()));
            newDuration.setText(String.valueOf(course.getDuration()));
            newType.setText(course.getType_of_class());
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
                        else if (TextUtils.isEmpty(newDayOfTheWeek.getText().toString())){
                            Toast.makeText(MainActivity.this, "Please Enter a Day", Toast.LENGTH_SHORT).show();
                            return;

                        }
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
                        else if (TextUtils.isEmpty(newType.getText().toString())){
                            Toast.makeText(MainActivity.this, "Please Enter a Type", Toast.LENGTH_SHORT).show();
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
                            UpdateCourse(newCourseName.getText().toString(), newDayOfTheWeek.getText().toString(),
                                    newTimeOfCourse.getText().toString(), Integer.parseInt(newCapacity.getText().toString()),
                                    Integer.parseInt(newDuration.getText().toString()), newType.getText().toString(),
                                    Double.parseDouble(newPrice.getText().toString()), newDescription.getText().toString()
                                    ,position);

                        }else{
                            CreateCourse(newCourseName.getText().toString(), newDayOfTheWeek.getText().toString(),
                                    newTimeOfCourse.getText().toString(), Integer.parseInt(newCapacity.getText().toString()),
                                    Integer.parseInt(newDuration.getText().toString()), newType.getText().toString(),
                                    Double.parseDouble(newPrice.getText().toString()), newDescription.getText().toString());

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

 //   @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}