package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.util.TestPersonBuilder;

public class SameFieldsPredicateTest {

    @Test
    public void equals() {
        Phone phone1 = new Phone("12345678");
        Email email1 = new Email("test@example.com");
        Address address1 = new Address("Test Address");

        SamePhonePredicate phonePredicate = new SamePhonePredicate(phone1);
        SameEmailPredicate emailPredicate = new SameEmailPredicate(email1);
        SameAddressPredicate addressPredicate = new SameAddressPredicate(address1);

        // Same set of predicates, same order -> returns true
        Set<Predicate<Person>> set1 = new HashSet<>(Arrays.asList(phonePredicate, emailPredicate, addressPredicate));
        Set<Predicate<Person>> set2 = new HashSet<>(Arrays.asList(phonePredicate, emailPredicate, addressPredicate));
        SameFieldsPredicate predicate1 = new SameFieldsPredicate(set1);
        SameFieldsPredicate predicate2 = new SameFieldsPredicate(set2);
        assertEquals(predicate1, predicate2);

        // Same set of predicates, different order -> returns true
        Set<Predicate<Person>> set3 = new HashSet<>(Arrays.asList(emailPredicate, addressPredicate, phonePredicate));
        SameFieldsPredicate predicate3 = new SameFieldsPredicate(set3);
        assertEquals(predicate1, predicate3);

        // Different predicates -> returns false
        Phone phone2 = new Phone("87654321");
        SamePhonePredicate phonePredicate2 = new SamePhonePredicate(phone2);
        Set<Predicate<Person>> set4 = new HashSet<>(Arrays.asList(phonePredicate2, emailPredicate, addressPredicate));
        SameFieldsPredicate predicate4 = new SameFieldsPredicate(set4);
        assertFalse(predicate1.equals(predicate4));

        // Different number of predicates -> returns false
        Set<Predicate<Person>> set5 = new HashSet<>(Arrays.asList(phonePredicate, emailPredicate));
        SameFieldsPredicate predicate5 = new SameFieldsPredicate(set5);
        assertFalse(predicate1.equals(predicate5));

        // Different types -> returns false
        assertFalse(predicate1.equals(1));

        // Null -> returns false
        assertFalse(predicate1.equals(null));
    }

    @Test
    public void test_allPredicatesMatch_returnsTrue() {
        Person person = new TestPersonBuilder()
                .withPhone(new Phone("12345678"))
                .withEmail(new Email("test@example.com"))
                .withAddress(new Address("Test Address"))
                .build();

        SamePhonePredicate phonePredicate = new SamePhonePredicate(new Phone("12345678"));
        SameEmailPredicate emailPredicate = new SameEmailPredicate(new Email("test@example.com"));
        SameAddressPredicate addressPredicate = new SameAddressPredicate(new Address("Test Address"));

        Set<Predicate<Person>> predicates = new HashSet<>();
        predicates.add(phonePredicate);
        predicates.add(emailPredicate);
        predicates.add(addressPredicate);

        SameFieldsPredicate sameFieldsPredicate = new SameFieldsPredicate(predicates);
        assertTrue(sameFieldsPredicate.test(person));
    }

    @Test
    public void test_onePredicateDoesNotMatch_returnsFalse() {
        Person person = new TestPersonBuilder()
                .withPhone(new Phone("12345678"))
                .withEmail(new Email("test@example.com"))
                .withAddress(new Address("Test Address"))
                .build();

        SamePhonePredicate phonePredicate = new SamePhonePredicate(new Phone("12345678"));
        SameEmailPredicate emailPredicate = new SameEmailPredicate(new Email("different@example.com"));
        SameAddressPredicate addressPredicate = new SameAddressPredicate(new Address("Test Address"));

        Set<Predicate<Person>> predicates = new HashSet<>();
        predicates.add(phonePredicate);
        predicates.add(emailPredicate);
        predicates.add(addressPredicate);

        SameFieldsPredicate sameFieldsPredicate = new SameFieldsPredicate(predicates);
        assertFalse(sameFieldsPredicate.test(person));
    }

    @Test
    public void test_emptyPredicates_returnsTrue() {
        Person person = new TestPersonBuilder().build();
        SameFieldsPredicate sameFieldsPredicate = new SameFieldsPredicate(Collections.emptySet());
        assertTrue(sameFieldsPredicate.test(person));
    }

    @Test
    public void getPredicates_modifyReturnedSet_throwsUnsupportedOperationException() {
        SamePhonePredicate phonePredicate = new SamePhonePredicate(new Phone("12345678"));
        Set<Predicate<Person>> predicates = new HashSet<>();
        predicates.add(phonePredicate);

        SameFieldsPredicate sameFieldsPredicate = new SameFieldsPredicate(predicates);
        Set<Predicate<Person>> returnedPredicates = sameFieldsPredicate.getPredicates();

        try {
            returnedPredicates.add(new SameEmailPredicate(new Email("test@example.com")));
            // If we get here, the test should fail because we were able to modify the collection
            assertFalse(true, "Should not be able to modify the returned set");
        } catch (UnsupportedOperationException e) {
            // Expected
            assertTrue(true);
        }
    }
}
