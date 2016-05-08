package tdt.didong.nhanhuynh.toeictest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tdt.didong.nhanhuynh.database.DatabaseReading;

public class LuyenThiReadingActivity extends AppCompatActivity {

    List<String> listDADanh;
    List<String> listDADung;
    boolean preview = false;
    ImageButton imgNext, imgPrev;
    Button btnNopBai;
    TextView textView, cauHoi, dapanDung;
    RadioButton rad1, rad2, rad3, rad4;
    RadioGroup radioGroup;
    int socau = 0;
    int i = 1;
    int k = 1;
    int prei = 1;
    int prek = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luyen_thi_reading);

        listDADanh = new ArrayList<>();
        listDADung = new ArrayList<>();

        imgNext = (ImageButton) findViewById(R.id.ImgBtnNext);
        imgPrev = (ImageButton) findViewById(R.id.ImgBtnPre);

        cauHoi = (TextView) findViewById(R.id.txtCauHoi);
        textView = (TextView) findViewById(R.id.txtSoCau);
        dapanDung = (TextView) findViewById(R.id.DapAnDung);

        rad1 = (RadioButton) findViewById(R.id.radDAA);
        rad2 = (RadioButton) findViewById(R.id.radDAB);
        rad3 = (RadioButton) findViewById(R.id.radDAC);
        rad4 = (RadioButton) findViewById(R.id.radDAD);

        btnNopBai = (Button) findViewById(R.id.nopBai);
        radioGroup = (RadioGroup) findViewById(R.id.groudDA);

        LoadCH(i, k);

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preview) {
                    imgPrev.setEnabled(true);
                    if (i < socau) {
                        if (rad1.isChecked()) {
                            listDADanh.add("A");
                        } else if (rad2.isChecked()) {
                            listDADanh.add("B");
                        } else if (rad3.isChecked()) {
                            listDADanh.add("C");
                        } else if (rad4.isChecked()) {
                            listDADanh.add("D");
                        }
                        i++;
                        k += 4;
                        LoadCH(i, k);
                        radioGroup.clearCheck();
                    } else {
                        imgNext.setEnabled(false);
                    }
                } else {
                    prei++;
                    prek += 4;
                    previewTest(prei, prek);
                }
            }
        });

        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preview) {
                    if (i > 1) {
                        i--;
                        k -= 4;
                        LoadCH(i, k);
                        loadDATruoc(i);
                    } else {
                        imgPrev.setEnabled(false);
                    }
                } else {

                }
            }
        });

        btnNopBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemTraDapAn();
            }
        });
    }

    private void LoadCH(int stt, int thutuDA) {

        DatabaseReading databaseDapAn = DatabaseReading.getInstance(this);
        databaseDapAn.open();

        List<String> listDA = databaseDapAn.getDapAnReading();
        List<String> listCH = databaseDapAn.getNoiDungCauHoiReading();
        socau = databaseDapAn.getCountCHReading() - 1;
        databaseDapAn.close();

        textView.setText("Câu " + stt + "/" + socau);
        cauHoi.setText(listCH.get(stt - 1));

        rad1.setText(listDA.get(thutuDA - 1));
        rad2.setText(listDA.get(thutuDA));
        rad3.setText(listDA.get(thutuDA + 1));
        rad4.setText(listDA.get(thutuDA + 2));
    }

    private void kiemTraDapAn() {
        DatabaseReading databaseDapAn = DatabaseReading.getInstance(this);
        databaseDapAn.open();

        listDADung = databaseDapAn.getDapAnDungReading();
        databaseDapAn.close();
        String temp1 = "";
        String temp2 = "";
        int cauDung = 0;

        for (int i = 0; i < listDADanh.size(); i++) {
            temp1 = listDADanh.get(i);
            temp2 = listDADung.get(i);
            if (temp1.equalsIgnoreCase(temp2)) {
                cauDung++;
            }
        }

        String mess = "Số câu đúng: " + cauDung + "/" + socau;
        ThongBao(mess);
    }

    private void ThongBao(String mess) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(mess);

        alertDialogBuilder.setPositiveButton(getString(R.string.review), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                preview = true;
                previewTest(prei, prek);
            }
        });

        alertDialogBuilder.setNegativeButton(getString(R.string.exit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void loadDATruoc(int cau) {
        String dapAn = listDADanh.get(cau - 1);
        switch (dapAn) {
            case "A":
                rad1.setChecked(true);
                break;
            case "B":
                rad2.setChecked(true);
                break;
            case "C":
                rad3.setChecked(true);
                break;
            case "D":
                rad4.setChecked(true);
                break;
            default:
                break;
        }
    }

    private void loadDASau(int cau) {
        String dapAn = listDADanh.get(listDADanh.size() - 1);
        switch (dapAn) {
            case "A":
                rad1.setChecked(true);
                break;
            case "B":
                rad2.setChecked(true);
                break;
            case "C":
                rad3.setChecked(true);
                break;
            case "D":
                rad4.setChecked(true);
                break;
            default:
                break;
        }
    }

    private void previewTest(int cauhoi, int dapan){
        btnNopBai.setEnabled(false);
        dapanDung.setText("Đáp án đúng: " + listDADung.get(cauhoi - 1));
        LoadCH(cauhoi, dapan);
        loadDATruoc(cauhoi);
    }

    @Override
    protected void onStop() {
        super.onStop();
        listDADanh.clear();
        listDADung.clear();
    }
}