package com.example.diyet;

public class Yemekler {
        private String Adı;
        private Double Kalori;
        private Double Protein ;
        private Double Karbonhidrat;
        private Double Yağ;
        private String Tür;
        private String Durum;
        public Yemekler(){}

        public Yemekler(String adı, Double kalori, Double karbonhidrat, Double protein, Double yağ, String tür, String durum) {
            this.Adı = adı;
            this.Kalori = kalori;
            this.Karbonhidrat = karbonhidrat;
            this.Protein = protein;
            this.Yağ = yağ;
            this.Tür=tür;
            this.Durum=durum;

        }

    public String getAdı() {
        return Adı;
    }

    public void setAdı(String adı) {
        Adı = adı;
    }

    public Double getKalori() {
        return Kalori;
    }

    public void setKalori(Double kalori) {
        Kalori = kalori;
    }

    public Double getProtein() {
        return Protein;
    }

    public void setProtein(Double protein) {
        Protein = protein;
    }

    public Double getKarbonhidrat() {
        return Karbonhidrat;
    }

    public void setKarbonhidrat(Double karbonhidrat) {
        Karbonhidrat = karbonhidrat;
    }

    public Double getYağ() {
        return Yağ;
    }

    public void setYağ(Double yağ) {
        Yağ = yağ;
    }

    public String getTür() {
        return Tür;
    }

    public void setTür(String tür) {
        Tür = tür;
    }

    public String getDurum() {
        return Durum;
    }

    public void setDurum(String durum) {
        Durum = durum;
    }
}
