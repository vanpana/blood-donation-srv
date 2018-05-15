package com.cyberschnitzel.View;

import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


public class DoctorView {

    private MainWindow mainWindow;
    private TextField quantity;
    private TextField urgency;
    private TextField patientName;
    private Button backButton;
    private ControlPanel controlPanel;
    private final VerticalLayout layout = new VerticalLayout();

    DoctorView(MainWindow mainWindow, ControlPanel controlPanel) {
        this.mainWindow = mainWindow;
        this.controlPanel = controlPanel;

        mainWindow.getPage().setTitle("Doctor");

        backButton = new Button("Go back");
        backButton.addClickListener(e -> mainWindow.setContent(controlPanel.getLayout()));

        patientName = new TextField("Patient name: ");
        quantity = new TextField("Quantity:");
        urgency = new TextField("Urgency level (between 1-3):");

        layout.addComponent(patientName);
        layout.addComponent(quantity);
        layout.addComponent(urgency);
        layout.addComponent(backButton);
    }

    public VerticalLayout getLayout() {
        return layout;
    }
}


