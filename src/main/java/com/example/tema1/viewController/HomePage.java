package com.example.tema1.viewController;

import com.example.tema1.model.Monom;
import com.example.tema1.model.Polynom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomePage {
    
    @FXML
    private TextField firstPolynom,secondPolynom;
    @FXML
    private TextArea answer;
    @FXML
    private CheckBox secondPolValue;


    int p1=-100,p2=-100;
    private void showAlertWithHeaderText(String p) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test Connection");
        alert.setHeaderText("Results:");
        alert.setContentText(p);

        alert.showAndWait();
    }

    public void addButtonOnAction(ActionEvent e){
        Polynom a=Polynom.buildPolynom(firstPolynom.getText());
        Polynom b=Polynom.buildPolynom(secondPolynom.getText());
        a.reduce(a);
        b.reduce(b);
        a=a.add_substract(a,b,1);
        Polynom.print(a,null,answer,"Suma = ","");
    }
    public void substractButtonOnAction(ActionEvent e){
        Polynom a=Polynom.buildPolynom(firstPolynom.getText());
        Polynom b=Polynom.buildPolynom(secondPolynom.getText());
        a.reduce(a);
        b.reduce(b);
        a=a.add_substract(a,b,-1);
        Polynom.print(a,null,answer,"Diferenta = ","");
    }
    public void multiplyButtonOnAction(ActionEvent e){
        Polynom a=Polynom.buildPolynom(firstPolynom.getText());
        Polynom b=Polynom.buildPolynom(secondPolynom.getText());
        a.reduce(a);
        b.reduce(b);
        Polynom rez=a.multiply(a,b);
        Polynom.print(rez,null,answer,"Produsul = ","");
    }
    public void divideButtonOnAction(ActionEvent e){
        Polynom a=Polynom.buildPolynom(firstPolynom.getText());
        Polynom b=Polynom.buildPolynom(secondPolynom.getText());
        a.reduce(a);
        b.reduce(b);
        if(b.getMonoms().size()==0)
            showAlertWithHeaderText("Imposibble to div at 0 (/0)");
        else {
            Polynom cat = new Polynom(new ArrayList<>()), rest = new Polynom(new ArrayList<>());
            rest = rest.divide(a, b, cat, rest);
            Polynom.print(cat, rest, answer, "Catul = ", "Restul = ");
        }
    }
    public void derivateButtonOnAction(ActionEvent e){
        Polynom a=Polynom.buildPolynom(firstPolynom.getText());
        Polynom b=Polynom.buildPolynom(secondPolynom.getText());
        a.reduce(a);
        b.reduce(b);
        Polynom rez1=a.derivate();
        Polynom rez2=b.derivate();
        Polynom.print(rez1,rez2,answer,"Derivata1 = ","Derivata2 = ");
    }
    public void integrateButtonOnAction(ActionEvent e){
        Polynom a=Polynom.buildPolynom(firstPolynom.getText());
        Polynom b=Polynom.buildPolynom(secondPolynom.getText());
        a.reduce(a);
        b.reduce(b);
        Polynom rez1=a.integrate();
        Polynom rez2=b.integrate();
        Polynom.print(rez1,rez2,answer,"Integrala1 = ","Integrala2 = ");

    }

    public boolean validate(String a){
        if(!a.contains("^"))
            return true;
        String []parts=a.split("\\^");
        String ultimul=parts[parts.length-1];
        if(ultimul.contains("+") ||ultimul.contains("-"))
            return true;
        return false;
    }

    public void showSymbolButtonOnAction(ActionEvent e){
        String src=e.getSource().toString();
        String[] p=src.split("\'");
        String s=p[1];
        boolean t=!secondPolValue.isSelected();
        String a=firstPolynom.getText();
        if(!t)
            a= secondPolynom.getText();
        if (s.equals("del")) {
            if (a.equals("")) {
                showAlertWithHeaderText("Stergere imposibila");
                if(t)
                    p1=-1;
                else p2=-1;
                return;
            }
            if (!a.equals("") && a.charAt(a.length() - 1) == '^')
                if (t)
                    p1 = 0;
                else p2 = 0;
            if ("+-".contains(String.valueOf(a.charAt(a.length() - 1))))
                if (t)
                    p1 = -1;
                else p2 = -1;
            if (!a.equals(""))
                if (t)
                   firstPolynom.setText(a.substring(0, a.length() - 1));
                else secondPolynom.setText(a.substring(0, a.length() - 1));

        } else if (!a.equals("") && a.charAt(a.length() - 1) == 'x' && !("^+-".contains(s)))
            showAlertWithHeaderText("Dupa x se pune +,- sau ^");
        else if (!a.equals("") && a.charAt(a.length() - 1) == '^' && !("-0123456789".contains(s)))
            showAlertWithHeaderText("Trebuie introdusa o cifra pozitiva sau negegativa");
        else if (a.length() >= 2 && a.charAt(a.length() - 2) == '^' && !("0123456789".contains(s)) && a.charAt(a.length() - 1) == '-')
            showAlertWithHeaderText("Introdu o cifra");
        else if (!a.equals("") && "0123456789".contains(String.valueOf(a.charAt(a.length() - 1))) && s.equals("^"))
            showAlertWithHeaderText("dupa cifra nu se pune ^");
        else if (a.equals("") && !("-1234567890x".contains(s)))
            showAlertWithHeaderText("Nu asa se incepe un polinom");
        else if(s.equals("^") && a.length()>=1 && a.charAt(a.length()-1)!='x')
            showAlertWithHeaderText("^ se pune doar dupa x");
        else if(!a.equals("")&&"+-".contains(s) && (a.charAt(a.length()-1)=='+'||a.charAt(a.length()-1)=='-'))
            showAlertWithHeaderText("2 semne langa olalta");
        else if(!validate(a) && s.equals("x"))
            showAlertWithHeaderText("la putere nu poti pune x");
        else {
            if ("^x".contains(s) && t) {
                if (p1 == 1) {
                    showAlertWithHeaderText("nu se pune asa ceva la exp");
                    return;
                }
            } else if ("x^".contains(s) && !t) {
                if (p2 == 1) {
                    showAlertWithHeaderText("nu se pune asa ceva la exp");
                    return;
                }
            }
            if ("0123456789x+-^".contains(s)) {
                if (t)
                    firstPolynom.setText(firstPolynom.getText() + s);
                else secondPolynom.setText(secondPolynom.getText() + s);
                if ("+-".contains(s) && a.length()>1 &&a.charAt(a.length()-1)!='^')
                    if (t)
                        p1 = -1;
                    else p2 = -1;
            }
            if (s.equals("^"))
                if (t)
                    p1 = 1;
                else p2 = 1;
        }
    }

}
