package seedu.address.model.person.predicates;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * A predicate that combines multiple predicates with AND logic.
 * Two CombinedPredicates are equal if they contain the same set of predicates,
 * regardless of the order they were added.
 */
public class CombinedPredicate implements Predicate<Person> {
    private final Set<Predicate<Person>> predicates;

    /**
     * Constructs a CombinedPredicate with the provided predicates.
     *
     * @param predicates The set of predicates to combine with AND logic
     */
    public CombinedPredicate(Set<Predicate<Person>> predicates) {
        this.predicates = new HashSet<>(predicates);
    }

    /**
     * Tests if the person satisfies all predicates in this combined predicate.
     *
     * @param person The person to test
     * @return true if the person satisfies all predicates
     */
    @Override
    public boolean test(Person person) {
        return predicates.stream().allMatch(predicate -> predicate.test(person));
    }

    /**
     * Gets an unmodifiable view of the predicates in this combined predicate.
     *
     * @return An unmodifiable set of the predicates
     */
    public Set<Predicate<Person>> getPredicates() {
        return Collections.unmodifiableSet(predicates);
    }

    /**
     * Checks if this CombinedPredicate equals another object.
     * Two CombinedPredicates are equal if they contain the same set of predicates,
     * regardless of the order they were added.
     *
     * @param other The object to compare with
     * @return true if the objects are equal
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CombinedPredicate)) {
            return false;
        }

        CombinedPredicate otherCombinedPredicate = (CombinedPredicate) other;

        // Since predicates are function objects that may not have natural ordering,
        // we need to compare the sets by checking if they contain the same elements
        if (predicates.size() != otherCombinedPredicate.predicates.size()) {
            return false;
        }

        // Check if each predicate in this set has a matching predicate in the other set
        for (Predicate<Person> pred : predicates) {
            boolean hasMatch = false;
            for (Predicate<Person> otherPred : otherCombinedPredicate.predicates) {
                if (pred.equals(otherPred)) {
                    hasMatch = true;
                    break;
                }
            }
            if (!hasMatch) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return predicates.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", predicates)
                .toString();
    }
}
