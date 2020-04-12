package hamdigtar.crudAccount;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainAcc extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/shoppy/gui/AdminUserFXMLv2.fxml"));
        AnchorPane pane=loader.load();
        Scene scene=new Scene(pane);
//        scene.getStylesheets().addAll(getClass().getResource("stylelogin.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
