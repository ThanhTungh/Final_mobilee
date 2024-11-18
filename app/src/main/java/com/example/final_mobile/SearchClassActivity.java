package com.example.final_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_mobile.adapter.ClassAdapter;
import com.example.final_mobile.adapter.CourseAdapter;
import com.example.final_mobile.adapter.SearchAdapter;
import com.example.final_mobile.database.DatabaseHelper;
import com.example.final_mobile.database.YogaClassDatabaseHelper;
import com.example.final_mobile.model.YogaClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SearchClassActivity extends AppCompatActivity {
    private ImageView btn_back;
    private RecyclerView recycler_view_class;
    private EditText edit_search;
    private ArrayList<YogaClass> classList;
    private SearchAdapter searchAdapter;
    private YogaClassDatabaseHelper dbClass;
    private ImageView searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //setup widgets
        btn_back = findViewById(R.id.btn_back);
        recycler_view_class = findViewById(R.id.recycler_view_classSearch);
        edit_search = findViewById(R.id.sv_search_view);
        searchButton = findViewById(R.id.searchButton);
        dbClass = new YogaClassDatabaseHelper(this);
        classList = new ArrayList<>();


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = edit_search.getText().toString().trim();
                classList.clear();
                classList.addAll(dbClass.getAllClassesByTeacherName(search));
                searchAdapter = new SearchAdapter(classList,SearchClassActivity.this,SearchClassActivity.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recycler_view_class.setLayoutManager(layoutManager);
                recycler_view_class.setItemAnimator(new DefaultItemAnimator());
                recycler_view_class.setAdapter(searchAdapter);
                searchAdapter.notifyDataSetChanged();
            }
        });

        //setup listener for widgets
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchClassActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler_view_class.setLayoutManager(linearLayoutManager);

    }

}