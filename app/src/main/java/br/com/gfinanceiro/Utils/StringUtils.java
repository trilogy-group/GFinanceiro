package br.com.gfinanceiro.Utils;

import android.text.TextUtils;

/**
 * Created by Kabom on 07/12/2015.
 */
public class StringUtils {

    public static boolean isEmpty(CharSequence s) {
        return TextUtils.isEmpty(s);
    }

    public static boolean isEmptyOrWhiteSpaces(CharSequence s) {
        return isEmpty(s) || s.toString().trim().isEmpty();
    }
}
