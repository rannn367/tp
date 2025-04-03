package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalStaff.ALEX;
import static seedu.address.testutil.TypicalStaff.BEN;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.PerformanceRating;
import seedu.address.model.person.Person;
import seedu.address.model.util.TestPersonBuilder;

public class SamePerformanceRatingPredicateTest {

    @Test
    public void equals() {
        PerformanceRating rating1 = new PerformanceRating("4.5");
        PerformanceRating rating2 = new PerformanceRating("3.2");

        SamePerformanceRatingPredicate firstPredicate = new SamePerformanceRatingPredicate(rating1);
        SamePerformanceRatingPredicate secondPredicate = new SamePerformanceRatingPredicate(rating2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SamePerformanceRatingPredicate firstPredicateCopy = new SamePerformanceRatingPredicate(rating1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different performance rating -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_performanceRatingMatches_returnsTrue() {
        // Match performance rating from ALEX in TypicalStaff (4.5)
        SamePerformanceRatingPredicate predicate = new SamePerformanceRatingPredicate(new PerformanceRating("4.5"));
        assertTrue(predicate.test(ALEX));
    }

    @Test
    public void test_performanceRatingDoesNotMatch_returnsFalse() {
        // Test with different performance rating
        SamePerformanceRatingPredicate predicate = new SamePerformanceRatingPredicate(new PerformanceRating("3.0"));
        assertFalse(predicate.test(ALEX));

        // Test with another staff's performance rating
        predicate = new SamePerformanceRatingPredicate(new PerformanceRating("4.5")); // ALEX's rating
        assertFalse(predicate.test(BEN)); // BEN has different rating (4.2)
    }

    @Test
    public void test_nonStaffInstance_returnsFalse() {
        // Test with a Person that is not a Staff
        SamePerformanceRatingPredicate predicate = new SamePerformanceRatingPredicate(new PerformanceRating("4.0"));
        Person person = new TestPersonBuilder().build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        PerformanceRating rating = new PerformanceRating("4.5");
        SamePerformanceRatingPredicate predicate = new SamePerformanceRatingPredicate(rating);

        String expected = SamePerformanceRatingPredicate.class.getCanonicalName()
                + "{performanceRating=" + rating + "}";
        assertEquals(expected, predicate.toString());
    }
}
