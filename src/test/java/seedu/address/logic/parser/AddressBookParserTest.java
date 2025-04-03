package seedu.address.logic.parser;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

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
import seedu.address.logic.commands.QuickAddCustomerCommand;
import seedu.address.logic.commands.QuickAddStaffCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
        assertTrue(parser.parseCommand(AddCustomerCommand.COMMAND_WORD + " cid/C001 n/John Doe p/98765432 "
            + "e/john@example.com a/311, Clementi Ave 2, #02-25 rp/150 vc/8 fi/Cappuccino ts/120 t/regular")
                instanceof AddCustomerCommand);
    }

    @Test
    public void parseCommand_quickAddCustomer() throws Exception {
        assertTrue(parser.parseCommand(QuickAddCustomerCommand.COMMAND_WORD_SHORTCUT + " C0102:Charlie:97285712")
                instanceof QuickAddCustomerCommand);
    }

    @Test
    public void parseCommand_editCustomerShortcut() throws Exception {
        assertTrue(parser.parseCommand(EditCustomerCommand.COMMAND_WORD_SHORTCUT + " 1 n/Jane Doe")
                instanceof EditCustomerCommand);
    }

    @Test
    public void parseCommand_findCustomerShortcut() throws Exception {
        assertTrue(parser.parseCommand(FindCustomerCommand.COMMAND_WORD_SHORTCUT + " n/Alice vc/5")
                instanceof FindCustomerCommand);
        assertTrue(parser.parseCommand(FindCustomerCommand.COMMAND_WORD_SHORTCUT + " all/true")
                instanceof FindCustomerCommand);
    }

    @Test
    public void parseCommand_findCustomerShortcutWithMultiplePrefixes() throws Exception {
        assertTrue(parser.parseCommand(FindCustomerCommand.COMMAND_WORD_SHORTCUT
                + " n/John p/98765432 e/john@example.com a/Clementi "
                + "cid/C001 rp/150 vc/8 fi/Cappuccino ts/120 t/regular")
                instanceof FindCustomerCommand);
    }

    @Test
    public void parseCommand_editStaffShortcut() throws Exception {
        assertTrue(parser.parseCommand(EditStaffCommand.COMMAND_WORD_SHORTCUT + " 1 p/99994567")
                instanceof EditStaffCommand);
    }

    @Test
    public void parseCommand_findStaffShortcut() throws Exception {
        assertTrue(parser.parseCommand(FindStaffCommand.COMMAND_WORD_SHORTCUT + " n/John role/Barista")
                instanceof FindStaffCommand);
        assertTrue(parser.parseCommand(FindStaffCommand.COMMAND_WORD_SHORTCUT + " all/true")
                instanceof FindStaffCommand);
    }

    @Test
    public void parseCommand_findStaffShortcutWithMultiplePrefixes() throws Exception {
        assertTrue(parser.parseCommand(FindStaffCommand.COMMAND_WORD_SHORTCUT
                + " n/John p/98765432 e/john@example.com a/Clementi "
                + "sid/S1234 role/Barista shift/9am-5pm hours/40 rating/4.5 t/fullTime")
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
        // Test for prefix-based search
        assertTrue(parser.parseCommand(
                FindCustomerCommand.COMMAND_WORD + " n/Alice vc/5")
                instanceof FindCustomerCommand);

        // Test for all/true search
        assertTrue(parser.parseCommand(
                FindCustomerCommand.COMMAND_WORD + " all/true")
                instanceof FindCustomerCommand);
    }

    @Test
    public void parseCommand_findCustomerWithMultiplePrefixes() throws Exception {
        // Test with multiple search criteria
        assertTrue(parser.parseCommand(
                FindCustomerCommand.COMMAND_WORD + " n/John p/98765432 e/john@example.com a/Clementi "
                + "cid/C001 rp/150 vc/8 fi/Cappuccino ts/120 t/regular")
                instanceof FindCustomerCommand);
    }

    @Test
    public void parseCommand_deleteCustomer() throws Exception {
        assertTrue(parser.parseCommand(DeleteCustomerCommand.COMMAND_WORD + " 2")
                instanceof DeleteCustomerCommand);
    }

    @Test
    public void parseCommand_addStaff() throws Exception {
        assertTrue(parser.parseCommand(AddStaffCommand.COMMAND_WORD
                + " sid/S1234 n/Alice Tan p/81234567 "
                + "e/alice@example.com a/123, Jurong West Ave 6, #08-111 "
                + "role/Barista shift/9am-5pm hours/40 rating/4.5 t/fullTime")
                instanceof AddStaffCommand);
    }

    @Test
    public void parseCommand_quickAddStaff() throws Exception {
        assertTrue(parser.parseCommand(QuickAddStaffCommand.COMMAND_WORD_SHORTCUT + " S0102:Ali:98291029")
                instanceof QuickAddStaffCommand);
    }

    @Test
    public void parseCommand_editStaff() throws Exception {
        assertTrue(parser.parseCommand(EditStaffCommand.COMMAND_WORD + " 1 p/99994567 e/newemail@example.com")
                instanceof EditStaffCommand);
    }

    @Test
    public void parseCommand_findStaff() throws Exception {
        // Test for prefix-based search
        assertTrue(parser.parseCommand(
                FindStaffCommand.COMMAND_WORD + " n/John role/Barista")
                instanceof FindStaffCommand);

        // Test for all/true search
        assertTrue(parser.parseCommand(
                FindStaffCommand.COMMAND_WORD + " all/true")
                instanceof FindStaffCommand);
    }

    @Test
    public void parseCommand_findStaffWithMultiplePrefixes() throws Exception {
        // Test with multiple search criteria
        assertTrue(parser.parseCommand(
                FindStaffCommand.COMMAND_WORD + " n/John p/98765432 e/john@example.com a/Clementi "
                + "sid/S1234 role/Barista shift/9am-5pm hours/40 rating/4.5 t/fullTime")
                instanceof FindStaffCommand);
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
