package br.com.gfinanceiro.Utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Kabom on 10/12/2015.
 */
public class NumberUtils {


    private static final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static String formatAsCurrency(double value) {
        return nf.format(value);
    }
}


