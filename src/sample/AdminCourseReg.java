package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eddylloyd on 10/25/16.
 */
public class AdminCourseReg extends Application {

    Connection Conn;
    @Override
    public void start(Stage primaryStage) throws Exception {

        Conn = ConnectionClass.Connector();

        BorderPane borderPane = new BorderPane();

        Label headerText = new Label("ADMINISTRATOR COURSE REGISTARTION");
        headerText.setId("headerText");


        // BORDERPANE CENTER FOR COURE REGISTRATION

        Label lRegNo = new Label("Reg No :");
        lRegNo.setId("whiteLabels");



        //DEAL WITH THE PROGRAM SELECTION COMBOBOX

        Map<String, String> programMap = new HashMap<>();// Creating a Map with key-value (Program Name , Program Code)
        ObservableList<String> programs = FXCollections.<String>observableArrayList();

        try{

            String query = "SELECT * FROM program";
            ResultSet rs = Conn.createStatement().executeQuery(query);
            while (rs.next())
            {
                String programName = rs.getString("Program_name");

                String programCode = rs.getString("Program_code");

                programMap.put(programName, programCode);//Seed the Map

                programs.add(programName);// Seed the Combobox ObservableList
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        ComboBox<String> cbprogram = new ComboBox<>(programs);

        cbprogram.setValue("Computer Science");


        Label lsem = new Label("Semeseter");

        ObservableList<String> semesters = FXCollections.<String>observableArrayList(
                "Semester One", "Semester Two", "Semester Three",
                "Semester Four", "Semester Five","Semester Six"
        );

        ComboBox<String> csem = new ComboBox<>(semesters);

        csem.setValue("Semester One");


        Label lCourseUnit = new Label("Course Unit : ");
        lCourseUnit.setId("whiteLabels");


        TextField tRegNo = new TextField();


        List<String> regNoSuggestions = new ArrayList<>();

        // Build The Suggestion Base
        try{

            String query = "SELECT RegNo FROM student";
            ResultSet rs = Conn.createStatement().executeQuery(query);
            while (rs.next())
            {
                String RegNo = rs.getString("RegNo");

                regNoSuggestions.add(RegNo);

            }



        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        TextFields.bindAutoCompletion(tRegNo,regNoSuggestions);

        TextField tCourseUnit = new TextField();

        List<String> courseSuggestions = new ArrayList<>();

        // Build The Suggestion Base
        try{

            String query = "SELECT  course_name FROM course_unit";
            ResultSet rs = Conn.createStatement().executeQuery(query);
            while (rs.next())
            {
                String courseName = rs.getString("course_name");
                courseSuggestions.add(courseName);

            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        TextFields.bindAutoCompletion(tCourseUnit, courseSuggestions);


        Button bregcourses = new Button("Regitser Courses");


        /* CREATING LEFTS ELEMENTS */

        Button btnLeftHome = new Button("Admin Home");
        btnLeftHome.setPadding(new Insets(20));
        btnLeftHome.setPrefSize(200, 80);

        Button btnleftregcourses = new Button("Register Courses");
        btnleftregcourses.setPadding(new Insets(20));
        btnleftregcourses.setPrefSize(200, 80);


        Button btnleftregretakes = new Button("Register Retakes");
        btnleftregretakes.setPadding(new Insets(20));
        btnleftregretakes.setPrefSize(200, 80);



        Button btnleftEnterMarks = new Button("Enter Marks");//Button to Present Interface for Entering Marks
        btnleftEnterMarks.setPadding(new Insets(20));
        btnleftEnterMarks.setPrefSize(200, 80);

        Button btnLeftLogOut = new Button("Log Out");//Button to Log Out
        btnLeftLogOut.setPadding(new Insets(20));
        btnLeftLogOut.setPrefSize(200, 80);
        VBox.setMargin(btnLeftLogOut, new Insets(10));



        VBox left = new VBox();
        left.setId("left");
        left.setPrefSize(200, 0);
        left.setStyle("-fx-background-color:#197;");
        left.getChildren().addAll(btnLeftHome, btnleftregcourses, btnleftregretakes, btnleftEnterMarks, btnLeftLogOut);
        VBox.setMargin(btnLeftHome, new Insets(10));
        VBox.setMargin(btnleftregcourses, new Insets(10));
        VBox.setMargin(btnleftregretakes, new Insets(10));
        VBox.setMargin(btnleftEnterMarks, new Insets(10));

        Label lerror = new Label();// Label For Displaying Error Messages
        lerror.setId("lerror");






        /* COURSE REGISTRATION : CREATION OF THE GRIDPANE AND CONFIGURATIONS*/

        GridPane gridPaneRegCourse = new GridPane();
        gridPaneRegCourse.setPrefSize(350, 450);

        gridPaneRegCourse.setId("ac_gripane");
        gridPaneRegCourse.setPadding(new Insets(10,0,10,10));
        gridPaneRegCourse.setVgap(20);
        gridPaneRegCourse.setHgap(20);
        gridPaneRegCourse.setAlignment(Pos.TOP_CENTER);

        GridPane.setHalignment(lRegNo, HPos.LEFT);
        gridPaneRegCourse.add(lRegNo, 0, 0);

        GridPane.setHalignment(lCourseUnit, HPos.LEFT);
        gridPaneRegCourse.add(lCourseUnit, 0, 3);


        GridPane.setHalignment(tRegNo, HPos.LEFT);
        gridPaneRegCourse.add(tRegNo, 1, 0);
;
        GridPane.setHalignment(tCourseUnit, HPos.LEFT);
        gridPaneRegCourse.add(tCourseUnit, 1, 3);
;


        GridPane.setHalignment(bregcourses, HPos.LEFT);
        gridPaneRegCourse.add(bregcourses, 1, 5);





        /*
         *    INSTANTIATING DIFFERENT WINDOW ELEMENTS
         */
        FlowPane centerRegCourse = new FlowPane();
        centerRegCourse.setId("centerRegCourse");
        centerRegCourse.setAlignment(Pos.CENTER);
        centerRegCourse.getChildren().addAll(gridPaneRegCourse);

        centerRegCourse.setId("center");


        FlowPane header = new FlowPane();
        header.setPrefSize(0, 50);
        headerText.setId("ac_header");
        headerText.setAlignment(Pos.CENTER);
        header.getChildren().add(headerText);

        borderPane.setTop(header);

        borderPane.setLeft(left);

        borderPane.setCenter(centerRegCourse);


        // SET EFFECTS AND STYLES TO LEFTS BUTTONS

        Shared.styleBtn(btnLeftLogOut);
        Shared.styleBtn(btnLeftHome);
        Shared.styleBtn(btnleftEnterMarks);
        Shared.styleBtn(btnleftregcourses);
        Shared.styleBtn(btnleftregretakes);
        Shared.styleBtn(bregcourses);


        Scene scene = new Scene(borderPane,750,550);
        scene.getStylesheets().add("sample/style.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Academic Registrar");
        primaryStage.show();


        // PROVIDE AN AUTOCOMPLETE FOR BOTH REG NUMBERS AND COURSE UNITS


        /**
         ** HANDLE REG COURSE BUTTON EVENT
         **/
        // First Handle The Dialogs Stuffs
        Alert alertError = new Alert(Alert.AlertType.WARNING);
        alertError.setTitle("ALERT WARNING");
        alertError.setHeaderText(null);

        bregcourses.setOnAction(event ->
        {
            // Validate RegNo
            if((tRegNo.getText()).equals("")) {
                alertError.setContentText("\nThe Field \" Reg No \"" +
                        "Is Required .....");
                alertError.showAndWait();

                return;
            }

            // Validate RegNo
            if((tCourseUnit.getText()).equals("")) {
                alertError.setContentText("\nThe Field \"Course Unit\" Is Required .....");
                alertError.showAndWait();

                return;
            }


            // Check whether the Course unit entered exists in the system

            String query1 = "SELECT course_code FROM course_unit WHERE course_name = ?";
            String courseCode = "Not Found";// Provide default coursecode name

            try {
                PreparedStatement pst = Conn.prepareStatement(query1);
                pst.setString(1, tCourseUnit.getText());
                ResultSet rs = pst.executeQuery();

                while (rs.next()){ courseCode = rs.getString("course_code");
                //System.out.print(courseCode);
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Check whether the RegNo entered exists in the system

            String query2 = "SELECT RegNo FROM student WHERE RegNo = ?";
            String StrRegNumber = "Not Found RegNo";// Provide default RegNo
            try {
                PreparedStatement pst = Conn.prepareStatement(query2);
                pst.setString(1, tRegNo.getText());
                ResultSet rs = pst.executeQuery();

                while (rs.next()){ StrRegNumber = rs.getString("RegNo");
                    // Just To Get The RegNo
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }

            // If The RegNo entered is not yet registered in the system

            if(StrRegNumber.equals("Not Found RegNo"))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ALERT ERROR");
                alert.setHeaderText(null);
                alert.setContentText("The Registration Number \""+tRegNo.getText()+"\"\nWas Not Found In The System\n" +
                        "Please Try Again ");
                alert.showAndWait();
                return;
            }


            // If The Course Entered Is not yet Registered Kill The Program And The Dialog Message
            if (courseCode.equals("Not Found"))
            {
                //JOptionPane.showMessageDialog(null, "Course Unit Not Found In The System");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ALERT ERROR");
                alert.setHeaderText(null);
                alert.setContentText("The Course Unit \""+tCourseUnit.getText()+"\"Was Not Found In The System" +
                        "\nPlease Try Again");
                alert.showAndWait();
                return;
            }


            String query3 = "INSERT INTO taken_courses(SRegNo, course_code) values(?, ?)";
            try {
                PreparedStatement pst2 = Conn.prepareStatement(query3);
                pst2.setString(1, tRegNo.getText());
                pst2.setString(2, courseCode);
                pst2.execute();

                //Alter Info
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ALERT INFORMATION");
                alert.setHeaderText(null);
                alert.setContentText("The Course Unit "+tCourseUnit.getText()+" has been successfully \n registered"+
                "for student "+tRegNo.getText());
                alert.showAndWait();
                // Clear Input Fields After a Successfull Registration
                tRegNo.setText("");
                tCourseUnit.setText("");


            } catch (SQLException e) {
                e.printStackTrace();
            }

        });


        /*
         * HANDLE EVENTS ON DIFFERENT LEFT BUTTONS
         */

        btnleftregcourses.setOnMouseClicked(event
                ->{
            try {
                new AdminCourseReg().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } );

        btnleftregretakes.setOnMouseClicked(event ->
        {
            try {
                new AdminRetakeReg().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnleftEnterMarks.setOnAction(event ->
        {
            try {
                new AdminMarksReg().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnLeftHome.setOnAction(event ->
        {
            try {
                new Admin().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnLeftLogOut.setOnAction(event ->
        {
            try {
                new Login().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



    }

    public  static  void main(String[] args)
    {
        launch();
    }
}
