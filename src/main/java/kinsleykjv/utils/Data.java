package kinsleykjv.utils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class Data {
    private FontIcon icon = new FontIcon();
    public  final Color folderColor = Color.web("#ddaa33");
    public  final Color audioColor = Color.web("#ff4a4a");
    public  final Color pdfColor = Color.web("#d62641");
    public  final Color fileColor = Color.web("#d74418");
    @FXML
    public ImageView fileIcon;
    @FXML
    public Label txtTitle;
    @FXML
    public Label txtSubtitle;
    @FXML
    public Label txtTime;
    @FXML
    public Pane rootParent;


    public Data() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/filesListIcon.fxml"));
        fxmlLoader.setController(this);
        try {
           // fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ImageView getFileIcon() {
        return fileIcon;
    }

    public void setFileIcon(String fileIcon) {
        Image icon = new Image(getClass().getResourceAsStream(fileIcon));
        this.fileIcon .setImage( icon);
    }

    public Label getTxtTitle() {
        return txtTitle;
    }

    public void setTxtTitle(String txtTitle) {
        this.txtTitle.setText(txtTitle);
    }

    public Label getTxtSubtitle() {
        return txtSubtitle;
    }

    public void setTxtSubtitle(String txtSubtitle) {
        this.txtSubtitle.setText(txtSubtitle);
    }

    public Label getTxtTime() {
        return txtTime;
    }

    public void setTxtTime(String txtTime) {
        this.txtTime.setText(txtTime);
    }

    public Pane getRootParent() {
        return rootParent;
    }

    public void setRootParent(Pane rootParent) {
        this.rootParent = rootParent;
    }
}
