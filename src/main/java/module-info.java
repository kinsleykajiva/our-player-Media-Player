module JavaFX.Template.main {

    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.media;
    requires com.jfoenix;
    requires kotlin.stdlib;
    requires java.sql;
    requires uk.co.caprica.vlcj;
    requires uk.co.caprica.vlcj.javafx;

//    requires jaudiotagger;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires org.kordamp.ikonli.javafx;
    requires org.apache.commons.io;



//    requires AnimateFX;
//    requires com.github.A ;

    exports kinsleykjv;
    opens kinsleykjv to javafx.fxml;


    exports kinsleykjv.controller to javafx.fxml;
    exports kinsleykjv.systemtree to javafx.fxml;
    exports kinsleykjv.utils to javafx.fxml;
    opens kinsleykjv.controller to javafx.fxml;
    // If your project uses FXML, make sure to open all packages that use
    // it to javafx.fxml like so:
    //
    // opens template.Main to javafx.fxml;


}