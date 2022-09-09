package com.spring_batch.model;

public class StudentCSV {

    private String first;
    private String last;
    private Double GPA;
    private int AGE;

    public StudentCSV() {
    }

    @Override
    public String toString() {
        return "StudentCSV{" +
                "first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", GPA=" + GPA +
                ", AGE=" + AGE +
                '}';
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Double getGPA() {
        return GPA;
    }

    public void setGPA(Double GPA) {
        this.GPA = GPA;
    }

    public int getAGE() {
        return AGE;
    }

    public void setAGE(int AGE) {
        this.AGE = AGE;
    }
}
