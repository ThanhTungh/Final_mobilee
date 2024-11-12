package com.example.final_mobile;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
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

public class YogaClassMainActivity extends AppCompatActivity {

    private ClassAdapter classAdapter;
    private ArrayList<YogaClass> classList  = new ArrayList<>();
    private RecyclerView recycler_view_class;
    private YogaClassDatabaseHelper dbClass;

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

        Bundle bundle = getIntent().getExtras();
        int course_id = bundle.getInt("course_id");
        classList.addAll(dbClass.getAllClasses(course_id));

        classAdapter = new ClassAdapter(this, classList,YogaClassMainActivity.this);
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
}