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
            "Làm bài thi", "Hướng dẫn sử dụng"};
    Integer[] mThumbnails = {R.drawable.ic_bikip, R.drawable.ic_nguphap,R.drawable.ic_lambaithi, R.drawable.ic_help};

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
                    Intent myIntent = new Intent(this, PartToeicActivity.class);
                    startActivity(myIntent);
                } else if (position == 3) {
                    Intent myIntent = new Intent(this, HuongDanActivity.class);
                    startActivity(myIntent);
                 }
    }
}