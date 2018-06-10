package com.cyberschnitzel.View;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;


public class ControlPanel extends VerticalLayout {
    private MainWindow mainWindow;
    private final VerticalLayout layout = new VerticalLayout();
    private Button buttonDoctor;
    private Button buttonPersonnel;
    private Button backButton;

    ControlPanel(MainWindow mainWindow){
        this.mainWindow = mainWindow;

        mainWindow.getPage().setTitle("Control Panel");
        backButton = new Button("Go back");
        backButton.addClickListener(e->mainWindow.goToMainView());
        buttonDoctor = new Button("Add a request");
        buttonDoctor.addClickListener(e-> mainWindow.setContent(new RequestBlood(mainWindow,this).getLayout()));
        buttonPersonnel = new Button("Add personnel");
        buttonPersonnel.addClickListener(e-> mainWindow.setContent(new CreatePersonnel(mainWindow).getVerticalLayout()));

        layout.addComponent(buttonDoctor);
        layout.addComponent(buttonPersonnel);
        layout.addComponent(backButton);
    }

    public VerticalLayout getLayout() {
        return layout;
    }
}
