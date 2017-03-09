package com.giousa.sqladapterdemo;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/3/9
 * Time:下午10:16
 */

public class CursorActivity extends Activity {

    private ListView lv;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("-----第二个界面调用了-----");

        lv = (ListView) findViewById(R.id.lv);

        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/info.db";
        db = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select * from " + Constant.TABLE_NAME, null);

        MyCursorAdapter myCursorAdapter = new MyCursorAdapter(this,cursor,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(myCursorAdapter);
    }

    /**
     * 以内部类方式定义适配器
     */
    public class MyCursorAdapter extends CursorAdapter{

        public MyCursorAdapter(Context context, Cursor c, int autoRequery) {
            super(context, c, autoRequery);
        }

        /**
         * 表示创建适配器控件中每个item对应view对象
         * @param context 上下文
         * @param cursor  数据源cursor对象
         * @param parent  当前item父布局
         * @return        每项item的view对象
         */
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(CursorActivity.this).inflate(R.layout.list_cursor, null);
            return view;
        }

        /**
         * 通过newView方法确定了每个item展示的view对象,在bindView中对布局中的控件进行填充
         * @param view     由newView方法返回的view对象
         * @param context  上下文
         * @param cursor   数据源cursor对象
         */
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tv_id = (TextView) view.findViewById(R.id.tv_id);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_age = (TextView) view.findViewById(R.id.tv_age);

            tv_id.setText(cursor.getInt(cursor.getColumnIndex(Constant._ID))+"");
            tv_name.setText(cursor.getString(cursor.getColumnIndex(Constant.NAME)));
            tv_age.setText(cursor.getInt(cursor.getColumnIndex(Constant.AGE))+"");
        }
    }
}
