package com.example.tema1.model;

import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynom {

    private ArrayList<Monom> monoms;

    public Polynom(ArrayList<Monom> monoms) {
        this.monoms = monoms;
    }

    public void reduce(Polynom t) {
        ArrayList<Monom> monoms = new ArrayList<>();
        for (Monom m : t.getMonoms()) {
            if (m.isStatus() && Math.abs(m.getCoef())>=1e-4) {
                monoms.add(m);
                m.setStatus(false);
                for (Monom p : t.getMonoms())
                    if (p.getPower() == m.getPower() && p.isStatus()) {
                        p.setStatus(false);
                        monoms.get(monoms.size() - 1).setCoef(p.getCoef() + monoms.get(monoms.size() - 1).getCoef());
                    }
            }
        }
        for (Monom m : monoms)
            m.setStatus(true);
        t.setMonoms(monoms);
    }

    public static void print(Polynom p, Polynom t, TextArea textArea,String a1,String a2) {
        String a = a1, k = a2;
        if (p != null) {
            for (Monom m : p.getMonoms()) {
                if (Math.abs(m.getCoef() - 1.0) >= 1e-4 || m.getPower() == 0) {
                    if (m.getCoef() > 0)
                        a = a + "+";
                    a = a + m.getCoef();
                } else if (m.getCoef() < 0)
                    a = a + "-";
                else if (m.getCoef() > 0)
                    a = a + "+";
                if (Math.abs(m.getPower()) >= 1e-4)
                    a = a + "x^" + m.getPower();
                a = a + " ";
            }
            if (a.charAt(a.length() - 1) != 'x' || !Character.isDigit(a.charAt(a.length() - 1)))
                a = a.substring(0, a.length() - 1);
        }
        if (t != null) {
            for (Monom m : t.getMonoms()) {
                if (Math.abs(m.getCoef() - 1.0) >= 1e-4 || m.getPower() == 0) {
                    if (m.getCoef() > 0)
                        k = k + "+";
                    k = k + m.getCoef();
                } else if (m.getCoef() < 0)
                    k = k + "-";
                else if (m.getCoef() > 0)
                    k = k + "+";
                if (Math.abs(m.getPower()) >= 1e-4)
                    k = k + "x^" + m.getPower();
                k = k + " ";
            }
            if (k.charAt(k.length() - 1) != 'x' || !Character.isDigit(k.charAt(k.length() - 1)))
                k = k.substring(0, k.length() - 1);
        }
        if(k.length()<=a2.length())
            k=k+"0";
        if(a.length()<=a1.length())
            a=a+"0";
        textArea.setText(a + "\n" + k);
    }

    public static Polynom buildPolynom(String a) {
        Pattern pattern = Pattern.compile("([-+]?(\\d+)?)?(x)?(\\^-?\\d+\\b)?");
        Matcher matcher = pattern.matcher(a);

        Polynom p = new Polynom(new ArrayList<>());
        while (matcher.find()) {
            if (matcher.group(2) != null) {
                double a1 = Double.parseDouble(matcher.group(1));
                if (matcher.group(4) != null) {
                    int a2 = Integer.parseInt(matcher.group(4).substring(1));
                    p.getMonoms().add(new Monom((int) a1, a2, true));
                } else if (matcher.group(3) != null)
                    p.getMonoms().add(new Monom((int) a1, 1, true));
                else p.getMonoms().add(new Monom((int) a1, 0, true));
            } else {
                int r = 1;
                if (matcher.group(1).equals("-"))
                    r = -1;
                if (matcher.group(4) != null) {
                    int a2 = Integer.parseInt(matcher.group(4).substring(1));
                    p.getMonoms().add(new Monom(r, (int) a2, true));
                } else if (matcher.group(3) != null)
                    p.getMonoms().add(new Monom(r, 1, true));
                else if (matcher.group(1).length() > 1) {
                    p.getMonoms().add(new Monom(r, 0, true));
                }
            }
        }
        return p;
    }

    public ArrayList<Monom> getMonoms() {
        return monoms;
    }

    public void setMonoms(ArrayList<Monom> monoms) {
        this.monoms = monoms;
    }

    @Override
    public String toString() {
        String a="";
        for(Monom m:monoms)
            a=a+(int)m.getCoef()+""+m.getPower();
        return a;
    }

    public void sortByExponent(ArrayList<Monom> s) {
        Collections.sort(s);
    }

    public Polynom add_substract(Polynom p, Polynom t, int sign) {
        Polynom result = new Polynom(new ArrayList<>());
        sortByExponent(t.getMonoms());
        sortByExponent(p.getMonoms());

        for (Monom m : p.getMonoms())
            for (Monom l : t.getMonoms())
                if (m.getPower() == l.getPower()) {
                    if (Math.abs(m.getCoef() + sign * l.getCoef()) >= 1e-4)
                        result.getMonoms().add(new Monom(m.getCoef() + sign * l.getCoef(), m.getPower(), true));
                    m.setStatus(false);
                    l.setStatus(false);
                }
        for (Monom m : p.getMonoms())
            if (m.isStatus() == true)
                result.getMonoms().add(m);
        for (Monom m : t.getMonoms())
            if (m.isStatus() == true)
                result.getMonoms().add(new Monom(sign * m.getCoef(), m.getPower(), true));
        reduce(result);
        sortByExponent(result.getMonoms());
        return result;
    }

    public Polynom multiply(Polynom a, Polynom b) {
        Polynom result = new Polynom(new ArrayList<>());
        sortByExponent(a.getMonoms());
        sortByExponent(b.getMonoms());
        for (Monom m : a.getMonoms())
            for (Monom p : b.getMonoms())
                result.getMonoms().add(new Monom(m.getCoef() * p.getCoef(), m.getPower() + p.getPower(), true));
        reduce(result);
        sortByExponent(result.getMonoms());
        return result;
    }

    public Polynom derivate() {
        Polynom result = new Polynom(new ArrayList<>());
        sortByExponent(getMonoms());
        for (Monom m : getMonoms())
            if (Math.abs(m.getPower()) >= 1e-4)
                result.getMonoms().add(new Monom(m.getCoef() * m.getPower(), m.getPower() - 1, true));
        return result;
    }
    public Polynom integrate(){
        Polynom result = new Polynom(new ArrayList<>());
        sortByExponent(getMonoms());
        for (Monom m : getMonoms())
            if (m.getPower()!=-1)
                result.getMonoms().add(new Monom(m.getCoef() /(m.getPower()+1), m.getPower() + 1, true));
        return result;
    }
    public Polynom divide(Polynom a,Polynom b,Polynom cat,Polynom rest){
        sortByExponent(a.getMonoms());
        sortByExponent(b.getMonoms());
        ArrayList<Monom> y=new ArrayList<>();
        for(Monom p:a.getMonoms())
            y.add(new Monom(p.getCoef(),p.getPower(),true));
        rest.setMonoms(y);
        while (rest !=null && rest.getMonoms().size()!=0 && rest.getMonoms().get(0).getCoef()!=0 && rest.getMonoms().get(0).getPower()>=b.getMonoms().get(0).getPower())
        {
            Monom t=new Monom(rest.getMonoms().get(0).getCoef()/b.getMonoms().get(0).getCoef(),rest.getMonoms().get(0).getPower()-b.getMonoms().get(0).getPower(),true);
            cat.getMonoms().add(t);
            Polynom intermediar1=new Polynom(new ArrayList<>());
            intermediar1.getMonoms().add(t);
            Polynom intermediar2=b.multiply(b,intermediar1);
            rest=rest.add_substract(rest,intermediar2,-1);
        }
        return rest;
    }
}
