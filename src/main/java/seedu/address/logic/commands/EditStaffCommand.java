package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS_WORKED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERFORMANCE_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHIFT_TIMING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STAFF_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STAFFS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.descriptors.EditStaffDescriptor;
import seedu.address.model.Model;
import seedu.address.model.person.Staff;
import seedu.address.model.util.StaffBuilder;

/**
 * Edits the details of an existing staff in the address book.
 */
public class EditStaffCommand extends Command {

    public static final String COMMAND_WORD = "staffedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the staff identified "
            + "by the index number used in the displayed staff list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_STAFF_ID + "STAFF_ID] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_SHIFT_TIMING + "SHIFT_TIMING] "
            + "[" + PREFIX_HOURS_WORKED + "HOURS_WORKED] "
            + "[" + PREFIX_PERFORMANCE_RATING + "PERFORMANCE_RATING] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Staff: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "Duplicated Staff ID found. This staff already exists in the address book.";

    private final Index index;
    private final EditStaffDescriptor editStaffDescriptor;

    /**
     * @param index               of the staff in the filtered staff list to edit
     * @param editStaffDescriptor details to edit the staff with
     */
    public EditStaffCommand(Index index, EditStaffDescriptor editStaffDescriptor) {
        requireNonNull(index);
        requireNonNull(editStaffDescriptor);

        this.index = index;
        this.editStaffDescriptor = new EditStaffDescriptor(editStaffDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Staff> lastShownList = model.getFilteredStaffList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Staff staffToEdit = lastShownList.get(index.getZeroBased());
        Staff editedStaff = createEditedStaff(staffToEdit, editStaffDescriptor);

        if (!staffToEdit.isSamePerson(editedStaff) && model.hasStaff(editedStaff)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setStaff(staffToEdit, editedStaff);
        model.updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFFS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedStaff)));
    }

    /**
     * Creates and returns a {@code Staff} with the details of
     * {@code staffToEdit} edited with {@code editStaffDescriptor}.
     */
    private static Staff createEditedStaff(Staff staffToEdit, EditStaffDescriptor editStaffDescriptor) {
        assert staffToEdit != null;

        StaffBuilder staffBuilder = new StaffBuilder(staffToEdit);

        staffBuilder = editStaffDescriptor.getName()
                .map(staffBuilder::withName)
                .orElse(staffBuilder);
        staffBuilder = editStaffDescriptor.getPhone()
                .map(staffBuilder::withPhone)
                .orElse(staffBuilder);
        staffBuilder = editStaffDescriptor.getEmail()
                .map(staffBuilder::withEmail)
                .orElse(staffBuilder);
        staffBuilder = editStaffDescriptor.getAddress()
                .map(staffBuilder::withAddress)
                .orElse(staffBuilder);
        staffBuilder = editStaffDescriptor.getTags()
                .map(staffBuilder::withTags)
                .orElse(staffBuilder);
        staffBuilder = editStaffDescriptor.getStaffId()
                .map(staffBuilder::withStaffId)
                .orElse(staffBuilder);
        staffBuilder = editStaffDescriptor.getRole()
                .map(staffBuilder::withRole)
                .orElse(staffBuilder);
        staffBuilder = editStaffDescriptor.getShiftTiming()
                .map(staffBuilder::withShiftTiming)
                .orElse(staffBuilder);
        staffBuilder = editStaffDescriptor.getHoursWorked()
                .map(staffBuilder::withHoursWorked)
                .orElse(staffBuilder);
        staffBuilder = editStaffDescriptor.getPerformanceRating()
                .map(staffBuilder::withPerformanceRating)
                .orElse(staffBuilder);

        return staffBuilder.build();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditStaffCommand)) {
            return false;
        }

        EditStaffCommand otherEditStaffCommand = (EditStaffCommand) other;
        return index.equals(otherEditStaffCommand.index)
                && editStaffDescriptor.equals(otherEditStaffCommand.editStaffDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editStaffDescriptor", editStaffDescriptor)
                .toString();
    }

}
