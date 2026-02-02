package hospital;

public abstract class Person {

    private String name;
    private String id;
    private String address;
    private String phoneNumber;

    public Person(String name, String id, String address, String phoneNumber) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }

    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phoneNumber);
    }

    public abstract String getRole();  
}
