package br.com.gfinanceiro.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kabom on 09/12/2015.
 */
public class DateUtils {


    private static final Locale locale = new Locale("pt", "BR");
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy", locale);

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }

        return df.format(date);
    }

    public static Date createDate(String date) {
        int[] info = parseDateInfo(date);
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(Calendar.DAY_OF_MONTH, info[0]);
        c.set(Calendar.MONTH, info[1] - 1);
        c.set(Calendar.YEAR, info[2]);
        return c.getTime();
    }

    public static String formatDate(int year, int monthOfYear, int dayOfMonth) {
        return String.format(locale, "%02d/%02d/%04d", dayOfMonth, monthOfYear, year);
    }

    public static int[] today() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);

        return new int[]{day, month, year};
    }

    public static int[] parseDateInfo(String date) {
        try {
            String[] tokens = date.split("/");
            int day = Integer.parseInt(tokens[0]);
            int month = Integer.parseInt(tokens[1]);
            int year = Integer.parseInt(tokens[2]);

            return new int[]{day, month, year};

        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static long[] getRange(int mes, int ano) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(ano, mes - 1, 1);
        long dateFrom = c.getTimeInMillis();

        c.set(ano, mes - 1, 1, 23, 59, 59);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

        long dateTo = c.getTimeInMillis();

        return new long[]{dateFrom, dateTo};
    }


}
