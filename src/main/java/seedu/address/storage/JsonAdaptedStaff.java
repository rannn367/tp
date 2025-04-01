package seedu.address.storage;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Staff;
import seedu.address.model.util.StaffBuilder;

/**
 * Jackson-friendly version of {@link Staff}.
 */
public class JsonAdaptedStaff extends JsonAdaptedPerson<Staff> {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Staff's %s field is missing!";

    // Staff-specific fields
    private final String staffId;
    private final String role;
    private final String shiftTiming;
    private final String hoursWorked;
    private final String performanceRating;

    /**
     * Constructs a {@code JsonAdaptedStaff} with the given staff details.
     */
    @JsonCreator
    public JsonAdaptedStaff(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                            @JsonProperty("email") String email, @JsonProperty("address") String address,
                            @JsonProperty("remark") String remark, @JsonProperty("tags") List<JsonAdaptedTag> tags,
                            @JsonProperty("staffId") String staffId, @JsonProperty("role") String role,
                            @JsonProperty("shiftTiming") String shiftTiming,
                            @JsonProperty("hoursWorked") String hoursWorked,
                            @JsonProperty("performanceRating") String performanceRating) {
        super(name, phone, email, address, remark, tags);
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
        super(source);
        staffId = source.getStaffId().value;
        role = source.getRole().value;
        shiftTiming = source.getShiftTiming().value;
        hoursWorked = source.getHoursWorked().value;
        performanceRating = source.getPerformanceRating().value;
    }

    public StaffBuilder modifyBuilder(StaffBuilder personBuilder) throws IllegalValueException {
        super.modifyBuilder(personBuilder);
        return personBuilder.withStaffId(staffId)
                .withRole(role)
                .withShiftTiming(shiftTiming)
                .withHoursWorked(hoursWorked)
                .withPerformanceRating(performanceRating);
    }

    /**
     * Converts this Jackson-friendly adapted staff object into the model's {@code Staff} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted staff.
     */
    public Staff toModelType() throws IllegalValueException {
        StaffBuilder personBuilder = new StaffBuilder();
        personBuilder = modifyBuilder(personBuilder);
        return personBuilder.build();
    }
}