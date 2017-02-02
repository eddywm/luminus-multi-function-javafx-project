package sample;

/**
 * Created by eddylloyd on 10/18/16.
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author eddylloyd
 */
public class AdmissionOffice extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
                  webEngine.load("http://app.dev/");




        Label headerText = new Label("ACADEMIC REGISTRAR HOME");



        // CREATING LEFTS ELEMENTS

        Button btnLeftHome = new Button("Home");
        btnLeftHome.setPadding(new Insets(20));
        btnLeftHome.setPrefSize(200, 80);
        VBox.setMargin(btnLeftHome, new Insets(10));

        Button btnLeftRegSt = new Button("Register \nStudents");
        btnLeftRegSt.setPadding(new Insets(20));
        btnLeftRegSt.setPrefSize(200, 80);
        VBox.setMargin(btnLeftRegSt, new Insets(10));

        Button btnLeftLogOut = new Button("Log Out");//Button to Log Out
        btnLeftLogOut.setPadding(new Insets(20));
        btnLeftLogOut.setPrefSize(200, 80);
        VBox.setMargin(btnLeftLogOut, new Insets(10));




        /*
         *    INSTANTIATING DIFFERENT WINDOW ELEMENTS
         */
        FlowPane center = new FlowPane();
        center.setAlignment(Pos.CENTER);
        center.setId("center");
        FlowPane header = new FlowPane();
        header.setPrefSize(0, 50);
        headerText.setId("ac_header");
        headerText.setAlignment(Pos.CENTER);

        header.getChildren().add(headerText);
        VBox left = new VBox();
        left.setPrefSize(200, 0);
        left.setId("left");
        left.getChildren().add(btnLeftHome);
        left.getChildren().add(btnLeftRegSt);


        // SET EFFECTS AND STYLES TO LEFTS BUTTONS

        Shared.styleBtn(btnLeftLogOut);
        Shared.styleBtn(btnLeftHome);
        Shared.styleBtn(btnLeftRegSt);




        borderPane.setCenter(webView);
        borderPane.setTop(header);
        borderPane.setLeft(left);

        Scene scene = new Scene(borderPane,800,600);
        scene.getStylesheets().add("sample/style.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Admission Office");
        primaryStage.show();

        btnLeftHome.setOnAction(event ->
        {
            try {
                new AdmissionOffice().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnLeftRegSt.setOnAction(event ->
        {
            try {
                new AdmissionRegStudent().start(primaryStage);
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
