package homework_7;

import java.util.Objects;

public class OldFashionedEmailAddress implements Comparable<OldFashionedEmailAddress> {

    private int houseNumber;
    private String streetName;
    private String nameOfTown;
    private String state;
    private int zipCode;

    public OldFashionedEmailAddress(
        int house,
        String street,
        String town,
        String stateOf,
        int zip
    ) {
        houseNumber = house;
        streetName = street;
        nameOfTown = town;
        state = stateOf;
        zipCode = zip;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setNameOfTown(String nameOfTown) {
        this.nameOfTown = nameOfTown;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getNameOfTown() {
        return nameOfTown;
    }

    public String getState() {
        return state;
    }

    public int getZipCode() {
        return zipCode;
    }

    @Override
    public String toString() {
        return String.format(
                "%d, %s, %s, %s, %d",
                houseNumber,
                streetName,
                nameOfTown,
                state.toUpperCase(),
                zipCode
        );
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        return Objects.equals(toString(), obj.toString());
    }

    @Override
    public int compareTo(OldFashionedEmailAddress o) {
        String incoming_str = o.toString();
        String this_str = toString();
        int result = 0;

        for(int i = 0; i < incoming_str.length(); i++) {
            char i_c = incoming_str.charAt(i);
            char c_c = this_str.charAt(i);

            if(c_c < i_c) {
                result = -1;
                break;
            } else if(c_c > i_c) {
                result = 1;
                break;
            }
        }

        return result;
    }
}
