package Classes;

import Application.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Application.Constants.CSV_NULL;
import static Application.Helpers.getMonthNumber;

public class ContactManager {
    private ArrayList<Contact> contacts;
    private static int contactCounter = 0;

    public ContactManager(){
        contacts = new ArrayList<Contact>();
    }

    public Contact newContact(String firstName, String lastName, String homePhone, String workPhone, Address homeAddress, String email, MyDate birthday, String notes){
        Contact contact = new Contact(firstName, lastName, homePhone, workPhone, homeAddress, email, birthday, notes);
        contacts.add(contact);
        contactCounter++;
        return contact;
    }

    public Contact generateTemporaryContact(String contactId, String firstName, String lastName, String homePhone, String workPhone, Address homeAddress, String email, MyDate birthday, String notes){
        return new Contact(contactId, firstName, lastName, homePhone, workPhone, homeAddress, email, birthday, notes);
    }

    public int getContactCounter(){ return contactCounter; }

    public ArrayList<Contact> getContacts(){ return contacts; }

    public void loadContactsFromFile() throws IOException {
        String record = "";
        String file = Constants.CONTACTS_FILE_LOCATION + Constants.CONTACTS_FILE_NAME;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while((record = reader.readLine()) != null){
            String[] temp = record.split(",");
            String imageId = temp[0].equals(CSV_NULL) ? "" : temp[0];
            String fName = temp[1].equals(CSV_NULL) ? "" : temp[1];
            String lName = temp[2].equals(CSV_NULL) ? "" : temp[2];
            String hPhone = temp[3].equals(CSV_NULL) ? "" : temp[3];
            String wPhone = temp[4].equals(CSV_NULL) ? "" : temp[4];
            String email = temp[5].equals(CSV_NULL) ? "" : temp[5];
            String lineOne = temp[6].equals(CSV_NULL) ? "" : temp[6];
            String lineTwo = temp[7].equals(CSV_NULL) ? "" : temp[7];
            String city = temp[8].equals(CSV_NULL) ? "" : temp[8];
            String province = temp[9].equals(CSV_NULL) ? "" : temp[9];
            String country = temp[10].equals(CSV_NULL) ? "" : temp[10];
            String postCode = temp[11].equals(CSV_NULL) ? "" : temp[11];
            String notes = temp[15].equals(CSV_NULL) ? "" : temp[15];
            Address tempAddress = new Address(lineOne, lineTwo, city, postCode, province, country);
            MyDate tempDate = new MyDate(Integer.parseInt(temp[12]), getMonthNumber(temp[13]), Integer.parseInt(temp[14]));
            loadContactRowFromFile(imageId, fName, lName, hPhone, wPhone, tempAddress, email, tempDate, notes);
        }
        reader.close();
    }

    public boolean saveContactsToFile(boolean appendFile) throws IOException {
        String fileName = Constants.CONTACTS_FILE_LOCATION + Constants.CONTACTS_FILE_NAME;
        File file = new File(fileName);
        if(!appendFile){
            file.delete();
        }
        file.createNewFile();
        try {
            FileWriter writer = new FileWriter(fileName, appendFile);
            for(Contact contact : contacts) {
                String id = contact.getContactId().equals("") ? CSV_NULL : contact.getContactId();
                String fName = contact.getFirstName().equals("") ? CSV_NULL : contact.getFirstName();
                String lName = contact.getLastName().equals("") ? CSV_NULL : contact.getLastName();
                String hPhone = contact.getHomePhone().equals("") ? CSV_NULL : contact.getHomePhone();
                String wPhone = contact.getWorkPhone().equals("") ? CSV_NULL : contact.getWorkPhone();
                String email = contact.getEmail().equals("") ? CSV_NULL : contact.getEmail();
                String lineOne = contact.getHomeAddress().streetInfo1.equals("") ? CSV_NULL : contact.getHomeAddress().streetInfo1;
                String lineTwo = contact.getHomeAddress().streetInfo2.equals("") ? CSV_NULL : contact.getHomeAddress().streetInfo2;
                String city = contact.getHomeAddress().city.equals("") ? CSV_NULL : contact.getHomeAddress().city;
                String province = contact.getHomeAddress().province.equals("") ? CSV_NULL : contact.getHomeAddress().province;
                String country = contact.getHomeAddress().country.equals("") ? CSV_NULL : contact.getHomeAddress().country;
                String postCode = contact.getHomeAddress().postalCode.equals("") ? CSV_NULL : contact.getHomeAddress().postalCode;
                String notes = contact.getNotes().equals("") ? CSV_NULL : contact.getNotes();
                int day = contact.getBirthday().getDay();
                int month = getMonthNumber(contact.getBirthday().getMonthLongForm());
                int year = contact.getBirthday().getYear();
                Contact temporary = generateTemporaryContact(id, fName, lName, hPhone, wPhone, new Address(lineOne, lineTwo, city, postCode, province, country), email, new MyDate(day, month, year), notes);
                writer.append(String.join(",", generateContactRow(temporary)));
                writer.append("\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateContact(Contact original, Contact updated){
        int index = 0;
        for(int i = 0; i < contactCounter; i++){
            if(contacts.get(i) == original){
                index = i;
                break;
            }
        }
        contacts.set(index, updated);
        return contacts.get(index) == updated;
    }

    public boolean deleteContact(Contact contactToDelete){
        boolean contactDeleted = false;
        String fileName = contactToDelete.getContactId() + ".png";
        String destinationPath = new File("Profiles").getAbsolutePath() + "\\" + fileName;
        File contactImage = new File(destinationPath);
        contactDeleted = (contacts.remove(contactToDelete)) && (contactImage.delete());
        return contactDeleted;
    }


    private List<String> generateContactRow(Contact contact){
        return Arrays.asList(contact.getContactId(), contact.getFirstName(), contact.getLastName(), contact.getHomePhone(), contact.getWorkPhone(), contact.getEmail(), contact.getHomeAddress().streetInfo1, contact.getHomeAddress().streetInfo2, contact.getHomeAddress().city, contact.getHomeAddress().province, contact.getHomeAddress().country, contact.getHomeAddress().postalCode, String.valueOf(contact.getBirthday().getDay()), contact.getBirthday().getMonthShortForm(), String.valueOf(contact.getBirthday().getYear()), contact.getNotes());
    }

    private void loadContactRowFromFile(String contactId, String firstName, String lastName, String homePhone, String workPhone, Address homeAddress, String email, MyDate birthday, String notes){
        Contact contact = new Contact(contactId, firstName, lastName, homePhone, workPhone, homeAddress, email, birthday, notes);
        contacts.add(contact);
        contactCounter++;
    }
}
