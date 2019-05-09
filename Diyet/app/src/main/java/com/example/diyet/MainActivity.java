package com.example.diyet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.se.omapi.Session;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import javax.xml.validation.TypeInfoProvider;

import static com.example.diyet.ExampleDialog.*;

public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener, DialogTwo.ExampleDialogListener{

    String myEmailString, passString, sendToEmailString, subjectString, textString;


    String Captcha[] = {"1","A","a","2","B","b","3","C","c","4","D","d","5","E","e","6","F","f","7","G","g","8","H","h","9","I","i","10","J","j","11","K","k",
            "12","L","l","13","M","m","14","N","n","15","O","o","16","P","p","17","Q","q","18","R","r","19","S","s","20","T","t","21","U","u","22","V","v","23","W","w","24","X","x",
            "25","Y","y","26","Z","z"};

    String RandomKontrol;
    public String girisYapanKullanici;
    public int veri=0;
    public String[] mailKontrol;
    Button send, giris,SingIn;
    FirebaseDatabase db;
    DatabaseReference dbRef;
    TextView kullaniciAdi, parola;
    int kontrol=0;
    CheckBox cbBeniHatırla;
    SQLiteDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mailKontrol = new String[]{""};
        db = FirebaseDatabase.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("KullanıcıBilgi");
        //kullaniciAdi = findViewById(R.id.textView);
        Window window=getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT_WATCH){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.bluelight));
        }
        send = findViewById(R.id.sifremiUnuttum);
        kullaniciAdi = findViewById(R.id.kullanıcıAdı);
        parola = findViewById(R.id.parola);
        giris = findViewById(R.id.giris);
        cbBeniHatırla = findViewById(R.id.cbBeniHatırla);
        SingIn = findViewById(R.id.btnSingIn);
        KodOlustur();
       /*
        try {
            myDatabase = this.openOrCreateDatabase("Kullanıcı",MODE_PRIVATE,null);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS benihatirla (username VARCHAR, deger INT(2))");

            myDatabase.execSQL("INSERT INTO benihatirla(username,deger) VALUES ('Deneme',0)");

            Cursor cursor = myDatabase.rawQuery("SELECT * FROM benihatirla",null);

            int nameIx= cursor.getColumnIndex("username");
            int degerIx = cursor.getColumnIndex("deger");

            cursor.moveToFirst();
            while (cursor !=null){

                if(1==1){
                    Intent intent = new Intent(this,Anasayfa.class);
                    startActivity(intent);
                    break;
                }
                cursor.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        */
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veri=0;
                openDialog();
            }
        });
    }


    public void SingIn(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void  openDialog(){
        if(veri==0) {
            ExampleDialog exampleDialog = new ExampleDialog();
            exampleDialog.show(getSupportFragmentManager(), "example dialog");
            veri++;
        }

    }

    public void SifreGonder(String sendToEmailString,String Kod){
        String email = sendToEmailString;
        String subject = "Şifremi Unuttum";
        String message = Kod;

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }

    public void KayıtOl(View view) {
        Intent intent = new Intent(this, KayitOl.class);
        startActivity(intent);
    }

    public String MaildAdresiCek(final String username){
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dr: dataSnapshot.getChildren()){
                    String deneme = username;
                    String usernameClass = dr.getValue(KullaniciBilgi.class).getKullaniciAdi();
                    String passwordClass = dr.getValue(KullaniciBilgi.class).getParola();
                    if(deneme.equals(usernameClass)){
                        mailKontrol[0]= dr.getValue(KullaniciBilgi.class).getMail();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Bir hata oluştu",Toast.LENGTH_LONG).show();
            }
        });
        if(mailKontrol[0].equals("")){
            mailKontrol[0]="nburakacar@gmail.com";
        }
        return mailKontrol[0];
    }

    public void kodKontrol(String DogrulamaKodu){
        if(DogrulamaKodu.equals(RandomKontrol)){
            Intent intent = new Intent(this,SifreYenileme.class);
            startActivity(intent);
        }
    }
    public void Giris(View view) {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dr : dataSnapshot.getChildren()) {
                    String username = kullaniciAdi.getText().toString();
                    String pass = parola.getText().toString();
                    String usernameClass = dr.getValue(KullaniciBilgi.class).getKullaniciAdi();
                    String passwordClass = dr.getValue(KullaniciBilgi.class).getParola();
                    if (usernameClass.equals(username) && passwordClass.equals(pass)) {
                        AnasayfayaYonlendirme(dr.getValue(KullaniciBilgi.class).getKullaniciAdi());
                        /*if(cbBeniHatırla.isChecked()){
                            myDatabase.execSQL("UPDATE benihatirla SET deger=1  where username='Deneme'");
                        }
                        */

                        break;
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "hata", Toast.LENGTH_LONG).show();
            }

        });


    }

    public void AnasayfayaYonlendirme(String kullanıcıAdı){
        Intent intent = new Intent(this, Anasayfa.class);
        intent.putExtra("send",kullanıcıAdı);
        startActivity(intent);
    }

    public void KodOlustur(){
        Random random = new Random();
        RandomKontrol="";
         for(int i=0;i<6;i++){
             int sayı = random.nextInt(Captcha.length);
            RandomKontrol=RandomKontrol + Captcha[sayı];
        }
    }
    public void applyTexts(String username) {
        if(veri==1){
            String mailAdresi = MaildAdresiCek(username);
            SifreGonder(mailAdresi,RandomKontrol);
            DialogTwo dialogTwo = new DialogTwo();
            dialogTwo.show(getSupportFragmentManager(), "dialog");
            veri=2;
        }
        if(veri==2){

            kodKontrol(username);
        }
        else{
            Intent intent = new Intent(this,SifreYenileme.class);
            startActivity(intent);
        }

    }

}