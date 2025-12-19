package com.example.sms.model;

public class Student {
    private int id;
    private String name;
    private double gpa;
    private String year;
    private String department;

    public Student() {
    }

    public Student(int id, String name, double gpa, String year, String department) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.year = year;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gpa=" + gpa +
                ", year='" + year + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
