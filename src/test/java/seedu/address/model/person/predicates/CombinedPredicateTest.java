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

public class CombinedPredicateTest {

    @Test
    public void equals() {
        Phone phone1 = new Phone("12345678");
        Email email1 = new Email("test@example.com");
        Address address1 = new Address("Test Address");

        PhonePredicate phonePredicate = new PhonePredicate(phone1);
        EmailPredicate emailPredicate = new EmailPredicate(email1);
        AddressPredicate addressPredicate = new AddressPredicate(address1);

        // Same set of predicates, same order -> returns true
        Set<Predicate<Person>> set1 = new HashSet<>(Arrays.asList(phonePredicate, emailPredicate, addressPredicate));
        Set<Predicate<Person>> set2 = new HashSet<>(Arrays.asList(phonePredicate, emailPredicate, addressPredicate));
        CombinedPredicate predicate1 = new CombinedPredicate(set1);
        CombinedPredicate predicate2 = new CombinedPredicate(set2);
        assertEquals(predicate1, predicate2);

        // Same set of predicates, different order -> returns true
        Set<Predicate<Person>> set3 = new HashSet<>(Arrays.asList(emailPredicate, addressPredicate, phonePredicate));
        CombinedPredicate predicate3 = new CombinedPredicate(set3);
        assertEquals(predicate1, predicate3);

        // Different predicates -> returns false
        Phone phone2 = new Phone("87654321");
        PhonePredicate phonePredicate2 = new PhonePredicate(phone2);
        Set<Predicate<Person>> set4 = new HashSet<>(Arrays.asList(phonePredicate2, emailPredicate, addressPredicate));
        CombinedPredicate predicate4 = new CombinedPredicate(set4);
        assertFalse(predicate1.equals(predicate4));

        // Different number of predicates -> returns false
        Set<Predicate<Person>> set5 = new HashSet<>(Arrays.asList(phonePredicate, emailPredicate));
        CombinedPredicate predicate5 = new CombinedPredicate(set5);
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

        PhonePredicate phonePredicate = new PhonePredicate(new Phone("12345678"));
        EmailPredicate emailPredicate = new EmailPredicate(new Email("test@example.com"));
        AddressPredicate addressPredicate = new AddressPredicate(new Address("Test Address"));

        Set<Predicate<Person>> predicates = new HashSet<>();
        predicates.add(phonePredicate);
        predicates.add(emailPredicate);
        predicates.add(addressPredicate);

        CombinedPredicate combinedPredicate = new CombinedPredicate(predicates);
        assertTrue(combinedPredicate.test(person));
    }

    @Test
    public void test_onePredicateDoesNotMatch_returnsFalse() {
        Person person = new TestPersonBuilder()
                .withPhone(new Phone("12345678"))
                .withEmail(new Email("test@example.com"))
                .withAddress(new Address("Test Address"))
                .build();

        PhonePredicate phonePredicate = new PhonePredicate(new Phone("12345678"));
        EmailPredicate emailPredicate = new EmailPredicate(new Email("different@example.com")); // Different email
        AddressPredicate addressPredicate = new AddressPredicate(new Address("Test Address"));

        Set<Predicate<Person>> predicates = new HashSet<>();
        predicates.add(phonePredicate);
        predicates.add(emailPredicate);
        predicates.add(addressPredicate);

        CombinedPredicate combinedPredicate = new CombinedPredicate(predicates);
        assertFalse(combinedPredicate.test(person));
    }

    @Test
    public void test_emptyPredicates_returnsTrue() {
        Person person = new TestPersonBuilder().build();
        CombinedPredicate combinedPredicate = new CombinedPredicate(Collections.emptySet());
        assertTrue(combinedPredicate.test(person));
    }

    @Test
    public void getPredicates_modifyReturnedSet_throwsUnsupportedOperationException() {
        PhonePredicate phonePredicate = new PhonePredicate(new Phone("12345678"));
        Set<Predicate<Person>> predicates = new HashSet<>();
        predicates.add(phonePredicate);

        CombinedPredicate combinedPredicate = new CombinedPredicate(predicates);
        Set<Predicate<Person>> returnedPredicates = combinedPredicate.getPredicates();

        try {
            returnedPredicates.add(new EmailPredicate(new Email("test@example.com")));
            // If we get here, the test should fail because we were able to modify the collection
            assertFalse(true, "Should not be able to modify the returned set");
        } catch (UnsupportedOperationException e) {
            // Expected
            assertTrue(true);
        }
    }
}
