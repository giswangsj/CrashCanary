package wsj.crash.lib.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

class DbProvider {

    private SQLiteDatabase sqLiteDatabase;

    public DbProvider(Context context) {
        DbHelper helper = new DbHelper(context);
        sqLiteDatabase = helper.getReadableDatabase();
    }

    /**
     * execSQL()方法可以执行insert，update，delete语句
     * 实现对数据库的 增，删，改 功能
     * sql为操作语句 ， bindArgs为操作传递参数
     **/
    public boolean updateSQLite(String sql, Object[] bindArgs) {
        boolean isSuccess = false;
        try {
            sqLiteDatabase.execSQL(sql, bindArgs);
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            Log.i("TAG:", "数据插入数据库中状态：" + isSuccess);
        }
        return isSuccess;
    }

    /**
     * rawQuery()方法可以执行select语句
     * 实现查询功能
     * sql为操作语句 ， bindArgs为操作传递参数
     **/
    public ArrayList<HashMap<String, String>> querySQLite(String sql, String[] bindArgs) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        /**Cursor是结果集游标，使用Cursou.moveToNext()方法可以从当前行移动到下一行**/
        Cursor cursor = sqLiteDatabase.rawQuery(sql, bindArgs);
        int clo_len = cursor.getColumnCount();                 //获取数据所有列数
//        Log.i("TAG:", "querySQLite()方法中获得总列数clos_len：" + clos_len);
        while (cursor.moveToNext()) {                            //循环表格中的每一行
//            Log.i("TAG:", "进入到while循环中");
            HashMap<String, String> map = new HashMap<>();
            for (int i = 0; i < clo_len; i++) {                      //循环表格中的每一列
                String clos_name = cursor.getColumnName(i);     //从给定的索引i返回列名
                String clos_value = cursor.getString(cursor.getColumnIndex(clos_name));//返回指定的名称，没有就返回-1
                if (clos_value == null) {
                    clos_value = "";
                }
                map.put(clos_name, clos_value);
            }
            list.add(map);
        }
        cursor.close();
        return list;
    }
}
