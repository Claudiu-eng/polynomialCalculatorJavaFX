package com.example.tema1.model;

public class Monom implements Comparable {

    private double coef;
    private int power;
    private boolean status;

    public Monom(double coef, int power,boolean status) {
        this.coef = coef;
        this.power = power;
        this.status=status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getCoef() {
        return coef;
    }

    public void setCoef(double coef) {
        this.coef = coef;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void add(Monom m) {
        this.coef += m.getCoef();
    }

    @Override
    public int compareTo(Object o) {
        Monom s = (Monom) o;
        if (this.getPower() > s.getPower())
            return -1;
        return 1;
    }
}
