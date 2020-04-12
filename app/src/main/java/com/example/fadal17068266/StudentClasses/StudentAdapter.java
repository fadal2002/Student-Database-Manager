package com.example.fadal17068266.StudentClasses;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fadal17068266.DatabaseHelper;
import com.example.fadal17068266.ImageHelper;
import com.example.fadal17068266.R;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private ArrayList<Student> mStudentList;
    private onStudentListener mOnStudentListener;

    public static class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView mID, mFirstName, mLastName, mAge, mModuleID;
        public ImageView mStudentImage;

        //creating an onClick listener for all the items that are displayed on the recycler view
        onStudentListener mOnStudentListener;

        public StudentViewHolder(@NonNull View itemView, onStudentListener mOnStudentListener) {
            super(itemView);
            mID = itemView.findViewById(R.id.tvID_Display);
            mFirstName = itemView.findViewById(R.id.tvFirstName_Display);
            mLastName = itemView.findViewById(R.id.tvLastName_Display);
            mAge = itemView.findViewById(R.id.tvAge_Display);
            mModuleID = itemView.findViewById(R.id.tvModuleID_Display);
            mStudentImage = itemView.findViewById(R.id.studentImgView);
            this.mOnStudentListener = mOnStudentListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnStudentListener.onStudentClick(getAdapterPosition());
        }
    }

    public StudentAdapter(ArrayList<Student> mStudentList, onStudentListener mOnStudentListener) {
        //passing through the data that needs to be displayed in the recycler view
        this.mStudentList = mStudentList;
        this.mOnStudentListener = mOnStudentListener;
    }

    //a function that will update the recycler view based on the array list of the new data that needs to be displayed
    public void updateRecyclerView(ArrayList<Student> mStudentList)
    {
        this.mStudentList.clear();
        this.mStudentList.addAll(mStudentList);
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item_view, parent, false);
        StudentViewHolder svh = new StudentViewHolder(view,mOnStudentListener);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student currentItem = mStudentList.get(position);
        holder.mID.setText(String.valueOf(currentItem.getID()));
        holder.mFirstName.setText(currentItem.getFirstName());
        holder.mLastName.setText(currentItem.getLastName());
        holder.mAge.setText(String.valueOf(currentItem.getAge()));
        holder.mModuleID.setText(String.valueOf(currentItem.getModuleID()));
        if(currentItem.getImage() != null)
        {
            holder.mStudentImage.setImageBitmap(ImageHelper.getResizedImage(currentItem.getImage()));
        }
        else
        {
            holder.mStudentImage.setImageBitmap(null);
        }
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    //an on click listener interface to user for the itemViewOnClick feature
    public interface onStudentListener
    {
        void onStudentClick(int position);
    }
}
