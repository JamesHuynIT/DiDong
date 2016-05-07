package tdt.didong.nhanhuynh.toeictest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import tdt.didong.nhanhuynh.database.DatabaseBiQuyet;

public class ThongTinBQActivity extends AppCompatActivity {

    TextView textView;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_bq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        textView = (TextView) findViewById(R.id.thongTin);
        Intent callerIntent = getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra("MyPackage");
        int position = packageFromCaller.getInt("position");

        DatabaseBiQuyet databaseBiQuyet = DatabaseBiQuyet.getInstance(this);
        databaseBiQuyet.open();

        List<String> biQuyet = databaseBiQuyet.getNoiDungBiQuyet();
        databaseBiQuyet.close();

        textView.setText(biQuyet.get(position));
    }
}
