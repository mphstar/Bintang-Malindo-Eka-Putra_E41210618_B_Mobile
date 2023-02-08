package com.mphstar.androidnative.belajar.sqllite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mphstar.androidnative.ListItems;
import com.mphstar.androidnative.R;
import com.mphstar.androidnative.belajar.MoviesActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainSqlLiteActivity extends AppCompatActivity {

    FloatingActionButton btnadd;
    ArrayList<DataDiri> listData;
    ListView listview;

    void insertData(SQLHelper helper, String nama, String alamat, String hobi){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseDataDiri.DataDiri.COLUMN_NAME_NAMA, nama);
        values.put(DatabaseDataDiri.DataDiri.COLUMN_NAME_ALAMAT, alamat);
        values.put(DatabaseDataDiri.DataDiri.COLUMN_NAME_HOBI, hobi);

        db.insert(DatabaseDataDiri.DataDiri.TABLE_NAME, null, values);
    }

    void readData(SQLHelper helper){
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {
                DatabaseDataDiri.DataDiri._ID,
                DatabaseDataDiri.DataDiri.COLUMN_NAME_NAMA,
                DatabaseDataDiri.DataDiri.COLUMN_NAME_ALAMAT,
                DatabaseDataDiri.DataDiri.COLUMN_NAME_HOBI
        };
        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseDataDiri.DataDiri.COLUMN_NAME_NAMA + " = ?";
        String[] selectionArgs = { "" };

        String sortOrder =
                DatabaseDataDiri.DataDiri.COLUMN_NAME_HOBI + " DESC";

        String selectQuery = "SELECT  * FROM " + DatabaseDataDiri.DataDiri.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        listData = new ArrayList<DataDiri>();
        if (cursor.moveToFirst()) {
            do {
                listData.add(new DataDiri(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3)));

            } while (cursor.moveToNext());
        }

        cursor.close();

        listDataAdapter adapt = new listDataAdapter(MainSqlLiteActivity.this, listData);
        listview.setAdapter(adapt);

    }

    SQLHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sql_lite);
        listview = findViewById(R.id.listview_items);
        dbhelper = new SQLHelper(this);
        readData(dbhelper);

        btnadd = findViewById(R.id.floating_adddata);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainSqlLiteActivity.this, R.style.DialogStyle);
                bottomSheetDialog.setContentView(R.layout.field_data_diri);
                setField(bottomSheetDialog);

                bottomSheetDialog.show();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainSqlLiteActivity.this, listData.get(i).getNama(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setField(BottomSheetDialog sheetDialog){
        TextView nama = sheetDialog.findViewById(R.id.txt_nama);
        TextView alamat = sheetDialog.findViewById(R.id.txt_alamat);
        TextView hobi = sheetDialog.findViewById(R.id.txt_hobi);
        MaterialButton btnsub = sheetDialog.findViewById(R.id.submit_data);

        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData(dbhelper, nama.getText().toString(), alamat.getText().toString(), hobi.getText().toString());
                readData(dbhelper);
                sheetDialog.dismiss();
            }
        });



    }

    @Override
    protected void onDestroy() {
        dbhelper.close();
        super.onDestroy();
    }
}

class DataDiri {
    int id;
    String nama, alamat, hobi;

    public DataDiri(int id, String nama, String alamat, String hobi) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.hobi = hobi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getHobi() {
        return hobi;
    }

    public void setHobi(String hobi) {
        this.hobi = hobi;
    }
}
