package com.cyberschnitzel.View;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;


public class ControlPanel extends VerticalLayout {
    private MainWindow mainWindow;
    private final VerticalLayout layout = new VerticalLayout();

    ControlPanel(MainWindow mainWindow){
        this.mainWindow = mainWindow;

        mainWindow.getPage().setTitle("Control Panel");
        Button backButton = new Button("Go back");
        backButton.addClickListener(e->mainWindow.goToMainView());
        Button buttonDoctor = new Button("Add a request");
        buttonDoctor.addClickListener(e-> mainWindow.setContent(new RequestBlood(mainWindow,this).getLayout()));
        Button buttonPersonnel = new Button("Add personnel");
        buttonPersonnel.addClickListener(e-> mainWindow.setContent(new CreatePersonnel(mainWindow,this).getVerticalLayout()));
        Button myRequests = new Button("See my requests");
        myRequests.addClickListener(e-> mainWindow.setContent(new DoctorRequests(mainWindow,this)));

        layout.addComponent(buttonDoctor);
        layout.addComponent(buttonPersonnel);
        layout.addComponent(myRequests);
        layout.addComponent(backButton);
    }

    public VerticalLayout getLayout() {
        return layout;
    }
}
