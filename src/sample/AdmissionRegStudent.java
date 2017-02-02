package sample;

/**
 * Created by eddylloyd on 10/18/16.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class AdmissionRegStudent extends Application {
    Connection Conn;
    TextField tfirstname, tlastname, tphone;

    // Method Definition To Consume Unwanted Characters Inputs

    public void consumeBadChars(KeyEvent event)
    {
        if((event.getSource().equals(tfirstname)) || (event.getSource().equals(tlastname))) {

            String str = event.getCharacter();
            if (!(str.matches("[a-zA-Z]"))) event.consume();
        }
        if(event.getSource().equals(tphone))
        {
            String str = event.getCharacter();
            if(!(str.matches("[0-9]"))) event.consume();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();

        Label headerText = new Label("ADMISSION OFFICE STUDENT REGISTRATION");

        /*
            CENTRAL COMPONENTS CREATIONS
        */
        Label lregno = new Label("RegNo(Main Section) :");
        Label lfirstname = new Label("First Name :");
        lfirstname.setId("whiteLabels");
        Label llastname = new Label("Last Name :");
        llastname.setId("whiteLabels");
        Label lsex = new Label("Sex :");
        lsex.setId("whiteLabels");
        Label lcountry = new Label("Country :");
        lcountry.setId("whiteLabels");
        Label ldob = new Label("Date Of Birth :");
        ldob.setId("whiteLabels");
        Label lprogram= new Label("Program :");
        lprogram.setId("whiteLabels");
        Label lemail = new Label("Email :");
        lemail.setId("whiteLabels");
        Label lphone = new Label("Phone Number :");
        lphone.setId("whiteLabels");
        Label lerror = new Label();// Label For Displaying Error Messages
              lerror.setId("lerror");

        Label tyear = new Label();


         tfirstname = new TextField();
         tlastname = new TextField();
         TextField temail = new TextField();
         tphone = new TextField();

        // KeyListeners Registrations and Handling

        tfirstname.setOnKeyTyped(e->consumeBadChars(e));
        tlastname.setOnKeyTyped(e->consumeBadChars(e));




        RadioButton rbmale = new RadioButton("Male");
                    rbmale.setSelected(true);rbmale.setId("whiteLabels");
        RadioButton rbfemale = new RadioButton("Female");rbfemale.setId("whiteLabels");
        ToggleGroup groupsex = new ToggleGroup();
        groupsex.getToggles().addAll(rbmale, rbfemale);

        UniqueConnection();// Using Singleton Design Pattern/Creating The Singleton Connection

        /* DEAL WITH THE COMPLEXITY OF THE COUNTRY COMBOBOX IN RELATION WITH THE DATABASE
        * GONNA USE EXACTLY THE SAME METHOD FOR OTHER COMBOBOX (PROGRAM SELECTION)
        */

        Map<String, String> countryMap = new HashMap<>();// Creating a Map with key-value (Country Name , Country Code)
        ObservableList<String> countries = FXCollections.<String>observableArrayList();

        try{

            String query = "SELECT * FROM countries";
            ResultSet rs = Conn.createStatement().executeQuery(query);
            while (rs.next())
            {
                String countryName = rs.getString("Country_name");

                String countryCode = rs.getString("Country_code");

                countryMap.put(countryName, countryCode);//Seed the Map

                countries.add(countryName);// Seed the Combobox ObservableList
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        ComboBox<String> cbcountry = new ComboBox<>(countries);
        cbcountry.setValue("Select Country");

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
        cbprogram.setValue("Select Program");

        DatePicker ddob = new DatePicker();
        ddob.setValue(LocalDate.now());

        //PasswordField ppassword = new PasswordField(); WON'T BE USED AGAIN

        Button btnregister = new Button("Register");



        /* CREATION OF THE GRIDPANE AND CONFIGURATIONS*/

        GridPane gridpane = new GridPane();
        gridpane.setPrefSize(420, 450);
        gridpane.setId("ac_gripane");
        gridpane.setPadding(new Insets(10,0,10,10));
        gridpane.setVgap(20);
        gridpane.setHgap(20);
        gridpane.setAlignment(Pos.TOP_CENTER);

       /*
        *   ADDING DIFFERENT NODES TO THE GRIDPANE
        */

        GridPane.setHalignment(lfirstname, HPos.RIGHT);
        gridpane.add(lfirstname, 0, 1);
        GridPane.setHalignment(llastname, HPos.RIGHT);
        gridpane.add(llastname, 0, 2);
        GridPane.setHalignment(lsex, HPos.RIGHT);
        gridpane.add(lsex, 0, 3);
        GridPane.setHalignment(lcountry, HPos.RIGHT);
        gridpane.add(lcountry, 0, 4);
        GridPane.setHalignment(ldob, HPos.RIGHT);
        gridpane.add(ldob, 0, 5);
        GridPane.setHalignment(lprogram, HPos.RIGHT);
        gridpane.add(lprogram, 0, 6);
        GridPane.setHalignment(lemail, HPos.RIGHT);
        gridpane.add(lemail, 0, 7);
        GridPane.setHalignment(lphone, HPos.RIGHT);
        gridpane.add(lphone, 0, 8);




        gridpane.add(tfirstname, 1, 1, 2, 1);
        gridpane.add(tlastname, 1, 2, 2, 1);
        gridpane.add(rbmale, 1, 3); gridpane.add(rbfemale, 2, 3);
        gridpane.add(cbcountry, 1, 4,2,1);
        gridpane.add(ddob, 1, 5, 2, 1);
        gridpane.add(cbprogram, 1, 6, 2, 1);
        gridpane.add(temail, 1, 7, 2, 1);
        gridpane.add(tphone, 1, 8, 2, 1);
        gridpane.add(btnregister, 1, 9,2,1);



        // CREATING LEFTS ELEMENTS

        Button btnLeftHome = new Button("Home Button");
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

        // SET EFFECTS AND STYLES TO LEFTS BUTTONS

        Shared.styleBtn(btnLeftLogOut);
        Shared.styleBtn(btnLeftHome);
        Shared.styleBtn(btnLeftRegSt);
        Shared.styleBtn(btnregister);




        /*
         *    INSTANTIATING DIFFERENT WINDOW ELEMENTS
         */
        FlowPane flerror = new FlowPane();//Flow Pane That Holds Label To Show Error Messages
                 flerror.getChildren().addAll(lerror);

        FlowPane center = new FlowPane();
        center.setId("center");
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(flerror,gridpane);


        FlowPane header = new FlowPane();
        header.setPrefSize(0, 50);
        headerText.setId("ac_header");
        headerText.setAlignment(Pos.CENTER);

        header.getChildren().add(headerText);

        VBox left = new VBox();
        left.setPrefSize(200, 0);
        left.setId("left");
        left.getChildren().add(btnLeftHome);
        left.getChildren().addAll(btnLeftRegSt);



        borderPane.setCenter(center);
        borderPane.setTop(header);
        borderPane.setLeft(left);

        Scene scene = new Scene(borderPane,750,550);
        scene.getStylesheets().add("sample/style.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Academic Registrar");
        primaryStage.show();

        // Date Stuffs
        Calendar now = Calendar.getInstance();;
        String yearString = (String.valueOf(now.get(Calendar.YEAR))).substring(2);// TRANSFORM TO STRINGS AND GET ONLY THE TWO LAST INT OF THE YEAR

        //CREATE THE RANDOM INTEGER FOR REGNO

        SecureRandom randomNumbers1 = new SecureRandom();
        SecureRandom randomNumbers2 = new SecureRandom();
        SecureRandom randomNumbers3 = new SecureRandom();
        SecureRandom randomNumbers4 = new SecureRandom();

        String randomInt = String.valueOf(randomNumbers1.nextInt(9))+
                        String.valueOf(randomNumbers2.nextInt(9))+
                        String.valueOf(randomNumbers3.nextInt(9))+
                        String.valueOf(randomNumbers4.nextInt(9));



        /*
         * HANDLE THE REGISTER STUDENT BUTTON // BIG WORK TO COME
         */
        Alert alertError = new Alert(Alert.AlertType.WARNING);
        alertError.setTitle("ALERT WARNING");
        alertError.setHeaderText(null);

        //AquaFx.style();

        btnregister.setOnAction(e->
        {

            // HANDLE FIRST THE VALIDATION BEFORE INSERT INTO THE DATABASE

//
//           // Validate First Name
           if((tfirstname.getText()).equals("")) {
               alertError.setContentText("THE FIELD FIRST NAME IS REQUIRED");
               alertError.showAndWait();

               return;
           }


            if((tlastname.getText()).equals("")) {
                alertError.setContentText("THE FIELD LAST NAME IS REQUIRED");
                alertError.showAndWait();
                return;
            }


            // Validate Country Selection
            if((cbcountry.getValue()).equals("Select Country")) {
                alertError.setContentText("THE FIELD COUNTRY IS REQUIRED");
                alertError.showAndWait();

                return;
            }
            // Validate DOB
            if(( (LocalDate.now().getYear()) - (ddob.getValue().getYear()) )< 16) {
                alertError.setContentText("\n Invalid Age Given..." +
                        "\n Age Below The Minimum Accepted");
                alertError.showAndWait();

                return;
            }
            // Validate Program
            if((cbprogram.getValue()).equals("Select Program")) {
                alertError.setContentText("THE FIELD PROGRAM IS REQUIRED");
                alertError.showAndWait();

                return;
            }

            // Validate Email
            if((temail.getText()).equals("")) {
                alertError.setContentText("THE FIELD EMAIL IS REQUIRED");
                alertError.showAndWait();

                return;
            }else if(!temail.getText().matches("^.+@.+\\.com$"))
            {
                alertError.setContentText("INVALID EMAIL GIVEN \n PLEASE TRY AGAIN !!!");
                alertError.showAndWait();
                return;
            }

            // Validate Phone Number
            if((tphone.getText()).equals("")) {
                alertError.setContentText("THE FIELD Phone Number IS REQUIRED");
                alertError.showAndWait();
                return;
            }





                try {

                    String query = "INSERT INTO student(RegNo, Sfname, Slname, Ssex, Snation, SDOB, Semail, phoneNumber, Program_code) " +
                            "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement pst = Conn.prepareStatement(query);
                    pst.setString(1, yearString + "/" + randomInt + "/" + countryMap.get(cbcountry.getValue()) + "/"
                           + programMap.get(cbprogram.getValue()));
                    pst.setString(2, tfirstname.getText());
                    pst.setString(3, tlastname.getText());
                    pst.setString(4, ((RadioButton) groupsex.getSelectedToggle()).getText());
                    pst.setString(5, countryMap.get(cbcountry.getValue()));
                    pst.setDate(6, Date.valueOf(ddob.getValue()));
                    pst.setString(7, temail.getText());
                    pst.setString(8, tphone.getText());
                    pst.setString(9, programMap.get(cbprogram.getValue()));

                    pst.execute();

                    // SHOW SUCCESS MESSAGE AND CLEAR INPUT

                    Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                    alertSuccess.setTitle("ALERT WARNING");
                    alertSuccess.setHeaderText(null);
                    alertSuccess.setContentText("Student Registered Successfully");
                    alertSuccess.showAndWait();


                    tfirstname.setText("");
                    tlastname.setText("");
                    ddob.setValue(LocalDate.now());
                    temail.setText("");
                    tphone.setText("");

                } catch (SQLException sqlex) {
                    JOptionPane.showMessageDialog(null, sqlex.getMessage());
                }
           
        });



        /*
         * SWITCHING SCENES CODES
         */

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


    }

    public void UniqueConnection()
    {
        Conn = ConnectionClass.Connector();
        if(Conn  == null){
            System.out.println("Database connection not successful");
            System.exit(1);
        }
        else{
            System.out.println("Database connection successful");
        }
    }


}
