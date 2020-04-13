package tn.shoppy.controller.gestionAccounts;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.sun.prism.Image;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashbordController implements Initializable{
    @FXML
    private AnchorPane root;
    @FXML
    private JFXHamburger menuBTN;
    @FXML
    private ImageView profile;
    @FXML
    private Label username;
    @FXML
    private ImageView logout;
    @FXML
    private ImageView chat;
    @FXML
    private ImageView notification;
    @FXML
    private Label mail;


    @FXML
    private AnchorPane pane1 ,pane2,pane3,pane4 , opacityPane,drawerPane;


    @FXML
    private ImageView drawerImage;

    @FXML
    private JFXDrawer menu;

    public Label getMail() {
        return mail;
    }

    public void setMail(Label mail) {
        this.mail = mail;
    }
    public static AnchorPane rootS;



    public Label getUsername() {
        return username;
    }

    public void setUsername(Label username) {
        this.username = username;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        opacityPane.setVisible(false);

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),opacityPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.5),drawerPane);
        translateTransition.setByX(-600);
        translateTransition.play();


        pane1.setStyle("-fx-background-image: url(\"/tn/shoppy/view/gestionAccounts/1.png\")");
        pane2.setStyle("-fx-background-image: url(\"/tn/shoppy/view/gestionAccounts/4.jpg\")");
        pane3.setStyle("-fx-background-image: url(\"/tn/shoppy/view/gestionAccounts/3.png\")");
        pane4.setStyle("-fx-background-image: url(\"/tn/shoppy/view/gestionAccounts/4.png\")");

        Animation();


        drawerImage.setOnMouseClicked(event -> {


            opacityPane.setVisible(true);

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),opacityPane);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),drawerPane);
            translateTransition1.setByX(+600);
            translateTransition1.play();
        });

        opacityPane.setOnMouseClicked(event -> {



            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),opacityPane);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                opacityPane.setVisible(false);
            });


            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),drawerPane);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });

        rootS = root;
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/tn/shoppy/view/gestionAccounts/menu.fxml"));
            menu.setSidePane(box);
        } catch (IOException ex) {

        }
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(menuBTN);
        transition.setRate(-1);
        menuBTN.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED,(e)->{
            transition.setRate(transition.getRate()*-1);
            transition.play();

            if(menu.isOpened())
            {
                menu.close();
            }else
                menu.open();
        });
        // TODO

    }

    public  void  Animation(){


        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(3),pane4);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        fadeTransition.setOnFinished(event -> {
            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(3),pane3);
            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {

                FadeTransition fadeTransition2=new FadeTransition(Duration.seconds(3),pane2);
                fadeTransition2.setFromValue(1);
                fadeTransition2.setToValue(0);
                fadeTransition2.play();

                fadeTransition2.setOnFinished(event2 -> {

                    FadeTransition fadeTransition00=new FadeTransition(Duration.seconds(3),pane2);
                    fadeTransition00.setFromValue(0);
                    fadeTransition00.setToValue(1);
                    fadeTransition00.play();


                    fadeTransition00.setOnFinished(event3 -> {
                        FadeTransition fadeTransition11=new FadeTransition(Duration.seconds(3),pane3);
                        fadeTransition11.setFromValue(0);
                        fadeTransition11.setToValue(1);
                        fadeTransition11.play();

                        fadeTransition11.setOnFinished(event4 -> {
                            FadeTransition fadeTransition22=new FadeTransition(Duration.seconds(3),pane4);
                            fadeTransition22.setFromValue(0);
                            fadeTransition22.setToValue(1);
                            fadeTransition22.play();

                            fadeTransition22.setOnFinished(event5 -> {
                                Animation();
                            });
                        });


                    });
                });

            });




        });



    }

}
