package com.example.diyet;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OneriAl extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference dbRef;

    Button btn;

    double toplamKalori,toplamProtein,toplamKarbonhidrat,toplamYağ;
    TextView tvo1,tvo2,tvo3,tvo4,tva1,tva2,tva3,tva4;
    List<Yemekler> Yemekler;
    List<Yemekler> Kahvaltı;
    List<Yemekler> Corba;
    List<Yemekler> EkmekVB;
    List<Yemekler> Salata;

    double KahvaltıKalori, OgleKalori,AksamKalori;
    TextView tvKahvaltı1,tvKahvaltı2,tvKahvaltı3,tvKahvaltı4,tvKahvaltı5,tvToplamKalori;
    Button btnKahvaltıOner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneri_al);
        toplamKalori=0;
        toplamKarbonhidrat=0;
        toplamProtein=0;
        toplamYağ=0;
        Yemekler= new ArrayList<>();
        EkmekVB = new ArrayList<>();
        Salata = new ArrayList<>();
        Corba = new ArrayList<>();
        Kahvaltı = new ArrayList<>();

        //üst çubuk rengi ayarlama
        Window window=getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT_WATCH){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.bluelight));
        }

        db = FirebaseDatabase.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("Yemekler");
        tvKahvaltı1 = findViewById(R.id.tvKahvaltı1);
        tvKahvaltı2 = findViewById(R.id.tvKahvaltı2);
        tvKahvaltı3 = findViewById(R.id.tvKahvaltı3);
        tvKahvaltı4 = findViewById(R.id.tvKahvaltı4);
        tvKahvaltı5 = findViewById(R.id.tvKahvaltı5);
        tvo1= findViewById(R.id.tvO1);
        tvo2= findViewById(R.id.tvO2);
        tvo3= findViewById(R.id.tvO3);
        tvo4= findViewById(R.id.tvO4);
        tva1= findViewById(R.id.tvA1);
        tva2= findViewById(R.id.tvA2);
        tva3= findViewById(R.id.tvA3);
        tva4= findViewById(R.id.tvA4);

        tvToplamKalori = findViewById(R.id.tvToplamKalori);
        btnKahvaltıOner= findViewById(R.id.btnOneri);
        YemekCek();
        btnKahvaltıOner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               EkranaYaz();
            }
        });
        Bundle extras = getIntent().getExtras();
        String Gkalori = String.valueOf(extras.getInt("Gkalori"));
        int KKalori = Integer.valueOf(Gkalori);
        KahvaltıKalori= (KKalori/100.0)*32.0;
        OgleKalori= (KKalori/100.0)*40.0;
        AksamKalori = (KKalori/100.0)*28.0;
        String Ikilo = String.valueOf(extras.getInt("İkilo"));
        String Kilo = String.valueOf(extras.getInt("Kilo"));


    }



    public void YemekCek(){
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dr: dataSnapshot.getChildren()){
                   if(dr.getValue(Yemekler.class).getTür().equals("Kahvaltı")){
                       KahvaltıDoldur(dr.getValue(Yemekler.class));
                   }
                   if(dr.getValue(Yemekler.class).getTür().equals("Çorba")){
                        CorbaDoldur(dr.getValue(Yemekler.class));
                   }
                   if(dr.getValue(Yemekler.class).getTür().equals("Yemek")){
                       YemekDoldur(dr.getValue(Yemekler.class));
                   }
                    if(dr.getValue(Yemekler.class).getTür().equals("EkmekVB")){
                        EkmekDoldur(dr.getValue(Yemekler.class));
                    }
                    if(dr.getValue(Yemekler.class).getTür().equals("Salata")){
                        SalataDoldur(dr.getValue(Yemekler.class));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Bir hata oluştu",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void SalataDoldur(Yemekler salata){Salata.add(salata); }
    public void EkmekDoldur(Yemekler ekmek){ EkmekVB.add(ekmek); }
    public void YemekDoldur(Yemekler yemek){
        Yemekler.add(yemek);
    }
    public void KahvaltıDoldur(Yemekler kahvaltı){
        Kahvaltı.add(kahvaltı);
    }
    public void CorbaDoldur(Yemekler corba){
        Corba.add(corba);
    }




    public void EkranaYaz(){
        toplamKalori=0;
        KahvaltıEkranaYaz();
        OgleEkranaYaz();
        AksamEkranaYaz();
        tvToplamKalori.setText(String.valueOf(toplamKalori));
    }

    public void OgleEkranaYaz(){
        Yemekler k1,k2,k3,k4;
        BesinDegerleri besinDegerleri = new BesinDegerleri();
        double kalori = 0;
        double protein = 0;
        double karbonhidrat=0;
        double yağ=0;
        Random rnd = new Random();
        for(int i=0;i<100;i++){
            k1=Corba.get(rnd.nextInt(Corba.size()));
            tvo1.setText(k1.getAdı()+"\nKalori:   "+k1.getKalori());
            kalori+=k1.getKalori();
            ProteinTopla(k1.getProtein());

            k2=Yemekler.get(rnd.nextInt(Yemekler.size()));
            tvo2.setText(k2.getAdı()+"\nKalori:   "+k2.getKalori());
            kalori+= k2.getKalori();
            ProteinTopla(k2.getProtein());


            k3=EkmekVB.get(rnd.nextInt(EkmekVB.size()));
            tvo3.setText(k3.getAdı()+"\nKalori:   "+k3.getKalori());
            kalori+=k3.getKalori();
            ProteinTopla(k3.getProtein());


            k4=Salata.get(rnd.nextInt(Salata.size()));
            tvo4.setText(k4.getAdı()+"\nKalori:   "+k4.getKalori());
            kalori+=k4.getKalori();
            ProteinTopla(k4.getProtein());


            if(Math.abs(OgleKalori-kalori)<100){
               // toplamKarbonhidrat=toplamKarbonhidrat+karbonhidrat;
                toplamKalori=toplamKalori+kalori;
                //toplamProtein=toplamProtein+protein;
                //toplamYağ=toplamYağ+yağ;
                break;
            }
            else{
                kalori=0;
            }
        }
      //  return besinDegerleri;
    }
    public void AksamEkranaYaz(){
        Yemekler k1,k2,k3,k4;
        double kalori = 0;
        double protein = 0;
        double karbonhidrat=0;
        double yağ=0;
        BesinDegerleri besinDegerleri = new BesinDegerleri();
        Random rnd = new Random();
        for(int i=0;i<100;i++){
            k1=Corba.get(rnd.nextInt(Corba.size()));
            tva1.setText(k1.getAdı()+"\nKalori:   "+k1.getKalori());
            kalori+=k1.getKalori();
            ProteinTopla(k1.getProtein());


            k2=Yemekler.get(rnd.nextInt(Yemekler.size()));
            tva2.setText(k2.getAdı()+"\nKalori:   "+k2.getKalori());
            kalori+= k2.getKalori();
            ProteinTopla(k2.getProtein());


            k3=EkmekVB.get(rnd.nextInt(EkmekVB.size()));
            tva3.setText(k3.getAdı()+"\nKalori:   "+k3.getKalori());
            kalori+=k3.getKalori();
            ProteinTopla(k3.getProtein());


            k4=Salata.get(rnd.nextInt(Salata.size()));
            tva4.setText(k4.getAdı()+"\nKalori:   "+k4.getKalori());
            kalori+=k4.getKalori();
            ProteinTopla(k4.getProtein());



            if(Math.abs(OgleKalori- kalori)<100){
                //toplamKarbonhidrat=toplamKarbonhidrat+karbonhidrat;
                toplamKalori=toplamKalori+kalori;
                //toplamProtein=toplamProtein+protein;
                //toplamYağ=toplamYağ+yağ;
                break;
            }
            else{
               kalori=0;
            }
        }
        //return besinDegerleri;
    }

    public void KahvaltıEkranaYaz(){

        double kalori = 0;
        double protein = 0;
        double karbonhidrat=0;
        double yağ=0;
        Yemekler k1,k2,k3,k4,k5;
        BesinDegerleri besinDegerleri = new BesinDegerleri();

        Random rnd = new Random();
        for(int i=0;i<1000;i++){
            k1=Kahvaltı.get(rnd.nextInt(Kahvaltı.size()));
            kalori+=k1.getKalori();
            ProteinTopla(k1.getProtein());

            tvKahvaltı1.setText(k1.getAdı()+"\nKalori:"+String.valueOf(k1.getKalori()));


            k2=Kahvaltı.get(rnd.nextInt(Kahvaltı.size()));
            kalori+=k2.getKalori();
            ProteinTopla(k2.getProtein());

            tvKahvaltı2.setText(k2.getAdı()+"\nKalori:"+String.valueOf(k2.getKalori()));

            k3=Kahvaltı.get(rnd.nextInt(Kahvaltı.size()));
            kalori+=k3.getKalori();
            ProteinTopla(k3.getProtein());


            tvKahvaltı3.setText(k3.getAdı()+"\nKalori:"+ String.valueOf(k3.getKalori()));

            k4=Kahvaltı.get(rnd.nextInt(Kahvaltı.size()));
            kalori+=k4.getKalori();
            ProteinTopla(k4.getProtein());

            tvKahvaltı4.setText(k4.getAdı()+"\nKalori:" + String.valueOf(k4.getKalori()));


            k5=Kahvaltı.get(rnd.nextInt(Kahvaltı.size()));
            kalori+=k5.getKalori();
            ProteinTopla(k5.getProtein());



            tvKahvaltı5.setText(k5.getAdı()+"\nKalori:"+String.valueOf(k5.getKalori()));

            if(Math.abs(KahvaltıKalori-kalori)<100){
                toplamKalori=toplamKalori+kalori;
                break;
            }
            else{
                kalori=0;
            }

        }
    //    return besinDegerleri;
    }

    public void ProteinTopla(double protein){
        toplamProtein=toplamProtein+protein;
    }
}
