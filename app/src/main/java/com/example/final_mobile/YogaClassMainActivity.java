package com.example.final_mobile;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_mobile.adapter.ClassAdapter;
import com.example.final_mobile.adapter.CourseAdapter;
import com.example.final_mobile.database.DatabaseHelper;
import com.example.final_mobile.database.YogaClassDatabaseHelper;
import com.example.final_mobile.model.YogaClass;
import com.example.final_mobile.model.YogaCourse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class YogaClassMainActivity extends AppCompatActivity {

    private ClassAdapter classAdapter;
    private ArrayList<YogaClass> classList = new ArrayList<>();
    private RecyclerView recycler_view_class;
    private YogaClassDatabaseHelper dbClass;
    private ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_yoga_class_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Class List");

        recycler_view_class = findViewById(R.id.recycler_view_class);
        dbClass = new YogaClassDatabaseHelper(this);
        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YogaClassMainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();
        int course_id = 0;
        if (bundle != null)
        {
            course_id = bundle.getInt("course_id");
        }
        classList.addAll(dbClass.getAllClasses(course_id));

        classAdapter = new ClassAdapter( classList, YogaClassMainActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view_class.setLayoutManager(layoutManager);
        recycler_view_class.setItemAnimator(new DefaultItemAnimator());
        recycler_view_class.setAdapter(classAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAndEditClasses(false, null, -1);
            }
        });
    }

    public void addAndEditClasses(final boolean isUpdated, final YogaClass yogaClass, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.add_class, null);
        //AlertDialog.Builder
        AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(YogaClassMainActivity.this);
        alerDialogBuilder.setView(view);



        TextView classTitle = view.findViewById(R.id.new_class_title);
        final EditText newTeacherName = view.findViewById(R.id.add_class_teacher_name);
        final EditText newDateClass = view.findViewById(R.id.add_date_class);
        final EditText newComment = view.findViewById(R.id.add_comment_class);

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        newDateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(YogaClassMainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        DatabaseHelper dbCourse = new DatabaseHelper(YogaClassMainActivity.this);
                        Bundle bundle = getIntent().getExtras();
                        int course_id = 0;
                        if (bundle != null)
                        {
                            course_id = bundle.getInt("course_id");
                        }
                        if (dbCourse.getCourse(course_id) != null)
                        {
                            YogaCourse yogaCourse = dbCourse.getCourse(course_id);
                            String dayCourse = yogaCourse.getDay_of_the_week();
                            String day_of_the_week_class = Util.showDayOfTheWeek(dayOfMonth, month + 1, year);
                            if(!day_of_the_week_class.equals(dayCourse)) {
                                Toast.makeText(YogaClassMainActivity.this, "The date does not match the day of the week!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            @SuppressLint("DefaultLocale") String date = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);
                            Toast.makeText(YogaClassMainActivity.this, "Select date: " + date, Toast.LENGTH_SHORT).show();
                            newDateClass.setText(date);
                        }
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        classTitle.setText(!isUpdated ? "Add New Class" : "Edit Class");


        if (isUpdated && yogaClass != null) {
            newTeacherName.setText(yogaClass.getYoga_class_teacher_name());
            newDateClass.setText(yogaClass.getYoga_class_date());
            newComment.setText(yogaClass.getYoga_class_comment());
        }

        alerDialogBuilder.setCancelable(false)
                .setPositiveButton(isUpdated ? "Update" : "Save", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (TextUtils.isEmpty(newTeacherName.getText().toString())) {
                            Toast.makeText(YogaClassMainActivity.this, "Please Enter Teacher Name", Toast.LENGTH_SHORT).show();
                            return;

                        } else if (TextUtils.isEmpty(newDateClass.getText().toString())) {
                            Toast.makeText(YogaClassMainActivity.this, "Please Enter class Date", Toast.LENGTH_SHORT).show();
                            return;

                        } else if (TextUtils.isEmpty(newComment.getText().toString())) {
                            Toast.makeText(YogaClassMainActivity.this, "Please Enter comment", Toast.LENGTH_SHORT).show();
                            return;

                        }else {

                            //alertDialog.dismiss();
                            dialogInterface.dismiss();
                        }

                        if (isUpdated && yogaClass != null) {
                            UpdateClass(newTeacherName.getText().toString(), newDateClass.getText().toString(),
                                    newComment.getText().toString() , position);

                        } else {
                            CreateClass(newTeacherName.getText().toString(), newDateClass.getText().toString(),
                                    newComment.getText().toString());

                        }
                    }
                })
                //.setNegativeButton("Delete",
                .setNegativeButton(isUpdated ? "Delete" : "Cancle",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (isUpdated) {
                                    DeleteClass(yogaClass, position);
                                } else {
                                    dialogInterface.cancel();
                                }
                            }
                        }
                );
        final AlertDialog alertDialog = alerDialogBuilder.create();
        alertDialog.show();

    }
    private void DeleteClass(YogaClass yogaClass, int position) {
        classList.remove(position);
        dbClass.deleteClass(yogaClass);
        classAdapter.notifyDataSetChanged();
    }

    private void UpdateClass(String teacherName, String dateClass, String comment, int position) {

        YogaClass yogaClass = classList.get(position);
        yogaClass.setYoga_class_teacher_name(teacherName);
        yogaClass.setYoga_class_date(dateClass);
        yogaClass.setYoga_class_comment(comment);


        dbClass.updateClass(yogaClass);

        classList.set(position, yogaClass);
        classAdapter.notifyDataSetChanged();
    }

    private void CreateClass(String teacherName, String dateClass, String comment) {
        Bundle bundle = getIntent().getExtras();
        int course_id = 0;
        if (bundle != null) {
            course_id = bundle.getInt("course_id");
        }
        long id = dbClass.insertClass(course_id, teacherName, dateClass, comment);
        YogaClass yogaClass = dbClass.getClass(id);

        if (yogaClass != null){
            classList.add(0, yogaClass);
            classAdapter.notifyDataSetChanged();
        }

    }
}