package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Customer;
import seedu.address.model.person.FavouriteItem;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Customer}'s {@code FavouriteItem} matches the favourite item given.
 */
public class FavouriteItemPredicate implements Predicate<Person> {
    private final FavouriteItem favouriteItem;

    public FavouriteItemPredicate(FavouriteItem favouriteItem) {
        this.favouriteItem = favouriteItem;
    }

    @Override
    public boolean test(Person person) {
        return person instanceof Customer && ((Customer) person).getFavouriteItem().equals(favouriteItem);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FavouriteItemPredicate)) {
            return false;
        }

        FavouriteItemPredicate otherFavouriteItemPredicate = (FavouriteItemPredicate) other;
        return favouriteItem.equals(otherFavouriteItemPredicate.favouriteItem);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("favouriteItem", favouriteItem).toString();
    }
}
