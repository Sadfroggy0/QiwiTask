package org.example.Entities;

public class Currency {
    private String valuteID;
    private String numCode;
    private String CharCode;
    private int nominal;
    private String name;
    private double value;

    @Override
    public String toString() {
        return String.format("%s (%s): %.4f", CharCode, name, value);
    }

    public String getValuteID() {
        return valuteID;
    }

    public String getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return CharCode;
    }

    public void setCharCode(String charCode) {
        CharCode = charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setValuteID(String valuteID) {
        this.valuteID = valuteID;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }
}
