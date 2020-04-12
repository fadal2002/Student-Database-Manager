package com.example.fadal17068266.StudentClasses;

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

public class StudentListActivity extends AppCompatActivity implements StudentAdapter.onStudentListener{

    DatabaseHelper mDatabaseHelper;
    public ArrayList<Student> studentArrayList;
    private boolean isInitialised = false;

    //static ints for onActivityResult request codes
    public static final int ADD_REQUEST_CODE = 0;
    public static final int UPDATE_REQUEST_CODE = 1;
    public static final int UPLOAD_REQUEST_CODE = 2;

    //information about the clicked student item view
    private int clickedItemPos;
    private int clickedStudentID;

    private RecyclerView mRecyclerView;
    public StudentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String TABLE_NAME = "student";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        mDatabaseHelper = new DatabaseHelper(this);

        createStudentData();
        buildRecyclerView();

    }

    //a function that will deal with all the operation that involve data received from other activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            //local variables to store received data
            String firstName;
            String lastName;
            int age;
            int moduleID;
            int ID;

            //a switch statement that will perform operations based on the request codes
            switch(requestCode)
            {
                case ADD_REQUEST_CODE: //add activity request code
                    if(resultCode == RESULT_OK)
                    {
                        //storing the data received from the add activity
                        firstName = data.getStringExtra("first name");
                        lastName = data.getStringExtra("last name");
                        age = data.getIntExtra("age", 0);
                        moduleID = data.getIntExtra("module id", 0);
                        //adding the student details to the student database table
                        mDatabaseHelper.addStudent(firstName, lastName, age, moduleID);
                        //getting all the student data from the student database table and storing it in an array list
                        studentArrayList = mDatabaseHelper.getAllStudent();
                        //uploading the student data to the recycler view
                        mAdapter.updateRecyclerView(studentArrayList);
                        //scroll to the last position of the recycler view
                        mRecyclerView.smoothScrollToPosition(studentArrayList.size() - 1);
                    }
                    break;
                case UPDATE_REQUEST_CODE: //update activity request code
                    if(resultCode == RESULT_OK)
                    {
                        //storing the data received from the update activity
                        firstName = data.getStringExtra("first name");
                        lastName = data.getStringExtra("last name");
                        age = data.getIntExtra("age", 0);
                        moduleID = data.getIntExtra("module id", 0);
                        ID = data.getIntExtra("id", 0);
                        //change the data that is in the student database table
                        mDatabaseHelper.updateStudent(firstName, lastName, age, moduleID, ID);
                        //getting all the student data from the student database table and storing it in an array list
                        studentArrayList = mDatabaseHelper.getAllStudent();
                        //uploading the student data to the recycler view
                        mAdapter.updateRecyclerView(studentArrayList);
                        //scroll to the position of the updated student in the recycler view
                        mRecyclerView.smoothScrollToPosition(ID - 1);
                    }
                    break;
                case UPLOAD_REQUEST_CODE: //upload activity request code
                    if(resultCode == RESULT_OK)
                    {
                        //storing the byte array of the image
                        byte[] imgBytes = data.getByteArrayExtra("imgBytes");
                        //adding the image to the student database table
                        mDatabaseHelper.addStudentImage(clickedStudentID, imgBytes);
                        //getting all the student data from the student database table
                        studentArrayList = mDatabaseHelper.getAllStudent();
                        //uploading the student data to the recycler view
                        mAdapter.updateRecyclerView(studentArrayList);
                        //scrolling to position of the clicked item
                        mRecyclerView.smoothScrollToPosition(clickedItemPos);
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

    public void updateStudentBtnOnClick(View view)
    {
        Intent updateIntent = new Intent(this, UpdateStudentActivity.class);
        startActivityForResult(updateIntent, UPDATE_REQUEST_CODE);
    }

    public void addStudentBtnOnClick(View view)
    {
        Intent addIntent = new Intent(this, AddStudentActivity.class);
        startActivityForResult(addIntent, ADD_REQUEST_CODE);
    }

    public void returnToMainActFromStudListBtnOnClick(View view)
    {
        finish();
    }

    //a function to retrieve the student data from the student database helper
    public void createStudentData()
    {
        studentArrayList = mDatabaseHelper.getAllStudent();
    }


    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.studentRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new StudentAdapter(studentArrayList, this);
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

            TextView tvIdDisp = viewHolder.itemView.findViewById(R.id.tvID_Display);
            //getting the id of the swiped student
            int id = Integer.valueOf(tvIdDisp.getText().toString());
            //deleting the student with the extracted id from the student database table
            mDatabaseHelper.deleteStudent(id);
            //clearing the student array list
            studentArrayList.clear();
            //getting all the student data from the student database table
            studentArrayList = mDatabaseHelper.getAllStudent();
            //upload the retrieved student data to the recycler view
            mAdapter.updateRecyclerView(studentArrayList);
        }
    };

    //a function to open the upload activity when clicked on student
    @Override
    public void onStudentClick(int position) {

        TextView tvClickedID = (TextView) mRecyclerView.findViewHolderForLayoutPosition(position).itemView.findViewById(R.id.tvID_Display);
        this.clickedStudentID = Integer.valueOf(tvClickedID.getText().toString());
        this.clickedItemPos = position;
        Intent uploadIntent = new Intent(this, UploadImageActivity.class);
        startActivityForResult(uploadIntent, UPLOAD_REQUEST_CODE);
    }

}
