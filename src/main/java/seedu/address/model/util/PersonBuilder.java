package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Person objects.
 */
public abstract class PersonBuilder<T extends Person, SELF extends PersonBuilder<T, SELF>> {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "She likes aardvarks.";
    
    protected final Name name;
    protected final Phone phone;
    protected final Email email;
    protected final Address address;
    protected final Remark remark;
    protected final Set<Tag> tags;

    protected PersonBuilder() {
        this.name = new Name(DEFAULT_NAME);
        this.phone = new Phone(DEFAULT_PHONE);
        this.email = new Email(DEFAULT_EMAIL);
        this.address = new Address(DEFAULT_ADDRESS);
        this.remark = new Remark(DEFAULT_REMARK);
        this.tags = new HashSet<>();
    }

    protected PersonBuilder(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.tags = tags == null ? new HashSet<>() : new HashSet<>(tags);
    }

    protected abstract SELF createBuilder(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags);

    // Constructor to update fields when setting the name (String version)
    public SELF withName(String name) {
        return createBuilder(new Name(name), this.phone, this.email, this.address, this.remark, this.tags);
    }

    // Constructor to update fields when setting the name (Name version)
    public SELF withName(Name name) {
        return createBuilder(name, this.phone, this.email, this.address, this.remark, this.tags);
    }

    // Constructor to update fields when setting the phone (String version)
    public SELF withPhone(String phone) {
        return createBuilder(this.name, new Phone(phone), this.email, this.address, this.remark, this.tags);
    }

    // Constructor to update fields when setting the phone (Phone version)
    public SELF withPhone(Phone phone) {
        return createBuilder(this.name, phone, this.email, this.address, this.remark, this.tags);
    }

    // Constructor to update fields when setting the email (String version)
    public SELF withEmail(String email) {
        return createBuilder(this.name, this.phone, new Email(email), this.address, this.remark, this.tags);
    }

    // Constructor to update fields when setting the email (Email version)
    public SELF withEmail(Email email) {
        return createBuilder(this.name, this.phone, email, this.address, this.remark, this.tags);
    }

    // Constructor to update fields when setting the address (String version)
    public SELF withAddress(String address) {
        return createBuilder(this.name, this.phone, this.email, new Address(address), this.remark, this.tags);
    }

    // Constructor to update fields when setting the address (Address version)
    public SELF withAddress(Address address) {
        return createBuilder(this.name, this.phone, this.email, address, this.remark, this.tags);
    }

    // Constructor to update fields when setting the remark (String version)
    public SELF withRemark(String remark) {
        return createBuilder(this.name, this.phone, this.email, this.address, new Remark(remark), this.tags);
    }

    // Constructor to update fields when setting the remark (Remark version)
    public SELF withRemark(Remark remark) {
        return createBuilder(this.name, this.phone, this.email, this.address, remark, this.tags);
    }

    public SELF withTags(Set<Tag> tags) {
        return createBuilder(this.name, this.phone, this.email, this.address, this.remark, new HashSet<>(tags));
    }

    public SELF withTags(String... tags) {
        Set<Tag> tagSet = new HashSet<>();
        for (String tag : tags) {
            tagSet.add(new Tag(tag));
        }
        return createBuilder(this.name, this.phone, this.email, this.address, this.remark, tagSet);
   }
    
    public abstract T build();
}
