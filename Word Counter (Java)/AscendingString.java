/**
 * File: AscendingString.java
 * Author: Matianyu Zang
 * Date: 03/30/2021
 * CS231 SectionA
 * Project06
 */

import java.util.Comparator;

public class AscendingString implements Comparator<String> {
    @Override
    //compares two Strings
    //returns a negative number, 0, or positive number if string1 is
    //smaller, equal, or larger than string2 accordingly
    public int compare(String string1, String string2) {
        return string1.compareTo(string2);
    }
}
