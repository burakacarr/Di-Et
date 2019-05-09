package com.example.diyet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KayitOl extends AppCompatActivity {

    EditText etKullaniciAdi,etParola,etParolaTekrar,etMail,etBoy,etKilo,etYas,etCinsiyet;
    FirebaseDatabase db;
    double gKalori,aktiviteFakoru;
    TextView tvHata;
    Spinner spCinsiyet, spYogunluk;
    String cinsiyet ="Kadın";
    double[] yogunlukErkek = {1.3,1.6,1.7,2.1,2.4};
    double[] yogunlukKadın = {1.3,1.5,1.6,1.9,2.2};
    Button giris, kayıt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        db=FirebaseDatabase.getInstance();
        etKullaniciAdi=findViewById(R.id.etKullaniciAdi);
        etParola=findViewById(R.id.etParola);
        etParolaTekrar=findViewById(R.id.etParolaTekrar);
        etMail=findViewById(R.id.etMail);
        etBoy=findViewById(R.id.etBoy);
        etKilo=findViewById(R.id.etKilo);
        etYas=findViewById(R.id.etYas);
        tvHata = findViewById(R.id.tvHata);
        spCinsiyet = findViewById(R.id.spCinsiyet);
        spYogunluk = findViewById(R.id.spYogunluk);
        giris = findViewById(R.id.btnGirisEkranı);
        kayıt = findViewById(R.id.btnKayitEkranı);
        ArrayAdapter arrayAdapterCinsiyet = ArrayAdapter.createFromResource(this,R.array.AktiviteYoğunluk,android.R.layout.simple_spinner_item);
        arrayAdapterCinsiyet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spYogunluk.setAdapter(arrayAdapterCinsiyet);
        spYogunluk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] yogunlukDizisi = getResources().getStringArray(R.array.AktiviteYoğunluk);
                if(cinsiyet.equals("Erkek")){
                    if(yogunlukDizisi[position].equals("Çok Hafif")){
                        aktiviteFakoru=1.3;
                    }
                    if(yogunlukDizisi[position].equals("Hafif")){
                        aktiviteFakoru=1.6;
                    }
                    if(yogunlukDizisi[position].equals("Orta")){
                        aktiviteFakoru=1.7;
                    }
                    if(yogunlukDizisi[position].equals("Ağır")){
                        aktiviteFakoru=2.1;
                    }
                    if(yogunlukDizisi[position].equals("Çok Ağır")){
                        aktiviteFakoru=2.4;
                    }
                }
                else{
                    if(yogunlukDizisi[position].equals("Çok Hafif")){
                        aktiviteFakoru=1.3;
                    }
                    if(yogunlukDizisi[position].equals("Hafif")){
                        aktiviteFakoru=1.5;
                    }
                    if(yogunlukDizisi[position].equals("Orta")){
                        aktiviteFakoru=1.6;
                    }
                    if(yogunlukDizisi[position].equals("Ağır")){
                        aktiviteFakoru=1.9;
                    }
                    if(yogunlukDizisi[position].equals("Çok Ağır")){
                        aktiviteFakoru=2.2;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                aktiviteFakoru=1.3;
            }
        });
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.cinsiyet,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCinsiyet.setAdapter(adapter);
        spCinsiyet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] cinsiyetDizisi = getResources().getStringArray(R.array.cinsiyet);
                cinsiyet = cinsiyetDizisi[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cinsiyet="Kadın";
            }
        });
    }

    public int ParolaKontrol(String parola, String parolaTekrar){
        if(parola.equals(parolaTekrar)){
            return 1;
        }
        else{
            return 0;
        }
    }

    public double  IdealKiloHesapla(Double boy, String cinsiyet){

        double idealKilo;
        if(cinsiyet.equals("Male")){
            idealKilo=2.3*((boy/2.54)-60);
            idealKilo=idealKilo+50;
        }
        else{
            idealKilo=45.5+((2.3)*((boy-60)/2.54));
        }

        return  idealKilo;
    }

    public void KaloriHesaplama(int yas, double kilo, String cinsiyet, double aktiviteFakoru) {
        if (cinsiyet.equals("Erkek")) {
            if (yas < 3) {
                gKalori = (59.5 * kilo) - 20.4;
                gKalori = gKalori * aktiviteFakoru;
            }
            if (yas > 3 && yas < 11) {
                gKalori = (22.7 * kilo) + 504.3;
                gKalori = gKalori * aktiviteFakoru;
            }
            if (yas > 10 && yas < 19) {
                gKalori = (17.7 * kilo) + 658.2;
                gKalori = gKalori * aktiviteFakoru;
            }
            if (yas > 18 && yas < 31) {
                gKalori = (15.1 * kilo) + 692.2;
                gKalori = gKalori * aktiviteFakoru;
            }
            if (yas > 30 && yas < 61) {
                gKalori = (11.5 * kilo) + 873.1;
                gKalori = gKalori * aktiviteFakoru;
            } else {
                gKalori = (11.7 * kilo) + 587.7;
                gKalori = gKalori * aktiviteFakoru;
            }
        } else {
            if (yas < 3) {
                gKalori = (58.3 * kilo) - 31.1;
                gKalori = gKalori * aktiviteFakoru;
            }
            if (yas > 3 && yas < 11) {
                gKalori = (20.3 * kilo) + 485.9;
                gKalori = gKalori * aktiviteFakoru;
            }
            if (yas > 10 && yas < 19) {
                gKalori = (13.4 * kilo) + 692.6;
                gKalori = gKalori * aktiviteFakoru;
            }
            if (yas > 18 && yas < 31) {
                gKalori = (14.8 * kilo) + 486.6;
                gKalori = gKalori * aktiviteFakoru;
            }
            if (yas > 30 && yas < 61) {
                gKalori = (8.1 * kilo) + 845.6;
                gKalori = gKalori * aktiviteFakoru;
            } else {
                gKalori = (9.1 * kilo) + 658.5;
                gKalori = gKalori * aktiviteFakoru;
            }
        }
    }



    public void GiriseDon(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void KayıtEkranı(View view){
        Intent intent = new Intent(this, KayitOl.class);
        startActivity(intent);
    }

    public  void Kaydet(View view){
        String kullaniciAdi = etKullaniciAdi.getText().toString();
        String parola = etParola.getText().toString();
        String parolaTekrar = etParolaTekrar.getText().toString();
        String mail = etMail.getText().toString();
        String s = etBoy.getText().toString();
        double boy=  Double.valueOf(s);
        String s1 = etKilo.getText().toString();
        Double kilo=  Double.valueOf(s1);
        String s2 = etYas.getText().toString();
        int yas=  Integer.valueOf(s2);
        KaloriHesaplama(yas,kilo,cinsiyet,aktiviteFakoru);
        if(ParolaKontrol(parola,parolaTekrar)==1){
            double iKilo = IdealKiloHesapla(boy,cinsiyet);
            DatabaseReference dbRef = db.getReference("KullanıcıBilgi");
            String key = dbRef.push().getKey();
            DatabaseReference dbRefKey = db.getReference("KullanıcıBilgi/"+key);
            dbRefKey.setValue(new KullaniciBilgi(kullaniciAdi,parola,mail,boy,kilo,iKilo,gKalori,yas,cinsiyet,0));
            tvHata.setText("");
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else{
            tvHata.setText("Parolalar aynı olmak zorunda");
        }

    }


}
