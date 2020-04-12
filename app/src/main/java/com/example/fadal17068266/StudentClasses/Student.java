package com.example.fadal17068266.StudentClasses;

import java.io.Serializable;

public class Student implements Serializable {
    private int ID;
    private String firstName;
    private String lastName;
    private int age;
    private int moduleID;
    private byte[] image;

    public Student(int ID, String firstName, String lastName, int age, int moduleID, byte[] img) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.moduleID = moduleID;
        this.image = img;
    }

    public Student(String firstName, String lastName, int age, int moduleID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.moduleID = moduleID;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
