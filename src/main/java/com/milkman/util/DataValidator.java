package com.milkman.util;

public class  DataValidator {
    public static boolean isEmptyOrNullValidator(int checkValue){
        return checkValue > 0;
    }
    public static boolean isEmptyOrNullValidator(String checkValue){
        return checkValue != null && !checkValue.isEmpty();
    }
    public static boolean isDateValid(String givenDate) {
        return CommonConversions.convertStringToDate(givenDate) != null;
    }
}
