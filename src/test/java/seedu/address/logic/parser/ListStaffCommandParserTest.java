package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class ListStaffCommandParserTest {
    private final ListStaffCommandParser parser = new ListStaffCommandParser();

    @Test
    void parse_emptyArgs_success() throws ParseException {
        assertDoesNotThrow(() -> parser.parse(""));
    }

    @Test
    void parse_nonEmptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("extra"));
    }
}
