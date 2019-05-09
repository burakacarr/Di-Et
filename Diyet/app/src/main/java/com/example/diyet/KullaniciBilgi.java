package com.example.diyet;

public class KullaniciBilgi {


    private String kullaniciAdi,parola,mail,cinsiyet;
    private Double boy;
    private Double kilo;
    private Double idealKilo;
    private Double Gkalori;
    private int yas;
    private int hatirla;

    public KullaniciBilgi(){}

    public KullaniciBilgi(String kullaniciAdi, String parola, String mail, Double boy, Double kilo, Double idealKilo, Double gkalori, int yas, String cinsiyet,int hatirla) {
        this.kullaniciAdi = kullaniciAdi;
        this.parola = parola;
        this.mail = mail;
        this.cinsiyet = cinsiyet;
        this.boy = boy;
        this.kilo = kilo;
        this.idealKilo = idealKilo;
        Gkalori = gkalori;
        this.yas = yas;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public Double getBoy() {
        return boy;
    }

    public void setBoy(Double boy) {
        this.boy = boy;
    }

    public Double getKilo() {
        return kilo;
    }

    public void setKilo(Double kilo) {
        this.kilo = kilo;
    }

    public Double getIdealKilo() {
        return idealKilo;
    }

    public void setIdealKilo(Double idealKilo) {
        this.idealKilo = idealKilo;
    }

    public Double getGkalori() {
        return Gkalori;
    }

    public void setGkalori(Double gkalori) {
        Gkalori = gkalori;
    }

    public int getYaş() {
        return yas;
    }

    public void setYaş(int yas) {
        this.yas = yas;
    }

    public int getHatirla() {
        return hatirla;
    }

    public void setHatirla(int hatirla) {
        this.hatirla = hatirla;
    }
}


