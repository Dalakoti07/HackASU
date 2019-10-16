package com.myproject.keeplearn;

import java.io.Serializable;

public class Course implements Serializable {
    private String title,imageUrl,id,InstructorId,CourseDescription;

    public Course(String id, String title, String imageUrl, String instructorId, String courseDescription) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.id = id;
        InstructorId = instructorId;
        CourseDescription = courseDescription;
    }

    public String getTitle() {
        return this.title;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getId() {
        return this.id;
    }

    public String getInstructorId() {
        return this.InstructorId;
    }

    public String getCourseDescription() {
        return this.CourseDescription;
    }
}
