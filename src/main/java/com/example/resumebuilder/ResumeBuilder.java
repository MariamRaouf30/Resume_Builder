package com.example.resumebuilder;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ResumeBuilder {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Resume";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Jesus_mr1";

    private static final Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font SECTION_TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    private static final Font CONTENT_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12);

    public static void generatePDF(CV cv) {
        String filePath = "resume.pdf"; // Relative file path
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Adding CV information to the PDF
            addTitle(document, "Curriculum Vitae");
            addEmptyLine(document, 2);
            addSectionTitle(document, "Personal Information");
            addContent(document, "Name: ", Font.BOLD, cv.getName());
            addContent(document, "Age: ", Font.NORMAL, String.valueOf(cv.getAge()));
            addContent(document, "Email: ", Font.NORMAL, cv.getEmail());
            addContent(document, "Phone: ", Font.NORMAL, String.valueOf(cv.getPhone()));
            addEmptyLine(document);
            addSectionTitle(document, "Objective");
            addContent(document, cv.getObjective());
            addEmptyLine(document);
            addSectionTitle(document, "Education");
            addEducationInformation(document, cv.getEducation());
            addEmptyLine(document);
            addSectionTitle(document, "Experience");
            addExperienceInformation(document, cv.getExperience());

            document.close();
            System.out.println("PDF generated successfully.");
            insertResumeData(cv);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertResumeData(CV cv) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // Insert into "education" table
            PreparedStatement educationStatement = connection.prepareStatement(
                    "INSERT INTO education (school, grade, year) VALUES (?, ?, ?)"
            );
            for (Education education : cv.getEducation()) {
                educationStatement.setString(1, education.getSchool());
                educationStatement.setString(2, education.getGrade());
                educationStatement.setString(3, education.getYear());
                educationStatement.executeUpdate();
            }

            // Insert into "experience" table
            PreparedStatement experienceStatement = connection.prepareStatement(
                    "INSERT INTO experience (position, company, start_date, end_date) VALUES (?, ?, ?, ?)"
            );
            for (Experience experience : cv.getExperience()) {
                experienceStatement.setString(1, experience.getPosition());
                experienceStatement.setString(2, experience.getCompany());
                experienceStatement.setString(3, experience.getStart_date());
                experienceStatement.setString(4, experience.getEnd_date());
                experienceStatement.executeUpdate();
            }

            // Insert into "resumes" table
            PreparedStatement resumeStatement = connection.prepareStatement(
                    "INSERT INTO resumes (name, age, email, phone, objective, education_id, experience_id) " +
                            "VALUES (?, ?, ?, ?, ?, CURRVAL('education_id_seq'), CURRVAL('experience_id_seq'))"
            );
            resumeStatement.setString(1, cv.getName());
            resumeStatement.setInt(2, cv.getAge());
            resumeStatement.setString(3, cv.getEmail());
            resumeStatement.setInt(4, cv.getPhone());
            resumeStatement.setString(5, cv.getObjective());
            resumeStatement.executeUpdate();

            System.out.println("Data inserted into the database successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addTitle(Document document, String text) throws DocumentException {
        Paragraph title = new Paragraph(text, TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
    }

    private static void addSectionTitle(Document document, String text) throws DocumentException {
        Paragraph sectionTitle = new Paragraph(text, SECTION_TITLE_FONT);
        sectionTitle.setAlignment(Element.ALIGN_LEFT);
        document.add(sectionTitle);
    }

    private static void addContent(Document document, String label, int style, String text) throws DocumentException {
        Paragraph content = new Paragraph();
        Chunk labelChunk = new Chunk(label, new Font(Font.FontFamily.TIMES_ROMAN, 12, style));
        Chunk textChunk = new Chunk(text, CONTENT_FONT);
        content.add(labelChunk);
        content.add(textChunk);
        document.add(content);
    }

    private static void addContent(Document document, String text) throws DocumentException {
        Paragraph content = new Paragraph(text, CONTENT_FONT);
        document.add(content);
    }

    private static void addEmptyLine(Document document) throws DocumentException {
        document.add(new Paragraph(" "));
    }

    private static void addEmptyLine(Document document, int lines) throws DocumentException {
        for (int i = 0; i < lines; i++) {
            addEmptyLine(document);
        }
    }

    private static void addEducationInformation(Document document, ArrayList<Education> educationList)
            throws DocumentException {
        for (Education education : educationList) {
            addContent(document, "School: ", Font.BOLD, education.getSchool());
            addContent(document, "Grade: ", Font.NORMAL, education.getGrade());
            addContent(document, "Year: ", Font.NORMAL, String.valueOf(education.getYear()));
            addEmptyLine(document);
        }
    }

    private static void addExperienceInformation(Document document, ArrayList<Experience> experienceList)
            throws DocumentException {
        for (Experience experience : experienceList) {
            addContent(document, "Company: ", Font.BOLD, experience.getCompany());
            addContent(document, "Position: ", Font.NORMAL, experience.getPosition());
            addContent(document, "Start Date: ", Font.NORMAL, experience.getStart_date());
            addContent(document, "End Date: ", Font.NORMAL, experience.getEnd_date());
            addEmptyLine(document);
        }
    }
}