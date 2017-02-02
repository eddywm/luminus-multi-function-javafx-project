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
public class Academ extends Application{

    /*
      This is the first screen of the admin panel
      that consists on regisering course units
     */




    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();
        Label headerText = new Label("ACADEMIC REGISTER MAIN INTERFACE");
        headerText.setId("headerText");

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("http://app.dev/");

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

        Button btnlefthome = new Button("Home");
        btnlefthome.setPadding(new Insets(20));
        btnlefthome.setPrefSize(200, 80);


        Button btnleftaddprogram = new Button("Add Prgram");
        btnleftaddprogram.setPadding(new Insets(20));
        btnleftaddprogram.setPrefSize(200, 80);




        VBox left = new VBox();
        left.setPrefSize(200, 0);
        left.setId("left");

        Button btnLeftLogOut = new Button("Log Out");//Button to Log Out
        btnLeftLogOut.setPadding(new Insets(20));
        btnLeftLogOut.setPrefSize(200, 80);
        VBox.setMargin(btnLeftLogOut, new Insets(10));


        left.getChildren().addAll(btnlefthome,btnleftaddprogram,btnLeftLogOut);
        VBox.setMargin(btnlefthome, new Insets(10));
        VBox.setMargin(btnleftaddprogram, new Insets(10));

        // SET EFFECTS AND STYLES TO LEFTS BUTTONS

        Shared.styleBtn(btnLeftLogOut);
        Shared.styleBtn(btnleftaddprogram);
        Shared.styleBtn(btnlefthome);



        /*
         *    INSTANTIATING DIFFERENT WINDOW ELEMENTS
         */


        FlowPane header = new FlowPane();
        header.setPrefSize(0, 50);
        headerText.setId("ac_header");
        headerText.setAlignment(Pos.CENTER);
        header.getChildren().add(headerText);

        FlowPane center = new FlowPane();
        center.setId("center");
        center.getChildren().addAll(gridPane);










        borderPane.setTop(header);

        borderPane.setLeft(left);

        borderPane.setCenter(webView);





        Scene scene = new Scene(borderPane,750,500);
        scene.getStylesheets().add("sample/style.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Academic Registrar");
        primaryStage.show();


        /*
         * HANDLE DIFFRENTS LEFT BUTTONS EVENTS
         */

        btnlefthome.setOnMouseClicked(event
                ->{
            try {
                new Academ().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } );

        btnleftaddprogram.setOnMouseClicked(event ->
        {
            try {
                new AcademAddProgram().start(primaryStage);
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
