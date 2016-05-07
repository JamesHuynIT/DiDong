package tdt.didong.nhanhuynh.toeictest;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    String[] mItems = {"Bí kíp luyện thi Toeic", "Ngữ pháp trong ôn thi Toeic",
            "Listening", "Reading", "All Test", "Hướng dẫn sử dụng"};
    Integer[] mThumbnails = {R.drawable.ic_bikip, R.drawable.ic_nguphap, R.drawable.ic_listening,
            R.drawable.ic_reading, R.drawable.ic_lambaithi, R.drawable.ic_help};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomIconLabelAdapter adapter = new CustomIconLabelAdapter(this, R.layout.custom_row_icon_label, mItems, mThumbnails);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (position == 0) {
            Intent myIntent = new Intent(this, BiQuyetActivity.class);
            startActivity(myIntent);
        } else if (position == 1) {
            Intent myIntent = new Intent(this, NguPhapActivity.class);
            startActivity(myIntent);
        } else if (position == 2) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(getString(R.string.dialog));

            alertDialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent myIntent = new Intent(MainActivity.this, LuyenThiListeningActivity.class);
                    startActivity(myIntent);
                }
            });

            alertDialogBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        } else if (position == 3) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(getString(R.string.dialog));

            alertDialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent myIntent = new Intent(MainActivity.this, LuyenThiReadingActivity.class);
                    startActivity(myIntent);
                }
            });

            alertDialogBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else if (position == 4) {
            Intent myIntent = new Intent(this, LuyenThiFullActivity.class);
            startActivity(myIntent);
        } else if (position == 5) {
            Intent myIntent = new Intent(this, HuongDanActivity.class);
            startActivity(myIntent);
        }
    }
}