module com.example.pt2022_30424_iaz_ania_assigment3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.desktop;

    opens com.example.pt2022_30424_iaz_ania_assigment3 to javafx.fxml;
    exports com.example.pt2022_30424_iaz_ania_assigment3;
    exports com.example.pt2022_30424_iaz_ania_assigment3.Presentation;
    opens com.example.pt2022_30424_iaz_ania_assigment3.Presentation to javafx.fxml;
    exports com.example.pt2022_30424_iaz_ania_assigment3.Connection;
    opens com.example.pt2022_30424_iaz_ania_assigment3.Connection to javafx.fxml;
    exports com.example.pt2022_30424_iaz_ania_assigment3.Model;
    opens com.example.pt2022_30424_iaz_ania_assigment3.Model to javafx.fxml;
    exports com.example.pt2022_30424_iaz_ania_assigment3.DataAccess;
    opens com.example.pt2022_30424_iaz_ania_assigment3.DataAccess to javafx.fxml;
    exports com.example.pt2022_30424_iaz_ania_assigment3.BusinessLogic;
    opens com.example.pt2022_30424_iaz_ania_assigment3.BusinessLogic to javafx.fxml;
}