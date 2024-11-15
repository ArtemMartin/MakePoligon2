package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MyConnector {
    private static Statement statement;

    public MyConnector(String adress) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + adress + ":5059/bp1",
                    "artem",
                    "95123@Arsen");
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(MyConnector.class.getName()).info("Shlapa: " + ex.getMessage());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Logger.getLogger(MyConnector.class.getName()).info("Shlapa: " + ex.getMessage());
            }
        }
    }

    public List<OP> getDataDatabaseOP(String nameTableDB) {
        List<OP> listOP = new ArrayList<>();
        String sqlComandOP = "SELECT*FROM " + nameTableDB;
        try (ResultSet resultsetOP = statement.executeQuery(sqlComandOP)) {
            while (resultsetOP.next()) {
                listOP.add(new OP(resultsetOP.getString(3),
                        resultsetOP.getString(2),
                        resultsetOP.getInt(8),
                        resultsetOP.getInt(9),
                        resultsetOP.getInt(4),
                        resultsetOP.getInt(5),
                        resultsetOP.getInt(7)));
            }
        } catch (SQLException e) {
            Logger.getLogger(MyConnector.class.getName()).info("Shlapa: " + e.getMessage());
        }
        return listOP;
    }
}
