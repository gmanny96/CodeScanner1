package codescanner.gurkirat.aarushi.codescanner1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class dataBaseAdapter {

    private Dbhelper helper;
    private static dataBaseAdapter adapter;

    static dataBaseAdapter getInstance(Context context){
        if(adapter==null) {
            adapter = new dataBaseAdapter(context);
            return adapter;
        }
        return adapter;
    }

    void insertCode(String name, String code, Long time){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Dbhelper.NAME, name);
        values.put(Dbhelper.TIME, time);
        values.put(Dbhelper.CODE, code);
        db.insert(Dbhelper.TABLE_NAME, null, values);
        Log.e("database", "insertCode");
    }

    void removeCode(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] where = {name};
        db.delete(Dbhelper.TABLE_NAME, Dbhelper.NAME+"=?",where);
        Log.e("database", "modify");
    }

    Boolean checkName(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Dbhelper.NAME};
        String[] args = {name};
        Cursor cursor = db.query(Dbhelper.TABLE_NAME, columns,Dbhelper.NAME+"=?", args, null, null, null);
        if(cursor.moveToNext()){
            cursor.close();
            return true;
        }
        return false;
    }

    String getCode(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Dbhelper.CODE};
        String[] args = {name};
        Cursor cursor = db.query(Dbhelper.TABLE_NAME, columns,Dbhelper.NAME+"=?", args, null, null, null);
        List<CodeData> list = new ArrayList<>();
        if(cursor.moveToNext()){
            return cursor.getString(cursor.getColumnIndex(Dbhelper.CODE));
        }
        return null;
    }

    void modifyCode(String name, String code){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Dbhelper.CODE, code);
        String[] where = {name};
        db.update(Dbhelper.TABLE_NAME,values, Dbhelper.NAME+"=?",where);
        Log.e("database", "modifyCode");
    }

    void updateName(String name, String newname){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Dbhelper.NAME, newname);
        String[] where = {name};
        db.update(Dbhelper.TABLE_NAME,values, Dbhelper.NAME+"=?",where);
        Log.e("database", "updateName");
    }

    List<CodeData> getCodes(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Dbhelper.NAME, Dbhelper.TIME};
        Cursor cursor = db.query(Dbhelper.TABLE_NAME, columns, null, null, null, null, Dbhelper.TIME +" DESC");
        List<CodeData> list = new ArrayList<>();
        while (cursor.moveToNext()){
            CodeData d = new CodeData();
            d.name = cursor.getString(cursor.getColumnIndex(Dbhelper.NAME));
            d.time = getDate(cursor.getLong(cursor.getColumnIndex(Dbhelper.TIME)));
            list.add(d);
            Log.e("code name", d.name);
        }
        cursor.close();
        if(list.size()==0)
            return null;
        return list;
    }

    private static String getDate(Long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());
        SimpleDateFormat syear = new SimpleDateFormat("yyyy", Locale.getDefault());

        if (Integer.parseInt(syear.format(System.currentTimeMillis())) > Integer.parseInt(syear.format(new Date(timestamp)))) {
            return sdf.format(new Date(timestamp));
        }

        SimpleDateFormat sday = new SimpleDateFormat("dd", Locale.getDefault());

        int difference = Integer.parseInt(sday.format(System.currentTimeMillis())) - Integer.parseInt(sday.format(new Date(timestamp)));
        switch(difference){
            case 1:return "Yesterday";

            case 0:return "Today";

            default:
                SimpleDateFormat sdfmonth = new SimpleDateFormat("dd MMM", Locale.getDefault());
                return sdfmonth.format(new Date(timestamp));

        }
    }

    dataBaseAdapter(Context context){
        helper = new Dbhelper(context);
    }

    public class Dbhelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "scandatabase";
        static final String TABLE_NAME = "CODES";
        static final String NAME = "NAME";
        static final String TIME = "TIME";
        static final String CODE = "CODE";
        private static final int DATABASE_VERSION = 1;

        private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " ("
                + NAME + " TEXT,"
                + CODE + " TEXT,"
                + TIME + " LONG);";

        Dbhelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}