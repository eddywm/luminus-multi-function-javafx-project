package sample;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import java.util.List;

/**
 * Created by eddylloyd on 10/25/16.
 */
public class AdminMarksReg extends Application {

    public Connection Conn;
    double creditUnit;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();

        Conn = ConnectionClass.Connector();

        Label headerText = new Label("ADMINISTRATOR : MARKS ENTERING");
        headerText.setId("headerText");

        /*
            CREATING CENTER ELEMENTS
         */
        Label lRegNo = new Label("Reg No :");
        lRegNo.setId("whiteLabels");
        Label lCourseUnit = new Label("Course Unit :");
        lCourseUnit.setId("whiteLabels");
        Label lTotalMarks = new Label("Total Marks:");
        lTotalMarks.setId("whiteLabels");


        TextField txtCourseUnit = new TextField();
        TextField txtTotalMarks = new TextField();

        // Consume The KeyType Event If It is not Wanted
        txtTotalMarks.setOnKeyTyped(event ->
        {
            String str = event.getCharacter();
            if(!(str.matches("[0-9]||[\\.]"))) event.consume();
        });

        TextField txtRegNo = new TextField();

        //AArrayList To Hold The Suggestions
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

        TextFields.bindAutoCompletion(txtRegNo,regNoSuggestions);



        //ArrayList For Course Name Suggestions
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

        TextFields.bindAutoCompletion(txtCourseUnit, courseSuggestions);










        Button btnEnterMarks = new Button("Save Marks");


        GridPane gridPaneRegMarks = new GridPane();
                 gridPaneRegMarks.setAlignment(Pos.TOP_CENTER);
                 gridPaneRegMarks.setPrefSize(350,450);
                 gridPaneRegMarks.setVgap(20);
                 gridPaneRegMarks.setHgap(20);
                 gridPaneRegMarks.setPadding(new Insets(30));

                 gridPaneRegMarks.add(lRegNo, 0, 0);
                 GridPane.setHalignment(lRegNo, HPos.LEFT);
                 gridPaneRegMarks.add(txtRegNo,1,0);
                 GridPane.setHalignment(txtRegNo, HPos.LEFT);
                 gridPaneRegMarks.add(lCourseUnit,0,1);
                 GridPane.setHalignment(lCourseUnit, HPos.LEFT);
                 gridPaneRegMarks.add(txtCourseUnit,1,1);
                 GridPane.setHalignment(txtCourseUnit, HPos.LEFT);
                 gridPaneRegMarks.add(lTotalMarks,0,2);
                 GridPane.setHalignment(lTotalMarks, HPos.LEFT);
                 gridPaneRegMarks.add(txtTotalMarks,1,2);
                 GridPane.setHalignment(txtTotalMarks, HPos.LEFT);

                 gridPaneRegMarks.add(btnEnterMarks, 1,3);


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
        left.setPrefSize(200, 0);
        left.setId("left");
        left.getChildren().addAll(btnLeftHome,btnleftregcourses,btnleftregretakes, btnleftEnterMarks, btnLeftLogOut);
        VBox.setMargin(btnLeftHome, new Insets(10));
        VBox.setMargin(btnleftregcourses, new Insets(10));
        VBox.setMargin(btnleftregretakes, new Insets(10));

        VBox.setMargin(btnleftEnterMarks, new Insets(10));

        // SET EFFECTS AND STYLES TO LEFTS BUTTONS

        Shared.styleBtn(btnLeftLogOut);
        Shared.styleBtn(btnLeftHome);
        Shared.styleBtn(btnleftEnterMarks);
        Shared.styleBtn(btnleftregcourses);
        Shared.styleBtn(btnleftregretakes);
        Shared.styleBtn(btnEnterMarks);


        /*
         *    INSTANTIATING DIFFERENT WINDOW ELEMENTS
         */


        FlowPane header = new FlowPane();
        header.setPrefSize(0, 50);
        headerText.setId("ac_header");
        headerText.setAlignment(Pos.CENTER);
        header.getChildren().add(headerText);

        FlowPane centerRegMarks = new FlowPane();
                 centerRegMarks.setId("center");
                 centerRegMarks.setAlignment(Pos.CENTER);
                 centerRegMarks.getChildren().add(gridPaneRegMarks);


        borderPane.setTop(header);

        borderPane.setLeft(left);

        borderPane.setCenter(centerRegMarks);





        Scene scene = new Scene(borderPane,750,550);
        scene.getStylesheets().add("sample/style.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin -- Marks Registration");
        primaryStage.setResizable(false);
        primaryStage.show();

        // Handle The Marks Registrations events

        // First Handle Dialogs Messages
        Alert alertError = new Alert(Alert.AlertType.WARNING);
        alertError.setTitle("ALERT WARNING");
        alertError.setHeaderText(null);

        btnEnterMarks.setOnAction(event ->
        {
            // Validate RegNo
            if((txtRegNo.getText()).equals("")) {
                alertError.setContentText("\nThe Field \"Registration Number(RegNo)\" Is Required .....");
                alertError.showAndWait();

                return;
            }

            // Validate Course Unit
            if((txtCourseUnit.getText()).equals("")) {
                alertError.setContentText("\nThe Field \"Course Unit\" Is Required .....");
                alertError.showAndWait();

                return;
            }

            // Validate Marks
            if((txtTotalMarks.getText()).equals("")) {
                alertError.setContentText("\nThe Field \"Total Marks\" Is Required .....");
                alertError.showAndWait();

                return;
            }




            // Check whether the Course unit entered exists in the system

            String query1 = "SELECT course_code FROM course_unit WHERE course_name = ?";
            String courseCode = "Not Found";// Provide default coursecode name

            try {
                PreparedStatement pst = Conn.prepareStatement(query1);
                pst.setString(1, txtCourseUnit.getText());
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
                pst.setString(1, txtRegNo.getText());
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
                alert.setContentText("The Registration Number \""+txtRegNo.getText()+"\"\nWas Not Found In The System\n" +
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
                alert.setContentText("The Course Unit \""+txtCourseUnit.getText()+"\"Was Not Found In The System" +
                        "\nPlease Try Again");
                alert.showAndWait();
                return;
            }


            // Check To See Whether The Marks Entered Are Above The Credit Unit Of The Course Unit

            String string3 = "SELECT course_credit FROM course_unit WHERE course_name = ?";


            try {
                PreparedStatement pst3 = Conn.prepareStatement(string3);
                pst3.setString(1, txtCourseUnit.getText());
                ResultSet rs  = pst3.executeQuery();

                while (rs.next())
                {
                     creditUnit = rs.getDouble("course_credit");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            Double totalMarks = Double.parseDouble( txtTotalMarks.getText());
           // System.out.println(totalMarks+"\n"+creditUnit);

            // If The Marks Entered Are Above The The Course Credit Total
            if(totalMarks>creditUnit)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ALERT ERROR");
                alert.setHeaderText(null);
                alert.setContentText("Marks Entered Cannot Be Greater Than The Maximum\n" +
                        "Credit Of The Course Unit");
                alert.showAndWait();
                return;
            }

            // Insert The Marks

            String finalQuery = "INSERT INTO marks(RegNo, course_code, total_mark) VALUES(?, ?, ?)";
            try {
                PreparedStatement pstFinal = Conn.prepareStatement(finalQuery);
                pstFinal.setString(1, txtRegNo.getText());
                pstFinal.setString(2, courseCode);
                pstFinal.setDouble(3, totalMarks);
                pstFinal.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("MESSAGE SUCCESS");
                alert.setHeaderText(null);
                alert.setContentText("Marks Entered Successfully \n" +
                        "For Student : "+txtRegNo.getText());
                alert.showAndWait();

                txtCourseUnit.setText("");
                txtTotalMarks.setText("");
                txtRegNo.setText("");


            } catch (SQLException e) {
                e.printStackTrace();
            }


        });


        /*
         * HANDLE DIFFRENTS LEFT BUTTONS EVENTS
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
}
