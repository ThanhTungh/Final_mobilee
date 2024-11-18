package com.example.final_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_mobile.R;
import com.example.final_mobile.SearchClassActivity;
import com.example.final_mobile.YogaClassMainActivity;
import com.example.final_mobile.model.YogaClass;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private ArrayList<YogaClass> classList;
    private Context context;
    private SearchClassActivity searchActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
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
    public SearchAdapter(ArrayList<YogaClass> classes, Context ctx , SearchClassActivity searchActivity) {
        this.classList = classes;
        this.context = ctx;
        this.searchActivity = searchActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.class_list_item, parent, false);

        return new SearchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        YogaClass yogaClass = classList.get(position);

        holder.class_teacher_name.setText(yogaClass.getYoga_class_teacher_name());
        holder.class_date.setText(yogaClass.getYoga_class_date());
        holder.class_comment.setText(yogaClass.getYoga_class_comment());
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
}
