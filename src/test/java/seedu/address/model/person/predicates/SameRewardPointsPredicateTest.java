package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCustomers.JAMES;
import static seedu.address.testutil.TypicalCustomers.OLIVIA;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.RewardPoints;
import seedu.address.model.util.TestPersonBuilder;

public class SameRewardPointsPredicateTest {

    @Test
    public void equals() {
        RewardPoints points1 = new RewardPoints("100");
        RewardPoints points2 = new RewardPoints("200");

        SameRewardPointsPredicate firstPredicate = new SameRewardPointsPredicate(points1);
        SameRewardPointsPredicate secondPredicate = new SameRewardPointsPredicate(points2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SameRewardPointsPredicate firstPredicateCopy = new SameRewardPointsPredicate(points1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different reward points -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_rewardPointsMatches_returnsTrue() {
        // Match reward points from JAMES in TypicalCustomers (200)
        SameRewardPointsPredicate predicate = new SameRewardPointsPredicate(new RewardPoints("200"));
        assertTrue(predicate.test(JAMES));
    }

    @Test
    public void test_rewardPointsDoesNotMatch_returnsFalse() {
        // Test with different reward points
        SameRewardPointsPredicate predicate = new SameRewardPointsPredicate(new RewardPoints("1000"));
        assertFalse(predicate.test(JAMES));

        // Test with another customer's reward points
        predicate = new SameRewardPointsPredicate(new RewardPoints("200")); // JAMES's points
        assertFalse(predicate.test(OLIVIA)); // OLIVIA has different points
    }

    @Test
    public void test_nonCustomerInstance_returnsFalse() {
        // Test with a Person that is not a Customer
        SameRewardPointsPredicate predicate = new SameRewardPointsPredicate(new RewardPoints("100"));
        Person person = new TestPersonBuilder().build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        RewardPoints points = new RewardPoints("100");
        SameRewardPointsPredicate predicate = new SameRewardPointsPredicate(points);

        String expected = SameRewardPointsPredicate.class.getCanonicalName() + "{rewardPoints=" + points + "}";
        assertEquals(expected, predicate.toString());
    }
}
