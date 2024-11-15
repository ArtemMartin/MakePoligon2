package org.example;

import java.util.ArrayList;
import java.util.List;

public class MyPoint {
    private final List<Double> listDolgShir;

    public MyPoint(double shirota, double dolgota) {
        listDolgShir = new ArrayList<>();
        listDolgShir.add(dolgota);
        listDolgShir.add(shirota);
    }

    public List<Double> getListDolgShir() {
        return listDolgShir;
    }
}
