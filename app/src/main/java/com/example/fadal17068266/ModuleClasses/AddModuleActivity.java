package com.example.fadal17068266.ModuleClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fadal17068266.R;

public class AddModuleActivity extends AppCompatActivity {
    private EditText moduleName, moduleDescription;
    private Button addModBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        moduleName = findViewById(R.id.etModuleName);
        moduleDescription = findViewById(R.id.etModuleDescription);
        addModBtn = findViewById(R.id.addModBtn);

        //adding text changed listener to all the edit text components of the view
        moduleName.addTextChangedListener(addModuleTextWatcher);
        moduleDescription.addTextChangedListener(addModuleTextWatcher);

        //initialising the state of the add module button
        addModBtn.setEnabled(false);
        addModBtn.setText("Fill All Fields!");
    }

    private TextWatcher addModuleTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //storing the contents of the edit text components in the activity into local variables
            String checkModuleName = moduleName.getText().toString().trim();
            String checkModuleDescription = moduleDescription.getText().toString().trim();

            //checking if all the edit text components are not empty
            if(!checkModuleName.isEmpty() && !checkModuleDescription.isEmpty())
            {
                //changing the add module button to enabled since there are values to add
                addModBtn.setEnabled(true);
                addModBtn.setText("ADD");
            }
            else
            {
                //changing the add module button to not enabled since not all edit text components are filled
                addModBtn.setEnabled(false);
                addModBtn.setText("Fill All Fields!");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void addModBtnOnClick(View view)
    {
        String modName = moduleName.getText().toString();
        String modDescription = moduleDescription.getText().toString();

        Intent intent = getIntent();
        intent.putExtra("module name", modName);
        intent.putExtra("module description", modDescription);
        setResult(RESULT_OK, intent);
        finish();

    }

    public void returnToModListActBtnOnClick(View view)
    {
        finish();
    }
}
