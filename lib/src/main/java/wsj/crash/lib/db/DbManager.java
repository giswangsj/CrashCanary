package wsj.crash.lib.db;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DbManager {

    private static DbManager mInstance;

    private DbProvider dbProvider;

    public static DbManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DbManager(context);
        }
        return mInstance;
    }

    private DbManager(Context context) {
        dbProvider = new DbProvider(context);
    }

    /**
     * 【插入数据】
     **/
    public void insert(Object[] data) {
//        Log.i("TAG:", "插入数据到数据库表：person中:" + data.toString());
        String sql = "insert into crash (profile, detail , time) values ( ? , ? , ?)";
        Object[] bindArgs = data;
        dbProvider.updateSQLite(sql, bindArgs);
    }

    /**
     * 【模糊查询】
     **/
    public ArrayList<HashMap<String, String>> queryAll() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        String sql = "select * from crash order by id desc";
        list = dbProvider.querySQLite(sql, null);
//        Log.i("TAG:", "查询完毕，返回数据：" + list.size());
        return list;
    }

    /**
     * 【模糊查询】
     **/
    public ArrayList<HashMap<String, String>> queryById(int id) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        String sql = "select * from crash where id = ?";
        list.addAll(dbProvider.querySQLite(sql, new String[]{String.valueOf(id)}));
//        Log.i("TAG:", "查询完毕，返回数据：" + list.size());
        return list;
    }

    /**
     * 【模糊查询】
     **/
    public ArrayList<HashMap<String, String>> query(String where1, String where2) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        String sql = "select * from crash where time like ? and info like ?";
        if (where1 == null) {
            list = dbProvider.querySQLite(sql, new String[]{"%", "%"});
        } else {
            where1 = "%" + where1 + "%";
            where2 = "%" + where2 + "%";
            list = dbProvider.querySQLite(sql, new String[]{where1, where2});
        }
//        Log.i("TAG:", "查询完毕，返回数据：" + list.size());
        return list;
    }

    /**
     * 【删除数据】
     **/
    public void deleteById(int id) {
        String sql = "delete from crash where id =  ? ";
        dbProvider.updateSQLite(sql, new String[]{String.valueOf(id)});
    }

    /**
     * 【删除数据】
     **/
    public void delete(Object[] data) {
        String sql = "delete from crash where id =  ? ";
        dbProvider.updateSQLite(sql, data);
    }

    /**
     * 【更新数据】
     **/
    public void update(Object[] data) {
        String sql = "update crash set info=? , time=? where id=?";
        dbProvider.updateSQLite(sql, data);
    }
}
