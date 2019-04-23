
package com.mobil.bulut.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("KullaniciDetay")
    @Expose
    private KullaniciDetay kullaniciDetay;
    @SerializedName("Menuler")
    @Expose
    private Menuler menuler;
    @SerializedName("Birimler")
    @Expose
    private List<Birimler> birimler = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;

    public KullaniciDetay getKullaniciDetay() {
        return kullaniciDetay;
    }

    public void setKullaniciDetay(KullaniciDetay kullaniciDetay) {
        this.kullaniciDetay = kullaniciDetay;
    }

    public Menuler getMenuler() {
        return menuler;
    }

    public void setMenuler(Menuler menuler) {
        this.menuler = menuler;
    }

    public List<Birimler> getBirimler() {
        return birimler;
    }

    public void setBirimler(List<Birimler> birimler) {
        this.birimler = birimler;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
