package mx.unam.jigm.ejercicio1.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import mx.unam.jigm.ejercicio1.model.ModelUserBdd;
import mx.unam.jigm.ejercicio1.sql.UsersDataSource;


/**
 * Created by Mario on 25/06/2016.
 */
public class UsersDataSource {

    private final SQLiteDatabase db;

    public UsersDataSource(Context context)
    {
        MySqliteHelper helper = new MySqliteHelper(context);
        db=helper.getWritableDatabase();
    }
    public void saveUser(ModelUserBdd modelUserbdd)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySqliteHelper.COLUMN_USER_NAME,modelUserbdd.nameusr);
        contentValues.put(MySqliteHelper.COLUMN_USER_PWD,modelUserbdd.pwdusr);
        contentValues.put(MySqliteHelper.COLUMN_USER_LASTSESSION,modelUserbdd.last_sessionusr);
        contentValues.put(MySqliteHelper.COLUMN_USER_TIMEINAPP,modelUserbdd.time_inusr);
        db.insert(MySqliteHelper.TABLE_USER_NAME,null,contentValues);
    }
    public void deletUser(ModelUserBdd modelUserbdd)
    {
        db.delete(MySqliteHelper.TABLE_USER_NAME,MySqliteHelper.COLUMN_USER_ID+"=?",new String[]{String.valueOf(modelUserbdd.id)});
    }
    public List<ModelUserBdd> getAllUsers()
    {
        List<ModelUserBdd> ModelUserBddList = new ArrayList<>();
        Cursor cursor = db.query(MySqliteHelper.TABLE_USER_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_USER_ID));
            String userName=cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_USER_NAME));
            String userPWD = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_USER_PWD));
            String userLastSession = cursor.getColumnName(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_USER_LASTSESSION));
            String userTimeIn = cursor.getColumnName(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_USER_TIMEINAPP));
            ModelUserBdd modelUserbdd = new ModelUserBdd();
            modelUserbdd.id=id;
            modelUserbdd.nameusr=userName;
            modelUserbdd.pwdusr=userPWD;
            modelUserbdd.last_sessionusr=userLastSession;
            modelUserbdd.time_inusr=userTimeIn;
            ModelUserBddList.add(modelUserbdd);
        }
        return ModelUserBddList;
    }
    public List<ModelUserBdd> getUser(String user, String pwd)
    {
        List<ModelUserBdd> ModelUserBddList = new ArrayList<>();
        Cursor cursor = db.query(MySqliteHelper.TABLE_USER_NAME,null,MySqliteHelper.COLUMN_USER_NAME+"=? and "+MySqliteHelper.COLUMN_USER_PWD+"=?",new String[]{user,pwd},null,null,null);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_USER_ID));
            String userName=cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_USER_NAME));
            String userPWD = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_USER_PWD));
            String userLastSession = cursor.getColumnName(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_USER_LASTSESSION));
            String userTimeIn = cursor.getColumnName(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_USER_TIMEINAPP));
            ModelUserBdd modelUserbdd = new ModelUserBdd();
            modelUserbdd.id=id;
            modelUserbdd.nameusr=userName;
            modelUserbdd.pwdusr=userPWD;
            modelUserbdd.last_sessionusr=userLastSession;
            modelUserbdd.time_inusr=userTimeIn;
            ModelUserBddList.add(modelUserbdd);
        }
        return ModelUserBddList;
    }
    public List<ModelUserBdd> getUserByID(String shared_id)
    {
        List<ModelUserBdd> ModelUserBddList = new ArrayList<>();
        Cursor cursor = db.query(MySqliteHelper.TABLE_USER_NAME,null,MySqliteHelper.COLUMN_USER_ID+"=?",new String[]{shared_id},null,null,null);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_USER_ID));
            String userName=cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_USER_NAME));
            String userPWD = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_USER_PWD));
            String userLastSession = cursor.getColumnName(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_USER_LASTSESSION));
            String userTimeIn = cursor.getColumnName(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_USER_TIMEINAPP));
            ModelUserBdd modelUserbdd = new ModelUserBdd();
            modelUserbdd.id=id;
            modelUserbdd.nameusr=userName;
            modelUserbdd.pwdusr=userPWD;
            modelUserbdd.last_sessionusr=userLastSession;
            modelUserbdd.time_inusr=userTimeIn;
            ModelUserBddList.add(modelUserbdd);
        }
        return ModelUserBddList;
    }
}

