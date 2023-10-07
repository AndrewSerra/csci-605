package homework_3;

import java.util.Arrays;

class StringIntegerArrays {

    static boolean firstTime = true;

    static String resturnsAstring(String arg)	{
        String rValue;

        if ( firstTime )
            rValue = "";
        else
            rValue = arg;
        firstTime = false;
        return rValue;
    }

    /**
     * Sort all character values in the string using ascii character code.
     *
     * @param       s    String value that is to be sorted. The assumption is
     *                   to not have whitespaces.
     */
    static String sortStringFromAscii(String s) {
        char[] sCharArr = s.toCharArray();

        for(int i = 1; i < sCharArr.length; i++) {
            int tmp = (int)sCharArr[i];
            int j = i - 1;

            while(j >= 0 && tmp < (int)sCharArr[j]) {
                sCharArr[j + 1] = sCharArr[j];
                j = j - 1;
            }

            sCharArr[j + 1] = (char)tmp;
        }

        String sortedStr = "";

        for(int i = 0; i < sCharArr.length; i++) {
            sortedStr = sortedStr + sCharArr[i];
        }
        return sortedStr;
    }

    public static void main( String args[] ) {
        String a, b;
        String aString= null;
        String bString= null;
        String cString= null;
        String dString= null;
        String eString= null;
        String fString= null;
        String gString= null;
        String hString= null;
        String iString= null;

        if ( args.length == 1 ) {
            // This is a string literal the final string saved in memory is
            // "Abba"
            aString = "Ab" + "ba";
            // This is the same value as aString which will point to the same
            // memory location the string values are equal and are also
            // identical
            bString = "Abba";
            // Unlike aString and bString, cString will be saved in a
            // separate memory location
            cString = "A";
            // The value for dString is "Abba" and will be identical to
            // aString and bString. Since "A" is separately saved, cString
            // and dString will not be equal and will not be identical.
            dString = cString + "b" + "b" + aString.substring(aString.length() - 1);
            fString = "Pink FLoyd";
            // gString becomes the value "Abba" the identical value of
            // aString, bString, and cString
            gString = "Abba" + resturnsAstring("");
            // The value for hString matches gString as there are new objects
            // created and the value of hString is a string literal "Abba".
            // It will be equal and identical to gString
            hString = "Ab" + resturnsAstring("ba");
        } else {
            // The string literal is created and stored as "Otto"
            aString = "Ot" + "to";
            // The value for bString is "Otto" which is equal and identical
            bString = "Otto";
            // "O" is saved in a separate location as a value for cString
            cString = "O";
            // This operation saves the value "Otto" to dString which is neither
            // identical nor equal to cString
            dString = cString + "t" + "t" + aString.substring(aString.length() - 1);
            fString = "Led ZeppeLin";
            // The value for hString matches gString as there are new objects
            // created and the value of hString is a string literal "Otto".
            // It will be equal and identical to gString
            gString = "Otto" + resturnsAstring("");
            hString = "Ot" + resturnsAstring("to");
        }

        // your code here

        for(int i = 0; i < fString.length(); i++) {
            if(fString.charAt(i) == 'L') {
                fString = fString.substring(0, i)
                        + 'l' + fString.substring(i+1);
            } else if (fString.charAt(i) == ' ') {
                fString = fString.substring(0, i) + fString.substring(i+1);
            }
        }

        System.out.print(sortStringFromAscii(fString));
    }
}
