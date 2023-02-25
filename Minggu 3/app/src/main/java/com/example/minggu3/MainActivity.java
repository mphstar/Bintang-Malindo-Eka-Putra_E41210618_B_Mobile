package com.example.minggu3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  onItemClickReyccleView{

    RecyclerView rey;
    ArrayList<MahasiswaModel> listMahasiswa;
    MahasiswaAdapter adapter;
    FloatingActionButton btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_add = findViewById(R.id.btn_add);
        addData();
        rey = findViewById(R.id.reycleview_list);
        adapter = new MahasiswaAdapter(listMahasiswa, this);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(MainActivity.this);
        rey.setLayoutManager(layout);
        rey.setAdapter(adapter);


        btn_add.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Add Mahasiswa");
            View view = getLayoutInflater().inflate(R.layout.dialog_mahasiswa, null);
            TextInputEditText nama = view.findViewById(R.id.txt_nama);
            TextInputEditText nim = view.findViewById(R.id.txt_nim);
            TextInputEditText nohp = view.findViewById(R.id.txt_nohp);
            builder.setView(view);
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.cancel();
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(nama.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this, "Nama wajib diisi", Toast.LENGTH_SHORT).show();
                    } else if (nim.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this, "NIM wajib diisi", Toast.LENGTH_SHORT).show();
                    } else if(nohp.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this, "No HP wajib diisi", Toast.LENGTH_SHORT).show();
                    } else {
                        listMahasiswa.add(new MahasiswaModel(nama.getText().toString(), nim.getText().toString(), nohp.getText().toString()));
//                        dialogInterface.dismiss();
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        });

    }

    ArrayList<MahasiswaModel> searchMahasiswa;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchv = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.equals("")){
                    searchMahasiswa.clear();
                    adapter = new MahasiswaAdapter(listMahasiswa, MainActivity.this);
                    rey.setAdapter(adapter);
                } else {
                    searchMahasiswa.clear();
                    for (int i = 0; i< listMahasiswa.size(); i++ ){
                        if(listMahasiswa.get(i).getNaam().toLowerCase().contains(s.toLowerCase())){
                            searchMahasiswa.add(listMahasiswa.get(i));
                        }
                    }
                    adapter = new MahasiswaAdapter(searchMahasiswa, MainActivity.this);
                    rey.setAdapter(adapter);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    private void addData(){
        searchMahasiswa = new ArrayList<MahasiswaModel>();
        listMahasiswa = new ArrayList<MahasiswaModel>();
        listMahasiswa.add(new MahasiswaModel("Bintang Malindo Eka Putra", "E41210618", "0895393933040"));
        listMahasiswa.add(new MahasiswaModel("Risqi Agung", "E41210829", "082121234121"));
        listMahasiswa.add(new MahasiswaModel("Fathur", "E41210628", "081291821271"));
        listMahasiswa.add(new MahasiswaModel("Athiyah", "E41210674", "082172612112"));
        listMahasiswa.add(new MahasiswaModel("M Rizal", "E41210011", "082117212127"));

    }

    String[] opsi = {"Ubah Data", "Hapus Data"};

    @Override
    public void onClick(View view, int position) {
        AlertDialog.Builder bui = new AlertDialog.Builder(MainActivity.this);
        if(searchMahasiswa.size() != 0){
            for (int x = 0; x < listMahasiswa.size(); x++){
                if(searchMahasiswa.get(position).getNaam().equals(listMahasiswa.get(x).getNaam())){
                    bui.setTitle(searchMahasiswa.get(position).getNaam().toString());
                }
            }
        } else {
            bui.setTitle(listMahasiswa.get(position).getNaam().toString());
        }
        bui.setItems(opsi, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    // ubah data
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Ubah Mahasiswa");
                    View view = getLayoutInflater().inflate(R.layout.dialog_mahasiswa, null);
                    TextInputEditText nama = view.findViewById(R.id.txt_nama);
                    TextInputEditText nim = view.findViewById(R.id.txt_nim);
                    TextInputEditText nohp = view.findViewById(R.id.txt_nohp);
                    if(searchMahasiswa.size() != 0){
                        for (int x = 0; x < listMahasiswa.size(); x++){
                            if(searchMahasiswa.get(position).getNaam().equals(listMahasiswa.get(x).getNaam())){
                                nama.setText(searchMahasiswa.get(position).getNaam().toString());
                                nim.setText(searchMahasiswa.get(position).getNim().toString());
                                nohp.setText(listMahasiswa.get(position).getNohp().toString());
                            }
                        }
                    } else {
                        nama.setText(listMahasiswa.get(position).getNaam().toString());
                        nim.setText(listMahasiswa.get(position).getNim().toString());
                        nohp.setText(listMahasiswa.get(position).getNohp().toString());
                    }

                    builder.setView(view);
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.cancel();
                        }
                    });
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(nama.getText().toString().equals("")){
                                Toast.makeText(MainActivity.this, "Nama wajib diisi", Toast.LENGTH_SHORT).show();
                            } else if (nim.getText().toString().equals("")){
                                Toast.makeText(MainActivity.this, "NIM wajib diisi", Toast.LENGTH_SHORT).show();
                            } else if(nohp.getText().toString().equals("")){
                                Toast.makeText(MainActivity.this, "No HP wajib diisi", Toast.LENGTH_SHORT).show();
                            } else {
                                if(searchMahasiswa.size() != 0){
                                    for (int j = 0; j < listMahasiswa.size(); j++){
                                        if(searchMahasiswa.get(position).getNaam().equals(listMahasiswa.get(j).getNaam())){
                                            listMahasiswa.get(j).setNaam(nama.getText().toString());
                                            listMahasiswa.get(j).setNim(nim.getText().toString());
                                            listMahasiswa.get(j).setNohp(nohp.getText().toString());
                                        }
                                    }

                                } else {
                                    listMahasiswa.get(position).setNaam(nama.getText().toString());
                                    listMahasiswa.get(position).setNim(nim.getText().toString());
                                    listMahasiswa.get(position).setNohp(nohp.getText().toString());
                                }
                                adapter.notifyItemChanged(position);
//                        dialogInterface.dismiss();
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    // hapus data
                    if(searchMahasiswa.size() != 0){
                        for (int k = 0; k < listMahasiswa.size(); k++){
                            if(searchMahasiswa.get(position).getNaam().equals(listMahasiswa.get(k).getNaam())){
                                listMahasiswa.remove(k);
                            }
                        }
                        searchMahasiswa.remove(position);
                    } else {
                        listMahasiswa.remove(position);
                    }
                    adapter.notifyItemRemoved(position);
                }
            }
        });
        AlertDialog dial = bui.create();
        dial.show();
//        Toast.makeText(this, listMahasiswa.get(position).getNaam().toString(), Toast.LENGTH_SHORT).show();
    }
}