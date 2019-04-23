
package com.mobil.bulut.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Birimler {

    @SerializedName("code")
    @Expose
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
