package com.example.mycashbook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    BarChart barChart;
    DatabaseHelper database;
    Cursor cursor, cursor2;
    TextView editPengeluaran, editPemasukan;
    ImageView imgSetting,tambahPemasukan,tambahPengeluaran,dataKeuangan;

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DatabaseHelper(this);

        tambahPemasukan = (ImageView)findViewById(R.id.imageIncome);
        tambahPengeluaran = (ImageView)findViewById(R.id.imageOutcome);
        dataKeuangan = (ImageView)findViewById(R.id.detailImage);
        editPemasukan = (TextView)findViewById(R.id.editPemasukan);
        editPengeluaran = (TextView)findViewById(R.id.editPengeluaran);
        imgSetting = (ImageView)findViewById(R.id.settingImage);

        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM keuangan WHERE kategori = 'Pemasukan'", null);
        cursor2 = db.rawQuery("SELECT * FROM keuangan WHERE kategori = 'Pengeluaran'", null);
        double pemasukan = 0;
        double pengeluaran = 0;
        while(cursor.moveToNext()){
            double nominal = Double.parseDouble(cursor.getString(2));
            pemasukan = pemasukan + nominal;
        }
        while(cursor2.moveToNext()){
            double nominal = Double.parseDouble(cursor2.getString(2));
            pengeluaran = pengeluaran + nominal;
        }

        String luaran = "Pengeluaran : " + formatRupiah(pengeluaran);
        String masukan = "Pemasukan : " + formatRupiah(pemasukan);

        editPemasukan.setText(String.valueOf(masukan));
        editPengeluaran.setText(String.valueOf(luaran));

//        Log.d("ADebugTag", "Value: " + String.valueOf(pemasukan) + " - " + String.valueOf(pengeluaran));

        // Session check
        Boolean checkSession = database.sessionCheck("kosong");
        if(checkSession == true){
            Intent loginIntent = new Intent(MainActivity.this,Login.class);
            startActivity(loginIntent);
            finish();
        }

        // tambah pemasukan
        tambahPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Pemasukan.class);
                startActivity(intent);
                finish();
            }
        });

        // tambah pemasukan
        tambahPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Pengeluaran.class);
                startActivity(intent);
                finish();
            }
        });

        // tambah pemasukan
        dataKeuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CashflowDetail.class);
                startActivity(intent);
                finish();
            }
        });

        // setting
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Setting.class);
                startActivity(intent);
                finish();
            }
        });

        // bar chart
        barChart = findViewById(R.id.graphDummy);

        // Initialize Array List
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        // Use for Loop
        for (int i=1; i<10; i++){
            float value = (float) (i*10.0);
            BarEntry barEntry = new BarEntry(i, value);
            barEntries.add(barEntry);
        }
        // intialize bar chart
        BarDataSet barDataSet = new BarDataSet(barEntries, "Chart Bar");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setDrawValues(false);
        barChart.setData(new BarData(barDataSet));
        barChart.animateY(5000);
        barChart.getDescription().setText("Chart My Cash Book");
        barChart.getDescription().setTextColor(Color.BLUE);

    }
}