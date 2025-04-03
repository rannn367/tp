package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.util.TestPersonBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);
        NameContainsKeywordsPredicate firstPredicateFuzzy =
                new NameContainsKeywordsPredicate(firstPredicateKeywordList, true);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // same keywords but different fuzzy setting -> returns false
        assertFalse(firstPredicate.equals(firstPredicateFuzzy));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new TestPersonBuilder().withName(new Name("Alice Bob")).build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new TestPersonBuilder().withName(new Name("Alice Bob")).build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new TestPersonBuilder().withName(new Name("Alice Carol")).build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new TestPersonBuilder().withName(new Name("Alice Bob")).build()));
    }

    @Test
    public void test_nameMatchesFuzzyKeywords_returnsTrue() {
        // Slight misspelling with fuzzy matching enabled
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Alic"), true);
        assertTrue(predicate.test(new TestPersonBuilder().withName(new Name("Alice")).build()));

        // Character transposition with fuzzy matching enabled
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Ailce"), true);
        assertTrue(predicate.test(new TestPersonBuilder().withName(new Name("Alice")).build()));

        // Character addition with fuzzy matching enabled
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alicee"), true);
        assertTrue(predicate.test(new TestPersonBuilder().withName(new Name("Alice")).build()));
    }

    @Test
    public void test_nameWithFuzzyMatchDisabled_returnsFalse() {
        // Slight misspelling with fuzzy matching disabled
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Alic"), false);
        assertFalse(predicate.test(new TestPersonBuilder().withName(new Name("Alice")).build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TestPersonBuilder().withName(new Name("Alice")).build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new TestPersonBuilder().withName(new Name("Alice Bob")).build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new TestPersonBuilder().withName(new Name("Alice")).withPhone(new Phone("12345"))
                .withEmail(new Email("alice@email.com")).withAddress(new Address("Main Street")).build()));

        // Too different even for fuzzy matching
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Xyz"), true);
        assertFalse(predicate.test(new TestPersonBuilder().withName(new Name("Alice")).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);
        NameContainsKeywordsPredicate predicateFuzzy = new NameContainsKeywordsPredicate(keywords, true);

        String expected = NameContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + ", useFuzzyMatching=false}";
        assertEquals(expected, predicate.toString());

        String expectedFuzzy = NameContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + ", useFuzzyMatching=true}";
        assertEquals(expectedFuzzy, predicateFuzzy.toString());
    }
}
