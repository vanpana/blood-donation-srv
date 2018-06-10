package com.cyberschnitzel.View;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.BloodType;
import com.cyberschnitzel.Domain.Entities.Location;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.vaadin.data.HasValue;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;


public class DoctorView {

    private MainWindow mainWindow;
    private TextField quantity;
    private TextField urgency;
    private TextField bloodType;
    private ComboBox<String> listingsChooser;
    private TextField bloodPartType;
    private Button createRequest;
    private Button backButton;
    private Label label;
    private ControlPanel controlPanel;
    private final VerticalLayout layout = new VerticalLayout();
    private int selectedID;
    private String selectedLocation;
    private List<Location> locations;

    DoctorView(MainWindow mainWindow, ControlPanel controlPanel) {
        this.mainWindow = mainWindow;
        this.controlPanel = controlPanel;
        label = new Label();

        mainWindow.getPage().setTitle("Doctor");

        backButton = new Button("Go back");
        backButton.addClickListener(e -> mainWindow.setContent(controlPanel.getLayout()));

        createRequest = new Button("Create request");
        createRequest.addClickListener(e-> label.setValue(addRequest()));

        quantity = new TextField("Quantity:");
        urgency = new TextField("Urgency level (between 1-3):");
        bloodType = new TextField("Blood Type:");
        bloodPartType = new TextField("Blood Part Type:");
        loadFirstListing();

        Layout l = new HorizontalLayout();
        l.addComponent(backButton);
        l.addComponent(createRequest);

        layout.addComponent(quantity);
        layout.addComponent(urgency);
        layout.addComponent(bloodType);
        layout.addComponent(listingsChooser);
        layout.addComponent(bloodPartType);
        layout.addComponent(label);
        layout.addComponent(l);

    }

    private String addRequest(){
        try {

            if (quantity.getValue().equals("")) {
                throw new ControllerException("Invalid quantity");
            }
            Float q = Float.valueOf(quantity.getValue());
            Integer urgencyInt = Integer.parseInt(urgency.getValue());
            switch (urgencyInt){
                case 1: break;
                case 2: break;
                case 3: break;
                default: throw new ControllerException("urgency not ok");
            }
            BloodType bloodTypeEnum;
            switch (bloodType.getValue()){
                case "A": bloodTypeEnum = BloodType.A;break;
                case "B": bloodTypeEnum = BloodType.B;break;
                case "AB": bloodTypeEnum = BloodType.AB;break;
                case "0": bloodTypeEnum = BloodType.ZERO;break;
                default: throw new ControllerException("Wrong Blood type");
            }
            if(!(bloodPartType.getValue().equals("Blood")) && !(bloodPartType.getValue().equals("Red cells")) &&
                    !(bloodPartType.getValue().equals("Thrombocites")) && !(bloodPartType.getValue().equals("Plasma"))){

                throw new ControllerException("Wrong Blood part type");
            }
            int requestID = Controller.addRequest(q, urgencyInt, bloodTypeEnum, selectedID, bloodPartType.getValue(), 1);

        } catch (Exception ignored){
            return ignored.getMessage();
        }
        return "Successfully added";
    }

    private void loadFirstListing() {
        // Init the combo box
        listingsChooser = new ComboBox<>();

        // Set the dropdown menu
        setupDropBox();

        // Check if listings exist
        if (locations.size() > 0) {
            // Get the first listing
            Location location = locations.get(0);
            selectedID = location.getId();

            // Set the listing as the current selected
            listingsChooser.setSelectedItem(locations.get(0).getName());

        } else {
            new Notification("No listings yet!",
                    "Come back later",
                    Notification.Type.HUMANIZED_MESSAGE)
                    .show(Page.getCurrent());
        }
    }

    private void setupDropBox() {
        try {
            // Populate listings that have the checking status
            locations = Controller.getAllLocations();

            // Populate combo box
            ArrayList<String> locations_string = new ArrayList<>();

            // Sort the items by ID
//            locations.sort(Collections.reverseOrder());

            // Get the strings from the listings
            for (Location location : locations)
                locations_string.add(location.getName());

            // Set the items
            listingsChooser.setItems(locations_string);

            // Don't let the user input text
            listingsChooser.setTextInputAllowed(false);

            // Don't let the user choose empty selection
            listingsChooser.setEmptySelectionAllowed(false);

            // Add change listener
            listingsChooser.addValueChangeListener((HasValue.ValueChangeListener<String>) event -> {
                if (!event.getValue().equals("")) {
                    selectedLocation = event.getValue();
                    if (selectedLocation != null) {
                        selectedID = getListingId();
                    }
                }
            });

        } catch (Exception e) {
            new Notification("Controller failed",
                    e.getMessage(),
                    Notification.Type.ERROR_MESSAGE)
                    .show(Page.getCurrent());

        }
    }

    private int getListingId(){
        for(Location location: locations){
            if(location.getName().equals(selectedLocation)){
                return location.getId();
            }
        }
        return -1;
    }

    public VerticalLayout getLayout() {
        return layout;
    }
}


