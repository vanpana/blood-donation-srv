package com.cyberschnitzel.Handlers.Demo;

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
}
