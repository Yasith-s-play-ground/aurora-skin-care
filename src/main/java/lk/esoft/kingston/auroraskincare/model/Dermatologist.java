package lk.esoft.kingston.auroraskincare.model;

public class Dermatologist extends Person {

    private int id;

    //Constructor
    public Dermatologist(String name, String email, String phone, int id) {
        super(name, email, phone);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}

