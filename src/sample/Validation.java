package sample;

/**
 * Created by eddylloyd on 11/2/16.
 */
public class Validation {

        public static boolean isNull(String string)
        {
            return string.equals("");
        }
        public static boolean validateName(String Name)
        {
           return Name.matches("[a-zA-Z]+");
        }
        public static boolean validateEmail(String email)
        {
            return email.matches("^.+@.+\\.com$");
        }
        public static boolean validatePassword(String password)
        {
            return password.isEmpty();
        }
        public static boolean isEmpty(String[] strings)
        {
            boolean value = false;

            for (String string:strings
                 ) {
                if(string.equals(null)) value  = false;
               value =  value || string.isEmpty();
            }
            return value;
        }
}
