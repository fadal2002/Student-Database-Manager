package com.example.fadal17068266;

//a class for the student and their assigned modules
public class Studmod {
    private int studentID;
    private String studentName;
    private String moduleName;
    private int moduleID;
    private byte[] studentImg;

    public Studmod(int studentID, String studentName, String moduleName, int moduleID, byte[] studentImg) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.moduleName = moduleName;
        this.moduleID = moduleID;
        this.studentImg = studentImg;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public byte[] getStudentImg() {
        return studentImg;
    }

    public void setStudentImg(byte[] studentImg) {
        this.studentImg = studentImg;
    }
}
