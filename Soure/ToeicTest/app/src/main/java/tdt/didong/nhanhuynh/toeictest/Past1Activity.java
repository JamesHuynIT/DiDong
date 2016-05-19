package tdt.didong.nhanhuynh.toeictest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tdt.didong.nhanhuynh.database.DatabaseListening;

/**
 * Created by HIEUHUYNH on 17/05/2016.
 */
public class Past1Activity extends Activity{
    List<String> listDADung;
    List<String> listDADanh;
    List<Bitmap> listHinhAnh;
    Button btnPlay, btnNopBai;
    ImageView imageView;
    ImageButton imgNext, imgPrev;
    TextView textView,dapanDung;
    RadioButton rad1, rad2, rad3, rad4;
    boolean preview = false;
    int i = 1;
    int k = 1;
    int prei = 1;
    int prek = 1;
    int socau;
    boolean isStart = false;
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
        setContentView(R.layout.activity_part1);

        listDADanh = new ArrayList<>();
        for(int i = 0 ; i < 10;i++){
            listDADanh.add("E");
        }

        btnNopBai = (Button) findViewById(R.id.nopBai);

        imgNext = (ImageButton) findViewById(R.id.ImgBtnNext);
        imgPrev = (ImageButton) findViewById(R.id.ImgBtnPre);
        dapanDung = (TextView) findViewById(R.id.DapAnDung);

        rad1 = (RadioButton) findViewById(R.id.radA);
        rad2 = (RadioButton) findViewById(R.id.radB);
        rad3 = (RadioButton) findViewById(R.id.radC);
        rad4 = (RadioButton) findViewById(R.id.radD);

        imageView = (ImageView) findViewById(R.id.hinhAnh);
        textView = (TextView) findViewById(R.id.txtSoCau);

        btnPlay = (Button) findViewById(R.id.btnPlay);

        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setClickable(false);

        DatabaseListening databaseCauHoi = DatabaseListening.getInstance(Past1Activity.this);
        databaseCauHoi.open();

        final List<byte[]> listAT = databaseCauHoi.getAmThanhListen();
        imgPrev.setVisibility(View.INVISIBLE);
        LoadCH(i, k);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(isStart == false) {
                seekbar.setProgress((int) startTime);
                //myHandler.postDelayed(UpdateSongTime, 100);
                playMp3(listAT.get(i - 1));
                isStart = true;
            }else{
                mediaPlayer.stop();
                isStart = false;
            }
            }
        });

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preview) {
                    imgPrev.setVisibility(View.VISIBLE);
                    if (i < socau) {
                        if (rad1.isChecked()) {
                            listDADanh.set((i-1),"A");
                        } else if (rad2.isChecked()) {
                            listDADanh.set((i-1),"B");
                        } else if (rad3.isChecked()) {
                            listDADanh.set((i-1),"C");
                        } else if (rad4.isChecked()) {
                            listDADanh.set((i-1),"D");
                        }
                        i++;
                        k += 4;
                        LoadCH(i, k);
                        rad1.setChecked(false);
                        rad2.setChecked(false);
                        rad3.setChecked(false);
                        rad4.setChecked(false);
                        if(10 >= (i+1)){
                            loadDASau(i);
                        }
                        imgNext.setVisibility(View.VISIBLE);
                        imgPrev.setVisibility(View.VISIBLE);
                    } else {
                        imgNext.setVisibility(View.INVISIBLE);
                    }
                }else{
                    if (i < socau) {
                        prei++;
                        prek += 4;
                        previewTest(prei, prek);
                    }
                }
            }
        });

        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(!preview) {
                  if (i > 1) {
                      if (rad1.isChecked()) {
                          listDADanh.set((i-1),"A");
                      } else if (rad2.isChecked()) {
                          listDADanh.set((i-1),"B");
                      } else if (rad3.isChecked()) {
                          listDADanh.set((i-1),"C");
                      } else if (rad4.isChecked()) {
                          listDADanh.set((i-1),"D");
                      }
                      i--;
                      k -= 4;
                      LoadCH(i, k);
                      loadDATruoc(i);
                      imgPrev.setVisibility(View.VISIBLE);
                      imgNext.setVisibility(View.VISIBLE);

                  } else {
                      imgPrev.setVisibility(View.INVISIBLE);
                  }
              }else{
                  if (i > 1) {
                      prei--;
                      prek -= 4;
                      previewTest(prei, prek);
                  }
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

    private void LoadCH(int stt, int sttDA) {

        DatabaseListening databaseCauHoi = DatabaseListening.getInstance(this);
        databaseCauHoi.open();
        List<String> listDA = databaseCauHoi.getDapAnListening();
        socau = 10;

        listHinhAnh = databaseCauHoi.getImagepart1();
        imageView.setImageBitmap(listHinhAnh.get(stt-1));
        databaseCauHoi.close();
        textView.setText("Câu " + stt + "/" + socau);

        if (listDA.get(sttDA - 1) == " " && listDA.get(sttDA) == " " && listDA.get(sttDA + 1) == " "
                && listDA.get(sttDA + 2) == " ") {
            rad1.setText("A");
            rad2.setText("B");
            rad3.setText("C");
            rad4.setText("D");
        } else {
            rad1.setText("A." + listDA.get(sttDA - 1));
            rad2.setText("B." + listDA.get(sttDA));
            rad3.setText("C." + listDA.get(sttDA + 1));
            rad4.setText("D." + listDA.get(sttDA + 2));
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


            mediaPlayer = new MediaPlayer();


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

        listDADung = databaseDapAn.getDapAnDungListening();
        Log.e("fg",listDADung.size()+"");
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
                preview = true;
                previewTest(prei,prek);
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
    private void previewTest(int cauhoi, int dapan){
        btnNopBai.setEnabled(false);
        dapanDung.setText("Đáp án đúng: " + listDADung.get(cauhoi - 1));
        LoadCH(cauhoi, dapan);
        loadDATruoc(cauhoi);
    }
    private void loadDATruoc(int cau) {
        String dapAn = listDADanh.get((cau - 1));
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
        String dapAn = listDADanh.get((cau - 1));
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