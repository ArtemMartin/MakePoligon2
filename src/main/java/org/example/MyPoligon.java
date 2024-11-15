package org.example;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class MyPoligon {
    private final String divizion;
    private final String name;
    private final String cololrLine;
    private final String color;
    private final List<MyPoint> listLinearRing;

    public MyPoligon(String divizion, String name, String cololrLine, String color
            , List<MyPoint> listLinearRing) {
        this.divizion = divizion;
        this.name = name;
        this.cololrLine = cololrLine;
        this.color = color;
        this.listLinearRing = listLinearRing;
        createPoligon();
    }

    public String getStrListPoint() {
        String str = "";
        for (MyPoint point : listLinearRing) {
            str += (point.getListDolgShir().get(0))
                    + (",")
                    + (point.getListDolgShir().get(1))
                    + (" ");
        }
        return str;
    }

    public void createPoligon() {
        File file = new File(divizion + name + ".kml");
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n" +
                    "  <Document>\n" +
                    "    <Placemark>\n");
            writer.write("<name>" + name + "</name>\n");
            writer.write("<description></description>\n" +
                    "      <Style>\n" +
                    "        <LineStyle>\n");
            writer.write("<color>" + cololrLine + "</color>\n");
            writer.write(" <width>10</width>\n" +
                    " <strokeOpacity>0.99</strokeOpacity>\n" +
                    "        </LineStyle>\n" +
                    "        <PolyStyle>\n");
            writer.write("<color>" + color + "</color>\n");
            writer.write("<fill>1</fill>\n" +
                    " <fillOpacity>0.9</fillOpacity>\n" +
                    "        </PolyStyle>\n" +
                    "      </Style>\n" +
                    "      <Polygon>\n" +
                    "        <extrude>1</extrude>\n" +
                    "        <outerBoundaryIs>\n" +
                    "          <LinearRing>\n");
            writer.write("<coordinates>" + getStrListPoint() + "</coordinates>\n");
            writer.write(" </LinearRing>\n" +
                    "        </outerBoundaryIs>\n" +
                    "      </Polygon>\n" +
                    "    </Placemark>\n" +
                    "  </Document>\n" +
                    "</kml>");
            writer.close();
        } catch (IOException e) {
            Logger.getLogger(MyPoligon.class.getName()).info("Шляпа: " + e.getMessage());
        }
    }
}
