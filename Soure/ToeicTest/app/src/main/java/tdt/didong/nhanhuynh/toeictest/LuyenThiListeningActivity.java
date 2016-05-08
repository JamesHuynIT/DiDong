package tdt.didong.nhanhuynh.toeictest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tdt.didong.nhanhuynh.database.DatabaseListening;

public class LuyenThiListeningActivity extends AppCompatActivity {

    List<String> listDADanh;
    Button btnPlay, btnNopBai;
    ImageView imageView;
    ImageButton imgNext, imgPrev;
    TextView textView;
    RadioGroup radioGroup;
    RadioButton rad1, rad2, rad3, rad4;
    int i = 1;
    int k = 1;
    int socau;

    private MediaPlayer mediaPlayer;
    private double startTime = 0;
    private double finalTime = 0;
    private SeekBar seekbar;
    private Handler myHandler = new Handler();
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            seekbar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luyen_thi_listening);

        listDADanh = new ArrayList<>();

        btnNopBai = (Button) findViewById(R.id.nopBai);
        radioGroup = (RadioGroup) findViewById(R.id.groudDA);

        imgNext = (ImageButton) findViewById(R.id.ImgBtnNext);
        imgPrev = (ImageButton) findViewById(R.id.ImgBtnPre);
        rad1 = (RadioButton) findViewById(R.id.radA);
        rad2 = (RadioButton) findViewById(R.id.radB);
        rad3 = (RadioButton) findViewById(R.id.radC);
        rad4 = (RadioButton) findViewById(R.id.radD);

        imageView = (ImageView) findViewById(R.id.hinhAnh);
        textView = (TextView) findViewById(R.id.tongCau);

        btnPlay = (Button) findViewById(R.id.btnPlay);
        //mediaPlayer = MediaPlayer.create(this, R.raw.song);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setClickable(false);
        DatabaseListening databaseCauHoi = DatabaseListening.getInstance(LuyenThiListeningActivity.this);
        databaseCauHoi.open();
        final List<byte[]> listAT = databaseCauHoi.getAmThanhListen();

        LoadCH(i, k);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekbar.setProgress((int) startTime);
                myHandler.postDelayed(UpdateSongTime, 100);
                playMp3(listAT.get(0));
            }
        });

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    radioGroup.clearCheck();
                    //loadDASau(i);
                    i++;
                    k += 4;
                    LoadCH(i, k);
                } else {
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
                    LoadCH(i, k);
                } else {
                    imgPrev.setEnabled(false);
                }
            }
        });
    }

    private void LoadCH(int stt, int sttDA) {

        DatabaseListening databaseCauHoi = DatabaseListening.getInstance(this);
        databaseCauHoi.open();

        Bitmap hinhanh = databaseCauHoi.getImage();
        List<String> listDA = databaseCauHoi.getDapAnListening();
        socau = databaseCauHoi.getCountCHListen();
        databaseCauHoi.close();

        imageView.setImageBitmap(hinhanh);

        textView.setText("Câu " + stt + "/" + socau);

        if (listDA.get(sttDA - 1) == " " && listDA.get(sttDA) == " " && listDA.get(sttDA + 1) == " "
                && listDA.get(sttDA + 2) == " ") {
            rad1.setText("A");
            rad2.setText("B");
            rad3.setText("C");
            rad4.setText("D");
        } else {
            rad1.setText(listDA.get(sttDA - 1));
            rad2.setText(listDA.get(sttDA));
            rad3.setText(listDA.get(sttDA + 1));
            rad4.setText(listDA.get(sttDA + 2));
        }
    }

    private void playMp3(byte[] mp3SoundByteArray) {
        try {
            // create temp file that will hold byte array
            File tempMp3 = File.createTempFile("kurchina", "mp3", getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(mp3SoundByteArray);
            fos.close();

            // Tried reusing instance of media player
            // but that resulted in system crashes...
            MediaPlayer mediaPlayer = new MediaPlayer();

            // Tried passing path directly, but kept getting
            // "Prepare failed.: status=0x1"
            // so using file descriptor instead
            FileInputStream fis = new FileInputStream(tempMp3);
            mediaPlayer.setDataSource(fis.getFD());

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException ex) {
            String s = ex.toString();
            ex.printStackTrace();
        }
    }

    private void kiemTraDapAn() {
        DatabaseListening databaseDapAn = DatabaseListening.getInstance(this);
        databaseDapAn.open();

        List<String> listDADung = databaseDapAn.getDapAnDungListening();
        databaseDapAn.close();
        String temp1 = "";
        String temp2 = "";
        int cauDung = 0;

        for (int i = 0; i < listDADanh.size() - 1; i++) {
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
}