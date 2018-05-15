package com.cyberschnitzel.View;

import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


public class DoctorView extends VerticalLayout {

    private MainWindow mainWindow;
    private TextField quantity;
    private TextField urgency;
    private TextField patientName;
    private Button backButton;
    private final VerticalLayout layout = new VerticalLayout();

    DoctorView(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        backButton = new Button("Go back");
        backButton.addClickListener(e -> mainWindow.goToMainView());

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


