package com.example.final_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_mobile.MainActivity;
import com.example.final_mobile.R;
import com.example.final_mobile.YogaClassMainActivity;
import com.example.final_mobile.model.YogaClass;
import com.example.final_mobile.model.YogaCourse;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<YogaClass> classList;
    private YogaClassMainActivity yogaClassMainActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView class_teacher_name;
        public TextView class_date;
        public TextView class_comment;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.class_teacher_name = itemView.findViewById(R.id.class_teacher_name);
            this.class_date = itemView.findViewById(R.id.class_date);
            this.class_comment = itemView.findViewById(R.id.class_comment);

        }
    }
    public ClassAdapter(Context context, ArrayList<YogaClass> classes, YogaClassMainActivity yogaClassMainActivity){
        this.context = context;
        this.classList = classes;
        this.yogaClassMainActivity = yogaClassMainActivity;
    }

    @NonNull
    @Override
    public ClassAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Nạp layout cho View biểu diễn phần tử cu the
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.class_list_item,parent,false);

        return new ClassAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapter.MyViewHolder holder, int positions){
        YogaClass yogaClass = classList.get(positions);

        holder.class_teacher_name.setText(yogaClass.getYoga_class_teacher_name());
        holder.class_date.setText(yogaClass.getYoga_class_date());
        holder.class_comment.setText(yogaClass.getYoga_class_comment());

        ////Xử lý khi nút Chi tiết được bấm
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yogaClassMainActivity.addAndEditClasses(true, yogaClass, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
}
