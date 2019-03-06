package com.example.root.tesaadmin.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Faculty {

    String name, email,  phoneNumber,  level, department, post, imageUrl;

    public Faculty() {
    }

    public Faculty(String name, String email, String phoneNumber, String level, String department, String post, String imageUrl) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.level = level;
        this.department = department;
        this.post = post;
        this.imageUrl = imageUrl;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
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
        result.put("phoneNumber",phoneNumber);
        result.put("level", level);
        result.put("department",department);
        result.put("post",post);
        result.put("imageUrl", imageUrl);

        return result;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty)) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(getName(), faculty.getName()) &&
                Objects.equals(getEmail(), faculty.getEmail()) &&
                Objects.equals(getPhoneNumber(), faculty.getPhoneNumber()) &&
                Objects.equals(getLevel(), faculty.getLevel()) &&
                Objects.equals(getDepartment(), faculty.getDepartment()) &&
                Objects.equals(getPost(), faculty.getPost()) &&
                Objects.equals(getImageUrl(), faculty.getImageUrl());
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", level='" + level + '\'' +
                ", department='" + department + '\'' +
                ", post='" + post + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getEmail(), getPhoneNumber(), getLevel(), getDepartment(), getPost(), getImageUrl());
    }
}
