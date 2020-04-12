package com.example.fadal17068266.ModuleClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fadal17068266.R;

import java.util.ArrayList;


public class ModuleAdapter extends  RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {
    private ArrayList<Module> mModuleList;

    public static class ModuleViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mModID, mModuleName, mModuleDescription;

        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            mModID = itemView.findViewById(R.id.tvModID_Display);
            mModuleName = itemView.findViewById(R.id.tvModuleName_Display);
            mModuleDescription = itemView.findViewById(R.id.tvDescription_Display);
        }

    }

    public ModuleAdapter(ArrayList<Module> mModuleList) {
        //passing through the data that needs to be displayed in the recycler view
        this.mModuleList = mModuleList;
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_item_view, parent, false);
        ModuleViewHolder mvh = new ModuleViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Module currentItem = mModuleList.get(position);
        holder.mModID.setText(String.valueOf(currentItem.getModId()));
        holder.mModuleName.setText(currentItem.getModuleName());
        holder.mModuleDescription.setText(currentItem.getModuleDescription());
    }

    @Override
    public int getItemCount() {
        return mModuleList.size();
    }

    public void updateRecyclerView(ArrayList<Module> mModuleList)
    {
        this.mModuleList.clear();
        this.mModuleList.addAll(mModuleList);
        this.notifyDataSetChanged();
    }

}
