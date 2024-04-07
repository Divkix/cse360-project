module me.divkix.cse360project {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.sql;

    opens me.divkix.cse360project to javafx.fxml;

    // database
    opens me.divkix.cse360project.helperFunctions to org.xerial.sqlite;


    exports me.divkix.cse360project;
}