package com.cyberschnitzel.View;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.cyberschnitzel.Util.DatabaseUtil;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;




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



    @Override
    public void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> layout.addComponent(new Label("Thanks " + name.getValue()
                + ", it works!")));
        
        layout.addComponents(name, button);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "BloodDonationServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainWindow.class, productionMode = PRODUCTION_MODE)
    public static class MyUIServlet extends VaadinServlet {
        // Override this function to do stuff when server initializes (get connection to DB, start schedulers etc)

        public MyUIServlet(){
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
