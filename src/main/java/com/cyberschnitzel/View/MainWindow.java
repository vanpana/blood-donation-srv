package com.cyberschnitzel.View;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.swing.*;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.CredentialsEntity;
import com.cyberschnitzel.Domain.Entities.Doctor;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.cyberschnitzel.Domain.Transport.Requests.MessageRequest;
import com.cyberschnitzel.Util.DatabaseUtil;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import java.awt.*;
import java.io.IOException;


/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MainWindow extends UI {
    private static final boolean PRODUCTION_MODE = false;
    public static int doctorID;
    private final VerticalLayout layout = new VerticalLayout();

    @Override
    public void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("Doctor Login");

        final TextField username = new TextField();
        username.setCaption("Username:");

        final PasswordField password = new PasswordField();
        password.setCaption("Password:");


        Button button = new Button("Log in");
        button.addClickListener(e -> {
            Label label = new Label(login(username.getValue(), password.getValue()));
            layout.addComponent(label);
            layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        });

        layout.addComponent(username);
        layout.addComponent(password);
        layout.addComponent(button);

        layout.setComponentAlignment(username, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        setContent(layout);
     
            System.out.println(Controller.getDonatorsForNotify(1));

    }

    private String login(String email, String password) {
        try {
            Controller.checkCredentialsNoToken(email, password, CredentialsEntity.EntityType.DOCTOR);
            Doctor doctor = Controller.getDoctorByEmail(email);

            if (doctor != null) {
                doctorID = doctor.getId();
            }

            ControlPanel view = new ControlPanel(this);
            setContent(view.getLayout());
            return "Successfully logged in";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public void goToMainView() {
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "BloodDonationServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainWindow.class, productionMode = PRODUCTION_MODE)
    public static class MyUIServlet extends VaadinServlet {
        // Override this function to do stuff when server initializes (get connection to DB, start schedulers etc)

        public MyUIServlet() {
            super();
        }

        @Override
        protected void servletInitialized() throws ServletException {
            super.servletInitialized();

            initializeServices();
        }

        // Override this function to do stuff when server finishes normally (stop connection to DB, stop threads etc)
        @Override
        public void destroy() {
            super.destroy();

            teardownServices();
        }

        /**
         * This method will start a thread to initialize the Database etc
         */
        private void initializeServices() {
            new Thread(() -> {

                // This will get the database connection
                // noinspection Convert2MethodRef
                DatabaseUtil.getConnection();
            }).run();
        }

        /**
         * This method will do the cleanup
         */
        private void teardownServices() {
            DatabaseUtil.closeConnection();
        }
    }
}
