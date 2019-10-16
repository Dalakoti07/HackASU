package com.myproject.keeplearn;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class customAdapter extends RecyclerView.Adapter<customAdapter.ViewHolder> {
    private ArrayList<Course> ListCourses=new ArrayList<>();
private Context context;
private onIemClickListener mOnItemClickListener;

public customAdapter(ArrayList<Course> courses,Context context,onIemClickListener listener){
        this.ListCourses=courses;
        this.context=context;
        this.mOnItemClickListener=listener;
        }
@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.each_course,parent,false);
        return new ViewHolder(v,mOnItemClickListener);
        }

@Override
public void onBindViewHolder(@NonNull customAdapter.ViewHolder myViewHolder, int i) {
        Course course=ListCourses.get(i);
//        Picasso.with(context).load(user.getAvatarUrl()).into(myViewHolder.avatarUrl);
        myViewHolder.title.setText(course.getTitle());
        myViewHolder.desc.setText(course.getCourseDescription());
        myViewHolder.avatarUrl.setImageResource(R.drawable.books);
//        Log.d("mmm",course.getLogin());
        }

@Override
public int getItemCount() {
        return ListCourses.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView title,desc;
    onIemClickListener mOnItemClickListener;
    private ImageView avatarUrl;
    public ViewHolder(@NonNull View itemView, onIemClickListener listener) {
        super(itemView);
        title=itemView.findViewById(R.id.tv_title);
        avatarUrl=itemView.findViewById(R.id.courseImage);
        desc=itemView.findViewById(R.id.tv_description);
        this.mOnItemClickListener=listener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mOnItemClickListener.onItemClicked(getAdapterPosition());
    }
}
    public interface onIemClickListener{
        void onItemClicked(int position);
    }
}
