module com.ifpb.todolist {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.ifpb.todolist.janelas_exemplo;
    opens com.ifpb.todolist.janelas_exemplo to javafx.fxml;
}