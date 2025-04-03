package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_STAFF_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalStaff.ALEX;
import static seedu.address.testutil.TypicalStaff.BEN;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.DrinkCatalog;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindStaffCommand}.
 */
public class FindStaffCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new DrinkCatalog());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new DrinkCatalog());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindStaffCommand findFirstCommand = new FindStaffCommand(firstPredicate);
        FindStaffCommand findSecondCommand = new FindStaffCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindStaffCommand findFirstCommandCopy = new FindStaffCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different staff -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noStaffFound() {
        String expectedMessage = String.format(MESSAGE_STAFF_LISTED_OVERVIEW, 0, "");
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindStaffCommand command = new FindStaffCommand(predicate);
        expectedModel.updateFilteredStaffList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // When there are no staffs found, the filtered staff list should be the same as the original list
        assertEquals(Arrays.asList(ALEX, BEN), model.getFilteredStaffList());
    }

    @Test
    public void execute_multipleKeywords_multipleStaffsFound() {
        String expectedMessage = String.format(MESSAGE_STAFF_LISTED_OVERVIEW, 2, "1. Alex Tan\n2. Ben Chua");
        NameContainsKeywordsPredicate predicate = preparePredicate("Alex Chua");
        FindStaffCommand command = new FindStaffCommand(predicate);
        expectedModel.updateFilteredStaffList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALEX, BEN), model.getFilteredStaffList());
    }

    @Test
    public void execute_multipleKeywords_oneStaffFound() {
        String expectedMessage = String.format(MESSAGE_STAFF_LISTED_OVERVIEW, 1, "1. Alex Tan");
        NameContainsKeywordsPredicate predicate = preparePredicate("Alex");
        FindStaffCommand command = new FindStaffCommand(predicate);
        expectedModel.updateFilteredStaffList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALEX), model.getFilteredStaffList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindStaffCommand findStaffCommand = new FindStaffCommand(predicate);
        String expected = FindStaffCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findStaffCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
