package org.example;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) {
        Color color = new Color(216, 157, 27, 140);
        System.out.println("ver 1.1");

        MyConnector myConnector = new MyConnector("127.0.0.1");
        System.out.println("Connect done...");
        //ГСАДн
        List<OP> listOP = myConnector.getDataDatabaseOP("minometu");
        System.out.println("Список ОП с БД прочтен...");

        PoligonPointMaker pointMaker = new PoligonPointMaker();

        cleanDirectory("D:\\YO_NA\\MakePoligon2\\GSADn\\");

        for (OP op : listOP) {
            pointMaker.setOp(op);
            new MyPoligon("D:\\YO_NA\\MakePoligon2\\GSADn\\", op.getName(), "BF10DA8B"
                    , "BF10DA8B", pointMaker.getListOPPoint());
        }
        System.out.println("ГСАДн отработано...");

        //АДн
        List<OP> listOP2 = myConnector.getDataDatabaseOP("angara");
        PoligonPointMaker pointMaker2 = new PoligonPointMaker();

        cleanDirectory("D:\\YO_NA\\MakePoligon2\\ADn\\");

        for (OP op : listOP2) {
            pointMaker2.setOp(op);
            new MyPoligon("D:\\YO_NA\\MakePoligon2\\ADn\\", op.getName(), "BF340CFF"
                    , "BF340CFF", pointMaker2.getListOPPoint());
        }
        System.out.println("АДн отработано...");

        //РЕАДн
        List<OP> listOP3 = myConnector.getDataDatabaseOP("baikal");
        PoligonPointMaker pointMaker3 = new PoligonPointMaker();

        cleanDirectory("D:\\YO_NA\\MakePoligon2\\READn");

        for (OP op : listOP3) {
            pointMaker3.setOp(op);
            new MyPoligon("D:\\YO_NA\\MakePoligon2\\READn\\", op.getName(), "D89D1B8C"
                    , "D89D1B8C", pointMaker3.getListOPPoint());
        }
        System.out.println("РЕАДн отработано...");
        System.out.println("Генерация секторов успешно!!!");
        pausa();
    }

    public static void pausa() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MyConnector.class.getName()).info("Shlapa: " + ex.getMessage());
        }
    }

    private static void cleanDirectory(String dirName) {
        Path pathToDelete = Paths.get(dirName);
        try {
            Files.walkFileTree(pathToDelete, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (exc != null) throw exc;
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(MyConnector.class.getName()).info("Shlapa: " + ex.getMessage());
        }
    }
}