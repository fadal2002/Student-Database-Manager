package com.example.fadal17068266.StudentClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fadal17068266.DatabaseHelper;
import com.example.fadal17068266.ModuleClasses.Module;
import com.example.fadal17068266.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class AddStudentActivity extends AppCompatActivity{
    private EditText firstName, lastName, age, moduleID;
    private Button addBtn;
    private ArrayList<Module> moduleArrayList = new ArrayList<Module>();
    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        firstName = findViewById(R.id.etFirstName);
        lastName = findViewById(R.id.etLastName);
        age = findViewById(R.id.etAge);
        moduleID = findViewById(R.id.etModuleID);
        addBtn = findViewById(R.id.addBtn);

        //getting the module data from the module database table
        this.moduleArrayList = mDatabaseHelper.getAllModules();

        //adding text changed listener to all the edit text components of the view
        firstName.addTextChangedListener(addStudentTextWatcher);
        lastName.addTextChangedListener(addStudentTextWatcher);
        age.addTextChangedListener(addStudentTextWatcher);
        moduleID.addTextChangedListener(addStudentTextWatcher);

        //initialising the state of the add button
        addBtn.setEnabled(false);
        addBtn.setText("Fill All Fields!");
    }

    private TextWatcher addStudentTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //storing the contents of the edit text components in the activity into local variables
            String checkFirstName = firstName.getText().toString().trim();
            String checkLastName = lastName.getText().toString().trim();
            String checkAge = age.getText().toString().trim();
            String checkModuleID = moduleID.getText().toString().trim();
            //a boolean variable that will state if the inputted module exists
            boolean moduleExists = false;
            //a variable that will store the integer value of the checkModuleID variable
            int integerInputtedModID = 0;
            //checking if the module id edit text field is not empty
            if(!checkModuleID.isEmpty())
            {
                integerInputtedModID = Integer.valueOf(checkModuleID);
            }

            //checking if the inputted id is not zero (id values start from 1)
            if(integerInputtedModID != 0)
            {
                //looping through the modules that are in the module array list
                for(Module currModule : moduleArrayList)
                {
                    //getting the module id of the current module
                    int moduleID = currModule.getModId();
                    //checking if the inputted module id exists in the module list
                    if(integerInputtedModID == moduleID)
                    {
                        moduleExists = true;
                        break;
                    }
                    else
                    {
                        moduleExists = false;
                    }
                }
            }

            //checking if all the edit text components are not empty
            if(!checkFirstName.isEmpty() && !checkLastName.isEmpty() && !checkAge.isEmpty() && !checkModuleID.isEmpty())
            {
                //changing the add student button to enabled since there are values to add
                addBtn.setText("ADD");
                addBtn.setEnabled(true);
                //checking if the inputted module doesn't exist
                if(!moduleExists)
                {
                    //changing the add student button to not enabled since the inputted module id is invalid
                    addBtn.setText("Module doesn't exist");
                    addBtn.setEnabled(false);
                }
            }
            else
            {
                //changing the update module button to not enabled since not all edit text components are filled
                addBtn.setText("Fill All Fields");
                addBtn.setEnabled(false);
            }



        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void addBtnOnClick(View view)
    {
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        int cAge = Integer.valueOf(age.getText().toString().trim());
        int cModID = Integer.valueOf(moduleID.getText().toString().trim());


        Intent intent = getIntent();
        intent.putExtra("first name", fName);
        intent.putExtra("last name", lName);
        intent.putExtra("age", cAge);
        intent.putExtra("module id", cModID);
        setResult(RESULT_OK, intent);
        finish();

    }

    public void returnToStudListBtnOnClick(View view)
    {
        finish();
    }

}
