import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {

    private final String name;
    private Integer balance;
    private final List<Property> ownedProperties = new ArrayList<>();

    private int currentPositionOnBoard;

    public Player(String name) {
        this.name = name;
        this.balance = 16;
    }

    public String getName() {
        return name;
    }

    public Integer getBalance() {
        return balance;
    }

    public void addAmountIntoBalance(int amount) {
        this.balance = getBalance() + amount;
    }

    public void deductAmountIntoBalance(int amount) {
        this.balance = getBalance() - amount;
    }

    public void ownProperty(Property property) {
        System.out.println(this.name + " Bought " + property.getName() + " For Price ( " + property.getPrice() + "$ )");
        ownedProperties.add(property);
        deductAmountIntoBalance(property.getPrice());
    }

    public void payRent(String owner, Property property, Integer amount) {
        System.out.println("( " + amount + "$ ) " + "Rent" + " Paid for property " + property.getName() + " [ " + this.name + " -> " + owner + " ]");
        deductAmountIntoBalance(amount);
    }

    public void takeRent(Player borrower, Property property, Integer amount) {
        System.out.println("( " + amount + "$ ) " + "Rent" + " Received for property " + property.getName() + " [ " + borrower.getName() + " -> " + this.name + " ]");
        addAmountIntoBalance(amount);
    }

    public void printAllOwnedProperties() {
        System.out.println("\t\t\t* * * Owned Properties By " + this.name + " Are ( " + this.ownedProperties.stream().map(Property::getName).collect(Collectors.joining(",")) + " ) * * *");
    }

}
