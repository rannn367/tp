package seedu.address.logic.parser;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCustomerCommand;
import seedu.address.logic.commands.AddDrinkCommand;
import seedu.address.logic.commands.AddStaffCommand;
import seedu.address.logic.commands.DeleteCustomerCommand;
import seedu.address.logic.commands.DeleteDrinkCommand;
import seedu.address.logic.commands.DeleteStaffCommand;
import seedu.address.logic.commands.EditCustomerCommand;
import seedu.address.logic.commands.EditStaffCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCustomerCommand;
import seedu.address.logic.commands.FindStaffCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.PurchaseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_addCustomer() throws Exception {
        // Full command format
        assertTrue(parser.parseCommand(AddCustomerCommand.COMMAND_WORD + " cid/C001 n/John Doe p/98765432 "
            + "e/john@example.com a/311, Clementi Ave 2, #02-25 rp/150 vc/8 fi/Cappuccino ts/120 t/regular")
                instanceof AddCustomerCommand);
    }

    @Test
    public void parseCommand_addCustomerShortcut() throws Exception {
        // Shortcut command format
        assertTrue(parser.parseCommand(AddCustomerCommand.COMMAND_WORD_SHORTCUT + " C0102:Charlie:97285712")
                instanceof AddCustomerCommand);
    }

    @Test
    public void parseCommand_editCustomerShortcut() throws Exception {
        assertTrue(parser.parseCommand(EditCustomerCommand.COMMAND_WORD_SHORTCUT + " 1 n/Jane Doe")
                instanceof EditCustomerCommand);
    }

    @Test
    public void parseCommand_findCustomerShortcut() throws Exception {
        assertTrue(parser.parseCommand(FindCustomerCommand.COMMAND_WORD_SHORTCUT + " foo bar baz")
                instanceof FindCustomerCommand);
    }

    @Test
    public void parseCommand_editStaffShortcut() throws Exception {
        assertTrue(parser.parseCommand(EditStaffCommand.COMMAND_WORD_SHORTCUT + " 1 p/99994567")
                instanceof EditStaffCommand);
    }

    @Test
    public void parseCommand_findStaffShortcut() throws Exception {
        assertTrue(parser.parseCommand(FindStaffCommand.COMMAND_WORD_SHORTCUT + " foo bar baz")
                instanceof FindStaffCommand);
    }

    @Test
    public void parseCommand_deleteStaffShortcut() throws Exception {
        assertTrue(parser.parseCommand(DeleteStaffCommand.COMMAND_WORD_SHORTCUT + " 2")
                instanceof DeleteStaffCommand);
    }

    @Test
    public void parseCommand_addDrinkShortcut() throws Exception {
        assertTrue(parser.parseCommand(AddDrinkCommand.COMMAND_WORD + " n/Iced Latte p/4.50 c/Coffee")
                instanceof AddDrinkCommand);
    }

    @Test
    public void parseCommand_deleteDrinkShortcut() throws Exception {
        assertTrue(parser.parseCommand(DeleteDrinkCommand.COMMAND_WORD_SHORTCUT + " 2")
                instanceof DeleteDrinkCommand);
    }

    @Test
    public void parseCommand_purchaseShortcut() throws Exception {
        // Simple purchase command
        assertTrue(parser.parseCommand(PurchaseCommand.COMMAND_WORD_SHORTCUT + " 1 n/Espresso")
                instanceof PurchaseCommand);

        // Purchase with redeem option
        assertTrue(parser.parseCommand(PurchaseCommand.COMMAND_WORD_SHORTCUT + " 2 n/Cappuccino redeem/true")
                instanceof PurchaseCommand);
    }

    @Test
    public void parseCommand_deleteCustomerShortcut() throws Exception {
        assertTrue(parser.parseCommand(DeleteCustomerCommand.COMMAND_WORD_SHORTCUT + " 2")
                instanceof DeleteCustomerCommand);
    }

    @Test
    public void parseCommand_editCustomer() throws Exception {
        assertTrue(parser.parseCommand(EditCustomerCommand.COMMAND_WORD + " 1 n/Jane Doe")
                instanceof EditCustomerCommand);
    }

    @Test
    public void parseCommand_findCustomer() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCustomerCommand command = (FindCustomerCommand) parser.parseCommand(
                FindCustomerCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCustomerCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_deleteCustomer() throws Exception {
        assertTrue(parser.parseCommand(DeleteCustomerCommand.COMMAND_WORD + " 2")
                instanceof DeleteCustomerCommand);
    }

    @Test
    public void parseCommand_addStaff() throws Exception {
        // Full command format
        assertTrue(parser.parseCommand(AddStaffCommand.COMMAND_WORD + " sid/S1234 n/Alice Tan p/81234567 "
            + "e/alice@example.com a/123, Jurong West Ave 6, #08-111 "
            + "role/Barista shift/9am-5pm hours/40 rating/4.5 t/fullTime")
                instanceof AddStaffCommand);
    }

    @Test
    public void parseCommand_addStaffShortcut() throws Exception {
        // Shortcut command format
        assertTrue(parser.parseCommand(AddStaffCommand.COMMAND_WORD_SHORTCUT + " S0102:Ali:98291029")
                instanceof AddStaffCommand);
    }

    @Test
    public void parseCommand_editStaff() throws Exception {
        assertTrue(parser.parseCommand(EditStaffCommand.COMMAND_WORD + " 1 p/99994567 e/newemail@example.com")
                instanceof EditStaffCommand);
    }

    @Test
    public void parseCommand_findStaff() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindStaffCommand command = (FindStaffCommand) parser.parseCommand(
                FindStaffCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindStaffCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_deleteStaff() throws Exception {
        assertTrue(parser.parseCommand(DeleteStaffCommand.COMMAND_WORD + " 2")
                instanceof DeleteStaffCommand);
    }


    @Test
    public void parseCommand_addDrink() throws Exception {
        assertTrue(parser.parseCommand(AddDrinkCommand.COMMAND_WORD + " n/Iced Latte p/4.50 c/Coffee")
                instanceof AddDrinkCommand);
    }

    @Test
    public void parseCommand_deleteDrink() throws Exception {
        assertTrue(parser.parseCommand(DeleteDrinkCommand.COMMAND_WORD + " 2")
                instanceof DeleteDrinkCommand);
    }

    @Test
    public void parseCommand_purchase() throws Exception {
        // Simple purchase command
        assertTrue(parser.parseCommand(PurchaseCommand.COMMAND_WORD + " 1 n/Espresso")
                instanceof PurchaseCommand);

        // Purchase with redeem option
        assertTrue(parser.parseCommand(PurchaseCommand.COMMAND_WORD + " 2 n/Cappuccino redeem/true")
                instanceof PurchaseCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    // Tests for the fuzzy matching functionality

    @Test
    public void parseCommand_typoWithCloseLevenshteinDistance_suggestsCorrectCommand() {
        // Test close typos that should be caught by Levenshtein distance (≤ 2)
        try {
            parser.parseCommand("stafadd");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains("Did you mean 'staffadd'?"));
        }

        try {
            parser.parseCommand("hlp");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains("Did you mean 'help'?"));
        }

        try {
            parser.parseCommand("customerdelet");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains("Did you mean 'customerdelete'?"));
        }

        try {
            parser.parseCommand("purcase");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains("Did you mean 'purchase'?"));
        }
    }

    @Test
    public void parseCommand_commandWithNoCloseMatch_doesNotSuggest() {
        // Test commands that are too different to suggest alternatives
        try {
            parser.parseCommand("xyz");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, e.getMessage());
        }

        try {
            parser.parseCommand("commandnotexist");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, e.getMessage());
        }

        try {
            parser.parseCommand("randomtext");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, e.getMessage());
        }
    }

    @Test
    public void parseCommand_multipleSpaces_throwsParseException() {
        assertThrows(ParseException.class, "Invalid command format: Multiple consecutive spaces are not allowed.", () ->
                parser.parseCommand("customeradd   cid/C015   n/John   p/12345678"));
    }

}
