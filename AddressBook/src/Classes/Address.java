package Classes;

public class Address extends Contact {
    public String streetInfo1;
    public String streetInfo2;
    public String city;
    public String postalCode;
    public String province;
    public String country;

    public Address(){}
    public Address(String st1, String st2, String city, String postCode, String prov, String country){
        streetInfo1 = st1;
        streetInfo2 = st2;
        this.city = city;
        postalCode = postCode;
        province = prov;
        this.country = country;
    }

    private boolean isEmptyOrNull(String text){
        return (text.isEmpty() || text.isBlank());
    }

    @Override
    public String toString(){
        String text = "";

        if(!isEmptyOrNull(streetInfo1))
            text += (streetInfo1 + ", ");

        if(!isEmptyOrNull(streetInfo2))
            text += (streetInfo2 + ", ");

        if(!isEmptyOrNull(city))
            text += (city + ", ");

        if(!isEmptyOrNull(province))
            text += (province + ", ");

        if(!isEmptyOrNull(country))
            text += (country + ", ");

        if(!isEmptyOrNull(postalCode))
            text += (postalCode + ", ");

        if(!isEmptyOrNull(text)){
            text = text.trim();
            int length = text.length();
            text = text.substring(0, length - 1);
        } else {
            text = "No Address on Record";
        }

        return text;
    }
}
