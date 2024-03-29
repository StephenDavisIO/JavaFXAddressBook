package Classes;

import java.util.UUID;

public class Contact {
    private String contactId;
    private String firstName;
    private String lastName;
    private String homePhone;
    private String workPhone;
    private Address homeAddress;
    private String email;
    private MyDate birthday;
    private String notes;

    public Contact(){}
    public Contact(String firstName, String lastName, String homePhone, String workPhone, Address homeAddress, String email, MyDate birthday, String notes){
        contactId = UUID.randomUUID().toString();
        //contactId = "b5cea1ed-1979-481d-9cc6-3096904d8594";
        this.firstName = firstName;
        this.lastName = lastName;
        this.homePhone = homePhone;
        this.workPhone = workPhone;
        this.homeAddress = homeAddress;
        this.email = email;
        this.birthday = birthday;
        this.notes = notes;
    }
    public Contact(String contactId, String firstName, String lastName, String homePhone, String workPhone, Address homeAddress, String email, MyDate birthday, String notes){
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.homePhone = homePhone;
        this.workPhone = workPhone;
        this.homeAddress = homeAddress;
        this.email = email;
        this.birthday = birthday;
        this.notes = notes;
    }

    public String getContactId(){ return contactId; }
    public void setContactId(String contactId){ this.contactId = contactId; }
    public String getFirstName(){ return firstName; }
    public void setFirstName(String firstName){ this.firstName = firstName; }
    public String getLastName(){ return lastName; }
    public void setLastName(String lastName){ this.lastName = lastName; }
    public String getHomePhone(){ return homePhone; }
    public void setHomePhone(String homePhone){ this.homePhone = homePhone; }
    public String getWorkPhone(){ return workPhone; }
    public void setWorkPhone(String workPhone){ this.workPhone = workPhone; }
    public Address getHomeAddress(){ return homeAddress; }
    public void setHomeAddress(Address homeAddress){ this.homeAddress = homeAddress; }
    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }
    public MyDate getBirthday(){ return birthday; }
    public void setBirthday(MyDate birthday){ this.birthday = birthday; }
    public String getNotes(){ return notes; }
    public void setNotes(String notes){ this.notes = notes; }

    @Override
    public String toString(){
        return  "--- CONTACT DETAILS ---\n" +
                "First Name: " + firstName + "\n" +
                "Last Name: " + lastName + "\n" +
                "Home Phone: " + homePhone + "\n" +
                "Work Phone: " + workPhone + "\n" +
                "Address: " + homeAddress.toString() + "\n" +
                "Email Address: " + email + "\n" +
                "Birthday: " + birthday.toString() + "\n" +
                "Notes: " + notes + "\n" +
                "-----";
    }
}
