package com.example.tom.sql8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    final String DB_NAME = "tblTable";//資料庫名稱
    final int DB_VERSION = 1;//資料庫版本
    final String tableName = "tblOrder";//資料表名稱
    String today;
    tblTable myDB;
    SQLiteDatabase db;
    EditText name,age,birthday,add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void upSQL() {
        name = (EditText)findViewById(R.id.editText);
        age = (EditText)findViewById(R.id.editText2);
        birthday = (EditText)findViewById(R.id.editText3);
        add = (EditText)findViewById(R.id.editText4);

        String nameAdd = name.getText().toString();
        String ageAdd = age.getText().toString();
        String birth = birthday.getText().toString();
        String addAdd = add.getText().toString();

        myDB = new tblTable(this, DB_NAME, null, DB_VERSION);
        db = myDB.getWritableDatabase();
        time();
        addData(db, nameAdd, ageAdd, birth, addAdd, today);

    }
    class tblTable extends SQLiteOpenHelper {
        public tblTable(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String create =
                    ("CREATE TABLE tblOrder (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "姓名 TEXT, "
                            + "年紀 TEXT, "
                            + "生日 TEXT, "
                            + "地址 TEXT, "
                            + "上傳時間 TEXT);");
            db.execSQL(create);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
    private void addData(SQLiteDatabase db, String nameAdd
            , String ageAdd, String birth, String addAdd, String today) {

        ContentValues addbase = new ContentValues();
        addbase.put("姓名", nameAdd);
        addbase.put("年紀", ageAdd);
        addbase.put("生日", birth);
        addbase.put("地址", addAdd);
        addbase.put("上傳時間", today);

        db.insert(tableName, null, addbase);

    }
    private void time() {
        Calendar mCal = Calendar.getInstance();
        String dateformat = "yyyy/MM/dd/";
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        today = df.format(mCal.getTime());
    }
    public void onClick (View v){
        upSQL();

    }
    public void onClick2(View v){
        cursor3();

    }
    public void onClick3(View v){

    }
    private void cursor3(){
        myDB = new tblTable(this, DB_NAME, null, DB_VERSION);
        db = myDB.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+tableName, null);
        ListView lv = (ListView)findViewById(R.id.lv);
        SimpleCursorAdapter adapter;
        adapter = new SimpleCursorAdapter(this,
                //android.R.layout.simple_expandable_list_item_2,
                R.layout.lview,
                c,
                //new String[] {"info","amount"},
                new String[] {"_id", "姓名", "年紀", "生日", "地址", "上傳時間"},
                //new int[] {android.R.id.text1,android.R.id.text2},
                new int[] {R.id.textView,R.id.textView2,R.id.textView3,R.id.textView4,R.id.textView5,R.id.textView6},
                0);
        lv.setAdapter(adapter);
    }


}
