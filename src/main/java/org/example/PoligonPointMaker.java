package org.example;

import org.apache.commons.lang3.StringUtils;
import org.osgeo.proj4j.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.lang.Math.abs;

public class PoligonPointMaker {
    private static OP op;
    private static Map<String, String> mapZariadu;

    public void setOp(OP op) {
        this.op = op;
    }

    public void readFile() {
        mapZariadu = new HashMap<>();
        File file = new File("D:\\YO_NA\\MakePoligon2\\Zariadu.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = "";
            String[] arr;
            while ((line = reader.readLine()) != null) {
                arr = line.split(",");
                mapZariadu.put(StringUtils.strip(arr[0], "\"")
                        , StringUtils.strip(arr[1], "\""));
            }
        } catch (Exception e) {
            Logger.getLogger(PoligonPointMaker.class.getName()).info("Шляпа: " + e.getMessage());
        }
    }

    //формируем список точек для сектора
    public List<MyPoint> getListOPPoint() {
        List<MyPoint> listPoint = new ArrayList<>();
        double x = op.getX();
        double y = op.getY();
        x += 5300000;
        if (y > 50000) {
            y += 7300000;
        } else {
            y += 7400000;
        }
        listPoint.add(refactorXYtoBL(x, y));

        double sector = abs(op.getTurnLeft()) + op.getTurnRight();
        double doliaSectora = sector / 12;
        double nachaloSectora = op.getOn() + op.getTurnLeft();
        if (nachaloSectora < 0) nachaloSectora += 6000;
        int dalTopo = getDalTopo();
        List<Double> listPromXY;

        for (int i = 0; i < 13; i++) {
            listPromXY = PGZ(x, y, nachaloSectora, dalTopo);
            listPoint.add(refactorXYtoBL(listPromXY.get(0), listPromXY.get(1)));
            nachaloSectora += doliaSectora;
        }
        //добавляем в конце начальную чтоб замкнуть сектор
        listPoint.add(refactorXYtoBL(x, y));
        return listPoint;
    }

    public static MyPoint refactorXYtoBL(double x, double y) {

// Создаем исходную и целевую системы координат
        CRSFactory factory = new CRSFactory();
        CoordinateReferenceSystem srcCRS = factory.createFromName("EPSG:28407");
        CoordinateReferenceSystem dstCRS = factory.createFromName("EPSG:4326");

        // Создаем объект для преобразования координат
        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CoordinateTransform transform = ctFactory.createTransform(srcCRS, dstCRS);

        // Преобразуем координаты
        //сначала вводим долготу потом широту
        ProjCoordinate srcCoord = new ProjCoordinate(y, x);
        ProjCoordinate dstCoord = new ProjCoordinate();
        transform.transform(srcCoord, dstCoord);

        // Выводим результат(наоборот x->y)
        //System.out.println("Преобразованные координаты: " + dstCoord.x + ", " + dstCoord.y);
        //возвращаем масив b, l
        return new MyPoint(dstCoord.y,dstCoord.x);
    }

    public List PGZ(double x, double y, double a, double d) {
        List<Double> xy = new ArrayList<>();
        double x1 = Math.cos(a / 100 * 6 * 3.141592 / 180) * d + x;
        double y1 = Math.sin(a / 100 * 6 * 3.141592 / 180) * d + y;
        xy.add(x1);
        xy.add(y1);
        return xy;
    }

    public int getDalTopo() {
        readFile();
        String name = op.getName();
        String zariad = mapZariadu.get(name);
        try {
            return switch (zariad) {
                case "130P" -> 26500;
                case "130Y" -> 19000;
                case "56P" -> 15200;
                case "56Y" -> 12800;
                case "D" -> 20400;
                case "Y" -> 15000;
                case "Dalnoboi" -> 7200;
                case "Shestoi" -> 5900;
                default -> 12800;
            };
        } catch (NullPointerException e) {
            System.out.println(op.getName());
            Logger.getLogger(PoligonPointMaker.class.getName()).info("Шляпа: " + e.getMessage());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MyConnector.class.getName()).info("Shlapa: " + ex.getMessage());
            }
            return 12800;
        }
    }
}

