package com.example.resumebuilder;

public class Education {
    String school;
    String Grade;
    String year;

    public Education(String school, String grade, String year) {
        this.school = school;
        Grade = grade;
        this.year = year;
    }



    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Education{" +
                "school='" + school + '\'' +
                ", Grade='" + Grade + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
