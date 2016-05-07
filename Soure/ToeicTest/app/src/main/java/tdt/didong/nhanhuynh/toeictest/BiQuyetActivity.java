package tdt.didong.nhanhuynh.toeictest;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import tdt.didong.nhanhuynh.database.DatabaseBiQuyet;

public class BiQuyetActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bi_quyet);

        DatabaseBiQuyet databaseBiQuyet = DatabaseBiQuyet.getInstance(this);
        databaseBiQuyet.open();

        List<String> biQuyet = databaseBiQuyet.getTenBiQuyet();
        databaseBiQuyet.close();

        CustomLabelAdapter adapter = new CustomLabelAdapter(this, R.layout.custom_row_label, biQuyet);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent myIntent = new Intent(this, ThongTinBQActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        myIntent.putExtra("MyPackage", bundle);
        startActivity(myIntent);
    }
}
