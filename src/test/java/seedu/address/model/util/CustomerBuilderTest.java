package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.drink.Drink;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.CustomerId;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.RewardPoints;
import seedu.address.model.person.TotalSpent;
import seedu.address.model.person.VisitCount;
import seedu.address.model.tag.Tag;

public class CustomerBuilderTest {

    private static final String TEST_NAME = "Test Customer";
    private static final String TEST_PHONE = "87654321";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_ADDRESS = "Test Address, #01-01";
    private static final String TEST_REMARK = "Test Remark";
    private static final String TEST_CUSTOMER_ID = "C2000";
    private static final String TEST_REWARD_POINTS = "150";
    private static final String TEST_VISIT_COUNT = "10";
    private static final String TEST_FAVOURITE_DRINK = "Mocha";
    private static final String TEST_TOTAL_SPENT = "99.99";
    private static final String[] TEST_TAGS = {"test", "premium"};

    @Test
    public void build_defaultValues_returnsCustomerWithDefaultValues() {
        Customer customer = new CustomerBuilder().build();

        assertEquals(CustomerBuilder.DEFAULT_NAME, customer.getName().fullName);
        assertEquals(CustomerBuilder.DEFAULT_PHONE, customer.getPhone().value);
        assertEquals(CustomerBuilder.DEFAULT_EMAIL, customer.getEmail().value);
        assertEquals(CustomerBuilder.DEFAULT_ADDRESS, customer.getAddress().value);
        assertEquals(CustomerBuilder.DEFAULT_REMARK, customer.getRemark().value);
        assertEquals(CustomerBuilder.DEFAULT_CUSTOMER_ID, customer.getCustomerId().value);
        assertEquals(CustomerBuilder.DEFAULT_REWARD_POINTS, customer.getRewardPoints().value);
        assertEquals(CustomerBuilder.DEFAULT_VISIT_COUNT, customer.getVisitCount().value);
        assertEquals(CustomerBuilder.DEFAULT_FAVOURITE_ITEM, customer.getFavouriteDrink().value);
        assertEquals(CustomerBuilder.DEFAULT_TOTAL_SPENT, customer.getTotalSpent().value);
    }

    @Test
    public void build_withAllStringParameters_returnsCustomerWithExpectedValues() {
        Customer customer = new CustomerBuilder()
                .withName(new Name(TEST_NAME))
                .withPhone(new Phone(TEST_PHONE))
                .withEmail(new Email(TEST_EMAIL))
                .withAddress(new Address(TEST_ADDRESS))
                .withRemark(new Remark(TEST_REMARK))
                .withCustomerId(new CustomerId(TEST_CUSTOMER_ID))
                .withRewardPoints(new RewardPoints(TEST_REWARD_POINTS))
                .withVisitCount(new VisitCount(TEST_VISIT_COUNT))
                .withFavouriteDrink(new Drink(TEST_FAVOURITE_DRINK))
                .withTotalSpent(new TotalSpent(TEST_TOTAL_SPENT))
                .withTags(Arrays.stream(TEST_TAGS)
                        .map(Tag::new)
                        .collect(Collectors.toSet()))
                .build();

        assertEquals(TEST_NAME, customer.getName().fullName);
        assertEquals(TEST_PHONE, customer.getPhone().value);
        assertEquals(TEST_EMAIL, customer.getEmail().value);
        assertEquals(TEST_ADDRESS, customer.getAddress().value);
        assertEquals(TEST_REMARK, customer.getRemark().value);
        assertEquals(TEST_CUSTOMER_ID, customer.getCustomerId().value);
        assertEquals(TEST_REWARD_POINTS, customer.getRewardPoints().value);
        assertEquals(TEST_VISIT_COUNT, customer.getVisitCount().value);
        assertEquals(TEST_FAVOURITE_DRINK, customer.getFavouriteDrink().value);
        assertEquals(TEST_TOTAL_SPENT, customer.getTotalSpent().value);

        // Verify tags
        Set<Tag> expectedTags = SampleDataUtil.getTagSet(TEST_TAGS);
        assertEquals(expectedTags, customer.getTags());
    }

    @Test
    public void build_withObjectParameters_returnsCustomerWithExpectedValues() {
        Name name = new Name(TEST_NAME);
        Phone phone = new Phone(TEST_PHONE);
        Email email = new Email(TEST_EMAIL);
        Address address = new Address(TEST_ADDRESS);
        Remark remark = new Remark(TEST_REMARK);
        CustomerId customerId = new CustomerId(TEST_CUSTOMER_ID);
        RewardPoints rewardPoints = new RewardPoints(TEST_REWARD_POINTS);
        VisitCount visitCount = new VisitCount(TEST_VISIT_COUNT);
        Drink favouriteDrink = new Drink(TEST_FAVOURITE_DRINK);
        TotalSpent totalSpent = new TotalSpent(TEST_TOTAL_SPENT);
        Set<Tag> tags = new HashSet<>();
        for (String tagName : TEST_TAGS) {
            tags.add(new Tag(tagName));
        }

        Customer customer = new CustomerBuilder()
                .withName(name)
                .withPhone(phone)
                .withEmail(email)
                .withAddress(address)
                .withRemark(remark)
                .withCustomerId(customerId)
                .withRewardPoints(rewardPoints)
                .withVisitCount(visitCount)
                .withFavouriteDrink(favouriteDrink)
                .withTotalSpent(totalSpent)
                .withTags(tags)
                .build();

        assertEquals(name, customer.getName());
        assertEquals(phone, customer.getPhone());
        assertEquals(email, customer.getEmail());
        assertEquals(address, customer.getAddress());
        assertEquals(remark, customer.getRemark());
        assertEquals(customerId, customer.getCustomerId());
        assertEquals(rewardPoints, customer.getRewardPoints());
        assertEquals(visitCount, customer.getVisitCount());
        assertEquals(favouriteDrink, customer.getFavouriteDrink());
        assertEquals(totalSpent, customer.getTotalSpent());
        assertEquals(tags, customer.getTags());
    }

    @Test
    public void build_withCustomerCopy_returnsEqualCustomer() {
        Customer original = new CustomerBuilder()
                .withName(new Name(TEST_NAME))
                .withPhone(new Phone(TEST_PHONE))
                .withEmail(new Email(TEST_EMAIL))
                .withAddress(new Address(TEST_ADDRESS))
                .withRemark(new Remark(TEST_REMARK))
                .withCustomerId(new CustomerId(TEST_CUSTOMER_ID))
                .withRewardPoints(new RewardPoints(TEST_REWARD_POINTS))
                .withVisitCount(new VisitCount(TEST_VISIT_COUNT))
                .withFavouriteDrink(new Drink(TEST_FAVOURITE_DRINK))
                .withTotalSpent(new TotalSpent(TEST_TOTAL_SPENT))
                .withTags(Arrays.stream(TEST_TAGS)
                        .map(Tag::new)
                        .collect(Collectors.toSet()))
                .build();

        CustomerBuilder copiedBuilder = new CustomerBuilder(original);
        Customer copy = copiedBuilder.build();

        assertEquals(original, copy);
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getPhone(), copy.getPhone());
        assertEquals(original.getEmail(), copy.getEmail());
        assertEquals(original.getAddress(), copy.getAddress());
        assertEquals(original.getRemark(), copy.getRemark());
        assertEquals(original.getCustomerId(), copy.getCustomerId());
        assertEquals(original.getRewardPoints(), copy.getRewardPoints());
        assertEquals(original.getVisitCount(), copy.getVisitCount());
        assertEquals(original.getFavouriteDrink(), copy.getFavouriteDrink());
        assertEquals(original.getTotalSpent(), copy.getTotalSpent());
        assertEquals(original.getTags(), copy.getTags());
    }

    @Test
    public void individualSetters_stringParameters_setCorrectly() {
        CustomerBuilder builder = new CustomerBuilder();

        // Test each setter method individually
        Customer nameCustomer = builder.withName(new Name(TEST_NAME)).build();
        assertEquals(TEST_NAME, nameCustomer.getName().fullName);

        Customer phoneCustomer = builder.withPhone(new Phone(TEST_PHONE)).build();
        assertEquals(TEST_PHONE, phoneCustomer.getPhone().value);

        Customer emailCustomer = builder.withEmail(new Email(TEST_EMAIL)).build();
        assertEquals(TEST_EMAIL, emailCustomer.getEmail().value);

        Customer addressCustomer = builder.withAddress(new Address(TEST_ADDRESS)).build();
        assertEquals(TEST_ADDRESS, addressCustomer.getAddress().value);

        Customer remarkCustomer = builder.withRemark(new Remark(TEST_REMARK)).build();
        assertEquals(TEST_REMARK, remarkCustomer.getRemark().value);

        Customer customerIdCustomer = builder.withCustomerId(new CustomerId(TEST_CUSTOMER_ID)).build();
        assertEquals(TEST_CUSTOMER_ID, customerIdCustomer.getCustomerId().value);

        Customer rewardPointsCustomer = builder.withRewardPoints(new RewardPoints(TEST_REWARD_POINTS)).build();
        assertEquals(TEST_REWARD_POINTS, rewardPointsCustomer.getRewardPoints().value);

        Customer visitCountCustomer = builder.withVisitCount(new VisitCount(TEST_VISIT_COUNT)).build();
        assertEquals(TEST_VISIT_COUNT, visitCountCustomer.getVisitCount().value);

        Customer favouriteDrinkCustomer = builder.withFavouriteDrink(new Drink(TEST_FAVOURITE_DRINK)).build();
        assertEquals(TEST_FAVOURITE_DRINK, favouriteDrinkCustomer.getFavouriteDrink().value);

        Customer totalSpentCustomer = builder.withTotalSpent(new TotalSpent(TEST_TOTAL_SPENT)).build();
        assertEquals(TEST_TOTAL_SPENT, totalSpentCustomer.getTotalSpent().value);
    }

    @Test
    public void individualSetters_objectParameters_setCorrectly() {
        CustomerBuilder builder = new CustomerBuilder();

        Name name = new Name(TEST_NAME);
        Customer nameCustomer = builder.withName(name).build();
        assertEquals(name, nameCustomer.getName());

        Phone phone = new Phone(TEST_PHONE);
        Customer phoneCustomer = builder.withPhone(phone).build();
        assertEquals(phone, phoneCustomer.getPhone());

        Email email = new Email(TEST_EMAIL);
        Customer emailCustomer = builder.withEmail(email).build();
        assertEquals(email, emailCustomer.getEmail());

        Address address = new Address(TEST_ADDRESS);
        Customer addressCustomer = builder.withAddress(address).build();
        assertEquals(address, addressCustomer.getAddress());

        Remark remark = new Remark(TEST_REMARK);
        Customer remarkCustomer = builder.withRemark(remark).build();
        assertEquals(remark, remarkCustomer.getRemark());

        CustomerId customerId = new CustomerId(TEST_CUSTOMER_ID);
        Customer customerIdCustomer = builder.withCustomerId(customerId).build();
        assertEquals(customerId, customerIdCustomer.getCustomerId());

        RewardPoints rewardPoints = new RewardPoints(TEST_REWARD_POINTS);
        Customer rewardPointsCustomer = builder.withRewardPoints(rewardPoints).build();
        assertEquals(rewardPoints, rewardPointsCustomer.getRewardPoints());

        VisitCount visitCount = new VisitCount(TEST_VISIT_COUNT);
        Customer visitCountCustomer = builder.withVisitCount(visitCount).build();
        assertEquals(visitCount, visitCountCustomer.getVisitCount());

        Drink favouriteDrink = new Drink(TEST_FAVOURITE_DRINK);
        Customer favouriteDrinkCustomer = builder.withFavouriteDrink(favouriteDrink).build();
        assertEquals(favouriteDrink, favouriteDrinkCustomer.getFavouriteDrink());

        TotalSpent totalSpent = new TotalSpent(TEST_TOTAL_SPENT);
        Customer totalSpentCustomer = builder.withTotalSpent(totalSpent).build();
        assertEquals(totalSpent, totalSpentCustomer.getTotalSpent());
    }

    @Test
    public void buildMultipleCustomers_withChaining_returnsDistinctCustomers() {
        CustomerBuilder builder = new CustomerBuilder();

        Customer customer1 = builder
                .withName(new Name("Customer One"))
                .withEmail(new Email("one@example.com"))
                .withCustomerId(new CustomerId("C1001"))
                .build();

        Customer customer2 = builder
                .withName(new Name("Customer Two"))
                .withEmail(new Email("two@example.com"))
                .withCustomerId(new CustomerId("C1002"))
                .build();

        // Verify that customer2 has the modified values
        assertEquals("Customer Two", customer2.getName().fullName);
        assertEquals("two@example.com", customer2.getEmail().value);
        assertEquals("C1002", customer2.getCustomerId().value);

        // And check that they're distinct objects
        assertNotNull(customer1);
        assertNotNull(customer2);
    }
}
