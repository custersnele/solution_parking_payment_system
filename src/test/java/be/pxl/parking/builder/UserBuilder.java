package be.pxl.parking.builder;

import be.pxl.parking.domain.BankAccountDetails;
import be.pxl.parking.domain.ContactInfo;
import be.pxl.parking.domain.User;

public final class UserBuilder {
    private BankAccountDetails bankAccountDetails;
    private String firstName;
    private String lastName;
    private ContactInfo contactInfo;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withBankAccountDetails(BankAccountDetails bankAccountDetails) {
        this.bankAccountDetails = bankAccountDetails;
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
        return this;
    }

    public User build() {
        User user = new User(firstName, lastName, contactInfo);
        user.setBankAccountDetails(bankAccountDetails);
        return user;
    }
}
