package tdt.didong.nhanhuynh.toeictest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by NhanHuynh on 5/3/2016.
 */
public class CustomIconLabelAdapter extends ArrayAdapter<String> {

    Context mContext;
    Integer[] mThumbails;
    String[] mItems;

    public CustomIconLabelAdapter(Context context, int resource, String[] items, Integer[] thumbails) {
        super(context, resource, items);
        this.mContext = context;
        this.mThumbails = thumbails;
        this.mItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity) mContext).getLayoutInflater();
        View row = layoutInflater.inflate(R.layout.custom_row_icon_label, null);

        TextView label = (TextView) row.findViewById(R.id.label);
        ImageView icon = (ImageView) row.findViewById(R.id.icon);

        label.setText(mItems[position]);
        icon.setImageResource(mThumbails[position]);

        return (row);
    }
}
