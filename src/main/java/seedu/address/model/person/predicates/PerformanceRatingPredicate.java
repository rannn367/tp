package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.PerformanceRating;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;

/**
 * Tests that a {@code Staff}'s {@code PerformanceRating} matches the performance rating given.
 */
public class PerformanceRatingPredicate implements Predicate<Person> {
    private final PerformanceRating performanceRating;

    public PerformanceRatingPredicate(PerformanceRating performanceRating) {
        this.performanceRating = performanceRating;
    }

    @Override
    public boolean test(Person person) {
        return person instanceof Staff && ((Staff) person).getPerformanceRating().equals(performanceRating);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PerformanceRatingPredicate)) {
            return false;
        }

        PerformanceRatingPredicate otherPerformanceRatingPredicate = (PerformanceRatingPredicate) other;
        return performanceRating.equals(otherPerformanceRatingPredicate.performanceRating);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("performanceRating", performanceRating).toString();
    }
}
