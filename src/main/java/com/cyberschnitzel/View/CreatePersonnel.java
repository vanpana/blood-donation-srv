package com.cyberschnitzel.View;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.vaadin.ui.*;

public class CreatePersonnel extends VerticalLayout {
    private TextField name;
    private TextField email;
    private Label label;
    private final VerticalLayout verticalLayout = new VerticalLayout();
    private ControlPanel controlPanel;
    CreatePersonnel(MainWindow mainWindow,ControlPanel controlPanel) {
        mainWindow.getPage().setTitle("Personnel");
        this.controlPanel=controlPanel;
        name = new TextField("Name:");
        email = new TextField("E-mail:");
        label = new Label();

        Button addButton = new Button("Add personnel");
        addButton.addClickListener(e -> label.setValue(createPersonnel()));
        Button backButton = new Button("Go back");
        backButton.addClickListener(e -> mainWindow.setContent(controlPanel.getLayout()));

        Layout l = new HorizontalLayout();
        l.addComponent(addButton);
        l.addComponent(backButton);
        verticalLayout.addComponent(name);
        verticalLayout.addComponent(email);
        verticalLayout.addComponent(l);
    }

    private String createPersonnel() {
        try {
            if (name.getValue().equals(""))
                throw new ControllerException("Empty name");
            if (email.getValue().equals(""))
                throw new ControllerException("Empty email");
            Controller.addPersonnel(name.getValue(), email.getValue());
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Personnel added successfully";
    }

    public VerticalLayout getVerticalLayout() {
        return verticalLayout;
    }
}
