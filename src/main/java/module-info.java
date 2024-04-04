module me.divkix.cse360project {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.sql;
    requires mysql.connector.j;

    opens me.divkix.cse360project to javafx.fxml;

    exports me.divkix.cse360project;
    exports me.divkix.cse360project.screens;
    opens me.divkix.cse360project.screens to javafx.fxml;
    exports me.divkix.cse360project.helperFunctions;
    opens me.divkix.cse360project.helperFunctions to javafx.fxml;
}