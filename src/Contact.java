import java.util.Objects;

public class Contact implements Comparable<Contact> {
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
        System.out.println(name.matches(nameRegex));
        return name.matches(nameRegex);

    }

    private boolean isValidPhone(String phone) {
        String phoneRegex = "^[0-9]{10}$";
        System.out.println(phone.matches(phoneRegex));
        return phone.matches(phoneRegex);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        System.out.println(email.matches(emailRegex));
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
}


