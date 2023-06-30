package com.example.resumebuilder;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ResumeBuilderGUI extends Application {

    private TextField nameField;
    private TextField ageField;
    private TextField emailField;
    private TextField phoneField;
    private TextArea objectiveArea;
    private TextArea educationArea;
    private TextArea experienceArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Resume Builder");
        Label labelTitle = new Label("Resume Builder");
        labelTitle.setTextFill(Color.DARKRED);
        labelTitle.setFont(Font.font("Verdana" , FontWeight.BOLD , FontPosture.ITALIC, 28));

        // Create UI controls
        Label nameLabel = new Label("Name:");
        nameField = new TextField();

        Label ageLabel = new Label("Age:");
        ageField = new TextField();

        Label emailLabel = new Label("Email:");
        emailField = new TextField();

        Label phoneLabel = new Label("Phone:");
        phoneField = new TextField();

        Label objectiveLabel = new Label("Objective:");
        objectiveArea = new TextArea();

        Label educationLabel = new Label("Education:");
        educationArea = new TextArea();

        Label experienceLabel = new Label("Experience:");
        experienceArea = new TextArea();

        Button generateButton = new Button("Generate PDF");
        generateButton.setOnAction(e -> generatePDF());

        // Create a layout and add UI controls
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(nameField, 1, 0);

        GridPane.setConstraints(ageLabel, 0, 1);
        GridPane.setConstraints(ageField, 1, 1);

        GridPane.setConstraints(emailLabel, 0, 2);
        GridPane.setConstraints(emailField, 1, 2);

        GridPane.setConstraints(phoneLabel, 0, 3);
        GridPane.setConstraints(phoneField, 1, 3);

        GridPane.setConstraints(objectiveLabel, 0, 4);
        GridPane.setConstraints(objectiveArea, 1, 4);

        GridPane.setConstraints(educationLabel, 0, 5);
        GridPane.setConstraints(educationArea, 1, 5);

        GridPane.setConstraints(experienceLabel, 0, 6);
        GridPane.setConstraints(experienceArea, 1, 6);

        GridPane.setConstraints(generateButton, 1, 7);

        gridPane.getChildren().addAll(
                labelTitle, nameLabel, nameField, ageLabel, ageField, emailLabel, emailField, phoneLabel, phoneField,
                objectiveLabel, objectiveArea, educationLabel, educationArea, experienceLabel, experienceArea,
                generateButton
        );

        VBox vBox = new VBox(labelTitle,gridPane);
        Scene scene = new Scene(vBox, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void generatePDF() {
        ResumeBuilder resumeBuilder = new ResumeBuilder();
        CV cv = new CV();
        cv.setName(nameField.getText());
        cv.setAge(Integer.parseInt(ageField.getText()));
        cv.setEmail(emailField.getText());
        cv.setPhone(Integer.parseInt(phoneField.getText()));
        cv.setObjective(objectiveArea.getText());

        ArrayList<Education> educationList = new ArrayList<>();
        String[] educationEntries = educationArea.getText().split("\n");
        for (String entry : educationEntries) {
            String[] parts = entry.split(",");
            String school = parts[0].trim();
            String degree = parts[1].trim();
            String year = parts[2].trim();
            educationList.add(new Education(school, degree, year));
        }
        cv.setEducation(educationList);

        ArrayList<Experience> experienceList = new ArrayList<>();
        String[] experienceEntries = experienceArea.getText().split("\n");
        for (String entry : experienceEntries) {
            String[] parts = entry.split(",");
            String company = parts[0].trim();
            String position = parts[1].trim();
            String start_date = parts[2].trim();
            String end_date = parts[3].trim();
            experienceList.add(new Experience(company, position, start_date, end_date));
        }
        cv.setExperience(experienceList);
        // Generate the PDF
        resumeBuilder.generatePDF(cv);

        // Show a success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resume Builder");
        alert.setHeaderText(null);
        alert.setContentText("PDF generated successfully!");
        alert.showAndWait();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
