package com.example;
import java.io.Serializable;
import java.util.Objects;

import com.google.gson.Gson;


public class Contact implements Comparable<Contact> ,Serializable{
    private String fname;
    private String lname;
    private String address;
    private String city;
    private String zip;
    private String state;
    private String phone;
    private String email;

    public Contact(String fname, String lname, String address, String city, String state, String zip, String phone,
            String email) {
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.state = state;
        this.phone = phone;
        this.email = email;

        if (!isValidName(fname) || !isValidName(lname)) {
            throw new IllegalArgumentException("Invalid name format");
        }

        if (!isValidPhone(phone)) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void updateContact(String address, String city, String state, String zip, String phone, String email) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    
    private boolean isValidName(String name) {
        String nameRegex = "^[A-Z][a-z]{2,}$";
        //System.out.println(name.matches(nameRegex));
        return name.matches(nameRegex);

    }

    private boolean isValidPhone(String phone) {
        String phoneRegex = "^[0-9]{10}$";
        //System.out.println(phone.matches(phoneRegex));
        return phone.matches(phoneRegex);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        //System.out.println(email.matches(emailRegex));
        return email.matches(emailRegex);
    }


    @Override
    public int compareTo(Contact other) {
        // Compare by last name, then first name
        int result = lname.compareToIgnoreCase(other.getLname());
        if (result == 0) {
            result = fname.compareToIgnoreCase(other.getFname());
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Contact contact = (Contact) obj;
        return Objects.equals(getFname(), contact.getFname()) &&
                Objects.equals(getLname(), contact.getLname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFname(), getLname());
    }

    @Override
    public String toString() {
        return String.format(
                "NAME : %s %s\nADDRESS : %s\nCITY : %s\nSTATE :%s\nZIP : %s\nPHONE NUMBER : %s\nEMAIL : %s\n",
                fname, lname, address, city, state, zip, phone, email);
    }
    public static Contact parseFromString(String data) {
        String[] contactData = data.split(",");
        
        if (contactData.length == 8) {
           
            return new Contact(
                    contactData[0], contactData[1], contactData[2],
                    contactData[3], contactData[4], contactData[5],
                    contactData[6], contactData[7]
            );
        } else {
            
            throw new IllegalArgumentException("Invalid data format: " + data);
        }
    }
    


    public static Contact parseFromString(String data, boolean hasHeader) {
        String[] contactData = data.split(",");

        int startIndex = hasHeader ? 1 : 0;
    
        if (contactData.length == 8) {
            return new Contact(
                    contactData[startIndex], contactData[startIndex + 1], contactData[startIndex + 2],
                    contactData[startIndex + 3], contactData[startIndex + 4], contactData[startIndex + 5],
                    contactData[startIndex + 6], contactData[startIndex + 7]
            );
        } else {
            throw new IllegalArgumentException("Invalid data format: " + data);
        }
    }

    public static Contact fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Contact.class);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }   
    
}


