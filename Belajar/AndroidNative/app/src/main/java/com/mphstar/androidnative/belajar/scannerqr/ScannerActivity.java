package com.mphstar.androidnative.belajar.scannerqr;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.mphstar.androidnative.R;

import java.util.ArrayList;

public class ScannerActivity extends AppCompatActivity {

    Button btnscan;
    TextView txt_result;
    TextView tab1;
    TextView tab2;
    TextView bgtab;
    RelativeLayout konten1;
    RelativeLayout konten2;
    EditText fieldinput;
    Button btn_generate;
    ImageView resultImage;
    String selectedCode;

    String[] listCode = {"Barcode", "QRCode"};

    private void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public static void slideTab(View view,
                                 int currenWidth,
                                 int newWidth) {

        ValueAnimator slideAnimator = ValueAnimator
                .ofInt(currenWidth, newWidth)
                .setDuration(500);

        /* We use an update listener which listens to each tick
         * and manually updates the height of the view  */

        slideAnimator.addUpdateListener(animation1 -> {
            Integer value = (Integer) animation1.getAnimatedValue();
            view.getLayoutParams().width = value.intValue();
            view.requestLayout();
        });

        /*  We use an animationSet to play the animation  */

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animationSet.play(slideAnimator);
        animationSet.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        changeStatusBarColor("#ffffffff");
        btnscan = findViewById(R.id.btn_scan);
        txt_result = findViewById(R.id.txt_result);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        bgtab = findViewById(R.id.bgtab);
        konten1 = findViewById(R.id.conten_tab1);
        konten2 = findViewById(R.id.conten_tab2);
        fieldinput = findViewById(R.id.field_inputtext);
        btn_generate = findViewById(R.id.btn_generate);
        resultImage = findViewById(R.id.result_code);

        tab1.setOnClickListener(v -> {
            konten1.setVisibility(View.VISIBLE);
            konten2.setVisibility(View.GONE);
            bgtab.animate().translationX(0).setDuration(300).start();
            slideTab(bgtab, bgtab.getWidth(), getResources().getDimensionPixelSize(R.dimen.width_tab1));
            tab2.setTextColor(Color.parseColor("#000000"));
            tab1.setTextColor(Color.parseColor("#ffffff"));
        });

        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                konten2.setVisibility(View.VISIBLE);
                konten1.setVisibility(View.GONE);
                bgtab.animate().translationX(250).setDuration(300).start();
                slideTab(bgtab, bgtab.getWidth(), getResources().getDimensionPixelSize(R.dimen.width_tab2));
                tab1.setTextColor(Color.parseColor("#000000"));
                tab2.setTextColor(Color.parseColor("#ffffff"));
            }
        });

        btnscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(ScannerActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(ScannerActivity.this, new String[]{Manifest.permission.CAMERA}, 0);

                } else {
                    showCam();
                }
            }
        });

        btn_generate.setOnClickListener(v -> {
            if(fieldinput.getText().toString().equals("")){
                Toast.makeText(ScannerActivity.this, "Masukkan text terlebih dahulu", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder build = new AlertDialog.Builder(ScannerActivity.this);
                build.setTitle("Pilih Format Code");
                build.setItems(listCode, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedCode = listCode[i].toString();
                        dialogInterface.dismiss();
                        if(selectedCode.equals("Barcode")){
                            try {
                                BarcodeEncoder encode = new BarcodeEncoder();
                                Bitmap bitmap = encode.encodeBitmap(fieldinput.getText().toString(), BarcodeFormat.CODE_128, 400, 200);
                                resultImage.setImageBitmap(bitmap);
                            } catch (Exception e){
                                Toast.makeText(ScannerActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            try {
                                BarcodeEncoder encode = new BarcodeEncoder();
                                Bitmap bitmap = encode.encodeBitmap(fieldinput.getText().toString(), BarcodeFormat.QR_CODE, 400, 400);
                                resultImage.setImageBitmap(bitmap);
                            } catch (Exception e){
                                Toast.makeText(ScannerActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                build.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = build.create();
                dialog.show();
            }
        });
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null ){
            txt_result.setText(result.getContents());
        }
    });

    private void showCam(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        barLauncher.launch(options);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showCam();
            } else {
                Toast.makeText(ScannerActivity.this, "Gagal membuka camera", Toast.LENGTH_LONG).show();
            }
        }
    }
}

