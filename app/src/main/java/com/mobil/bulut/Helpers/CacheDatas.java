package com.mobil.bulut.Helpers;

import com.mobil.bulut.Models.LoginResponse;

public class CacheDatas {

    //Plugins
    public static LoginResponse loginResponse = null;
    public static String uuid = null;

    public static void clearAll(){
        uuid = null;
        loginResponse = null;
    }

}
