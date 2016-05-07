package tdt.didong.nhanhuynh.toeictest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import tdt.didong.nhanhuynh.database.DatabaseListening;

public class LuyenThiListeningActivity extends AppCompatActivity {

    ImageView imageView;
    ImageButton imgNext, imgPrev;
    TextView textView;
    RadioButton rad1, rad2, rad3, rad4;
    int i = 1;
    int k = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luyen_thi_listening);

        imgNext = (ImageButton) findViewById(R.id.ImgBtnNext);
        imgPrev = (ImageButton) findViewById(R.id.ImgBtnPre);
        rad1 = (RadioButton) findViewById(R.id.radA);
        rad2 = (RadioButton) findViewById(R.id.radB);
        rad3 = (RadioButton) findViewById(R.id.radC);
        rad4 = (RadioButton) findViewById(R.id.radD);
        imageView = (ImageView) findViewById(R.id.hinhAnh);
        textView = (TextView) findViewById(R.id.tongCau);

        LoadCH();

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPrev.setEnabled(true);
                i++;
                k += 4;
                LoadCH();
            }
        });

        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 1) {
                    i--;
                    k -= 4;
                    LoadCH();
                } else {
                    imgPrev.setEnabled(false);
                }
            }
        });
    }

    private void LoadCH() {

        DatabaseListening databaseCauHoi = DatabaseListening.getInstance(this);
        databaseCauHoi.open();

        Bitmap hinhanh = databaseCauHoi.getImage();
        List<String> listDA = databaseCauHoi.getDapAnListening();
        int socau = databaseCauHoi.getCountCHListen();
        databaseCauHoi.close();

        imageView.setImageBitmap(hinhanh);

        textView.setText("Câu " + i + "/" + socau);

        rad1.setText(listDA.get(k - 1));
        rad2.setText(listDA.get(k));
        rad3.setText(listDA.get(k + 1));
        rad4.setText(listDA.get(k + 2));
    }
}