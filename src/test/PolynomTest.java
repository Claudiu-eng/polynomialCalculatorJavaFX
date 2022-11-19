import com.example.tema1.model.Monom;
import com.example.tema1.model.Polynom;

import org.junit.Test;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PolynomTest {



    @Test
    public void add_substract() {
        Polynom a1= Polynom.buildPolynom("x^2+1");
        Polynom a2=Polynom.buildPolynom("x^3+1");
        Polynom a3=Polynom.buildPolynom("x^3+x^2+2");

        String rez=a3.toString();
        Polynom p=a1.add_substract(a1,a2,1);
        String rez2=p.toString();
        assertTrue(rez.equals(rez2));
        p=a1.add_substract(a1,a2,-1);
        a3=Polynom.buildPolynom("-x^3+x^2");
        rez=a3.toString();
        rez2=p.toString();
        assertTrue(rez.equals(rez2));
    }

    @Test
    public void multiply() {
        Polynom a1= Polynom.buildPolynom("x^2+1");
        Polynom a2=Polynom.buildPolynom("x^3+1");
        Polynom a3=Polynom.buildPolynom("x^5+x^3+x^2+1");

        String rez=a3.toString();
        Polynom p=a1.multiply(a1,a2);
        String rez2=p.toString();
        assertTrue(rez.equals(rez2));
    }

    @Test
    public void derivate() {
        Polynom a1= Polynom.buildPolynom("x^2+1");
        Polynom a3=Polynom.buildPolynom("2x");

        String rez=a3.toString();
        Polynom p=a1.derivate();
        String rez2=p.toString();
        assertTrue(rez.equals(rez2));
    }

    @Test
    public void integrate() {
        Polynom a1= Polynom.buildPolynom("3x^2+1");
        Polynom a3=Polynom.buildPolynom("x^3+x");

        String rez=a3.toString();
        Polynom p=a1.integrate();
        String rez2=p.toString();
        assertTrue(rez.equals(rez2));
    }

    @Test
    public void divide() {
        Polynom a2= Polynom.buildPolynom("x^2+1");
        Polynom a1=Polynom.buildPolynom("x^4-1");
        Polynom a3=Polynom.buildPolynom("x^2-1");

        Polynom cat = new Polynom(new ArrayList<>()), rest = new Polynom(new ArrayList<>());
        rest = rest.divide(a1, a2, cat, rest);

        String rez=a3.toString();
        String rez2=cat.toString();
        assertTrue(rez.equals(rez2));
    }
}