package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class ListStaffCommandTest {
    private final Model model = new ModelManager();
    private final Model expectedModel = new ModelManager();

    @Test
    public void execute_listStaff_success() {
        assertCommandSuccess(new ListStaffCommand(), model, ListStaffCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
