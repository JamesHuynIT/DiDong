package tdt.didong.nhanhuynh.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NhanHuynh on 5/6/2016.
 */
public class DatabaseBiQuyet {

    private static DatabaseBiQuyet instance;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseBiQuyet(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseBiQuyet getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseBiQuyet(context);
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
    public List<String> getTenBiQuyet() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT TENBQ FROM BIQUYETONTHI", null);
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
    public List<String> getNoiDungBiQuyet() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT NOIDUNGBQ FROM BIQUYETONTHI ORDER BY MABQ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}