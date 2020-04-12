package com.example.fadal17068266.StudentClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fadal17068266.DatabaseHelper;
import com.example.fadal17068266.ModuleClasses.Module;
import com.example.fadal17068266.R;

import java.util.ArrayList;

public class UpdateStudentActivity extends AppCompatActivity {

    EditText firstName, lastName, age, moduleID, ID;
    Button updateButton;
    private ArrayList<Module> moduleArrayList = new ArrayList<Module>();
    private ArrayList<Student> studentArrayList = new ArrayList<Student>();
    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        firstName = findViewById(R.id.etUpdFirstName);
        lastName = findViewById(R.id.etUpdLastName);
        age = findViewById(R.id.etUpdAge);
        moduleID = findViewById(R.id.etUpdModuleInStudTableID);
        ID = findViewById(R.id.etUpdStudID);
        updateButton = findViewById(R.id.updateStudBtn);

        //getting the student and the module data from the database
        this.moduleArrayList = mDatabaseHelper.getAllModules();
        this.studentArrayList = mDatabaseHelper.getAllStudent();

        //adding text changed listener to all the edit text components of the view
        firstName.addTextChangedListener(updateStudentTextWatcher);
        lastName.addTextChangedListener(updateStudentTextWatcher);
        age.addTextChangedListener(updateStudentTextWatcher);
        moduleID.addTextChangedListener(updateStudentTextWatcher);
        ID.addTextChangedListener(updateStudentTextWatcher);

        //initialising the state of the update button
        updateButton.setEnabled(false);
        updateButton.setText("Fill All Fields!");
    }

    private TextWatcher updateStudentTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //storing the contents of the edit text components in the activity into local variables
            String checkStudentID = ID.getText().toString().trim();
            String checkFirstName = firstName.getText().toString().trim();
            String checkLastName = lastName.getText().toString().trim();
            String checkAge = age.getText().toString().trim();
            String checkModuleID = moduleID.getText().toString().trim();

            //a boolean variable that will state if the inputted module exists
            boolean moduleExists = false;
            //a boolean variable that will state if the inputted student exists
            boolean studentExists = false;
            //a variable that will store the integer value of the checkStudentID variable
            int integerInputtedStudID = 0;
            //a variable that will store the integer value of the checkModuleID variable
            int integerInputtedModID = 0;

            //checking if the module id edit text field is not empty
            if(!checkModuleID.isEmpty())
            {
                integerInputtedModID = Integer.valueOf(checkModuleID);
            }
            //checking if the student id edit text field is not empty
            if(!checkStudentID.isEmpty())
            {
                integerInputtedStudID = Integer.valueOf(checkStudentID);
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

            //checking if the inputted id is not zero (id values start from 1)
            if(integerInputtedStudID != 0)
            {
                //looping through the students that are in the student array list
                for(Student currStudent : studentArrayList)
                {
                    //getting the student id of the current student
                    int studentID = currStudent.getID();
                    //checking if the inputted student id exists in the module list
                    if(integerInputtedStudID == studentID)
                    {
                        studentExists = true;
                        break;
                    }
                    else
                    {
                        studentExists = false;
                    }
                }
            }

            //checking if all the edit text components are not empty
            if(!checkStudentID.isEmpty() && !checkFirstName.isEmpty() && !checkLastName.isEmpty() && !checkAge.isEmpty() && !checkModuleID.isEmpty())
            {
                //changing the update module button to enabled since there are values to update
                updateButton.setText("Update");
                updateButton.setEnabled(true);
                //checking if the inputted module doesn't exist
                if(!moduleExists)
                {
                    //changing the update student button to not enabled since the inputted module id is invalid
                    updateButton.setText("Module doesn't exist");
                    updateButton.setEnabled(false);
                }
                //checking if the inputted module doesn't exist
                if(!studentExists)
                {
                    //changing the update student button to not enabled since the inputted student id is invalid
                    updateButton.setText("Student doesn't exist");
                    updateButton.setEnabled(false);
                }
            }
            else
            {
                //changing the update module button to not enabled since not all edit text components are filled
                updateButton.setText("Fill All Fields");
                updateButton.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void updateStudBtnOnClick(View view)
    {
        //storing the text values of the view components into local variables
        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        int cAge = Integer.valueOf(age.getText().toString());
        int cModID = Integer.valueOf(moduleID.getText().toString());
        int cID = Integer.valueOf(ID.getText().toString());

        //sending the inputted data back to the student list activity
        Intent intent = getIntent();
        intent.putExtra("first name", fName);
        intent.putExtra("last name", lName);
        intent.putExtra("age", cAge);
        intent.putExtra("module id", cModID);
        intent.putExtra("id", cID);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void returnToStudListFromUpdActBtnOnClick(View view)
    {
        finish();
    }


}
