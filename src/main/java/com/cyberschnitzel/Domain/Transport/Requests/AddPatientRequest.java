package com.cyberschnitzel.Domain.Transport.Requests;

public class AddPatientRequest extends MessageRequest{
    private String cnp;
    private String name;

    public AddPatientRequest(String email, String password, String token, String cnp, String name) {
        super(email, password, token);
        this.cnp = cnp;
        this.name = name;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
