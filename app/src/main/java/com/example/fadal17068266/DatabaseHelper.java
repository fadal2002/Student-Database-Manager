package com.example.fadal17068266;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.fadal17068266.ModuleClasses.Module;
import com.example.fadal17068266.StudentClasses.Student;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase mDataBase;
    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "student_module.db";

    //columns for the student table
    public static final String STUDENT_TABLE = "student";
    public static final String STUDENT_ID_COL = "id";
    public static final String STUDENT_FIRSTNAME_COL = "first_name";
    public static final String STUDENT_LASTNAME_COL = "last_name";
    public static final String STUDENT_AGE_COL = "age";
    public static final String STUDENT_MODULE_ID_COL = "module_id";
    public static final String STUDENT_IMAGE_COL = "image";

    //columns for the module table
    public static final String MODULE_TABLE = "module";
    public static final String MODULE_ID_COL = "id";
    public static final String MODULE_NAME_COL = "name";
    public static final String MODULE_DESCRIPTION_COL = "description";

    //columns for the student-module info table
    public static final String STUDMOD_TABLE = "studmod";
    public static final String STUDMOD_STUDENT_ID_COL = "student_id";
    public static final String STUDMOD_STUDENT_NAME_COL = "student_name";
    public static final String STUDMOD_MODULE_NAME_COL = "module_name";
    public static final String STUDMOD_MODULE_ID_COL = "module_id";
    public static final String STUDMOD_STUDENT_IMAGE_COL = "student_image";


    //student table create string
    public static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + STUDENT_TABLE + "("
            + STUDENT_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + STUDENT_FIRSTNAME_COL + " TEXT NOT NULL, "
            + STUDENT_LASTNAME_COL + " TEXT NOT NULL, "
            + STUDENT_AGE_COL + " INTEGERT NOT NULL, "
            + STUDENT_MODULE_ID_COL + " INTEGER NOT NULL,"
            + STUDENT_IMAGE_COL + " BLOB" + ");";

    //module table create string
    public  static final String CREATE_TABLE_MODULES = "CREATE TABLE " + MODULE_TABLE + "("
            + MODULE_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MODULE_NAME_COL + " TEXT NOT NULL, "
            + MODULE_DESCRIPTION_COL + " TEXT NOT NULL" + ");";

    //student-module table create string
    public static final String CREATE_TABLE_STUDMOD = "CREATE TABLE " + STUDMOD_TABLE + "("
            + STUDMOD_STUDENT_ID_COL + " INTEGER PRIMARY KEY, "
            + STUDMOD_STUDENT_NAME_COL + " TEXT NOT NULL, "
            + STUDMOD_MODULE_NAME_COL + " TEXT NOT NULL, "
            + STUDMOD_MODULE_ID_COL + " INTEGERT NOT NULL, "
            + STUDMOD_STUDENT_IMAGE_COL + " BLOB" + ");";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_MODULES);
        db.execSQL(CREATE_TABLE_STUDMOD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MODULE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STUDMOD_TABLE);
        onCreate(db);
    }

    //================================STUDENT TABLE FUNCTIONS=======================================
    //a function to add a student to the student database table
    public boolean addStudent(String firstName, String lastName, int age, int moduleID)
    {
        mDataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.STUDENT_FIRSTNAME_COL, firstName);
        values.put(this.STUDENT_LASTNAME_COL, lastName);
        values.put(this.STUDENT_AGE_COL, age);
        values.put(this.STUDENT_MODULE_ID_COL, moduleID);
        Log.d(TAG, "addData: Adding " + firstName + ", " + lastName + ", " + age + ", " + moduleID + " to " + STUDENT_TABLE);
        //inserting the inputted data into student database table
        long result = mDataBase.insert(this.STUDENT_TABLE, null, values);
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //a function to update a student in the student database table
    public boolean updateStudent(String firstName, String lastName, int age, int moduleID, int ID)
    {
        mDataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.STUDENT_FIRSTNAME_COL, firstName);
        values.put(this.STUDENT_LASTNAME_COL, lastName);
        values.put(this.STUDENT_AGE_COL, age);
        values.put(this.STUDENT_MODULE_ID_COL, moduleID);
        Log.d(TAG, "updateData: Updating " + firstName + ", " + lastName + ", " + age + ", " + moduleID + " in " + STUDENT_TABLE);
        //update the data of the specified student in the student database table based on the inputted student id
        long result = mDataBase.update(STUDENT_TABLE, values, "id=?", new String[]{String.valueOf(ID)});
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //a function to delete a student from the student database table
    public boolean deleteStudent(int ID)
    {
        mDataBase = this.getWritableDatabase();
        Log.d(TAG, "deleteData: Deleting " + ID + " from " + STUDENT_TABLE);
        //deleting the specified student from the student database table based on the inputted student id
        long result = mDataBase.delete(STUDENT_TABLE, "id=?", new String[]{String.valueOf(ID)});
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //a function to upload/add an image to a student in the student database table
    public boolean addStudentImage(int ID, byte[] image)
    {
        mDataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.STUDENT_IMAGE_COL, image);
        Log.d(TAG, "addImage: adding image to " + ID + " in " + STUDENT_TABLE);
        //inserting the byte array of the image and store them in the data of the specified student based on the inputted id
        long result = mDataBase.update(STUDENT_TABLE, values, "id=?", new String[]{String.valueOf(ID)});
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //a function to retrieve all the student information from the Student table
    public ArrayList<Student> getAllStudent()
    {
        //this function will get all the data available in the student database table and store it in an Arraylist of Student objects
        mDataBase = this.getReadableDatabase();
        ArrayList<Student> studentArrayList = new ArrayList<>();
        //string to store the query that will select all the data in the student database table
        String selectQuery = "SELECT * FROM " + this.STUDENT_TABLE;
        Cursor cursor = mDataBase.rawQuery(selectQuery, null);
        while(cursor.moveToNext())
        {
            //getting the student data from the cursor
            int id = cursor.getInt(cursor.getColumnIndex(this.STUDENT_ID_COL));
            String firstName = cursor.getString(cursor.getColumnIndex(this.STUDENT_FIRSTNAME_COL));
            String lastName = cursor.getString(cursor.getColumnIndex(this.STUDENT_LASTNAME_COL));
            int age = cursor.getInt(cursor.getColumnIndex(this.STUDENT_AGE_COL));
            int moduleID = cursor.getInt(cursor.getColumnIndex(this.STUDENT_MODULE_ID_COL));
            byte[] img = cursor.getBlob(cursor.getColumnIndex(this.STUDENT_IMAGE_COL));
            //creating Student object and inputting the student data into it
            Student currentStudent = new Student(id, firstName, lastName, age, moduleID, img);
            //storing the Student object in the created ArrayList
            studentArrayList.add(currentStudent);
        }
        //returning the ArrayList with all the student objects that were created from the data that is inside the student database table
        return studentArrayList;
    }
    //==============================================================================================

    //================================MODULE TABLE FUNCTIONS========================================
    //a function to add a module to the module database table
    public boolean addModule(String modName, String modDescription)
    {
        mDataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.MODULE_NAME_COL, modName);
        values.put(this.MODULE_DESCRIPTION_COL, modDescription);
        Log.d(TAG, "addData: Adding " + modName + ", " + modDescription + " to " + MODULE_TABLE);
        //inserting the inputted data into student database table
        long result = mDataBase.insert(this.MODULE_TABLE, null, values);
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //a function to retrieve all the modules information from the module table
    public ArrayList<Module> getAllModules()
    {
        //this function will get all the data available in the module database table and store it in an Arraylist of Module objects
        mDataBase = this.getReadableDatabase();
        ArrayList<Module> moduleArrayList = new ArrayList<>();
        //string to store the query that will select all the data in the module database table
        String selectQuery = "SELECT * FROM " + this.MODULE_TABLE;
        Cursor cursor = mDataBase.rawQuery(selectQuery, null);
        while(cursor.moveToNext())
        {
            //getting the student data from the cursor
            int mod_id = cursor.getInt(cursor.getColumnIndex(this.MODULE_ID_COL));
            String mod_Name = cursor.getString(cursor.getColumnIndex(this.MODULE_NAME_COL));
            String mod_Description = cursor.getString(cursor.getColumnIndex(this.MODULE_DESCRIPTION_COL));
            //creating Module object and inputting the module data into it
            Module currentModule = new Module(mod_id, mod_Name, mod_Description);
            //storing the Module object in the created ArrayList
            moduleArrayList.add(currentModule);
        }
        //returning the ArrayList with all the module objects that were created from the data that is inside the module database table
        return moduleArrayList;
    }

    //a function to update a module in the module database table
    public boolean updateModule(String modName, String modDescription, int ID)
    {
        mDataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.MODULE_NAME_COL, modName);
        values.put(this.MODULE_DESCRIPTION_COL, modDescription);
        Log.d(TAG, "updateData: Updating " + modName + ", " + modDescription +  " in " + MODULE_TABLE);
        //update the data of the specified module in the module database table based on the inputted module id
        long result = mDataBase.update(MODULE_TABLE, values, "id=?", new String[]{String.valueOf(ID)});
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //a function to delete a Module from the Module database table
    public boolean deleteModule(int ID)
    {
        mDataBase = this.getWritableDatabase();
        Log.d(TAG, "deleteData: Deleting " + ID + " from " + MODULE_TABLE);
        //deleting the specified module from the module database table based on the inputted module id
        long result = mDataBase.delete(MODULE_TABLE, "id=?", new String[]{String.valueOf(ID)});
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    //==============================================================================================

    //================================STUDENT-MODULE TABLE FUNCTIONS========================================
    //a function to update the student-module database table
    public boolean updateStudModDB(ArrayList<Studmod> mStudmodArrayList)
    {
        //this function will get student-module objects from arraylist
        mDataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //looping through the arraylist of student-module arraylist
        for(Studmod currStudmod : mStudmodArrayList)
        {
            //getting the data of each student-module object
            values.put(this.STUDMOD_STUDENT_ID_COL, currStudmod.getStudentID());
            values.put(this.STUDMOD_STUDENT_NAME_COL, currStudmod.getStudentName());
            values.put(this.STUDMOD_MODULE_NAME_COL, currStudmod.getModuleName());
            values.put(this.STUDMOD_MODULE_ID_COL, currStudmod.getModuleID());
            values.put(this.STUDMOD_STUDENT_IMAGE_COL, currStudmod.getStudentImg());
        }
        //clearing the student-module database table
        clearDatabase(STUDMOD_TABLE);
        Log.d(TAG, "updateData: updating " + STUDMOD_TABLE + " table data!");
        //inserting the data that was collected from the inputted student-module arraylist
        long result = mDataBase.insert(this.STUDMOD_TABLE, null, values);
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //==============================================================================================

    //a function to clear the database table
    public void clearDatabase(String table_name)
    {
        mDataBase = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + table_name + "'";
        mDataBase.delete(table_name, null, null);
        mDataBase.execSQL(clearDBQuery);
    }

    //a function to check if a table in the database is empty
    public boolean isEmpty(String table_name)
    {
        mDataBase = this.getReadableDatabase();
        Cursor cursor = mDataBase.rawQuery("SELECT COUNT(*) FROM " + table_name, null);
        boolean empty;
        if(cursor != null && cursor.moveToFirst())
        {
            empty = (cursor.getInt(0) == 0);
        }
        else
        {
            empty = false;
        }

        return empty;
    }
}










