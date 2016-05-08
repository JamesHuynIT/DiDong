package tdt.didong.nhanhuynh.toeictest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.List;

import tdt.didong.nhanhuynh.database.DatabaseNguPhap;

public class ThongTinNPActivity extends AppCompatActivity {

    TextView textView;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_np);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.thongTin);

        Intent callerIntent = getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra("MyPackage");
        int position = packageFromCaller.getInt("position");

        DatabaseNguPhap databaseNguPhap = DatabaseNguPhap.getInstance(this);
        databaseNguPhap.open();

        List<String> biQuyet = databaseNguPhap.getNoiDungNguPhap();
        databaseNguPhap.close();

        textView.setText(biQuyet.get(position));
    }
}
