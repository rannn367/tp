package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * A generic builder class for creating {@code Person} objects.
 *
 * @param <T>    The type of Person being built.
 * @param <SELF> The type of the concrete builder class.
 */
public abstract class PersonBuilder<T extends Person, SELF extends PersonBuilder<T, SELF>> {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "She likes aardvarks.";
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    protected final Name name;
    protected final Phone phone;
    protected final Email email;
    protected final Address address;
    protected final Remark remark;
    protected final Set<Tag> tags;

    /**
     * Constructs a {@code PersonBuilder} with default values.
     */
    protected PersonBuilder() {
        this.name = new Name(DEFAULT_NAME);
        this.phone = new Phone(DEFAULT_PHONE);
        this.email = new Email(DEFAULT_EMAIL);
        this.address = new Address(DEFAULT_ADDRESS);
        this.remark = new Remark(DEFAULT_REMARK);
        this.tags = new HashSet<>();
    }

    /**
     * Constructs a {@code PersonBuilder} with specified values.
     *
     * @param name    The name of the person.
     * @param phone   The phone number of the person.
     * @param email   The email of the person.
     * @param address The address of the person.
     * @param remark  The remark about the person.
     * @param tags    The set of tags associated with the person.
     */
    protected PersonBuilder(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.tags = tags == null ? new HashSet<>() : new HashSet<>(tags);
    }

    /**
     * Returns the error message for a missing field.
     * @param fieldName The name of the field that is missing.
     * @return The error message indicating the missing field.
     */
    public String getErrorMessage(String fieldName) {
        return String.format(MISSING_FIELD_MESSAGE_FORMAT, fieldName);
    }

    /**
     * Creates a new builder instance with updated fields.
     */
    protected abstract SELF createBuilder(
            Name name,
            Phone phone,
            Email email,
            Address address,
            Remark remark,
            Set<Tag> tags);

    /**
     * Sets the name of the person.
     *
     * @param name The name to set.
     * @return A new builder instance with the updated name.
     * @throws IllegalValueException if name is invalid.
     */
    public SELF withName(String name) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(getErrorMessage(Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return withName(new Name(name));
    }

    /**
     * Sets the name of the person.
     *
     * @param name The {@code Name} object to set.
     * @return A new builder instance with the updated name.
     */
    public SELF withName(Name name) {
        return createBuilder(name, this.phone, this.email, this.address, this.remark, this.tags);
    }

    /**
     * Sets the phone number of the person.
     *
     * @param phone The phone number to set.
     * @return A new builder instance with the updated phone number.
     * @throws IllegalValueException if phone is invalid.
     */
    public SELF withPhone(String phone) throws IllegalValueException {
        if (phone == null) {
            throw new IllegalValueException(getErrorMessage(Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        return withPhone(new Phone(phone));
    }

    /**
     * Sets the phone number of the person.
     *
     * @param phone The {@code Phone} object to set.
     * @return A new builder instance with the updated phone number.
     */
    public SELF withPhone(Phone phone) {
        return createBuilder(this.name, phone, this.email, this.address, this.remark, this.tags);
    }

    /**
     * Sets the email of the person.
     *
     * @param email The email to set.
     * @return A new builder instance with the updated email.
     * @throws IllegalValueException if email is invalid.
     */
    public SELF withEmail(String email) throws IllegalValueException {
        if (email == null) {
            throw new IllegalValueException(getErrorMessage(Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        return withEmail(new Email(email));
    }

    /**
     * Sets the email of the person.
     *
     * @param email The {@code Email} object to set.
     * @return A new builder instance with the updated email.
     */
    public SELF withEmail(Email email) {
        return createBuilder(this.name, this.phone, email, this.address, this.remark, this.tags);
    }

    /**
     * Sets the address of the person.
     *
     * @param address The address to set.
     * @return A new builder instance with the updated address.
     * @throws IllegalValueException if address is invalid.
     */
    public SELF withAddress(String address) throws IllegalValueException {
        if (address == null) {
            throw new IllegalValueException(getErrorMessage(Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        return withAddress(new Address(address));
    }

    /**
     * Sets the address of the person.
     *
     * @param address The {@code Address} object to set.
     * @return A new builder instance with the updated address.
     */
    public SELF withAddress(Address address) {
        return createBuilder(this.name, this.phone, this.email, address, this.remark, this.tags);
    }

    /**
     * Sets the remark for the person.
     *
     * @param remark The remark to set.
     * @return A new builder instance with the updated remark.
     * @throws IllegalValueException if remark is invalid.
     */
    public SELF withRemark(String remark) throws IllegalValueException {
        if (remark == null) {
            throw new IllegalValueException(getErrorMessage(Remark.class.getSimpleName()));
        }
        if (!Remark.isValidRemark(remark)) {
            throw new IllegalValueException(Remark.MESSAGE_CONSTRAINTS);
        }
        return withRemark(new Remark(remark));
    }

    /**
     * Sets the remark for the person.
     *
     * @param remark The {@code Remark} object to set.
     * @return A new builder instance with the updated remark.
     */
    public SELF withRemark(Remark remark) {
        return createBuilder(this.name, this.phone, this.email, this.address, remark, this.tags);
    }

    /**
     * Sets the tags for the person.
     *
     * @param tags The set of tags to assign.
     * @return A new builder instance with the updated tags.
     */
    public SELF withTags(Set<Tag> tags) {
        return createBuilder(this.name, this.phone, this.email, this.address, this.remark, new HashSet<>(tags));
    }

    /**
     * Sets the tags for the person using string values.
     *
     * @param tags The tags to assign, given as strings.
     * @return A new builder instance with the updated tags.
     * @throws IllegalValueException if any tag is invalid.
     */
    public SELF withTags(String... tags) throws IllegalValueException {
        if (tags == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Tags"));
        }

        Set<Tag> tagSet = new HashSet<>();
        for (String tag : tags) {
            if (tag == null) {
                throw new IllegalValueException(getErrorMessage(Tag.class.getSimpleName()));
            }
            if (!Tag.isValidTagName(tag)) {
                throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
            }
            tagSet.add(new Tag(tag));
        }
        return createBuilder(this.name, this.phone, this.email, this.address, this.remark, tagSet);
    }

    /**
     * Builds and returns the final {@code Person} object.
     *
     * @return The constructed person.
     */
    public abstract T build();
}
