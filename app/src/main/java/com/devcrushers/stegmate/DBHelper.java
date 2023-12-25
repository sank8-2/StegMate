package com.devcrushers.stegmate;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "STUDENT INFO";
    private static final String TBL_NAME = "STUDENT";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_USN = "USN";
    private static final String KEY_EMAIL= "EMAIL";
    private static final String KEY_PHONE = "PHONE";
    private static final String KEY_PASS = "PASSWORD";
    private static final int DB_VERSION = 1;


    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TBL_NAME
                +"("+KEY_NAME+" TEXT,"
                +KEY_USN+" TEXT PRIMARY KEY,"
                +KEY_EMAIL+" TEXT,"
                +KEY_PHONE+" TEXT,"
                +KEY_PASS+" TEXT)");
    }


    public void addStudent(String name,String usn,String email,String phone,String pass){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_USN,usn);
        values.put(KEY_EMAIL,email);
        values.put(KEY_PHONE,phone);
        values.put(KEY_PASS,pass);

        db.insert(TBL_NAME,null,values);
    }

    public ArrayList<StudentModel> fetchStudent(){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery("SELECT * FROM "+TBL_NAME,null);

        ArrayList<StudentModel> arrStudents = new ArrayList<>();

        while(cursor.moveToNext()) {
            StudentModel model = new StudentModel();
            model.name = cursor.getString(0);
            model.usn = cursor.getString(1);
            model.email = cursor.getString(2);
            model.phone = cursor.getString(3);
            model.pass = cursor.getString(4);
            arrStudents.add(model);
        }
        return arrStudents;
    }

    public ArrayList<StudentModel> fetchLoggedStudent(String USN){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery("SELECT * FROM "+TBL_NAME +" WHERE "+KEY_USN+"=\""+USN+"\"",null);

        ArrayList<StudentModel> arrStudents = new ArrayList<>();

        while(cursor.moveToNext()) {
            StudentModel model = new StudentModel();
            model.name = cursor.getString(0);
            model.usn = cursor.getString(1);
            model.email = cursor.getString(2);
            model.phone = cursor.getString(3);
            model.pass = cursor.getString(4);
            arrStudents.add(model);
        }
        return arrStudents;
    }


    public void updateStudent(ContentValues contentValues){
        SQLiteDatabase db=this.getWritableDatabase();

        String Susn=contentValues.get("USN").toString();

        db.update(TBL_NAME,contentValues,KEY_USN+"=?", new String[]{Susn});

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TBL_NAME);
        onCreate(db);
    }
}