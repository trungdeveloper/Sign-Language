package com.example.signlanguage.model;

import java.util.Comparator;

public class NameComparator implements Comparator<Subcategory> {
    public int compare(Subcategory s1, Subcategory s2) {
        return s1.getKeyword().compareTo(s2.getKeyword());
    }
}
