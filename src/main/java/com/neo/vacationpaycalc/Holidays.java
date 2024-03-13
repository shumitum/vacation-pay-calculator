package com.neo.vacationpaycalc;

import java.time.LocalDate;

public class Holidays {
    private static final int[] holidaysLeapYear = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 54, 68, 122, 130, 164, 309};
    private static final int[] holidaysRegularYear = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 54, 67, 121, 129, 163, 308};

    private Holidays() {
    }

    public static int[] getHolidays() {
        final int currentYear;
        currentYear = LocalDate.now().getYear();
        if (currentYear % 400 == 0 || currentYear % 4 == 0 && currentYear % 100 != 0) {
            return holidaysLeapYear;
        } else {
            return holidaysRegularYear;
        }
    }
}

/**
 * високосный год -- невисокосный год
 * 1, 2, 3, 4, 5, 6 и 8 января — новогодние каникулы;
 * 7 января — Рождество Христово;
 * 23 февраля — День защитника Отечества; 54
 * 8 марта — Международный женский день; 68 -- 67
 * 1 мая — Праздник Весны и Труда; 122 -- 121
 * 9 мая — День Победы; 130 -- 129
 * 12 июня — День России; 164 -- 163
 * 4 ноября — День народного единства. 309 -- 308
 */