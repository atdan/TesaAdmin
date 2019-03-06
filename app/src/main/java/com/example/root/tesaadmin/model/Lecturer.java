package com.example.root.tesaadmin.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Lecturer {

    String name, email, officeAddress, phoneNumber, degree, level, department, imageUrl;



    public Lecturer(String name, String email, String officeAddress, String phoneNumber, String degree,
                    String level, String department, String imageUrl) {
        this.name = name;
        this.email = email;
        this.officeAddress = officeAddress;
        this.phoneNumber = phoneNumber;
        this.degree = degree;
        this.level = level;
        this.department = department;
        this.imageUrl = imageUrl;
    }

    public Lecturer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name",name);
        result.put("email",email);
        result.put("officeAddress",officeAddress);
        result.put("phoneNumber",phoneNumber);
        result.put("degree",degree);
        result.put("level", level);
        result.put("department",department);
        result.put("imageUrl", imageUrl);



        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lecturer)) return false;
        Lecturer lecturer = (Lecturer) o;
        return Objects.equals(getName(), lecturer.getName()) &&
                Objects.equals(getEmail(), lecturer.getEmail()) &&
                Objects.equals(getOfficeAddress(), lecturer.getOfficeAddress()) &&
                Objects.equals(getPhoneNumber(), lecturer.getPhoneNumber()) &&
                Objects.equals(getDegree(), lecturer.getDegree()) &&
                Objects.equals(getLevel(), lecturer.getLevel()) &&
                Objects.equals(getDepartment(), lecturer.getDepartment()) &&
                Objects.equals(getImageUrl(), lecturer.getImageUrl());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getEmail(), getOfficeAddress(), getPhoneNumber(), getDegree(), getLevel(), getDepartment(), getImageUrl());
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", officeAddress='" + officeAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", degree='" + degree + '\'' +
                ", level='" + level + '\'' +
                ", department='" + department + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
