package br.com.gfinanceiro.service.data;

import android.net.Uri;

/**
 * Created by Kabom on 06/12/2015.
 */
public class InvalidURIException extends RuntimeException {

    public InvalidURIException(Uri uri) {
        super("URI inválida: " + uri);
    }
}
