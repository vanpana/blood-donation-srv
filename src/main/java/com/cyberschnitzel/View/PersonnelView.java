package com.cyberschnitzel.View;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class PersonnelView extends VerticalLayout {
    private MainWindow mainWindow;
    private ControlPanel controlPanel;
    private final VerticalLayout verticalLayout = new VerticalLayout();
    private Button backButton;

    PersonnelView(MainWindow mainWindow, ControlPanel controlPanel) {
        this.mainWindow = mainWindow;
        this.controlPanel = controlPanel;

        backButton = new Button("Go back");
        mainWindow.getPage().setTitle("Personnel");
        backButton.addClickListener(e->mainWindow.setContent(controlPanel.getLayout()));

        verticalLayout.addComponent(backButton);
    }

    public VerticalLayout getVerticalLayout() {
        return verticalLayout;
    }
}
