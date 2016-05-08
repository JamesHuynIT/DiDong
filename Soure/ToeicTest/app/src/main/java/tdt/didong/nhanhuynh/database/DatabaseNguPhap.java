package tdt.didong.nhanhuynh.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NhanHuynh on 5/8/2016.
 */
public class DatabaseNguPhap {

    private static DatabaseNguPhap instance;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseNguPhap(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseNguPhap getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseNguPhap(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of bi quyet
     */
    public List<String> getTenNguPhap() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT TENNP FROM NGUPHAP", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    /**
     * Read all quotes from the database.
     *
     * @return a text of bi quyet
     */
    public List<String> getNoiDungNguPhap() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT NOIDUNGNP FROM NGUPHAP ORDER BY MANP", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
