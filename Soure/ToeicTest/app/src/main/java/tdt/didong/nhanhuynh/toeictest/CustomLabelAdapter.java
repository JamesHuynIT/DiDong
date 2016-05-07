package tdt.didong.nhanhuynh.toeictest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by NhanHuynh on 5/3/2016.
 */
public class CustomLabelAdapter extends ArrayAdapter<String> {

    Context mContext;
    List<String> mItems;

    public CustomLabelAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity) mContext).getLayoutInflater();
        View row = layoutInflater.inflate(R.layout.custom_row_label, null);

        TextView label = (TextView) row.findViewById(R.id.label);

        label.setText(mItems.get(position));

        return (row);
    }
}
