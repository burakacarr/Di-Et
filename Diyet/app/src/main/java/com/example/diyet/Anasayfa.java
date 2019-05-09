package com.example.diyet;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Anasayfa extends AppCompatActivity {


    TextView tv;
    KullaniciBilgi kullaniciBilgi;
    FirebaseDatabase db;
    DatabaseReference dbRef;
    String value;
    Integer sayı, kilo;
    float protein,yağ,karbonhidrat;
    int veri=0;
    Button btnOneri;

    int oKilo, oİkilo, oGkalori;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);
        progressBar = findViewById(R.id.progressBar);
        tv = findViewById(R.id.tvKaloriMiktar);
        Window window=getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT_WATCH){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.bluelight));
        }
        btnOneri = findViewById(R.id.btnOneri);
        btnOneri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OneriAl.class);
                intent.putExtra("Gkalori",oGkalori);
                intent.putExtra("İkilo",oİkilo);
                intent.putExtra("Kilo",oKilo);
                startActivity(intent);
            }
        });
        //progressBar.setMax(1000);
        //progressBar.setIndeterminate(false);
        db = FirebaseDatabase.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("KullanıcıBilgi");
        Bundle extras = getIntent().getExtras();
        value = extras.getString("send");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            int kontrol=0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dr : dataSnapshot.getChildren()) {
                    String usernameClass = dr.getValue(KullaniciBilgi.class).getKullaniciAdi();
                    if (usernameClass.equals(value)) {
                        kontrol = 1;
                        sayı = dr.getValue(KullaniciBilgi.class).getGkalori().intValue();
                        kilo = dr.getValue(KullaniciBilgi.class).getKilo().intValue();
                        int ikilo = dr.getValue(KullaniciBilgi.class).getIdealKilo().intValue();
                        setupPieChart(kilo,sayı);
                        BarDoldur(sayı);
                        KaloriVeKiloDurumuCek(kilo,ikilo,sayı);
                        break;
                    }
                }
                tv.setText(sayı.toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"hata",Toast.LENGTH_LONG);
            }
        });


    }
   public void KaloriVeKiloDurumuCek(int Kilo, int ikilo, int Gkalori){
        oKilo=kilo;
        oGkalori=Gkalori;
        oİkilo = ikilo;
    }


    private void setupPieChart(int kilo, int kalori) {
        List<PieEntry> pieEntries = new ArrayList<>();

        protein = kilo*0.8f;
        karbonhidrat = (kalori*0.6f)/4;
        yağ = ((kalori*0.6f)-(protein*4))/9;
        pieEntries.add(new PieEntry(protein,"Protein"));
        pieEntries.add(new PieEntry(karbonhidrat,"Karbonhidrat"));
        pieEntries.add(new PieEntry(yağ,"Yağ"));

        PieDataSet dataSet = new PieDataSet(pieEntries,"Makro Gıdalar");
        PieData data = new PieData(dataSet);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.animateY(1000);
        chart.invalidate();



    }


    public void BarDoldur(int sayı){
        progressBar.setMax(sayı);
        progressBar.setIndeterminate(false);

    }

}
