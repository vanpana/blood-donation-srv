package com.cyberschnitzel.Domain.Handlers.Demo;

import java.util.ArrayList;
import java.util.List;

public class Demo {

    /**
     * @return String - Standard greeting
     */
    public static String hello() {
        return "Hello world!";
    }

    /**
     * @param name - Name of the greeter
     * @return String - Custom greeting with name
     */
    public static String hello(String name) {
        return "Hello " + name + "!";
    }

    /**
     * Demo user class to show how to serialize and get user(s) using an API.
     */
    public static class User {
        private String name;
        private String email;
        private transient String password;  // transient specifies that the field will not be serialized

        public User(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    /**
     * @return List of users serialized as JSON
     */
    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "email1", "password1"));
        users.add(new User("user2", "email2", "password2"));
        users.add(new User("user3", "email3", "password3"));
        return users;
    }


    /**
     * @param name - name of the user to be searched
     * @return The user with the specified name
     */
    public static User getUser(String name) {
        for (User user : getUsers())
            if (user.getName().equals(name))
                return user;
        return null;
    }
}
