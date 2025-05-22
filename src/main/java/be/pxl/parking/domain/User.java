package be.pxl.parking.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Embedded
    private ContactInfo contactInfo;
    @Embedded
    private BankAccountDetails bankAccountDetails;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LicensePlate> licensePlates = new ArrayList<>();

    public User(String firstName, String lastName, ContactInfo contactInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactInfo = contactInfo;
    }

    public User() {
        // JPA only
    }



    public void setBankAccountDetails(BankAccountDetails bankAccountDetails) {
        this.bankAccountDetails = bankAccountDetails;
    }

    public void addLicensePlate(String licensePlate) {
        LicensePlate licensePateEntity = new LicensePlate(this, licensePlate);
        licensePlates.add(licensePateEntity);
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public BankAccountDetails getBankAccountDetails() {
        return bankAccountDetails;
    }
}
