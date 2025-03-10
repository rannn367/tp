package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Staff;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Staff}.
 */
class JsonAdaptedStaff {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Staff's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String remark;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    // Staff-specific fields
    private final String staffId;
    private final String role;
    private final String shiftTiming;
    private final int hoursWorked;
    private final double performanceRating;

    /**
     * Constructs a {@code JsonAdaptedStaff} with the given staff details.
     */
    @JsonCreator
    public JsonAdaptedStaff(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                            @JsonProperty("email") String email, @JsonProperty("address") String address,
                            @JsonProperty("remark") String remark, @JsonProperty("tags") List<JsonAdaptedTag> tags,
                            @JsonProperty("staffId") String staffId, @JsonProperty("role") String role,
                            @JsonProperty("shiftTiming") String shiftTiming,
                            @JsonProperty("hoursWorked") int hoursWorked,
                            @JsonProperty("performanceRating") double performanceRating) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.staffId = staffId;
        this.role = role;
        this.shiftTiming = shiftTiming;
        this.hoursWorked = hoursWorked;
        this.performanceRating = performanceRating;
    }

    /**
     * Converts a given {@code Staff} into this class for Jackson use.
     */
    public JsonAdaptedStaff(Staff source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        remark = source.getRemark().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        staffId = source.getStaffId();
        role = source.getRole();
        shiftTiming = source.getShiftTiming();
        hoursWorked = source.getHoursWorked();
        performanceRating = source.getPerformanceRating();
    }

    /**
     * Converts this Jackson-friendly adapted staff object into the model's {@code Staff} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted staff.
     */
    public Staff toModelType() throws IllegalValueException {
        final List<Tag> staffTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            staffTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(remark);

        if (staffId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Staff ID"));
        }

        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Role"));
        }

        if (shiftTiming == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Shift Timing"));
        }

        final Set<Tag> modelTags = new HashSet<>(staffTags);
        return new Staff(modelName, modelPhone, modelEmail, modelAddress, modelRemark, modelTags,
                staffId, role, shiftTiming, hoursWorked, performanceRating);
    }
}
