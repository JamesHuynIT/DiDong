package tdt.didong.nhanhuynh.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NhanHuynh on 5/6/2016.
 */
public class DatabaseListening {

    private static DatabaseListening instance;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseListening(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseListening getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseListening(context);
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
    public ArrayList<Bitmap> getImagepart1(){
        ArrayList<Bitmap> lstHinhAnh = new ArrayList<Bitmap>();
        String query = "SELECT HINHANH FROM CAUHOI WHERE CAUHOI.LOAICH='Listening' " +
                "AND CAUHOI.HINHANH IS  NOT NULL AND CAUHOI.MAPHAN='Part 1' ORDER BY CAUHOI.MACH" ;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
       do{
            byte[] imgByte = cursor.getBlob(0);
            Bitmap theImage = BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
            lstHinhAnh.add(theImage);

        } while(cursor.moveToNext());
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return lstHinhAnh;
    }

    public int getCountCHListenPart1(){
        int count = 0;
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM CAUHOI WHERE CAUHOI.LOAICH='Listening' and CAUHOI.MAPHAN='Part 1'", null);
        if (cursor.moveToFirst()){
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public List<String> getNoiDungCauHoiListen() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT CAUHOIBAITHI FROM CAUHOI WHERE CAUHOI.LOAICH='Listening' ORDER BY CAUHOI.MACH", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<byte[]> getAmThanhListen() {
        List<byte[]> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT AMTHANH FROM CAUHOI WHERE CAUHOI.LOAICH='Listening' ORDER BY CAUHOI.MACH", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getBlob(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<byte[]> getAmThanhListenpart2() {
        List<byte[]> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT AMTHANH FROM CAUHOI WHERE CAUHOI.LOAICH='Listening' AND CAUHOI.MAPHAN='Part 2'  ORDER BY CAUHOI.MACH", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getBlob(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getDapAnListening() {
        List<String> list = new ArrayList<>();
            Cursor cursor = database.rawQuery("SELECT NOIDUNGDA FROM DAPAN, CAUHOI WHERE DAPAN.MACH=CAUHOI.MACH " +
                    "AND CAUHOI.LOAICH='Listening' ORDER BY DAPAN.MADA", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getDapAnDungListening() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT MADA FROM DAPAN,CAUHOI WHERE DAPAN.DADUNG = 1 " +
                "AND DAPAN.MACH=CAUHOI.MACH AND CAUHOI.LOAICH='Listening' AND CAUHOI.MAPHAN='Part 1' ORDER BY DAPAN.MADA", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String da = cursor.getString(0);
            list.add(String.valueOf(da.charAt(da.length() - 1)));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getDapAnDungListeningPart2() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT MADA FROM DAPAN,CAUHOI WHERE DAPAN.DADUNG = 1 " +
                "AND DAPAN.MACH=CAUHOI.MACH AND CAUHOI.LOAICH='Listening' AND CAUHOI.MAPHAN='Part 2' ORDER BY DAPAN.MADA", null);
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
