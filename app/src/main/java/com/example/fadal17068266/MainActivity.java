package com.example.fadal17068266;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fadal17068266.ModuleClasses.ModuleListActivity;
import com.example.fadal17068266.StudentClasses.StudentListActivity;

public class MainActivity extends AppCompatActivity {

    private final static String STUDENT_TABLE_NAME = "student";
    private final static String MODULE_TABLE_NAME = "module";
    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialising the student database table
        mDatabaseHelper.clearDatabase(STUDENT_TABLE_NAME);
        mDatabaseHelper.addStudent("Fadal", "Alenazi", 20, 2);
        mDatabaseHelper.addStudent("Abdul", "Alenazi", 17, 3);
        mDatabaseHelper.addStudent("Ali", "Alenazi", 24, 1);
        mDatabaseHelper.addStudent("Abbas", "Ezzeddin", 14, 3);
        mDatabaseHelper.addStudent("Ensa", "Touray", 21, 4);

        //initialising the module database table
        mDatabaseHelper.clearDatabase(MODULE_TABLE_NAME);
        mDatabaseHelper.addModule("Computer Science", "The study of computers and computing concepts");
        mDatabaseHelper.addModule("Mechanical Engineering", "Research, design, develop, and test mechanical and thermal devices");
        mDatabaseHelper.addModule("Civil Engineering", "Design and develop infrastructure projects");
        mDatabaseHelper.addModule("Psychology", "The study of the mind, its thought, feeling, and behaviour");
    }

    public void studentListOnClick(View view)
    {
        Intent intent = new Intent(this, StudentListActivity.class);
        startActivity(intent);
    }

    public void moduleListOnClick(View view)
    {
        Intent intent = new Intent(this, ModuleListActivity.class);
        startActivity(intent);
    }

    public void studmodListOnClick(View view)
    {
        Intent intent = new Intent(this, StudModListAcitvity.class);
        startActivity(intent);
    }
}
