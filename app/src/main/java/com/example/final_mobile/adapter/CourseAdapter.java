package com.example.final_mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_mobile.MainActivity;
import com.example.final_mobile.R;
import com.example.final_mobile.YogaClassMainActivity;
import com.example.final_mobile.model.YogaCourse;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<YogaCourse> courseList;
    private MainActivity mainActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView course_name;
        public TextView day_of_the_week;
        public TextView time_of_course;
        public TextView capacity;
        public TextView duration;
        public TextView type_of_class;
        public TextView price_per_class;
        public TextView description;
        public Button listClass;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.course_name = itemView.findViewById(R.id.course_name);
            this.day_of_the_week = itemView.findViewById(R.id.day_of_the_week);
            this.time_of_course = itemView.findViewById(R.id.time_of_course);
            this.capacity = itemView.findViewById(R.id.capacity);
            this.duration = itemView.findViewById(R.id.duration);
            this.type_of_class = itemView.findViewById(R.id.type_of_class);
            this.price_per_class = itemView.findViewById(R.id.price_per_class);
            this.description = itemView.findViewById(R.id.description);
            this.listClass = itemView.findViewById(R.id.listClass);
        }
    }

    public CourseAdapter(Context context, ArrayList<YogaCourse> courses, MainActivity mainActivity){
        this.context = context;
        this.courseList = courses;
        this.mainActivity = mainActivity;
    }
    @NonNull
    @Override
    public CourseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Nạp layout cho View biểu diễn phần tử cu the
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.course_list_item,parent,false);

        return new MyViewHolder(itemView);
    }
    //chuyển dữ liệu phần tử vào ViewHolder
    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.MyViewHolder holder, int positions){
        YogaCourse course = courseList.get(positions);

        holder.course_name.setText(course.getCourse_name());
        holder.day_of_the_week.setText(course.getDay_of_the_week());
        holder.time_of_course.setText(course.getTime_of_course());
        holder.capacity.setText(String.valueOf(course.getCapacity()));
        holder.duration.setText(String.valueOf(course.getDuration()));
        holder.type_of_class.setText(course.getType_of_class());
        holder.price_per_class.setText(String.valueOf(course.getPrice_per_class()));
        holder.description.setText(course.getDescription());
        ////Xử lý khi nút Chi tiết được bấm
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.addAndEditCourses(true, course, holder.getAdapterPosition());
            }
        });

        holder.listClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.changeToClassActivity(course.getId());
            }
        });

    }
    //cho biết số phần tử của dữ liệu
    @Override
    public int getItemCount() {
        return courseList.size();
    }

}
