package seedu.address.model.person.predicates;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tags} contains all the tags given.
 */
public class TagsContainPredicate implements Predicate<Person> {
    private final Set<Tag> tags;

    public TagsContainPredicate(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().containsAll(tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagsContainPredicate)) {
            return false;
        }

        TagsContainPredicate otherTagsContainPredicate = (TagsContainPredicate) other;
        return tags.equals(otherTagsContainPredicate.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tags", tags).toString();
    }
}
