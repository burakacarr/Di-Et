package com.example.diyet;

public class BesinDegerleri {

    private double protein, karbonhidrat, yağ, kalori;

    public BesinDegerleri() {
        this.protein = 0;
        this.karbonhidrat = 0;
        this.yağ = 0;
        this.kalori = 0;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getKarbonhidrat() {
        return karbonhidrat;
    }

    public void setKarbonhidrat(double karbonhidrat) {
        this.karbonhidrat = karbonhidrat;
    }

    public double getYağ() {
        return yağ;
    }

    public void setYağ(double yağ) {
        this.yağ = yağ;
    }

    public double getKalori() {
        return kalori;
    }

    public void setKalori(double kalori) {
        this.kalori = kalori;
    }
}
