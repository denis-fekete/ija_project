module ija.ija2023.ija_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.base;

    opens ija.ija2023.ija_project to javafx.fxml;
    exports ija.ija2023.ija_project;
    exports ija.ija2023.ija_project.JavaSpecific;
}