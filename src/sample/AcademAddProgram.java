package sample;

/**
 * Created by eddylloyd on 10/18/16.
 */
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 *
 * @author eddylloyd
 */
public class AcademAddProgram extends Application{

    /*
      This is the first screen of the admin panel
      that consists on regisering course units
     */

    public Connection Conn;
    public String errorMessage;





    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();
        Label headerText = new Label("ACADEMIC REGISTER ADD PROGRAM");
        headerText.setId("headerText");



        GridPane gridPane = new GridPane();

        Label lProgramName = new Label("Program Name");
        lProgramName.setId("whiteLabels");
        Label lProgramCode = new Label("Program Code");
        lProgramCode.setId("whiteLabels");
        Label lerror = new Label();

        TextField tProgramName = new TextField();
        TextField tProgramCode = new TextField();


        Button btnSaveProgram = new Button("Save Program");

        gridPane.setPadding(new Insets(10,0,10,10));
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        gridPane.setAlignment(Pos.TOP_CENTER);

        GridPane.setHalignment(lProgramName, HPos.RIGHT);
        gridPane.add(lProgramName, 0, 0);
        GridPane.setHalignment(lProgramCode, HPos.RIGHT);
        gridPane.add(lProgramCode, 0, 1);
        GridPane.setHalignment(btnSaveProgram, HPos.RIGHT);
        gridPane.add(btnSaveProgram, 1, 2);


        GridPane.setHalignment(tProgramName, HPos.RIGHT);
        gridPane.add(tProgramName, 1, 0);
        GridPane.setHalignment(tProgramCode, HPos.RIGHT);
        gridPane.add(tProgramCode, 1, 1);



        /* CREATING LEFTS ELEMENTS */

        Button btnleftHome = new Button("Home");
        btnleftHome.setPadding(new Insets(20));
        btnleftHome.setPrefSize(200, 80);


        Button btnleftaddprogram = new Button("Add Prgram");
        btnleftaddprogram.setPadding(new Insets(20));
        btnleftaddprogram.setPrefSize(200, 80);

        Button btnLeftLogOut = new Button("Log Out");//Button to Log Out
        btnLeftLogOut.setPadding(new Insets(20));
        btnLeftLogOut.setPrefSize(200, 80);
        VBox.setMargin(btnLeftLogOut, new Insets(10));




        VBox left = new VBox();
        left.setPrefSize(200, 0);
        left.setId("left");

        left.getChildren().addAll(btnleftHome,btnleftaddprogram,btnLeftLogOut);
        VBox.setMargin(btnleftHome, new Insets(10));
        VBox.setMargin(btnleftaddprogram, new Insets(10));

        // SET EFFECTS AND STYLES TO LEFTS BUTTONS

        Shared.styleBtn(btnLeftLogOut);
        Shared.styleBtn(btnleftaddprogram);
        Shared.styleBtn(btnleftHome);
        Shared.styleBtn(btnSaveProgram);







        /*
         *    INSTANTIATING DIFFERENT WINDOW ELEMENTS
         */

        FlowPane flerror = new FlowPane();//Flow Pane That Holds Label To Show Error Messages
        flerror.setAlignment(Pos.CENTER);
        flerror.getChildren().addAll(lerror);

        FlowPane header = new FlowPane();
        header.setPrefSize(0, 50);
        headerText.setId("ac_header");
        headerText.setAlignment(Pos.CENTER);
        header.getChildren().add(headerText);

        FlowPane center = new FlowPane();
        center.setId("center");
        center.setAlignment(Pos.TOP_CENTER);
        center.getChildren().addAll(flerror,gridPane);


        borderPane.setTop(header);

        borderPane.setLeft(left);

        borderPane.setCenter(center);


        Scene scene = new Scene(borderPane,750,500);
        scene.getStylesheets().add("sample/style.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Academic Registrar");
        primaryStage.show();

        // Handle App Program Button Event

        Alert alertError = new Alert(Alert.AlertType.WARNING);
        alertError.setTitle("ALERT WARNING");
        alertError.setHeaderText(null);


        btnSaveProgram.setOnAction(e->
        {
            // Get Connection Instance
            Conn = ConnectionClass.Connector();

            // Validate Program Name
            if((tProgramName.getText()).equals("")) {
                alertError.setContentText("\nThe Field \"Program Name\" Is Required .....");
                alertError.showAndWait();

                return;
            }

            // Validate Program Code
            if((tProgramCode.getText()).equals("")) {
                alertError.setContentText("\nThe Field \"Program Code\" Is Required .....");
                alertError.showAndWait();

                return;
            }



            String query = "INSERT INTO program(Program_name, Program_code)"+
                           "VALUES(?, ?)"  ;
            try {
                PreparedStatement pst = Conn.prepareStatement(query);
                pst.setString(1, tProgramName.getText());
                pst.setString(2, tProgramCode.getText());
                pst.execute();

                // SHOW SUCCESS MESSAGE AND CLEAR INPUT

                Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                alertSuccess.setTitle("ALERT SUCCESS");
                alertSuccess.setHeaderText(null);
                alertSuccess.setContentText("A New Program Has Been Registered Successfully");
                alertSuccess.showAndWait();


                tProgramName.setText(null);
                tProgramCode.setText(null);

            // Handle Integrity Constraint Exception

            }catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e2)
            {
                alertError.setContentText("\nThe Program Name or Code Already Exists\n" +
                        " In The System  Please Try Another Name Or Code...");
                alertError.showAndWait();
                tProgramName.setText("");
                tProgramCode.setText("");
                return;
            }

            catch (SQLException e1) {
                e1.printStackTrace();
            }


        });


        /*
         * HANDLE DIFFRENTS LEFT BUTTONS EVENTS
         */

        btnleftHome.setOnMouseClicked(event
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

        btnLeftLogOut.setOnMouseClicked(event ->
        {
            try {
                new Login().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }

}
