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
    //宣告
    final String DB_NAME = "tblTable";//資料庫名稱
    final int DB_VERSION = 1;//資料庫版本
    final String tableName = "tblOrder";//資料表名稱
    String today;
    tblTable myDB;
    /**
     * 先實做SQLiteOpenHelp類別可呼叫SQLiteDatabase類別  用來存取SQLite資料庫
     * 如query查詢 insert新增 update更新 等...
     */
    SQLiteDatabase db;
    EditText name,age,birthday,add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //實做 tblTable(繼承SQLiteOpenHelp)類別 建立資料庫（傳入的地方(this),資料庫名稱,標準模式處理Cursor,資料庫版本)
        myDB = new tblTable(this, DB_NAME, null, DB_VERSION);
        //實做 db(繼承SQLiteDatabase)類別 getWritableDatabase用來更新 新增修改刪除
        db = myDB.getWritableDatabase();
    }
    public void upSQL() {
        //取得輸入的字串
        name = (EditText)findViewById(R.id.editText);
        age = (EditText)findViewById(R.id.editText2);
        birthday = (EditText)findViewById(R.id.editText3);
        add = (EditText)findViewById(R.id.editText4);

        String nameAdd = name.getText().toString();
        String ageAdd = age.getText().toString();
        String birth = birthday.getText().toString();
        String addAdd = add.getText().toString();
        //執行抓取現在時間
        time();
        //執行addData方法 參數帶入輸入的值字串
        addData(db, nameAdd, ageAdd, birth, addAdd, today);

    }

    /**
     * SqLiteOpenHelper類別 據有存取資料庫的能力
     * 抽象類別 需實做必要方法
     */
    class tblTable extends SQLiteOpenHelper {
        //建構子
        public tblTable(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        //建立資料表格(假如資料庫檔案不存在)
        @Override
        public void onCreate(SQLiteDatabase db) {
            final String create =
                    //資料表格的名稱 和PK值(主鍵)
                    ("CREATE TABLE tblOrder (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "姓名 TEXT, "
                            + "年紀 TEXT, "
                            + "生日 TEXT, "
                            + "地址 TEXT, "
                            + "上傳時間 TEXT);");
            //把資料表格欄位放入資料表格
            db.execSQL(create);

        }
        //版本更新後所要處理項目
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
    //資料表格放入資料的方法
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
    //抓取現在時間的方法
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
    //查詢資料庫的全部資料表格 並顯示在字定的ListView上
    private void cursor3(){

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
