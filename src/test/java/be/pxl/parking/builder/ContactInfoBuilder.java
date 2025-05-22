package be.pxl.parking.builder;

import be.pxl.parking.domain.ContactInfo;

public final class ContactInfoBuilder {
    private String phoneNumber;
    private String email;

    private ContactInfoBuilder() {
    }

    public static ContactInfoBuilder aContactInfo() {
        return new ContactInfoBuilder();
    }

    public ContactInfoBuilder withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public ContactInfoBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public ContactInfo build() {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setPhoneNumber(phoneNumber);
        contactInfo.setEmail(email);
        return contactInfo;
    }
}
