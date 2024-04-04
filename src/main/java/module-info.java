module me.divkix.cse360project {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.sql;
    requires mysql.connector.j;

    opens me.divkix.cse360project to javafx.fxml;

    exports me.divkix.cse360project;
}