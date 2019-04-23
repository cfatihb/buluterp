
package com.mobil.bulut.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KullaniciDetay {

    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("ceptel")
    @Expose
    private String ceptel;
    @SerializedName("Firma")
    @Expose
    private String firma;
    @SerializedName("EskiFirma")
    @Expose
    private String eskiFirma;
    @SerializedName("CalismaYili")
    @Expose
    private String calismaYili;
    @SerializedName("VeriTabani")
    @Expose
    private String veriTabani;
    @SerializedName("VersionMSG")
    @Expose
    private String versionMSG;
    @SerializedName("Yetki")
    @Expose
    private String yetki;
    @SerializedName("RolYetki")
    @Expose
    private String rolYetki;
    @SerializedName("SAHA")
    @Expose
    private String sAHA;
    @SerializedName("SAHAYRD")
    @Expose
    private String sAHAYRD;
    @SerializedName("SLSMANREF")
    @Expose
    private String sLSMANREF;
    @SerializedName("SiparisEkle")
    @Expose
    private String siparisEkle;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCeptel() {
        return ceptel;
    }

    public void setCeptel(String ceptel) {
        this.ceptel = ceptel;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getEskiFirma() {
        return eskiFirma;
    }

    public void setEskiFirma(String eskiFirma) {
        this.eskiFirma = eskiFirma;
    }

    public String getCalismaYili() {
        return calismaYili;
    }

    public void setCalismaYili(String calismaYili) {
        this.calismaYili = calismaYili;
    }

    public String getVeriTabani() {
        return veriTabani;
    }

    public void setVeriTabani(String veriTabani) {
        this.veriTabani = veriTabani;
    }

    public String getVersionMSG() {
        return versionMSG;
    }

    public void setVersionMSG(String versionMSG) {
        this.versionMSG = versionMSG;
    }

    public String getYetki() {
        return yetki;
    }

    public void setYetki(String yetki) {
        this.yetki = yetki;
    }

    public String getRolYetki() {
        return rolYetki;
    }

    public void setRolYetki(String rolYetki) {
        this.rolYetki = rolYetki;
    }

    public String getSAHA() {
        return sAHA;
    }

    public void setSAHA(String sAHA) {
        this.sAHA = sAHA;
    }

    public String getSAHAYRD() {
        return sAHAYRD;
    }

    public void setSAHAYRD(String sAHAYRD) {
        this.sAHAYRD = sAHAYRD;
    }

    public String getSLSMANREF() {
        return sLSMANREF;
    }

    public void setSLSMANREF(String sLSMANREF) {
        this.sLSMANREF = sLSMANREF;
    }

    public String getSiparisEkle() {
        return siparisEkle;
    }

    public void setSiparisEkle(String siparisEkle) {
        this.siparisEkle = siparisEkle;
    }

}
