package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.RewardPoints;

/**
 * Tests that a {@code Customer}'s {@code RewardPoints} matches the reward points given.
 */
public class RewardPointsPredicate implements Predicate<Person> {
    private final RewardPoints rewardPoints;

    public RewardPointsPredicate(RewardPoints rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    @Override
    public boolean test(Person person) {
        return person instanceof Customer && ((Customer) person).getRewardPoints().equals(rewardPoints);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RewardPointsPredicate)) {
            return false;
        }

        RewardPointsPredicate otherRewardPointsPredicate = (RewardPointsPredicate) other;
        return rewardPoints.equals(otherRewardPointsPredicate.rewardPoints);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("rewardPoints", rewardPoints).toString();
    }
}
