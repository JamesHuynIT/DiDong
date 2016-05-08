package tdt.didong.nhanhuynh.toeictest;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import tdt.didong.nhanhuynh.database.DatabaseNguPhap;

public class NguPhapActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngu_phap);

        DatabaseNguPhap databaseNguPhap = DatabaseNguPhap.getInstance(this);
        databaseNguPhap.open();
        List<String> nguPhap = databaseNguPhap.getTenNguPhap();

        databaseNguPhap.close();

        CustomLabelAdapter adapter = new CustomLabelAdapter(this, R.layout.custom_row_label, nguPhap);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent myIntent = new Intent(this, ThongTinNPActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        myIntent.putExtra("MyPackage", bundle);
        startActivity(myIntent);
    }
}
