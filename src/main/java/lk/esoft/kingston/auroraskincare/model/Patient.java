package lk.esoft.kingston.auroraskincare.model;

public class Patient extends Person {
    private String nic;

    // Constructor
    public Patient(String nic, String name, String email, String phone) {
        super(name, email, phone);  // Call to superclass constructor
        this.nic = nic;
    }

    // Getters and Setters
    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    @Override
    public String toString() {
        return this.getName()+" "+this.getNic();
    }


}

