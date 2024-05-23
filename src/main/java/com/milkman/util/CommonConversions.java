package com.milkman.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CommonConversions {
    public static LocalDateTime convertStringToDate(String dateString){
        try {
            System.out.println("local date *************");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
