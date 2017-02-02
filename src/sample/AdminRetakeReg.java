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
public class AdminRetakeReg extends Application {

    public String errorMessage;
    public Connection Conn;

    @Override
    public void start(Stage primaryStage) throws Exception {


        BorderPane borderPane = new BorderPane();

        Label headerText = new Label("ADMINISTRATOR RETAKE  REGISTARTION");
        headerText.setId("headerText");

        Label lblTilte = new Label("RETAKES REGISTRATION");//TODOS To be deleted in the future problem
        lblTilte.setId("lblTitle");lblTilte.setText("");//It is tied in the GridPane
        lblTilte.setAlignment(Pos.CENTER);//Ill find a way

        Label lRegNoRT = new Label("Reg No :");
        lRegNoRT.setId("whiteLabels");




        Label lblRetakeCourse = new Label("Retake Course :");
        lblRetakeCourse.setId("whiteLabels");


        TextField tRegNoRT = new TextField();
        TextField txtRetakeCourse = new TextField();



        Button btnRegRetakes = new Button("Regitser Retake");

        Conn = ConnectionClass.Connector();

        // BUILD THE AUTO-COMPLETE SUGGESTION BASE

        List<String> regNoSuggestions = new ArrayList<>();


        String queryRegSugg = "SELECT RegNo FROM student";

        try(ResultSet rs = Conn.createStatement().executeQuery(queryRegSugg)){

            while (rs.next())
            {
                String RegNo = rs.getString("RegNo");
                regNoSuggestions.add(RegNo);

            }

        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        TextFields.bindAutoCompletion(tRegNoRT,regNoSuggestions);



        List<String> courseSuggestions = new ArrayList<>();



            String queryCourseSugg = "SELECT  course_name FROM course_unit";

            try(ResultSet rs = Conn.createStatement().executeQuery(queryCourseSugg)) {

                while (rs.next()) {
                    String courseName = rs.getString("course_name");
                    courseSuggestions.add(courseName);

                }
            }catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }


        TextFields.bindAutoCompletion(txtRetakeCourse, courseSuggestions);


        GridPane gridPaneRegRetake = new GridPane();
        gridPaneRegRetake.setPrefSize(350, 450);
        gridPaneRegRetake.setId("gridPaneRegRetake");
        gridPaneRegRetake.setPadding(new Insets(0,10,10,10));
        gridPaneRegRetake.setVgap(20);
        gridPaneRegRetake.setHgap(20);
        gridPaneRegRetake.setAlignment(Pos.TOP_CENTER);

        gridPaneRegRetake.add(lblTilte,0,0,2,1);
        GridPane.setHalignment(lRegNoRT, HPos.LEFT);
        gridPaneRegRetake.add(lRegNoRT, 0, 1);


        GridPane.setHalignment(lblRetakeCourse, HPos.LEFT);
        gridPaneRegRetake.add(lblRetakeCourse, 0, 3);

        GridPane.setHalignment(tRegNoRT, HPos.LEFT);
        gridPaneRegRetake.add(tRegNoRT, 1, 1);

        GridPane.setHalignment(txtRetakeCourse, HPos.LEFT);


        GridPane.setHalignment(txtRetakeCourse, HPos.LEFT);
        gridPaneRegRetake.add(txtRetakeCourse, 1, 3);
        GridPane.setHalignment(btnRegRetakes, HPos.LEFT);
        gridPaneRegRetake.add(btnRegRetakes, 1, 5);


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
        left.getChildren().addAll(btnLeftHome, btnleftregcourses,btnleftregretakes,btnleftEnterMarks,btnLeftLogOut);
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
        Shared.styleBtn(btnRegRetakes);



        FlowPane centerRegRetake = new FlowPane();
        centerRegRetake.setAlignment(Pos.CENTER);
        centerRegRetake.getChildren().addAll(gridPaneRegRetake);
        centerRegRetake.setId("center");

        FlowPane header = new FlowPane();
        header.setPrefSize(0, 50);
        headerText.setId("ac_header");
        headerText.setAlignment(Pos.CENTER);
        header.getChildren().add(headerText);

        borderPane.setTop(header);

        borderPane.setLeft(left);

        borderPane.setCenter(centerRegRetake);


        Scene scene = new Scene(borderPane,750,550);
        scene.getStylesheets().add("sample/style.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Academic Registrar");
        primaryStage.show();


        /**
         ** HANDLE REG COURSE BUTTON EVENT
         **/
        // First Handle The Dialogs Stuffs
        Alert alertError = new Alert(Alert.AlertType.WARNING);
        alertError.setTitle("ALERT WARNING");
        alertError.setHeaderText(null);

        btnRegRetakes.setOnAction(event ->
        {
            // Validate RegNo
            if((tRegNoRT.getText()).equals("")) {
                alertError.setContentText("\nThe Field \"(RegNo)\" Is Required .....");
                alertError.showAndWait();

                return;
            }

            // Validate Course Name
            if((txtRetakeCourse.getText()).equals("")) {
                alertError.setContentText("\nThe Field \"Course Name\" Is Required .....");
                alertError.showAndWait();

                return;
            }

            String query1 = "SELECT course_code FROM course_unit WHERE course_name = ?";

            String courseCode = "Not Found";

            try(PreparedStatement pst = Conn.prepareStatement(query1)){

                pst.setString(1, txtRetakeCourse.getText());
                ResultSet rs = pst.executeQuery();

                while (rs.next()){ courseCode = rs.getString("course_code");
                    //System.out.print(courseCode);
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
            // If The Course Entered Is not yet Registered Kill The Program And Show  The Dialog Message
            if (courseCode.equals("Not Found"))
            {
                alertError.setContentText("The Course Unit Given Was Not Found In The System" +
                        "\n Please Try Again  .....");
                alertError.showAndWait();

                return;
            }



            // Check whether the RegNo entered exists in the system

            String query2 = "SELECT RegNo FROM student WHERE RegNo = ?";

            String StrRegNumber = "Not Found RegNo";// Provide default RegNo

            try(PreparedStatement pst = Conn.prepareStatement(query2)){

                pst.setString(1, tRegNoRT.getText());
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
                alert.setContentText("The Registration Number \""+tRegNoRT.getText()+"\"\nWas Not Found In The System\n" +
                        "Please Try Again ");
                alert.showAndWait();
                return;
            }




            String query3 = "INSERT INTO retakes(SRegNo, course_code) values(?, ?)";
            try(PreparedStatement pst2 = Conn.prepareStatement(query3)){
                pst2.setString(1, tRegNoRT.getText());
                pst2.setString(2, courseCode);
                pst2.execute();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION SUCCESS");
                alert.setHeaderText(null);
                alert.setContentText("\nThe Retake Has Been Registered Successfully ");
                alert.showAndWait();

                tRegNoRT.setText("");
                txtRetakeCourse.setText("");

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
}
