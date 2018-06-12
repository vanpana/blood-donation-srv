package com.cyberschnitzel.View;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.BloodType;
import com.cyberschnitzel.Domain.Entities.Location;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.cyberschnitzel.Util.FirebaseUtil;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.Query;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class RequestBlood {

    private MainWindow mainWindow;
    private TextField quantity;
    private Button createRequest;
    private Button backButton;
    private Label label;
    private ControlPanel controlPanel;
    private final VerticalLayout layout = new VerticalLayout();
    private int selectedID;

    // Dropdowns
    private ComboBox<String> urgencyChooser;
    private ComboBox<String> bloodTypeChooser;
    private ComboBox<String> locationChooser;
    private ComboBox<String> bloodPartTypeChooser;


    // Dropdowns selected
    private String selectedUrgency;
    private String selectedBloodType;
    private String selectedLocation;
    private String selectedBloodPartType;


    private List<Location> locations;

    RequestBlood(MainWindow mainWindow, ControlPanel controlPanel) {
        this.mainWindow = mainWindow;
        this.controlPanel = controlPanel;
        label = new Label();

        mainWindow.getPage().setTitle("Doctor");

        backButton = new Button("Go back");
        backButton.addClickListener(e -> mainWindow.setContent(controlPanel.getLayout()));

        createRequest = new Button("Create request");
        createRequest.addClickListener(e -> label.setValue(addRequest()));

        quantity = new TextField("Quantity:");

        // Init combo boxes
        loadFirstLocationInDropDown();
        loadFirstBloodTypeInDropDown();
        loadFirstUrgencyInDropDown();
        loadFirstBloodPartTypeInDropDown();

        Layout l = new HorizontalLayout();
        l.addComponent(backButton);
        l.addComponent(createRequest);

        layout.addComponent(quantity);
        layout.addComponent(urgencyChooser);
        layout.addComponent(bloodTypeChooser);
        layout.addComponent(locationChooser);
        layout.addComponent(bloodPartTypeChooser);
        layout.addComponent(label);
        layout.addComponent(l);

    }

    private String addRequest() {
        try {

            if (quantity.getValue().equals("")) {
                throw new ControllerException("Invalid quantity");
            }
            float q = Float.parseFloat(quantity.getValue());
            int urgencyInt = Integer.parseInt(urgencyChooser.getValue());

            BloodType bloodTypeEnum = BloodType.getByString(bloodTypeChooser.getValue());

            if (!(bloodPartTypeChooser.getValue().equals("Blood")) && !(bloodPartTypeChooser.getValue().equals("Red cells")) &&
                    !(bloodPartTypeChooser.getValue().equals("Thrombocites")) && !(bloodPartTypeChooser.getValue().equals("Plasma"))) {

                throw new ControllerException("Wrong Blood part type");
            }
            int requestID = Controller.addRequest(q, urgencyInt, bloodTypeEnum, selectedID, bloodPartTypeChooser.getValue(), MainWindow.doctorID);

            for (int userID : Controller.getDonatorsForNotify(requestID)) {
                // TODO: Replace with firebase token
                System.out.println(userID);
//                FirebaseUtil.sendAsyncNotification(Controller.getDonatorByEmail(userID).getFirebase_token());
            }
        } catch (Exception ignored) {
            return ignored.getMessage();
        }
        return "Successfully added";
    }

    private void loadFirstUrgencyInDropDown() {
        // Init the combo box
        urgencyChooser = new ComboBox<>("Urgency");

        // Set the dropdown menu
        setupUrgencyDropDown();

        urgencyChooser.setSelectedItem(urgencyChooser.getDataProvider().fetch(new Query<>()).collect(Collectors.toList()).get(0));
    }

    private void loadFirstLocationInDropDown() {
        // Init the combo box
        locationChooser = new ComboBox<>("Location");

        // Set the dropdown menu
        setupLocationDropDown();

        // Check if listings exist
        if (locations.size() > 0) {
            // Get the first listing
            Location location = locations.get(0);
            selectedID = location.getId();

            // Set the listing as the current selected
            locationChooser.setSelectedItem(locations.get(0).getName());

        } else {
            new Notification("No locations yet!",
                    "Come back later",
                    Notification.Type.HUMANIZED_MESSAGE)
                    .show(Page.getCurrent());
        }
    }

    private void loadFirstBloodTypeInDropDown() {
        // Init the combo box
        bloodTypeChooser = new ComboBox<>("Blood Type");

        // Set the dropdown menu
        setupBloodTypesDropDown();

        bloodTypeChooser.setSelectedItem(bloodTypeChooser.getDataProvider().fetch(new Query<>()).collect(Collectors.toList()).get(0));
    }

    private void loadFirstBloodPartTypeInDropDown() {
        // Init the combo box
        bloodPartTypeChooser = new ComboBox<>("Blood Part Type");

        // Set the dropdown menu
        setupBloodPartDropDown();

        bloodPartTypeChooser.setSelectedItem(bloodPartTypeChooser.getDataProvider().fetch(new Query<>()).collect(Collectors.toList()).get(0));
    }

    private void setupLocationDropDown() {
        try {
            // Populate listings that have the checking status
            locations = Controller.getAllLocations();

            // Populate combo box
            ArrayList<String> locations_string = new ArrayList<>();

            // Get the strings from the listings
            for (Location location : locations)
                locations_string.add(location.getName());

            // Set the items
            locationChooser.setItems(locations_string);

            // Don't let the user input text
            locationChooser.setTextInputAllowed(false);

            // Don't let the user choose empty selection
            locationChooser.setEmptySelectionAllowed(false);

            // Add change listener
            locationChooser.addValueChangeListener((HasValue.ValueChangeListener<String>) event -> {
                if (!event.getValue().equals("")) {
                    selectedLocation = event.getValue();
                    if (selectedLocation != null) {
                        selectedID = getLocationID();
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

    private void setupBloodTypesDropDown() {
        try {
            // Populate listings that have the checking status
            ArrayList<String> bloodTypes = new ArrayList<>();
            Arrays.stream(BloodType.values()).forEach(bloodType -> bloodTypes.add(bloodType.name()));

            // Set the items
            bloodTypeChooser.setItems(bloodTypes);

            // Don't let the user input text
            bloodTypeChooser.setTextInputAllowed(false);

            // Don't let the user choose empty selection
            bloodTypeChooser.setEmptySelectionAllowed(false);

            // Add change listener
            bloodTypeChooser.addValueChangeListener((HasValue.ValueChangeListener<String>) event -> {
                if (!event.getValue().equals(""))
                    selectedBloodType = event.getValue();
            });

        } catch (Exception e) {
            new Notification("Controller failed",
                    e.getMessage(),
                    Notification.Type.ERROR_MESSAGE)
                    .show(Page.getCurrent());

        }
    }

    private void setupUrgencyDropDown() {
        try {
            // Populate listings that have the checking status
            ArrayList<String> urgencies = new ArrayList<>(Arrays.asList("1", "2", "3"));

            // Set the items
            urgencyChooser.setItems(urgencies);

            // Don't let the user input text
            urgencyChooser.setTextInputAllowed(false);

            // Don't let the user choose empty selection
            urgencyChooser.setEmptySelectionAllowed(false);

            // Add change listener
            urgencyChooser.addValueChangeListener((HasValue.ValueChangeListener<String>) event -> {
                if (!event.getValue().equals(""))
                    selectedUrgency = event.getValue();
            });

        } catch (Exception e) {
            new Notification("Controller failed",
                    e.getMessage(),
                    Notification.Type.ERROR_MESSAGE)
                    .show(Page.getCurrent());

        }
    }

    private void setupBloodPartDropDown() {
        try {
            // Populate listings that have the checking status
            ArrayList<String> urgencies = new ArrayList<>(Arrays.asList("Blood", "Red cells", "Plasma", "Thrombocites"));

            // Set the items
            bloodPartTypeChooser.setItems(urgencies);

            // Don't let the user input text
            bloodPartTypeChooser.setTextInputAllowed(false);

            // Don't let the user choose empty selection
            bloodPartTypeChooser.setEmptySelectionAllowed(false);

            // Add change listener
            bloodPartTypeChooser.addValueChangeListener((HasValue.ValueChangeListener<String>) event -> {
                if (!event.getValue().equals(""))
                    selectedBloodPartType = event.getValue();
            });

        } catch (Exception e) {
            new Notification("Controller failed",
                    e.getMessage(),
                    Notification.Type.ERROR_MESSAGE)
                    .show(Page.getCurrent());

        }
    }

    private int getLocationID() {
        for (Location location : locations) {
            if (location.getName().equals(selectedLocation)) {
                return location.getId();
            }
        }
        return -1;
    }

    public VerticalLayout getLayout() {
        return layout;
    }
}


