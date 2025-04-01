package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

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
import seedu.address.model.tag.Tag;

public class StaffBuilderTest {

    private static final String TEST_NAME = "Test Staff";
    private static final String TEST_PHONE = "90123456";
    private static final String TEST_EMAIL = "staff@example.com";
    private static final String TEST_ADDRESS = "Staff Address, #02-02";
    private static final String TEST_REMARK = "Hardworking employee";
    private static final String TEST_STAFF_ID = "S2000";
    private static final String TEST_ROLE = "Manager";
    private static final String TEST_SHIFT_TIMING = "9am-5pm";
    private static final String TEST_HOURS_WORKED = "45";
    private static final String TEST_PERFORMANCE_RATING = "4.8";
    private static final String[] TEST_TAGS = {"reliable", "experienced"};

    @Test
    public void build_defaultValues_returnsStaffWithDefaultValues() {
        Staff staff = new StaffBuilder().build();

        assertEquals(StaffBuilder.DEFAULT_NAME, staff.getName().fullName);
        assertEquals(StaffBuilder.DEFAULT_PHONE, staff.getPhone().value);
        assertEquals(StaffBuilder.DEFAULT_EMAIL, staff.getEmail().value);
        assertEquals(StaffBuilder.DEFAULT_ADDRESS, staff.getAddress().value);
        assertEquals(StaffBuilder.DEFAULT_REMARK, staff.getRemark().value);
        assertEquals(StaffBuilder.DEFAULT_STAFF_ID, staff.getStaffId().value);
        assertEquals(StaffBuilder.DEFAULT_ROLE, staff.getRole().value);
        assertEquals(StaffBuilder.DEFAULT_SHIFT_TIMING, staff.getShiftTiming().value);
        assertEquals(StaffBuilder.DEFAULT_HOURS_WORKED, staff.getHoursWorked().value);
        assertEquals(StaffBuilder.DEFAULT_PERFORMANCE_RATING, staff.getPerformanceRating().value);
    }

    @Test
    public void build_withAllStringParameters_returnsStaffWithExpectedValues() {
        Staff staff = new StaffBuilder()
                .withName(new Name(TEST_NAME))
                .withPhone(new Phone(TEST_PHONE))
                .withEmail(new Email(TEST_EMAIL))
                .withAddress(new Address(TEST_ADDRESS))
                .withRemark(new Remark(TEST_REMARK))
                .withStaffId(new StaffId(TEST_STAFF_ID))
                .withRole(new Role(TEST_ROLE))
                .withShiftTiming(new ShiftTiming(TEST_SHIFT_TIMING))
                .withHoursWorked(new HoursWorked(TEST_HOURS_WORKED))
                .withPerformanceRating(new PerformanceRating(TEST_PERFORMANCE_RATING))
                .withTags(Arrays.stream(TEST_TAGS)
                        .map(Tag::new)
                        .collect(Collectors.toSet()))
                .build();

        assertEquals(TEST_NAME, staff.getName().fullName);
        assertEquals(TEST_PHONE, staff.getPhone().value);
        assertEquals(TEST_EMAIL, staff.getEmail().value);
        assertEquals(TEST_ADDRESS, staff.getAddress().value);
        assertEquals(TEST_REMARK, staff.getRemark().value);
        assertEquals(TEST_STAFF_ID, staff.getStaffId().value);
        assertEquals(TEST_ROLE, staff.getRole().value);
        assertEquals(TEST_SHIFT_TIMING, staff.getShiftTiming().value);
        assertEquals(TEST_HOURS_WORKED, staff.getHoursWorked().value);
        assertEquals(TEST_PERFORMANCE_RATING, staff.getPerformanceRating().value);

        // Verify tags
        Set<Tag> expectedTags = SampleDataUtil.getTagSet(TEST_TAGS);
        assertEquals(expectedTags, staff.getTags());
    }

    @Test
    public void build_withObjectParameters_returnsStaffWithExpectedValues() {
        Name name = new Name(TEST_NAME);
        Phone phone = new Phone(TEST_PHONE);
        Email email = new Email(TEST_EMAIL);
        Address address = new Address(TEST_ADDRESS);
        Remark remark = new Remark(TEST_REMARK);
        StaffId staffId = new StaffId(TEST_STAFF_ID);
        Role role = new Role(TEST_ROLE);
        ShiftTiming shiftTiming = new ShiftTiming(TEST_SHIFT_TIMING);
        HoursWorked hoursWorked = new HoursWorked(TEST_HOURS_WORKED);
        PerformanceRating performanceRating = new PerformanceRating(TEST_PERFORMANCE_RATING);
        Set<Tag> tags = new HashSet<>();
        for (String tagName : TEST_TAGS) {
            tags.add(new Tag(tagName));
        }

        Staff staff = new StaffBuilder()
                .withName(name)
                .withPhone(phone)
                .withEmail(email)
                .withAddress(address)
                .withRemark(remark)
                .withStaffId(staffId)
                .withRole(role)
                .withShiftTiming(shiftTiming)
                .withHoursWorked(hoursWorked)
                .withPerformanceRating(performanceRating)
                .withTags(tags)
                .build();

        assertEquals(name, staff.getName());
        assertEquals(phone, staff.getPhone());
        assertEquals(email, staff.getEmail());
        assertEquals(address, staff.getAddress());
        assertEquals(remark, staff.getRemark());
        assertEquals(staffId, staff.getStaffId());
        assertEquals(role, staff.getRole());
        assertEquals(shiftTiming, staff.getShiftTiming());
        assertEquals(hoursWorked, staff.getHoursWorked());
        assertEquals(performanceRating, staff.getPerformanceRating());
        assertEquals(tags, staff.getTags());
    }

    @Test
    public void build_withStaffCopy_returnsEqualStaff() {
        Staff original = new StaffBuilder()
                .withName(new Name(TEST_NAME))
                .withPhone(new Phone(TEST_PHONE))
                .withEmail(new Email(TEST_EMAIL))
                .withAddress(new Address(TEST_ADDRESS))
                .withRemark(new Remark(TEST_REMARK))
                .withStaffId(new StaffId(TEST_STAFF_ID))
                .withRole(new Role(TEST_ROLE))
                .withShiftTiming(new ShiftTiming(TEST_SHIFT_TIMING))
                .withHoursWorked(new HoursWorked(TEST_HOURS_WORKED))
                .withPerformanceRating(new PerformanceRating(TEST_PERFORMANCE_RATING))
                .withTags(Arrays.stream(TEST_TAGS)
                        .map(Tag::new)
                        .collect(Collectors.toSet()))
                .build();

        StaffBuilder copiedBuilder = new StaffBuilder(original);
        Staff copy = copiedBuilder.build();

        assertEquals(original, copy);
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getPhone(), copy.getPhone());
        assertEquals(original.getEmail(), copy.getEmail());
        assertEquals(original.getAddress(), copy.getAddress());
        assertEquals(original.getRemark(), copy.getRemark());
        assertEquals(original.getStaffId(), copy.getStaffId());
        assertEquals(original.getRole(), copy.getRole());
        assertEquals(original.getShiftTiming(), copy.getShiftTiming());
        assertEquals(original.getHoursWorked(), copy.getHoursWorked());
        assertEquals(original.getPerformanceRating(), copy.getPerformanceRating());
        assertEquals(original.getTags(), copy.getTags());
    }

    @Test
    public void individualSetters_stringParameters_setCorrectly() {
        StaffBuilder builder = new StaffBuilder();

        // Test each setter method individually
        Staff nameStaff = builder.withName(new Name(TEST_NAME)).build();
        assertEquals(TEST_NAME, nameStaff.getName().fullName);

        Staff phoneStaff = builder.withPhone(new Phone(TEST_PHONE)).build();
        assertEquals(TEST_PHONE, phoneStaff.getPhone().value);

        Staff emailStaff = builder.withEmail(new Email(TEST_EMAIL)).build();
        assertEquals(TEST_EMAIL, emailStaff.getEmail().value);

        Staff addressStaff = builder.withAddress(new Address(TEST_ADDRESS)).build();
        assertEquals(TEST_ADDRESS, addressStaff.getAddress().value);

        Staff remarkStaff = builder.withRemark(new Remark(TEST_REMARK)).build();
        assertEquals(TEST_REMARK, remarkStaff.getRemark().value);

        Staff staffIdStaff = builder.withStaffId(new StaffId(TEST_STAFF_ID)).build();
        assertEquals(TEST_STAFF_ID, staffIdStaff.getStaffId().value);

        Staff roleStaff = builder.withRole(new Role(TEST_ROLE)).build();
        assertEquals(TEST_ROLE, roleStaff.getRole().value);

        Staff shiftTimingStaff = builder.withShiftTiming(new ShiftTiming(TEST_SHIFT_TIMING)).build();
        assertEquals(TEST_SHIFT_TIMING, shiftTimingStaff.getShiftTiming().value);

        Staff hoursWorkedStaff = builder.withHoursWorked(new HoursWorked(TEST_HOURS_WORKED)).build();
        assertEquals(TEST_HOURS_WORKED, hoursWorkedStaff.getHoursWorked().value);

        Staff performanceRatingStaff = builder.withPerformanceRating(new PerformanceRating(TEST_PERFORMANCE_RATING)).build();
        assertEquals(TEST_PERFORMANCE_RATING, performanceRatingStaff.getPerformanceRating().value);
    }

    @Test
    public void individualSetters_objectParameters_setCorrectly() {
        StaffBuilder builder = new StaffBuilder();

        Name name = new Name(TEST_NAME);
        Staff nameStaff = builder.withName(name).build();
        assertEquals(name, nameStaff.getName());

        Phone phone = new Phone(TEST_PHONE);
        Staff phoneStaff = builder.withPhone(phone).build();
        assertEquals(phone, phoneStaff.getPhone());

        Email email = new Email(TEST_EMAIL);
        Staff emailStaff = builder.withEmail(email).build();
        assertEquals(email, emailStaff.getEmail());

        Address address = new Address(TEST_ADDRESS);
        Staff addressStaff = builder.withAddress(address).build();
        assertEquals(address, addressStaff.getAddress());

        Remark remark = new Remark(TEST_REMARK);
        Staff remarkStaff = builder.withRemark(remark).build();
        assertEquals(remark, remarkStaff.getRemark());

        StaffId staffId = new StaffId(TEST_STAFF_ID);
        Staff staffIdStaff = builder.withStaffId(staffId).build();
        assertEquals(staffId, staffIdStaff.getStaffId());

        Role role = new Role(TEST_ROLE);
        Staff roleStaff = builder.withRole(role).build();
        assertEquals(role, roleStaff.getRole());

        ShiftTiming shiftTiming = new ShiftTiming(TEST_SHIFT_TIMING);
        Staff shiftTimingStaff = builder.withShiftTiming(shiftTiming).build();
        assertEquals(shiftTiming, shiftTimingStaff.getShiftTiming());

        HoursWorked hoursWorked = new HoursWorked(TEST_HOURS_WORKED);
        Staff hoursWorkedStaff = builder.withHoursWorked(hoursWorked).build();
        assertEquals(hoursWorked, hoursWorkedStaff.getHoursWorked());

        PerformanceRating performanceRating = new PerformanceRating(TEST_PERFORMANCE_RATING);
        Staff performanceRatingStaff = builder.withPerformanceRating(performanceRating).build();
        assertEquals(performanceRating, performanceRatingStaff.getPerformanceRating());
    }

    @Test
    public void buildMultipleStaff_withChaining_returnsDistinctStaff() {
        StaffBuilder builder = new StaffBuilder();

        Staff staff1 = builder
                .withName(new Name("Staff One"))
                .withEmail(new Email("staff1@example.com"))
                .withStaffId(new StaffId("S1001"))
                .build();

        Staff staff2 = builder
                .withName(new Name("Staff Two"))
                .withEmail(new Email("staff2@example.com"))
                .withStaffId(new StaffId("S1002"))
                .build();

        // Verify that staff2 has the modified values
        assertEquals("Staff Two", staff2.getName().fullName);
        assertEquals("staff2@example.com", staff2.getEmail().value);
        assertEquals("S1002", staff2.getStaffId().value);

        // And check that they're distinct objects
        assertNotNull(staff1);
        assertNotNull(staff2);
    }
}
