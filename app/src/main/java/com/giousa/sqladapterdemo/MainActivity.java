package com.giousa.sqladapterdemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.io.File;

/**
 * 查询SD卡中数据库
 */
public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);

        //1.获取数据源
        //String path, CursorFactory factory, int flags
        //String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"info.db";
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/info.db";
        db = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select * from " + Constant.TABLE_NAME, null);
        //2.将数据源加载到适配器中
        //SimpleCursorAdapter(Context context, int layout, Cursor c, String[] from,int[] to, int flags)
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_cursor, cursor, new String[]{Constant._ID, Constant.NAME, Constant.AGE},
                new int[]{R.id.tv_id, R.id.tv_name, R.id.tv_age}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        //3.将适配器数据加载到控件
        lv.setAdapter(adapter);

    }
}
