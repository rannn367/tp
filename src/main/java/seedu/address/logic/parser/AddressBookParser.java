package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.TextMatchUtil;
import seedu.address.logic.commands.AddCustomerCommand;
import seedu.address.logic.commands.AddDrinkCommand;
import seedu.address.logic.commands.AddStaffCommand;
import seedu.address.logic.commands.Command;
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

/**
 * Parses user input.
 */
public class AddressBookParser {

    @FunctionalInterface
    interface ThrowingFunction<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    private static final Map<String, ThrowingFunction<String, Command, ParseException>> COMMAND_MAP = new HashMap<>();

    static {
        COMMAND_MAP.put(ExitCommand.COMMAND_WORD.toLowerCase(),
                arguments -> new ExitCommand());

        COMMAND_MAP.put(HelpCommand.COMMAND_WORD.toLowerCase(),
                arguments -> new HelpCommand());

        COMMAND_MAP.put(AddStaffCommand.COMMAND_WORD.toLowerCase(),
                arguments -> new AddStaffCommandParser().parse(arguments));
        COMMAND_MAP.put(AddStaffCommand.COMMAND_WORD_SHORTCUT.toLowerCase(),
                arguments -> new AddStaffCommandParser().parse(arguments));

        COMMAND_MAP.put(QuickAddStaffCommand.COMMAND_WORD.toLowerCase(),
                arguments -> new QuickAddStaffCommandParser().parse(arguments));
        COMMAND_MAP.put(QuickAddStaffCommand.COMMAND_WORD_SHORTCUT.toLowerCase(),
                arguments -> new QuickAddStaffCommandParser().parse(arguments));

        COMMAND_MAP.put(EditStaffCommand.COMMAND_WORD.toLowerCase(),
                arguments -> new EditStaffCommandParser().parse(arguments));
        COMMAND_MAP.put(EditStaffCommand.COMMAND_WORD_SHORTCUT.toLowerCase(),
                arguments -> new EditStaffCommandParser().parse(arguments));

        COMMAND_MAP.put(FindStaffCommand.COMMAND_WORD.toLowerCase(),
                arguments -> new FindStaffCommandParser().parse(arguments));
        COMMAND_MAP.put(FindStaffCommand.COMMAND_WORD_SHORTCUT.toLowerCase(),
                arguments -> new FindStaffCommandParser().parse(arguments));

        COMMAND_MAP.put(DeleteStaffCommand.COMMAND_WORD.toLowerCase(),
                arguments -> new DeleteStaffCommandParser().parse(arguments));
        COMMAND_MAP.put(DeleteStaffCommand.COMMAND_WORD_SHORTCUT.toLowerCase(),
                arguments -> new DeleteStaffCommandParser().parse(arguments));

        COMMAND_MAP.put(AddCustomerCommand.COMMAND_WORD.toLowerCase(),
                arguments -> new AddCustomerCommandParser().parse(arguments));
        COMMAND_MAP.put(AddCustomerCommand.COMMAND_WORD_SHORTCUT.toLowerCase(),
                arguments -> new AddCustomerCommandParser().parse(arguments));

        COMMAND_MAP.put(QuickAddCustomerCommand.COMMAND_WORD.toLowerCase(),
                arguments -> new QuickAddCustomerCommandParser().parse(arguments));
        COMMAND_MAP.put(QuickAddCustomerCommand.COMMAND_WORD_SHORTCUT.toLowerCase(),
                arguments -> new QuickAddCustomerCommandParser().parse(arguments));

        COMMAND_MAP.put(EditCustomerCommand.COMMAND_WORD.toLowerCase(),
                arguments -> new EditCustomerCommandParser().parse(arguments));
        COMMAND_MAP.put(EditCustomerCommand.COMMAND_WORD_SHORTCUT.toLowerCase(),
                arguments -> new EditCustomerCommandParser().parse(arguments));

        COMMAND_MAP.put(FindCustomerCommand.COMMAND_WORD.toLowerCase(),
                arguments -> new FindCustomerCommandParser().parse(arguments));
        COMMAND_MAP.put(FindCustomerCommand.COMMAND_WORD_SHORTCUT.toLowerCase(),
                arguments -> new FindCustomerCommandParser().parse(arguments));

        COMMAND_MAP.put(DeleteCustomerCommand.COMMAND_WORD.toLowerCase(),
                arguments -> new DeleteCustomerCommandParser().parse(arguments));
        COMMAND_MAP.put(DeleteCustomerCommand.COMMAND_WORD_SHORTCUT.toLowerCase(),
                arguments -> new DeleteCustomerCommandParser().parse(arguments));

        COMMAND_MAP.put(AddDrinkCommand.COMMAND_WORD.toLowerCase(),
                arguments -> new AddDrinkCommandParser().parse(arguments));
        COMMAND_MAP.put(AddDrinkCommand.COMMAND_WORD_SHORTCUT.toLowerCase(),
                arguments -> new AddDrinkCommandParser().parse(arguments));

        COMMAND_MAP.put(PurchaseCommand.COMMAND_WORD.toLowerCase(),
                arguments -> new PurchaseCommandParser().parse(arguments));
        COMMAND_MAP.put(PurchaseCommand.COMMAND_WORD_SHORTCUT.toLowerCase(),
                arguments -> new PurchaseCommandParser().parse(arguments));

        COMMAND_MAP.put(DeleteDrinkCommand.COMMAND_WORD.toLowerCase(),
                arguments -> new DeleteDrinkCommandParser().parse(arguments));
        COMMAND_MAP.put(DeleteDrinkCommand.COMMAND_WORD_SHORTCUT.toLowerCase(),
                arguments -> new DeleteDrinkCommandParser().parse(arguments));
    }


    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");

        // Check for multiple spaces
        if (arguments.matches(".*\\s{2,}.*")) {
            throw new ParseException("Invalid command format: Multiple consecutive spaces are not allowed.");
        }

        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        if (COMMAND_MAP.containsKey(commandWord)) {
            return COMMAND_MAP.get(commandWord).apply(arguments);
        }

        String closestCommand = findClosestCommand(commandWord);
        if (closestCommand != null) {
            throw new ParseException(
                String.format(
                    "Unknown command: '%s'. Did you mean '%s'?",
                    commandWord,
                    closestCommand
                )
            );
        }

        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }

    private String findClosestCommand(String inputCommand) {
        return TextMatchUtil.findClosestMatch(inputCommand, COMMAND_MAP.keySet());
    }
}
