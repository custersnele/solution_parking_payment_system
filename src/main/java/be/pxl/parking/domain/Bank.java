package be.pxl.parking.domain;

public enum Bank {
    KBC("KREDBEBB"),
    BELFIUS("GKCCBEBB");

    private String bic;

    Bank(String bic) {
        this.bic = bic;
    }

    public String getBic() {
        return bic;
    }
}
