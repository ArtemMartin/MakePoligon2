package org.example;

public class OP {
    private final String sistema;
    private final String name;
    private final int turnLeft;
    private final int turnRight;
    private final double x;
    private final double y;
    private final int on;
    private String zariad = null;

    public OP(String sistema, String name, int turnLeft, int turnRight, double x, double y, int on) {
        this.sistema = sistema;
        this.name = name;
        this.turnLeft = turnLeft;
        this.turnRight = turnRight;
        this.x = x;
        this.y = y;
        this.on = on;
    }

    public int getOn() {
        return on;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getSistema() {
        return sistema;
    }

    public String getName() {
        return name;
    }

    public String getZariad() {
        return zariad;
    }

    public void setZariad(String zariad) {
        this.zariad = zariad;
    }

    public int getTurnLeft() {
        return turnLeft;
    }

    public int getTurnRight() {
        return turnRight;
    }
}
