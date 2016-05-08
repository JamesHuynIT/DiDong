package tdt.didong.nhanhuynh.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

 public class DatabaseReading {

    private static DatabaseReading instance;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseReading(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseReading getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseReading(context);
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

    public int getCountCHReading(){
        int count = 0;
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM CAUHOI WHERE LOAICH='Reading'", null);
        if (cursor.moveToFirst()){
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public List<String> getNoiDungCauHoiReading() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT CAUHOIBAITHI FROM CAUHOI WHERE LOAICH='Reading'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getDapAnReading() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT NOIDUNGDA FROM DAPAN, CAUHOI WHERE DAPAN.MACH=CAUHOI.MACH " +
                "AND CAUHOI.LOAICH='Reading' ORDER BY DAPAN.MADA", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

     public List<String> getDapAnDungReading() {
         List<String> list = new ArrayList<>();
         Cursor cursor = database.rawQuery("SELECT MADA FROM DAPAN,CAUHOI WHERE DAPAN.DADUNG = 1 " +
                 "AND DAPAN.MACH=CAUHOI.MACH AND CAUHOI.LOAICH='Reading' ORDER BY DAPAN.MADA", null);
         cursor.moveToFirst();
         while (!cursor.isAfterLast()) {
             String da = cursor.getString(0);
             list.add(String.valueOf(da.charAt(da.length() - 1)));
             cursor.moveToNext();
         }
         cursor.close();
         return list;
     }
}
