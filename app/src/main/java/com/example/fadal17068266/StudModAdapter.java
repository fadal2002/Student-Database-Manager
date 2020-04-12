package com.example.fadal17068266;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudModAdapter extends RecyclerView.Adapter<StudModAdapter.StudModViewHolder> {
    private ArrayList<Studmod> mStudmodList;

    public class StudModViewHolder extends RecyclerView.ViewHolder {

        public TextView mStudentID, mStudentName, mModuleName, mModuleID;
        public ImageView mStudentImg;

        public StudModViewHolder(@NonNull View itemView) {
            super(itemView);
            mStudentID = itemView.findViewById(R.id.tvSMID_Display);
            mStudentName = itemView.findViewById(R.id.tvSMStudentName_Display);
            mModuleName = itemView.findViewById(R.id.tvSMModuleName_Display);
            mModuleID = itemView.findViewById(R.id.tvSMModuleID_Display);
            mStudentImg = itemView.findViewById(R.id.SMStudentImgView);

        }
    }

    public StudModAdapter(ArrayList<Studmod> mStudmodList) {
        //inputting the data that needs to be displayed into the student adapter
        this.mStudmodList = mStudmodList;
    }


    @NonNull
    @Override
    public StudModViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the layout with the student-module item view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studmod_item_view, parent, false);
        StudModViewHolder smvh = new StudModViewHolder(view);
        return smvh;
    }

    @Override
    public void onBindViewHolder(@NonNull StudModViewHolder holder, int position) {
        //setting the values of the components that are in the student-module item view to the data that needs to be displayed
        Studmod currentItem = mStudmodList.get(position);
        holder.mStudentID.setText(String.valueOf(currentItem.getStudentID()));
        holder.mStudentName.setText(currentItem.getStudentName());
        holder.mModuleName.setText(currentItem.getModuleName());
        holder.mModuleID.setText(String.valueOf(currentItem.getModuleID()));
        if(currentItem.getStudentImg() != null)
        {
            holder.mStudentImg.setImageBitmap(ImageHelper.getResizedImage(currentItem.getStudentImg()));
        }
        else
        {
            holder.mStudentImg.setImageBitmap(null);
        }
    }

    @Override
    public int getItemCount() {
        return mStudmodList.size();
    }



}
