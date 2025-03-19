package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.HoursAddCommand.MESSAGE_INVALID_HOURS;
import static seedu.address.logic.commands.HoursAddCommand.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.commands.HoursAddCommand.MESSAGE_SUCCESS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Name;
import seedu.address.model.person.PerformanceRating;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.person.ShiftTiming;
import seedu.address.model.person.Staff;
import seedu.address.model.person.StaffId;

public class HoursAddCommandTest {
    private Model model;
    private List<Staff> staffList;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        staffList = new ArrayList<>();

        // Create and add staff to the model with non-null required fields
        Staff staff1 = new Staff(
                new Name("John Doe"),
                new Phone("12345678"),
                new Email("john@example.com"),
                new Address("123 Street"),
                new Remark("No remarks"),
                new HashSet<>(), // tags (empty Set)
                new StaffId("S001"), // valid staffId
                new Role("Manager"), // valid role
                new ShiftTiming("9am-5pm"), // valid shift timing
                new HoursWorked("40"), // valid hours worked
                new PerformanceRating("4.5") // valid performance rating
        );
        staffList.add(staff1);
        model.addStaff(staff1);
    }



    @Test
    public void execute_validIndex_success() throws CommandException {
        Index validIndex = Index.fromOneBased(1);
        int hoursToAdd = 5;
        HoursAddCommand command = new HoursAddCommand(validIndex, hoursToAdd);

        CommandResult result = command.execute(model);

        Staff updatedStaff = staffList.get(validIndex.getZeroBased());
        int updatedHours = Integer.parseInt(updatedStaff.getHoursWorked().value) + hoursToAdd;

        assertEquals(String.format(MESSAGE_SUCCESS, updatedStaff.getName(), 40, updatedHours),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index invalidIndex = Index.fromOneBased(2);
        int hoursToAdd = 5;
        HoursAddCommand command = new HoursAddCommand(invalidIndex, hoursToAdd);

        assertThrows(CommandException.class, () -> command.execute(model), MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_invalidHours_throwsCommandException() {
        Index validIndex = Index.fromOneBased(1);
        int invalidHours = -5;
        HoursAddCommand command = new HoursAddCommand(validIndex, invalidHours);

        assertThrows(CommandException.class, () -> command.execute(model), MESSAGE_INVALID_HOURS);
    }
}
