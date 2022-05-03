package kinsleykjv;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kinsleykjv.database.DBManager;
import kinsleykjv.systemtree.FileTreeItem;

import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    @Override
    public void start(Stage primaryStage) throws IOException {

       new DBManager(). connect();

        Parent root = FXMLLoader.load(getClass().getResource("/view/mainscreen.fxml"));

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        primaryStage.setTitle("MY Player");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new Image((getClass().getResourceAsStream( "/images/defaul/app.png" ))));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();

        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
       // fileSystemView.
        // UserHome
        String userHome = System.getProperty("user.home");

        // User Folder
        FileTreeItem userFolder = new FileTreeItem(userHome);


    }


}
