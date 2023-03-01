/**
 * File: IntegerComparator.java
 * Author: Matianyu Zang
 * Date: 04/03/2021
 * CS231 SectionA
 * Project06
 */

import java.util.Comparator;

public class IntegerComparator implements Comparator<Integer> {
    @Override
    //compares two Integers
    //returns a negative number, 0, or positive number if Int1 is
    //smaller, equal, or larger than Int2 accordingly
    public int compare(Integer int1,Integer int2) {
        return int1.compareTo(int2);
    }
}
