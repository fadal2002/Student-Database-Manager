package com.example.fadal17068266;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.fadal17068266.ModuleClasses.Module;
import com.example.fadal17068266.StudentClasses.Student;

import java.util.ArrayList;

public class StudModListAcitvity extends AppCompatActivity {
    DatabaseHelper mDatabaseHelper;
    public ArrayList<Studmod> studmodArrayList = new ArrayList<Studmod>();
    public ArrayList<Student> studentArrayList;
    public ArrayList<Module> moduleArrayList;

    private RecyclerView mRecyclerView;
    public StudModAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studmod_list);
        mDatabaseHelper = new DatabaseHelper(this);

        //retrieving the data from the database
        getModuleData();
        getStudentData();
        //creating the student-module data
        createStudmodData();
        //storing the inputted data into the student-module database table
        mDatabaseHelper.updateStudModDB(studmodArrayList);

        buildRecyclerView();
    }

    public void returnToMainActFromStudmodListActBtnOnClick(View view)
    {
        finish();
    }

    //a function to retrieve the student data from the database
    public void getStudentData()
    {
        this.studentArrayList = mDatabaseHelper.getAllStudent();
    }

    //a function to retrieve the module data from the database
    public void getModuleData()
    {
        this.moduleArrayList = mDatabaseHelper.getAllModules();
    }

    //a function that uses the student data and the module data to generate the student-module data
    public void createStudmodData()
    {
        String studentName;
        String moduleName;
        int moduleID;
        int studentID;
        byte[] studentImg;
        Studmod stm;

        //looping through the student arraylist
        for(Student currStudent : studentArrayList)
        {
            //get the module id that was referenced in the student database table
            int studentModId = currStudent.getModuleID();
            //looping through the module arraylist
            for(Module currModule : moduleArrayList)
            {
                //get the module id of the modules in the module database table
                int currMod_ModId = currModule.getModId();

                //check if the module id that was referenced in the student's data equals the current modules id
                if(studentModId == currMod_ModId)
                {
                    //getting some of the student's data and some of the module's data and storing them in local variables
                    studentName = currStudent.getFirstName() + " " + currStudent.getLastName();
                    moduleName = currModule.getModuleName();
                    studentID = currStudent.getID();
                    moduleID = currModule.getModId();
                    studentImg = currStudent.getImage();

                    //using the gathered data to create a student-module object
                    stm = new Studmod(studentID, studentName, moduleName, moduleID, studentImg);
                    //storing the student-module object in an array list
                    studmodArrayList.add(stm);
                }
            }
        }

    }

    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.studmodRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new StudModAdapter(studmodArrayList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.setAdapter(mAdapter);
    }
}
