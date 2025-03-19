package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.drink.DrinkCatalog;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.CustomerId;
import seedu.address.model.person.Email;
import seedu.address.model.person.FavouriteItem;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.RewardPoints;
import seedu.address.model.person.TotalSpent;
import seedu.address.model.person.VisitCount;

public class PointsAddCommandTest {

    private static final int VALID_POINTS = 100;
    private static final int NEGATIVE_POINTS = -10;
    private static final int ZERO_POINTS = 0;

    private Model model;
    private Customer testCustomer;

    @BeforeEach
    public void setUp() {
        // Create a test address book with a known customer
        AddressBook addressBook = new AddressBook();
        testCustomer = new Customer(
            new Name("Test Customer"),
            new Phone("12345678"),
            new Email("test@example.com"),
            new Address("Test Address"),
            new Remark(""),
            new HashSet<>(),
            new CustomerId("C001"),
            new RewardPoints("50"),
            new VisitCount("5"),
            new FavouriteItem("Coffee"),
            new TotalSpent("50.0")
        );
        addressBook.addCustomer(testCustomer);

        // Create the model
        model = new ModelManager(addressBook, new UserPrefs(), new DrinkCatalog());
    }

    @Test
    public void execute_validIndexValidPoints_success() throws Exception {
        Customer customerBefore = (Customer) model.getFilteredCustomerList().get(0);

        // Convert RewardPoints to int
        int initialPoints = Integer.parseInt(customerBefore.getRewardPoints().value);

        PointsAddCommand pointsAddCommand = new PointsAddCommand(INDEX_FIRST_PERSON, VALID_POINTS);

        // Fix: Extract the RewardPoints value as an integer for formatting
        String expectedMessage = String.format(PointsAddCommand.MESSAGE_ADD_POINTS_SUCCESS,
                customerBefore.getName().fullName, VALID_POINTS, initialPoints + VALID_POINTS);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new DrinkCatalog());

        // Create the expected updated customer
        Customer updatedCustomer = new Customer(
                testCustomer.getName(),
                testCustomer.getPhone(),
                testCustomer.getEmail(),
                testCustomer.getAddress(),
                testCustomer.getRemark(),
                testCustomer.getTags(),
                testCustomer.getCustomerId(),
                new RewardPoints(String.valueOf(initialPoints + VALID_POINTS)),
                // Convert updated points to RewardPoints
                testCustomer.getVisitCount(),
                testCustomer.getFavoriteItem(),
                testCustomer.getTotalSpent()
        );

        // Update the model directly to match expected state after command execution
        expectedModel.setCustomer(testCustomer, updatedCustomer);

        assertCommandSuccess(pointsAddCommand, model, expectedMessage, expectedModel);

        // Verify the points were added correctly
        Customer customerAfter = (Customer) model.getFilteredCustomerList().get(0);
        assertEquals(initialPoints + VALID_POINTS, Integer.parseInt(customerAfter.getRewardPoints().value));
    }



    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        PointsAddCommand pointsAddCommand = new PointsAddCommand(outOfBoundIndex, VALID_POINTS);

        assertCommandFailure(pointsAddCommand, model, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_negativePoints_throwsCommandException() {
        PointsAddCommand pointsAddCommand = new PointsAddCommand(INDEX_FIRST_PERSON, NEGATIVE_POINTS);

        assertCommandFailure(pointsAddCommand, model, PointsAddCommand.MESSAGE_INVALID_POINTS);
    }

    @Test
    public void execute_zeroPoints_throwsCommandException() {
        PointsAddCommand pointsAddCommand = new PointsAddCommand(INDEX_FIRST_PERSON, ZERO_POINTS);

        assertCommandFailure(pointsAddCommand, model, PointsAddCommand.MESSAGE_INVALID_POINTS);
    }

    @Test
    public void equals() {
        PointsAddCommand addHundredPointsCommand = new PointsAddCommand(INDEX_FIRST_PERSON, 100);
        PointsAddCommand addFiftyPointsCommand = new PointsAddCommand(INDEX_FIRST_PERSON, 50);
        PointsAddCommand addHundredPointsToSecondCommand = new PointsAddCommand(INDEX_SECOND_PERSON, 100);

        // same object -> returns true
        assertTrue(addHundredPointsCommand.equals(addHundredPointsCommand));

        // same values -> returns true
        PointsAddCommand addHundredPointsCommandCopy = new PointsAddCommand(INDEX_FIRST_PERSON, 100);
        assertTrue(addHundredPointsCommand.equals(addHundredPointsCommandCopy));

        // different types -> returns false
        assertFalse(addHundredPointsCommand.equals(1));

        // null -> returns false
        assertFalse(addHundredPointsCommand.equals(null));

        // different points value -> returns false
        assertFalse(addHundredPointsCommand.equals(addFiftyPointsCommand));

        // different index -> returns false
        assertFalse(addHundredPointsCommand.equals(addHundredPointsToSecondCommand));
    }
}
