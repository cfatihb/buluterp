package com.mobil.bulut.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

public class CodeHelper {

    public static Bitmap getImageAssent(Context context, String path) {
        InputStream stream = null;
        try
        {
            stream = context.getAssets().open(path);
            return BitmapFactory.decodeStream(stream);
        }
        catch (Exception ignored) {} finally
        {
            try
            {
                if(stream != null)
                {
                    stream.close();
                }
            } catch (Exception ignored) {}
        }
        return null;
    }

    public static String getChangeCharacter(String text) {

        text = text.toLowerCase();

        text = text.replace("ö", "o");
        text = text.replace("ş", "s");
        text = text.replace("ı", "i");
        text = text.replace("ç", "c");
        text = text.replace("ü", "u");
        text = text.replace("ğ", "g");
        text = text.replace("/", "");
        text = text.replace(" ", "");

        return text;
    }

}
