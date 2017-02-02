package sample;

/**
 * Created by eddylloyd on 10/18/16.
 */

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


/**
 *
 * @author eddylloyd
 */
public class Admin extends Application{

    /*
      This is the first screen of the admin panel

     */




    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("http://app.dev/");

        Label headerText = new Label("ADMINISTRATOR MAIN INTERFACE");
        headerText.setId("headerText");



        GridPane gridPane = new GridPane();

        Label lStudentSearch = new Label("Enter Student Name Or RegNo");

        TextField txtSearch = new TextField();

        Button btnSearch = new Button("Search");

        gridPane.setPadding(new Insets(10,0,10,10));
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        gridPane.setAlignment(Pos.CENTER);

        GridPane.setHalignment(lStudentSearch, HPos.LEFT);
        gridPane.add(lStudentSearch, 0, 0);
        GridPane.setHalignment(txtSearch, HPos.LEFT);
        gridPane.add(txtSearch, 1, 0);
        GridPane.setHalignment(btnSearch, HPos.LEFT);
        gridPane.add(btnSearch, 2, 0);




        /* CREATING LEFTS ELEMENTS */

        Button btnleftregcourses = new Button("Register Courses");
        btnleftregcourses.setPadding(new Insets(20));
        btnleftregcourses.setPrefSize(200, 80);


        Button btnleftregretakes = new Button("Register Retakes");
        btnleftregretakes.setPadding(new Insets(20));
        btnleftregretakes.setPrefSize(200, 80);



        Button btnleftEnterMarks = new Button("Enter Marks");//Button to Present GUI for Entering Marks
        btnleftEnterMarks.setPadding(new Insets(20));
        btnleftEnterMarks.setPrefSize(200, 80);

        Button btnLeftHome = new Button("Admin Home");//Button to Present Interface for Entering Marks
        btnLeftHome.setPadding(new Insets(20));
        btnLeftHome.setPrefSize(200, 80);

        Button btnLeftLogOut = new Button("Log Out");//Button to Log Out
        btnLeftLogOut.setPadding(new Insets(20));
        btnLeftLogOut.setPrefSize(200, 80);
        VBox.setMargin(btnLeftLogOut, new Insets(10));



        VBox left = new VBox();
        left.setPrefSize(200, 0);
        left.setId("left");

        left.getChildren().addAll(btnLeftHome,btnleftregcourses,btnleftregretakes,
                          btnleftEnterMarks,btnLeftLogOut);
        VBox.setMargin(btnleftregcourses, new Insets(10));
        VBox.setMargin(btnleftregretakes, new Insets(10));
        VBox.setMargin(btnLeftLogOut, new Insets(10));
        VBox.setMargin(btnleftEnterMarks, new Insets(10));
        VBox.setMargin(btnLeftHome, new Insets(10));


        // SET EFFECTS AND STYLES TO LEFTS BUTTONS

        Shared.styleBtn(btnLeftLogOut);
        Shared.styleBtn(btnLeftHome);
        Shared.styleBtn(btnleftEnterMarks);
        Shared.styleBtn(btnleftregcourses);
        Shared.styleBtn(btnleftregretakes);




        /*
         *    INSTANTIATING DIFFERENT WINDOW ELEMENTS
         */


        FlowPane header = new FlowPane();
        header.setPrefSize(0, 50);
        headerText.setId("ac_header");
        headerText.setAlignment(Pos.CENTER);
        header.getChildren().add(headerText);

        FlowPane center = new FlowPane();
                 center.getChildren().addAll(gridPane);





        borderPane.setTop(header);

        borderPane.setLeft(left);

        borderPane.setCenter(webView);





       Scene scene = new Scene(borderPane,950,550);
        scene.getStylesheets().add("sample/style.css");

       primaryStage.setScene(scene);
       primaryStage.setTitle("Administrator Interface");
       primaryStage.show();


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
