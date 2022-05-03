package kinsleykjv.utils;


import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import javafx.scene.control.ContentDisplay;


import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

import static kinsleykjv.utils.ExtensionTool.isAudio;
import static kinsleykjv.utils.ExtensionTool.isVideo;
import static kinsleykjv.utils.IOInfo.getFileCreationDate;

public class ListViewCell extends JFXListCell<File> {

   // private


    @Override
    protected void updateItem(File item, boolean empty) {
        super.updateItem(item, empty);
        if(empty || item == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        if(item != null)
        {
            Data data = new Data();
            data.setTxtTitle(item.getName());
            data.setTxtTime(" ");
            data.setTxtSubtitle( getFileCreationDate (item) );
            if(!item.isDirectory()){
                if(isVideo(item.getAbsolutePath())){
                    data.setFileIcon("/images/defaul/icons/video.png");
                }else{
                    // is audio file
                    data.setFileIcon("/images/defaul/icons/music.png");
                }
            }
            //  data.setInfo(item);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(data.getRootParent());
        }

    }
}
