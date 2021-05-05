package ru.itmo.scs.utils;

public class RussianMonthConverter {
    public static int convertMonthToMonthNumber(String month) {
        switch (month) {
            case "января":
                return 1;
            case "февраля":
                return 2;
            case "марта":
                return 3;
            case "апреля":
                return 4;
            case "мая":
                return 5;
            case "июня":
                return 6;
            case "июля":
                return 7;
            case "августа":
                return 8;
            case "сентября":
                return 9;
            case "октября":
                return 10;
            case "ноября":
                return 11;
            case "декабря":
                return 12;
        }
        return 0;
    }

}
