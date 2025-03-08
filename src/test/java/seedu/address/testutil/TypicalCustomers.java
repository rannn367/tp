package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.person.Customer;

/**
 * A utility class containing a list of {@code Customer} objects to be used in tests.
 */
public class TypicalCustomers {

    public static final Customer ALICE = new CustomerBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withRemark("Regular customer")
            .withRewardPoints(150)
            .withVisitCount(8)
            .withFavoriteItem("Cappuccino")
            .withTotalSpent(120.50)
            .withRating(5)
            .withTags("regular", "vip").build();
            
    public static final Customer BOB = new CustomerBuilder().withName("Bob Choo")
            .withAddress("Block 123, Bobby Street 3")
            .withEmail("bob@example.com")
            .withPhone("98765432")
            .withRemark("New customer")
            .withRewardPoints(50)
            .withVisitCount(2)
            .withFavoriteItem("Americano")
            .withTotalSpent(35.20)
            .withRating(4)
            .withTags("new").build();
            
    public static final Customer CARL = new CustomerBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("carl@example.com")
            .withAddress("Wall Street")
            .withRemark("Comes in the morning")
            .withRewardPoints(300)
            .withVisitCount(15)
            .withFavoriteItem("Croissant")
            .withTotalSpent(250.75)
            .withRating(5).build();
            
    public static final Customer DANIEL = new CustomerBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("daniel@example.com")
            .withAddress("10th Street")
            .withRemark("Likes extra sugar")
            .withRewardPoints(75)
            .withVisitCount(4)
            .withFavoriteItem("Espresso")
            .withTotalSpent(45.00)
            .withRating(3)
            .withTags("student").build();
            
    public static final Customer ELLE = new CustomerBuilder().withName("Elle Meyer")
            .withPhone("94822241")
            .withEmail("elle@example.com")
            .withAddress("Michegan Drive")
            .withRemark("Allergic to nuts")
            .withRewardPoints(200)
            .withVisitCount(10)
            .withFavoriteItem("Chai Latte")
            .withTotalSpent(150.25)
            .withRating(4)
            .withTags("allergies").build();
            
    public static final Customer FIONA = new CustomerBuilder().withName("Fiona Kunz")
            .withPhone("94824271")
            .withEmail("fiona@example.com")
            .withAddress("Little Tokyo")
            .withRemark("Birthday on January 15")
            .withRewardPoints(450)
            .withVisitCount(20)
            .withFavoriteItem("Mocha")
            .withTotalSpent(320.00)
            .withRating(5)
            .withTags("vip", "birthday-jan").build();
            
    public static final Customer GEORGE = new CustomerBuilder().withName("George Best")
            .withPhone("94824421")
            .withEmail("george@example.com")
            .withAddress("4th Street")
            .withRemark("Only visits on weekends")
            .withRewardPoints(100)
            .withVisitCount(5)
            .withFavoriteItem("Green Tea")
            .withTotalSpent(80.50)
            .withRating(4)
            .withTags("weekend").build();

    private TypicalCustomers() {} // prevents instantiation

    /**
     * Returns a list of all typical customers for testing.
     */
    public static List<Customer> getTypicalCustomers() {
        return new ArrayList<>(Arrays.asList(ALICE, BOB, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}