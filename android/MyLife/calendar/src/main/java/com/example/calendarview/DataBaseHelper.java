package com.example.calendarview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static android.provider.Settings.System.DATE_FORMAT;
import static java.sql.Types.DATE;
import static java.sql.Types.INTEGER;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_INCOME = "INCOME";
    public static final String COLUMN_OUTCOME = "OUTCOME";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "account.db", null, 1);
    }

    // this is called the first time a database is accessed. There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String creatTableStatement= "CREATE TABLE " + ACCOUNT_TABLE + " ( " + COLUMN_NAME + " TEXT, " + COLUMN_DATE + " LONG, " + COLUMN_INCOME + " DECIMAL, " + COLUMN_OUTCOME + " DECIMAL, " + COLUMN_DESCRIPTION + " TEXT)";

        db.execSQL(creatTableStatement);
    }
    // this is called if the database version number changes. It prevents previous users apps from breaking when you change the database design.
    //自动触发，自动修复结构
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(AccountModel accountModel){
        SQLiteDatabase db = this.getWritableDatabase();
        //Can take pairs of values and associate with them
        ContentValues cv = new ContentValues();

        /*cv.put(COLUMN_ID, accountModel.getId());*/
        cv.put(COLUMN_NAME,accountModel.getName());
        cv.put(COLUMN_DATE, accountModel.getDate());
        cv.put(COLUMN_INCOME, accountModel.getIncome());
        cv.put(COLUMN_OUTCOME, accountModel.getOutcome());
        cv.put(COLUMN_DESCRIPTION, accountModel.getDescription());

        long insert = db.insert(ACCOUNT_TABLE, null, cv);
        if (insert == -1)
            return false;
        else
            return true;
    }

    public boolean deleteOne(AccountModel accountModel){
        // find accountModel in the database. if it found, delete it and return true.
        // if it's not found, return false

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + ACCOUNT_TABLE + " WHERE " + COLUMN_DATE + " = " + accountModel.getDate() +
                " AND " + COLUMN_INCOME + " = " + BigDecimal.valueOf(accountModel.getIncome()) +
                " AND " + COLUMN_OUTCOME + " = " + BigDecimal.valueOf(accountModel.getOutcome());
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        }else
            return false;
    }

    //DB闪退定位↓
    public List<AccountModel> getAll(){
        List<AccountModel> returnList = new ArrayList<>();

        //get data from database
        String queryString = "SELECT * FROM " + ACCOUNT_TABLE + " WHERE " + COLUMN_DATE + " != " + 0;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            // loop through the cursor (result set) and create new customer objects. Put them into the return list.
            do{
                /*int id = cursor.getInt(0);*/
                String name = cursor.getString(0);
                long date = cursor.getLong(1);
                float income = cursor.getFloat(2);
                float outcome = cursor.getFloat(3);
                String description = cursor.getString(4);

                AccountModel newAccount = new AccountModel(name,date, income, outcome, description);
                returnList.add(newAccount);

            }while(cursor.moveToNext());
        }
        else {
            //failure. do not add anything to the list.
        }
        // close both cursor and db when done.
        cursor.close();
        db.close();
        return returnList;
    }

    //读取指定日期的 account
    public List<AccountModel> getSelected(long sDate){
        List<AccountModel> returnList = new ArrayList<>();

        //get data from database
        String queryString = "SELECT * FROM " + ACCOUNT_TABLE + " WHERE " + COLUMN_DATE + " = " + sDate ;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            // loop through the cursor (result set) and create new customer objects. Put them into the return list.
            do{
                /*int id = cursor.getInt(0);*/
                String name = cursor.getString(0);
                Long date = cursor.getLong(1);
                float income = cursor.getFloat(2);
                float outcome = cursor.getFloat(3);
                String description = cursor.getString(4);

                AccountModel newAccount = new AccountModel(name,date, income, outcome, description);
                returnList.add(newAccount);

            }while(cursor.moveToNext());
        }
        else {
            //failure. do not add anything to the list.
        }
        // close both cursor and db when done.
        cursor.close();
        db.close();
        return returnList;
    }

    //统计每月收入/支出
    /*public float monthIncome(String name,long sDate){
        //此处测试时无参，应传入参数为 long 类型的 sDate
        float in = 0;
        String queryString = "SELECT * FROM " + ACCOUNT_TABLE + " WHERE " + COLUMN_DATE + " = " + sDate + " AND "  + COLUMN_NAME + " = " + name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            // loop through the cursor (result set) and create new customer objects. Put them into the return list.
            do{
                int id = cursor.getInt(0);
                long date = cursor.getLong(0);
                float income = cursor.getFloat(1);
                float outcome = cursor.getFloat(2);
                String description = cursor.getString(3);
                in = in + income;
            }while(cursor.moveToNext());
        }
        else {
            //failure. do not add anything to the list.
        }
        return in;
    }

    public float monthOutcome(String name,long sDate){
        //此处测试时无参，应传入参数为 long 类型的 sDate, select 语句
        float out = 0;
        String queryString = "SELECT * FROM " + ACCOUNT_TABLE + " WHERE " + COLUMN_DATE + " = " + sDate + " AND "  + COLUMN_NAME + " = " + name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            // loop through the cursor (result set) and create new customer objects. Put them into the return list.
            do{
                /*int id = cursor.getInt(0);*/
                /*long date = cursor.getLong(0);
                float income = cursor.getFloat(1);
                float outcome = cursor.getFloat(2);
                String description = cursor.getString(3);
                out = out + outcome;
            }while(cursor.moveToNext());
        }
        else {
            //failure. do not add anything to the list.
        }
        return out;
    }*/
    public float monthIncome (String name,String date){
        DateUtils du = new DateUtils();
        float in = 0;
        long tmp_ts = du.date2TimeStamp(date);
        long gap = (3600*24*30);
        String queryString =  "SELECT * FROM " + ACCOUNT_TABLE + " WHERE " +
                "(" + COLUMN_DATE + "-"  + tmp_ts + ")" + "/" +1000 + "<=" + gap +
                " AND " + "(" + COLUMN_DATE + "-"  + tmp_ts + ")" + "/" + 1000 + ">=" + 0 + " AND "
                + COLUMN_NAME + "=" + name;
        //String queryString = "SELECT * FROM " + ACCOUNT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            // loop through the cursor (result set) and create new customer objects. Put them into the return list.
            do{
                /*int id = cursor.getInt(0);*/
                /*long date = cursor.getLong(0);*/
                float income = cursor.getFloat(2);
                /*float outcome = cursor.getFloat(2);*/
                String description = cursor.getString(4);
                System.out.println(cursor.getString(0));
                in = in + income;
            }while(cursor.moveToNext());
        }
        else {
            //failure. do not add anything to the list.
        }
        return in;
    }

    public float monthOutcome (String name,String date){
        DateUtils du = new DateUtils();
        float out = 0;
        long tmp_ts = du.date2TimeStamp(date);
        long gap = (3600*24*30);
        String queryString =  "SELECT * FROM " + ACCOUNT_TABLE + " WHERE " +
                "(" + COLUMN_DATE + "-"  + tmp_ts + ")" + "/" +1000 + "<=" + gap +
                " AND " + "(" + COLUMN_DATE + "-"  + tmp_ts + ")" + "/" + 1000 + ">=" + 0 + " AND "
                + COLUMN_NAME + "=" + name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            // loop through the cursor (result set) and create new customer objects. Put them into the return list.
            do{
                /*int id = cursor.getInt(0);*/
                /*long date = cursor.getLong(0);*/
                /*float income = cursor.getFloat(1);*/
                float outcome = cursor.getFloat(3);
                String description = cursor.getString(4);
                out = out + outcome;
                System.out.println(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        else {
            //failure. do not add anything to the list.
        }
        return out;
    }

}
