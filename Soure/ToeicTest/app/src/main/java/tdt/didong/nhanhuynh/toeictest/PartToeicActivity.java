package tdt.didong.nhanhuynh.toeictest;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * Created by HIEUHUYNH on 17/05/2016.
 */
public class PartToeicActivity extends ListActivity {
    private Intent myIntent;
    String[] mItems = {"Photo", "Question - response",
            "Short Conversations", "Short talk", "Incomplete Sentences" ,"Text Completetion" ,"Reading Comprehension"};
    Integer[] mThumbnails = {R.drawable.part1,R.drawable.part3, R.drawable.part2, R.drawable.ic_help,R.drawable.ic_lambaithi,R.drawable.ic_help,
            R.drawable.ic_help};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7part_toeic);
        CustomIconLabelAdapter adapter = new CustomIconLabelAdapter(this, R.layout.custom_row_icon_label, mItems, mThumbnails);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position){
            case 0:
                myIntent = new Intent(this, Past1Activity.class);
                startActivity(myIntent);
                break;
            case 1:
                myIntent = new Intent(this, Past2Activity.class);
                startActivity(myIntent);
                break;
            case 2:
                myIntent = new Intent(this, Past3Activity.class);
                startActivity(myIntent);
            break;
            case 3:

                break;
            case 4:
                myIntent = new Intent(this, Past5Activity.class);
                startActivity(myIntent);
                break;
            case 5:

                break;
            case 6:
                break;
            case 7:
                break;
        }








    }
}
