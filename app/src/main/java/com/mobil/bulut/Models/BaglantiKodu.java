package com.mobil.bulut.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaglantiKodu {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("firma")
    @Expose
    private String firma;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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