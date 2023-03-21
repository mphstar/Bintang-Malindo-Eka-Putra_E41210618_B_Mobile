package com.example.minggu6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SqliteActivity extends AppCompatActivity {

    FloatingActionButton btnadd;
    ArrayList<DataDiri> listData;
    ListView listview;
    SQLHelper dbhelper;
    TextView txt_nodata;
    void insertData(SQLHelper helper, String nama, String alamat, String hobi) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseDataDiri.DataDiri.COLUMN_NAME_NAMA, nama);
        values.put(DatabaseDataDiri.DataDiri.COLUMN_NAME_ALAMAT, alamat);
        values.put(DatabaseDataDiri.DataDiri.COLUMN_NAME_HOBI, hobi);

        db.insert(DatabaseDataDiri.DataDiri.TABLE_NAME, null, values);
        db.close();
    }

    void deleteData(SQLHelper helper, String idnya) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(DatabaseDataDiri.DataDiri.TABLE_NAME, DatabaseDataDiri.DataDiri._ID + " = ?", new String[]{idnya});
        db.close();
    }

    void updateData(SQLHelper helper, DataDiri data) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseDataDiri.DataDiri.COLUMN_NAME_NAMA, data.getNama());
        values.put(DatabaseDataDiri.DataDiri.COLUMN_NAME_ALAMAT, data.getAlamat());
        values.put(DatabaseDataDiri.DataDiri.COLUMN_NAME_HOBI, data.getHobi());

        db.update(DatabaseDataDiri.DataDiri.TABLE_NAME, values, DatabaseDataDiri.DataDiri._ID + " = ?", new String[]{String.valueOf(data.getIdnya())});
        db.close();
    }

    void readData(SQLHelper helper) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {
                DatabaseDataDiri.DataDiri._ID,
                DatabaseDataDiri.DataDiri.COLUMN_NAME_NAMA,
                DatabaseDataDiri.DataDiri.COLUMN_NAME_ALAMAT,
                DatabaseDataDiri.DataDiri.COLUMN_NAME_HOBI
        };
        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseDataDiri.DataDiri.COLUMN_NAME_NAMA + " = ?";
        String[] selectionArgs = {""};

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

        ListDataAdapter adapt = new ListDataAdapter(SqliteActivity.this, listData);
        listview.setAdapter(adapt);
        db.close();

        if(listData.size() == 0){
            listview.setVisibility(View.GONE);
            txt_nodata.setVisibility(View.VISIBLE);
        } else {
            listview.setVisibility(View.VISIBLE);
            txt_nodata.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("SQLite");
        bar.setDisplayHomeAsUpEnabled(true);
        listview = findViewById(R.id.listview_items);
        dbhelper = new SQLHelper(this);
        txt_nodata = findViewById(R.id.txt_nodata);

        readData(dbhelper);

        btnadd = findViewById(R.id.floating_adddata);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SqliteActivity.this, R.style.DialogStyle);
                bottomSheetDialog.setContentView(R.layout.field_data_diri);
                MaterialButton btndel = bottomSheetDialog.findViewById(R.id.hapus_data);
                btndel.setVisibility(View.GONE);
                setField(bottomSheetDialog, false, 0);

                bottomSheetDialog.show();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SqliteActivity.this, R.style.DialogStyle);
                bottomSheetDialog.setContentView(R.layout.field_data_diri);
                setField(bottomSheetDialog, true, i);

                bottomSheetDialog.show();
                Toast.makeText(SqliteActivity.this, String.valueOf(listData.get(i).getIdnya()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setField(BottomSheetDialog sheetDialog, boolean isEdit, int i) {
        TextView nama = sheetDialog.findViewById(R.id.txt_nama);
        TextView alamat = sheetDialog.findViewById(R.id.txt_alamat);
        TextView hobi = sheetDialog.findViewById(R.id.txt_hobi);
        MaterialButton btnsub = sheetDialog.findViewById(R.id.submit_data);
        MaterialButton btndel = sheetDialog.findViewById(R.id.hapus_data);

        sheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogInterface.cancel();
            }
        });

        if (isEdit) {
            nama.setText(listData.get(i).getNama());
            alamat.setText(listData.get(i).getAlamat());
            hobi.setText(listData.get(i).getHobi());
            btnsub.setText("Update");
        }

        btndel.setOnClickListener(v -> {
            deleteData(dbhelper, String.valueOf(listData.get(i).getIdnya()));
            readData(dbhelper);
            sheetDialog.dismiss();
        });

        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit) {
                    if (nama.getText().toString().equals("") || alamat.getText().toString().equals("") || hobi.getText().toString().equals("")) {
                        Toast.makeText(SqliteActivity.this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        listData.get(i).setNama(nama.getText().toString());
                        listData.get(i).setAlamat(alamat.getText().toString());
                        listData.get(i).setHobi(hobi.getText().toString());
                        updateData(dbhelper, listData.get(i));
                        readData(dbhelper);
                        sheetDialog.dismiss();
                    }

                } else {
                    if (nama.getText().toString().equals("") || alamat.getText().toString().equals("") || hobi.getText().toString().equals("")) {
                        Toast.makeText(SqliteActivity.this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        insertData(dbhelper, nama.getText().toString(), alamat.getText().toString(), hobi.getText().toString());
                        readData(dbhelper);
                        sheetDialog.dismiss();
                    }
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        dbhelper.close();
        super.onDestroy();
    }
}