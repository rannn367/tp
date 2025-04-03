package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.util.TestPersonBuilder;

public class TagsContainPredicateTest {

    @Test
    public void equals() {
        Set<Tag> firstTagSet = SampleDataUtil.getTagSet("friend", "colleague");
        Set<Tag> secondTagSet = SampleDataUtil.getTagSet("family");

        TagsContainPredicate firstPredicate = new TagsContainPredicate(firstTagSet);
        TagsContainPredicate secondPredicate = new TagsContainPredicate(secondTagSet);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsContainPredicate firstPredicateCopy = new TagsContainPredicate(firstTagSet);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tags -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagsContainAllSpecifiedTags_returnsTrue() {
        // Person with exactly the same tags
        TagsContainPredicate predicate = new TagsContainPredicate(SampleDataUtil.getTagSet("friend", "colleague"));
        assertTrue(predicate.test(new TestPersonBuilder()
                .withTags(SampleDataUtil.getTagSet("friend", "colleague")).build()));

        // Person with the specified tags and additional tags
        predicate = new TagsContainPredicate(SampleDataUtil.getTagSet("friend"));
        assertTrue(predicate.test(new TestPersonBuilder()
                .withTags(SampleDataUtil.getTagSet("friend", "colleague", "family")).build()));

        // Empty tag set (matches any person)
        predicate = new TagsContainPredicate(new HashSet<>());
        assertTrue(predicate.test(new TestPersonBuilder()
                .withTags(SampleDataUtil.getTagSet("friend")).build()));
    }

    @Test
    public void test_tagsDoNotContainAllSpecifiedTags_returnsFalse() {
        // Person with some but not all the specified tags
        TagsContainPredicate predicate = new TagsContainPredicate(
                SampleDataUtil.getTagSet("friend", "colleague", "family"));
        assertFalse(predicate.test(new TestPersonBuilder()
                .withTags(SampleDataUtil.getTagSet("friend", "colleague")).build()));

        // Person with none of the specified tags
        predicate = new TagsContainPredicate(SampleDataUtil.getTagSet("friend", "family"));
        assertFalse(predicate.test(new TestPersonBuilder()
                .withTags(SampleDataUtil.getTagSet("colleague")).build()));

        // Person with no tags
        predicate = new TagsContainPredicate(SampleDataUtil.getTagSet("friend"));
        assertFalse(predicate.test(new TestPersonBuilder().build()));
    }

    @Test
    public void toStringMethod() {
        Set<Tag> tags = SampleDataUtil.getTagSet("friend", "colleague");
        TagsContainPredicate predicate = new TagsContainPredicate(tags);

        String expected = TagsContainPredicate.class.getCanonicalName() + "{tags=" + tags + "}";
        assertEquals(expected, predicate.toString());
    }
}
