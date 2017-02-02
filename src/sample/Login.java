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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.sql.*;




/**
 *
 * @author eddylloyd
 */
public class Login extends Application {

    public static Connection conn;
    public static PreparedStatement pst;
    public static ResultSet rst;


    public TextField txtemail;
    public PasswordField ppassword;
    public ComboBox<String> crole;
    public ObservableList<String> roles = FXCollections.<String>observableArrayList(
            "Administrator", "Academic R.", "Admission Office");
    public Label lerror;
    public Button blogin;
    public String ErrorMessage;



    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();
        borderPane.setId("pane");

        // Create The Login Button with all Styling Effects Stuffs  & Event Handlers

        blogin = new Button("Login");

        DropShadow shadow = new DropShadow();
        blogin.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            blogin.setEffect(shadow);
            blogin.setTextFill(Color.WHITE);
            blogin.setStyle("-fx-color:skyblue;");});
        blogin.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            blogin.setEffect(null);
            blogin.setTextFill(Color.BLACK);
            blogin.setStyle(null);});
        lerror = new Label("");
        lerror.setTextFill(Color.rgb(200,200,200));
        lerror.setId("lerror");


        /*
          INSTANTIATING DIFFERENT SCENE COMPONENTS
         */

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10));
        FlowPane header = new FlowPane();
        FlowPane center = new FlowPane();
        center.getChildren().addAll(lerror,gridpane);
        center.setAlignment(Pos.CENTER);

        gridpane.setVgap(20);
        gridpane.setHgap(20);

        ColumnConstraints Column2 = new ColumnConstraints(150, 150, 200);
        ColumnConstraints Column1 = new ColumnConstraints(100);
        gridpane.getColumnConstraints().addAll(Column1, Column2);

        Label lusername = new Label("Your E-mail :");
        lusername.setId("whiteLabels");
        Label lpassword = new Label("Password :");
        lpassword.setId("whiteLabels");
        Label lrole = new Label("Login As :");
        lrole.setId("whiteLabels");
        Label lheader = new Label("WELCOME");





        header.setId("header");
        lheader.setId("login_header");

        txtemail = new TextField();
        txtemail.setPromptText("Email");
        ppassword = new PasswordField();
        ppassword.setPromptText("Password");

        crole = new ComboBox<>(roles);
        //crole.setPromptText("Select Role");
        crole.setValue("Select Role");


        GridPane.setHalignment(lusername, HPos.RIGHT);
        gridpane.add(lusername, 0, 0);

        GridPane.setHalignment(lpassword, HPos.RIGHT);
        gridpane.add(lpassword, 0, 1);

        GridPane.setHalignment(lrole, HPos.RIGHT);
        gridpane.add(lrole, 0, 2);

        GridPane.setHalignment(txtemail, HPos.LEFT);
        gridpane.add(txtemail, 1, 0);

        GridPane.setHalignment(ppassword, HPos.LEFT);
        gridpane.add(ppassword, 1, 1);

        GridPane.setHalignment(crole, HPos.LEFT);
        gridpane.add(crole, 1, 2);

        GridPane.setHalignment(blogin, HPos.LEFT);
        gridpane.add(blogin, 1, 3);

        header.getChildren().add(lheader);


        borderPane.setCenter(center);
        borderPane.setTop(header);

        //STYLE BUTTON LOGIN
        Shared.styleBtn(blogin);

        Scene scene = new Scene(borderPane,320,280);
              scene.getStylesheets().add("sample/style.css");
        UniqueConnection();
        primaryStage.setScene(scene);
        primaryStage.setTitle("LOGIN INTERFACE");
        primaryStage.setResizable(false);
        primaryStage.show();


        /*
            HANDLE LOGIN BUTTON LOGIC AND ALL RELATING STUFFS
         */

        blogin.addEventHandler(MouseEvent.MOUSE_CLICKED, e->
        {

            ErrorMessage = "";

            if(txtemail.getText().isEmpty() || ppassword.getText().isEmpty())
            {

                ErrorMessage +="Sorry All The Fields Are Required !\n"; lerror.setText(ErrorMessage);
                return;// Kill The Process
            }

            if(!(txtemail.getText().matches("^.+@.+\\.com$"))){

                ErrorMessage +="Sorry Invalid Email Given\n"; lerror.setText(ErrorMessage);
                return;// Kill The Process
            }

            if((crole.getValue().equals("Select Role")))
            {
                ErrorMessage +="Please Select A Role."; lerror.setText(ErrorMessage);
                return;// Kill The Process
            }


            if((crole.getValue().equalsIgnoreCase("Administrator")) && (!txtemail.getText().isEmpty())
                  &&  (!ppassword.getText().isEmpty()))
            {
                try
                {
                    String query = "select * from admin WHERE  email=? and password=? ";
                    pst = conn.prepareStatement(query);
                    pst.setString(1,txtemail.getText());
                    pst.setString(2,ppassword.getText());
                    rst=pst.executeQuery();

                    if(rst.next())
                    {
                        try {
                            new Admin().start(primaryStage);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        System.out.println("LOGIN SUCCESSFULL");
                    }
                    else
                    {
                        lerror.setText("Invalid Credentials, Please Try Again");
                        return;
                    }

                }catch (SQLException ex1)
                {
                    System.out.print(ex1.getMessage());
                }
                finally {

                }
            }
            else if((crole.getValue().equalsIgnoreCase("Admission Office")) && (!txtemail.getText().isEmpty())
                    &&  (!ppassword.getText().isEmpty()))
            {
                try
                {
                    String query = "select * from admission WHERE  email=? and password=? ";
                    pst = conn.prepareStatement(query);
                    pst.setString(1,txtemail.getText());
                    pst.setString(2,ppassword.getText());
                    rst=pst.executeQuery();

                    if(rst.next())
                    {
                        try {
                            new AdmissionOffice().start(primaryStage);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        System.out.println("LOGIN SUCCESSFULL");
                    }
                    else
                    {
                        lerror.setText("Invalid Credentials, Please Try Again");
                        return;// Kill The Process
                    }

                }catch (SQLException ex1)
                {
                    System.out.print(ex1.getMessage());
                }
                finally {

                }
                // HANDLE THE ACADEMIC REGISTRAR LOGIN SCENARIO
            }else if((crole.getValue().equalsIgnoreCase("Academic Registrar")) && (!txtemail.getText().isEmpty())
                    &&  (!ppassword.getText().isEmpty()))
            {
                try
                {
                    String query = "select * from academ WHERE  email=? and password=? ";
                    pst = conn.prepareStatement(query);
                    pst.setString(1,txtemail.getText());
                    pst.setString(2,ppassword.getText());
                    rst=pst.executeQuery();

                    if(rst.next())
                    {
                        try {
                            new Academ().start(primaryStage);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        System.out.println("LOGIN SUCCESSFULL");
                    }
                    else
                    {
                        lerror.setText("Invalid Credentials, Please Try Again");
                        return;// Kill The Process
                    }

                }catch (SQLException ex1)
                {
                    System.out.print(ex1.getMessage());
                }
                finally {

                }
            }
        });


    }

    public void UniqueConnection()
    {
        conn = ConnectionClass.Connector();

        if(conn  == null){
            System.out.println("Database connection not successful");
            System.exit(1);
        }
        else{
            System.out.println("Database connection successful");
        }
    }

}
