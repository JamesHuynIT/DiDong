package tdt.didong.nhanhuynh.toeictest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import tdt.didong.nhanhuynh.database.DatabaseReading;

public class LuyenThiReadingActivity extends AppCompatActivity {

    ImageButton imgNext, imgPrev;
    TextView textView, cauHoi;
    RadioButton rad1, rad2, rad3, rad4;
    int socau = 0;
    int i = 1;
    int k = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luyen_thi_reading);

        imgNext = (ImageButton) findViewById(R.id.ImgBtnNext);
        imgPrev = (ImageButton) findViewById(R.id.ImgBtnPre);
        cauHoi = (TextView) findViewById(R.id.txtCauHoi);
        textView = (TextView) findViewById(R.id.txtSoCau);

        rad1 = (RadioButton) findViewById(R.id.radDAA);
        rad2 = (RadioButton) findViewById(R.id.radDAB);
        rad3 = (RadioButton) findViewById(R.id.radDAC);
        rad4 = (RadioButton) findViewById(R.id.radDAD);

        LoadCH();

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPrev.setEnabled(true);
                if (i <= socau) {
                    i++;
                    k += 4;
                    LoadCH();
                    rad1.setChecked(false);
                    rad2.setChecked(false);
                    rad3.setChecked(false);
                    rad4.setChecked(false);
                }
                else{
                    imgNext.setEnabled(false);
                }
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

        DatabaseReading databaseDapAn = DatabaseReading.getInstance(this);
        databaseDapAn.open();

        List<String> listDA = databaseDapAn.getDapAnReading();
        List<String> listCH = databaseDapAn.getNoiDungCauHoiReading();
        socau = databaseDapAn.getCountCHReading();
        databaseDapAn.close();

        textView.setText("Câu " + i + "/" + socau);
        cauHoi.setText(listCH.get(i - 1));

        rad1.setText(listDA.get(k - 1));
        rad2.setText(listDA.get(k));
        rad3.setText(listDA.get(k + 1));
        rad4.setText(listDA.get(k + 2));
    }
}
