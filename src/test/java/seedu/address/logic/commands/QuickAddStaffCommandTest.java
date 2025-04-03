package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStaff.ALEX;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.ReadOnlyDrinkCatalog;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;
import seedu.address.model.util.StaffBuilder;

public class QuickAddStaffCommandTest {

    @Test
    public void constructor_nullStaff_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new QuickAddStaffCommand(null));
    }

    @Test
    public void execute_staffAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStaffAdded modelStub = new ModelStubAcceptingStaffAdded();
        Staff validStaff = new StaffBuilder().build();

        CommandResult commandResult = new QuickAddStaffCommand(validStaff).execute(modelStub);

        assertEquals(String.format(QuickAddStaffCommand.MESSAGE_SUCCESS, Messages.format(validStaff)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStaff), modelStub.staffAdded);
    }

    @Test
    public void execute_duplicateStaff_throwsCommandException() {
        Staff validStaff = new StaffBuilder().build();
        QuickAddStaffCommand quickAddStaffCommand = new QuickAddStaffCommand(validStaff);
        ModelStub modelStub = new ModelStubWithStaff(validStaff);

        assertThrows(CommandException.class,
                QuickAddStaffCommand.MESSAGE_DUPLICATE_STAFF, () -> quickAddStaffCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Staff alex = new StaffBuilder().withName(new Name("Alex")).build();
        Staff bob = new StaffBuilder().withName(new Name("Bob")).build();
        QuickAddStaffCommand addAlexCommand = new QuickAddStaffCommand(alex);
        QuickAddStaffCommand addBobCommand = new QuickAddStaffCommand(bob);

        // same object -> returns true
        assertTrue(addAlexCommand.equals(addAlexCommand));

        // same values -> returns true
        QuickAddStaffCommand addAlexCommandCopy = new QuickAddStaffCommand(alex);
        assertTrue(addAlexCommand.equals(addAlexCommandCopy));

        // different types -> returns false
        assertFalse(addAlexCommand.equals(1));

        // null -> returns false
        assertFalse(addAlexCommand.equals(null));

        // different staff -> returns false
        assertFalse(addAlexCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        QuickAddStaffCommand quickAddStaffCommand = new QuickAddStaffCommand(ALEX);
        String expected = QuickAddStaffCommand.class.getCanonicalName() + "{toAdd=" + ALEX + "}";
        assertEquals(expected, quickAddStaffCommand.toString());
    }

    /**
     * A default model stub that has all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStaff(Staff staffMember) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStaff(Staff staffMember, Staff other) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStaff(Staff staffMember) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Staff> getFilteredStaffList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStaffList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStaff(Staff staffMember) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Customer> getFilteredCustomerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCustomerList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCustomer(Customer customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCustomer(Customer customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCustomer(Customer customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCustomer(Customer target, Customer editedCustomer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDrink(Drink drink) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDrink(Drink drink) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDrink(Drink target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDrink(Drink target, Drink editedDrink) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Drink> getFilteredDrinkList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDrinkList(Predicate<Drink> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyDrinkCatalog getDrinkCatalog() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDrinkCatalog(ReadOnlyDrinkCatalog drinkCatalog) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getDrinkCatalogFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDrinkCatalogFilePath(Path drinkCatalogFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getFilteredCustomersCount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getFilteredCustomersAsString() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getFilteredStaffsCount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getFilteredStaffsAsString() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single staff.
     */
    private class ModelStubWithStaff extends ModelStub {
        private final Staff staff;

        ModelStubWithStaff(Staff staff) {
            requireNonNull(staff);
            this.staff = staff;
        }

        @Override
        public boolean hasStaff(Staff staffMember) {
            requireNonNull(staffMember);
            return this.staff.isSamePerson(staffMember);
        }
    }

    /**
     * A Model stub that always accept the staff being added.
     */
    private class ModelStubAcceptingStaffAdded extends ModelStub {
        final ArrayList<Staff> staffAdded = new ArrayList<>();

        @Override
        public boolean hasStaff(Staff staffMember) {
            requireNonNull(staffMember);
            return staffAdded.stream().anyMatch(s -> s.isSamePerson(staffMember));
        }

        @Override
        public void addStaff(Staff staffMember) {
            requireNonNull(staffMember);
            staffAdded.add(staffMember);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
