package com.example.fadal17068266.ModuleClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fadal17068266.DatabaseHelper;
import com.example.fadal17068266.R;

import java.util.ArrayList;

public class ModuleListActivity extends AppCompatActivity {
    DatabaseHelper mDatabaseHelper;
    public ArrayList<Module> moduleArrayList;

    private RecyclerView mRecyclerView;
    public ModuleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //static ints for onActivityResult request codes
    public static final int ADD_REQUEST_CODE = 0;
    public static final int UPDATE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_list);

        mDatabaseHelper = new DatabaseHelper(this);

        createModuleData();
        buildRecyclerView();

    }

    //a function that will deal with all the operation that involve data received from other activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            //local variables to store received data
            String moduleName;
            String moduleDescription;
            int mod_ID;

            //a switch statement that will perform operations based on the request codes
            switch(requestCode)
            {
                case ADD_REQUEST_CODE: //add activity request code
                    if(resultCode == RESULT_OK)
                    {
                        //storing the data received from the add activity
                        moduleName = data.getStringExtra("module name");
                        moduleDescription = data.getStringExtra("module description");
                        //adding the module details to the module database table
                        mDatabaseHelper.addModule(moduleName, moduleDescription);
                        //getting all the module data from the module database table and storing it in an array list
                        moduleArrayList = mDatabaseHelper.getAllModules();
                        //uploading the student data to the recycler view
                        mAdapter.updateRecyclerView(moduleArrayList);
                        //scrolling to the last position of the array list
                        mRecyclerView.smoothScrollToPosition(moduleArrayList.size() - 1);
                    }
                    break;
                case UPDATE_REQUEST_CODE: //update activity request code
                    if(resultCode == RESULT_OK)
                    {
                        //storing the data received from the update activity
                        moduleName = data.getStringExtra("module name");
                        moduleDescription = data.getStringExtra("module description");
                        mod_ID = data.getIntExtra("module id", 0);
                        //update module that is currently in the database tabling
                        mDatabaseHelper.updateModule(moduleName, moduleDescription, mod_ID);
                        //getting all the module data from the module database table and storing it in an array list
                        moduleArrayList = mDatabaseHelper.getAllModules();
                        //uploading the student data to the recycler view
                        mAdapter.updateRecyclerView(moduleArrayList);
                        //scrolling to the position of the clicked item in the array list
                        mRecyclerView.smoothScrollToPosition(mod_ID - 1);
                    }
                    break;
                default:
                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void updateModuleBtnOnClick(View view)
    {
        Intent updateIntent = new Intent(this, UpdateModuleActivity.class);
        startActivityForResult(updateIntent, UPDATE_REQUEST_CODE);
    }

    public void addModuleBtnOnClick(View view)
    {
        Intent addIntent = new Intent(this, AddModuleActivity.class);
        startActivityForResult(addIntent, ADD_REQUEST_CODE);
    }

    public void returnToMainActFromModListBtnOnClick(View view)
    {
        finish();
    }

    public void createModuleData()
    {
        moduleArrayList = mDatabaseHelper.getAllModules();
    }

    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.moduleRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ModuleAdapter(moduleArrayList);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.setAdapter(mAdapter);
    }

    //swipe deleting an item on the recycler view
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            TextView tvModID_Disp = viewHolder.itemView.findViewById(R.id.tvModID_Display);
            int id = Integer.valueOf(tvModID_Disp.getText().toString());
            mDatabaseHelper.deleteModule(id);
            int pos = viewHolder.getLayoutPosition();
            moduleArrayList.clear();
            moduleArrayList = mDatabaseHelper.getAllModules();
            mAdapter.updateRecyclerView(moduleArrayList);

        }
    };
}
