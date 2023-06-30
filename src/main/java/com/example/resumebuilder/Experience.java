package com.example.resumebuilder;

import java.util.ArrayList;

public class Experience {
    String position;
    String company;

    String start_date;
    String end_date;
    ArrayList <String> responsibilities;


    public Experience(String position, String company, String start_date, String end_date) {
        this.position = position;
        this.company = company;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public ArrayList<String> getResponsibilities() {
        return responsibilities;
    }


    public void setResponsibilities(ArrayList<String> responsibilities) {
        this.responsibilities = responsibilities;
    }


    @Override
    public String toString() {
        return "Experience{" +
                "position='" + position + '\'' +
                ", comany='" + company + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", responsibilities=" + responsibilities +
                '}';
    }
}
