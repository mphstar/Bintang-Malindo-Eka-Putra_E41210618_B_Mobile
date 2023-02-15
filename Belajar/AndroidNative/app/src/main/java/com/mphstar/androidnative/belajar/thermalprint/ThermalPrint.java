package com.mphstar.androidnative.belajar.thermalprint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.connection.tcp.TcpConnection;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.mphstar.androidnative.R;

public class ThermalPrint extends AppCompatActivity {
    Button btn_print;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermal_print);
        btn_print = findViewById(R.id.btn_print);
        btn_print.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, 1);
            } else {

                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                EscPosPrinter printer = new EscPosPrinter(new TcpConnection("192.168.0.45", 9100), 200, 80f, 45                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                );
                                printer.printFormattedText(
                                        "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, getApplicationContext().getResources().getDrawableForDensity(R.drawable.ic_logo, 200)) + "</img>\n" +
                                                
                                                "[L]\n" +
                                                "[C]<u><font size='big'>ORDER Mphstarr</font></u>\n" +
                                                "[L]\n" +
                                                "[C]================================================\n" +
                                                "[L]\n" +
                                                "[L]<b>BEAUTIFUL SHIRT</b>[R]9.99e\n" +
                                                "[L]  + Size : S\n" +
                                                "[L]\n" +
                                                "[L]<b>AWESOME HAT</b>[R]24.99e\n" +
                                                "[L]  + Size : 57/58\n" +
                                                "[L]\n" +
                                                "[C]================================================\n" +
                                                "[R]TOTAL PRICE :[R]34.98e\n" +
                                                "[R]TAX :[R]4.23e\n" +
                                                "[L]\n" +
                                                "[C]================================================\n" +
                                                "[L]\n" +
                                                "[L]<font size='tall'>Customer :</font> [R]<b>Tes</b>\n" +
                                                "[L]Raymond DUPONT\n" +
                                                "[L]5 rue des girafes\n" +
                                                "[L]31547 PERPETES\n" +
                                                "[L]Tel : +33801201456\n" +
                                                "[L]\n" +
                                                "[C]<barcode type='ean13' height='10'>831254784551</barcode>\n" +
                                                "[L]<qrcode size='20'>http://www.developpeur-web.dantsu.com/</qrcode>\n"
                                );
                                Toast.makeText(ThermalPrint.this, "sukses", Toast.LENGTH_SHORT).show();


                            } catch (Exception e){
                            }
                        }
                    }).start();



            }
        });
    }
}