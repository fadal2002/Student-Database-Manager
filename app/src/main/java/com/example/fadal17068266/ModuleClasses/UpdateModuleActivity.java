package com.example.fadal17068266.ModuleClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fadal17068266.DatabaseHelper;
import com.example.fadal17068266.R;

import java.util.ArrayList;

public class UpdateModuleActivity extends AppCompatActivity {
    EditText modName, modDescription, modID;
    Button updateModBtn;
    private ArrayList<Module> moduleArrayList = new ArrayList<Module>();
    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_module);

        modName = findViewById(R.id.etUpdModuleName);
        modDescription = findViewById(R.id.etUpdModuleDescription);
        modID = findViewById(R.id.etUpdModID);
        updateModBtn = findViewById(R.id.updateModBtn);

        //adding text changed listener to all the edit text components of the view
        modName.addTextChangedListener(updateModuleTextWatcher);
        modDescription.addTextChangedListener(updateModuleTextWatcher);
        modID.addTextChangedListener(updateModuleTextWatcher);

        //getting all the modules from the modules database table
        this.moduleArrayList = mDatabaseHelper.getAllModules();

        //initialising the state of the update module button
        updateModBtn.setEnabled(false);
        updateModBtn.setText("Fill All Fields!");
    }

    private TextWatcher updateModuleTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //storing the contents of the edit text components in the activity into local variables
            String checkModuleName = modName.getText().toString().trim();
            String checkModuleDescription = modDescription.getText().toString().trim();
            String checkModuleID = modID.getText().toString().trim();
            //a boolean variable that will state if the inputted module exists
            boolean moduleExists = false;
            //a variable that will store the integer value of the checkModuleID variable
            int integerInputtedModID = 0;

            //checking if the module id edit text field is not empty
            if(!checkModuleID.isEmpty())
            {
                //getting the integer value of the checkModuleID (the inputted id)
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
            if(!checkModuleName.isEmpty() && !checkModuleDescription.isEmpty() && !checkModuleID.isEmpty())
            {
                //changing the update module button to enabled since there are values to add
                updateModBtn.setText("Update");
                updateModBtn.setEnabled(true);
                //checking if the inputted module doesn't exist
                if(!moduleExists)
                {
                    //changing the update module button to not enabled since the inputted module id is invalid
                    updateModBtn.setText("Module doesn't exist");
                    updateModBtn.setEnabled(false);
                }
            }
            else
            {
                //changing the update module button to not enabled since not all edit text components are filled
                updateModBtn.setText("Fill All Fields");
                updateModBtn.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void updateModBtnOnClick(View view)
    {
        //storing the values of the edit text components into local variable
        String mName = modName.getText().toString();
        String mDescription = modDescription.getText().toString();
        int mID = Integer.valueOf(modID.getText().toString());

        //returning the data back to the module list activity
        Intent intent = getIntent();
        intent.putExtra("module name", mName);
        intent.putExtra("module description", mDescription);
        intent.putExtra("module id", mID);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void returnToModListFromUpdActBtnOnClick(View view)
    {
        finish();
    }
}
