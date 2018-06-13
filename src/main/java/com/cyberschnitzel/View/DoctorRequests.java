package com.cyberschnitzel.View;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.Request;
import com.vaadin.ui.*;

public class DoctorRequests extends VerticalLayout {
    private Grid<Request> requestGrid;
    private ControlPanel controlPanel;
    DoctorRequests(MainWindow mainWindow, ControlPanel controlPanel) {
        mainWindow.getPage().setTitle("My requests");
        this.controlPanel = controlPanel;
        Button backButton = new Button("Go back");
        backButton.addClickListener(e -> mainWindow.setContent(controlPanel.getLayout()));

        requestGrid = new Grid<>();
        requestGrid.setItems(Controller.getAllRequests().stream().filter(request -> request.getDoctorId() == MainWindow.doctorID));
        requestGrid.addColumn(Request::getId).setCaption("Request ID");
        requestGrid.addColumn(Request::getQuantity).setCaption("Qty");
        requestGrid.addColumn(Request::getStatus).setCaption("Status");

        Layout l = new HorizontalLayout();
        l.addComponent(requestGrid);
        l.addComponent(backButton);
        addComponent(l);
    }

}
