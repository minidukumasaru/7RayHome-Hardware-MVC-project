package lk.ijse.Hardware7RayHome.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

        public static boolean isTextFieldValid(TextField textField, String text) {
            String filed = "";

            switch (textField) {
                case NAME:
                    filed = "^[A-z|\\\\s]{3,}$";
                    break;
                case CONTACT:
                    filed = "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";
                    break;
                case NIC:
                    filed = "^([0-9]{9}[x|X|v|V]|[0-9]{12})$";
                    break;
                case ADDRESS:
                    filed = "^([A-z0-9]|[-/,.@+]|\\s){4,}$";
                    break;
                case PRICE:
                    filed = "^([0-9]){1,}[.]([0-9]){1,}$";
                    break;
                case QUANTITY:
                    filed = "^\\d+$";
                    break;
                case PASSWORD:
                    filed = "^\\D*\\d\\D*\\d\\D*\\d\\D*$";
                    break;
                case USERNAME:
                    filed = "^[a-zA-Z0-9_.]+$";
                    break;
                case DATE:
                    filed = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";

            }

            Pattern pattern = Pattern.compile(filed);

            if (text != null){
                if (text.trim().isEmpty()){
                    return false;
                }
            }else {
                return false;
            }

            Matcher matcher = pattern.matcher(text);

            if (matcher.matches()){
                return true;
            }
            return false;
        }
    public static boolean setTextColor(TextField location, javafx.scene.control.TextField textField){
        if (Regex.isTextFieldValid(location, textField.getText())){

            textField.setStyle(" -fx-text-box-border: #34d734;\n" +
                    "    -fx-focus-color: #12dc12;\n" +
                    "    -fx-faint-focus-color: rgba(246,68,68,0);;");

            return true;
        }else {
            textField.setStyle(" -fx-text-box-border: #f10000;\n" +
                    "    -fx-focus-color: #ff0000;\n" +
                    "    -fx-faint-focus-color: rgba(246,68,68,0);;");
            return false;
        }
    }
}

