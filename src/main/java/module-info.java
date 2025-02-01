module org.kfu.itis.allayarova.orissemesterwork2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens org.kfu.itis.allayarova.orissemesterwork2 to javafx.fxml;
    exports org.kfu.itis.allayarova.orissemesterwork2;
    exports org.kfu.itis.allayarova.orissemesterwork2.controllers;
    opens org.kfu.itis.allayarova.orissemesterwork2.controllers to javafx.fxml;
    exports org.kfu.itis.allayarova.orissemesterwork2.service;
    opens org.kfu.itis.allayarova.orissemesterwork2.service to javafx.fxml;
    exports org.kfu.itis.allayarova.orissemesterwork2.service.server;
    opens org.kfu.itis.allayarova.orissemesterwork2.service.server to javafx.fxml;
    exports org.kfu.itis.allayarova.orissemesterwork2.client.messageListener;
    opens org.kfu.itis.allayarova.orissemesterwork2.client.messageListener to javafx.fxml;
    exports org.kfu.itis.allayarova.orissemesterwork2.server;
    opens org.kfu.itis.allayarova.orissemesterwork2.server to javafx.fxml;
    exports org.kfu.itis.allayarova.orissemesterwork2.server.serverHandlers;
    opens org.kfu.itis.allayarova.orissemesterwork2.server.serverHandlers to javafx.fxml;
}