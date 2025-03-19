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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Staff;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.person.StaffId;
import seedu.address.model.person.ShiftTiming;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.PerformanceRating;
import seedu.address.model.tag.Tag;

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
    public static final String MESSAGE_DUPLICATE_PERSON = "This staff already exists in the address book.";

    private final Index index;
    private final EditStaffDescriptor editStaffDescriptor;

    /**
     * @param index of the staff in the filtered staff list to edit
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

        if (!staffToEdit.isSameStaff(editedStaff) && model.hasStaff(editedStaff)) {
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

        Name updatedName = editStaffDescriptor.getName().orElse(staffToEdit.getName());
        Phone updatedPhone = editStaffDescriptor.getPhone().orElse(staffToEdit.getPhone());
        Email updatedEmail = editStaffDescriptor.getEmail().orElse(staffToEdit.getEmail());
        Address updatedAddress = editStaffDescriptor.getAddress().orElse(staffToEdit.getAddress());
        Remark updatedRemark = staffToEdit.getRemark(); // edit command does not allow editing remarks
        Set<Tag> updatedTags = editStaffDescriptor.getTags().orElse(staffToEdit.getTags());
        StaffId updatedStaffId = editStaffDescriptor.getStaffId().orElse(staffToEdit.getStaffId());
        Role updatedRole = editStaffDescriptor.getRole().orElse(staffToEdit.getRole());
        ShiftTiming updatedShiftTiming = editStaffDescriptor.getShiftTiming().orElse(staffToEdit.getShiftTiming());
        HoursWorked updatedHoursWorked = editStaffDescriptor.getHoursWorked().orElse(staffToEdit.getHoursWorked());
        PerformanceRating updatedPerformanceRating = editStaffDescriptor.getPerformanceRating()
                .orElse(staffToEdit.getPerformanceRating());

        return new Staff(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRemark, updatedTags,
                updatedStaffId, updatedRole, updatedShiftTiming, updatedHoursWorked, updatedPerformanceRating);
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

    /**
     * Stores the details to edit the staff with. Each non-empty field value
     * will replace the corresponding field value of the staff.
     */
    public static class EditStaffDescriptor {

        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Remark remark;

        private Set<Tag> tags;

        // Staff-specific fields
        private StaffId staffId; // Unique identifier for staff
        private Role role; // Job role (e.g., "Barista", "Manager")
        private ShiftTiming shiftTiming; // Work schedule (e.g., "9am-5pm")
        private HoursWorked hoursWorked; // Total hours worked in a period
        private PerformanceRating performanceRating; // Performance rating out of 5.0

        public EditStaffDescriptor() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used
         * internally.
         */
        public EditStaffDescriptor(EditStaffDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setRemark(toCopy.remark);
            setTags(toCopy.tags);
            setStaffId(toCopy.staffId);
            setRole(toCopy.role);
            setShiftTiming(toCopy.shiftTiming);
            setHoursWorked(toCopy.hoursWorked);
            setPerformanceRating(toCopy.performanceRating);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, remark, tags,
                staffId, role, shiftTiming, hoursWorked, performanceRating);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        /**
         * Sets {@code tags} to this object's {@code tags}. A defensive copy of
         * {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws
         * {@code UnsupportedOperationException} if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public Optional<StaffId> getStaffId() {
            return Optional.ofNullable(staffId);
        }

        public void setStaffId(StaffId staffId) {
            this.staffId = staffId;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<ShiftTiming> getShiftTiming() {
            return Optional.ofNullable(shiftTiming);
        }

        public void setShiftTiming(ShiftTiming shiftTiming) {
            this.shiftTiming = shiftTiming;
        }

        public Optional<HoursWorked> getHoursWorked() {
            return Optional.ofNullable(hoursWorked);
        }

        public void setHoursWorked(HoursWorked hoursWorked) {
            this.hoursWorked = hoursWorked;
        }

        public Optional<PerformanceRating> getPerformanceRating() {
            return Optional.ofNullable(performanceRating);
        }

        public void setPerformanceRating(PerformanceRating performanceRating) {
            this.performanceRating = performanceRating;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditStaffDescriptor)) {
                return false;
            }

            EditStaffDescriptor otherEditStaffDescriptor = (EditStaffDescriptor) other;
            return Objects.equals(name, otherEditStaffDescriptor.name)
                    && Objects.equals(phone, otherEditStaffDescriptor.phone)
                    && Objects.equals(email, otherEditStaffDescriptor.email)
                    && Objects.equals(address, otherEditStaffDescriptor.address)
                    && Objects.equals(tags, otherEditStaffDescriptor.tags)
                    && Objects.equals(staffId, otherEditStaffDescriptor.staffId)
                    && Objects.equals(role, otherEditStaffDescriptor.role)
                    && Objects.equals(shiftTiming, otherEditStaffDescriptor.shiftTiming)
                    && Objects.equals(hoursWorked, otherEditStaffDescriptor.hoursWorked)
                    && Objects.equals(performanceRating, otherEditStaffDescriptor.performanceRating);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .add("staffId", staffId)
                    .add("role", role)
                    .add("shiftTiming", shiftTiming)
                    .add("hoursWorked", hoursWorked)
                    .add("performanceRating", performanceRating)
                    .toString();
        }
    }
}
