module com.ifpb.todolist {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ifpb.todolist to javafx.fxml;
    exports com.ifpb.todolist;
}