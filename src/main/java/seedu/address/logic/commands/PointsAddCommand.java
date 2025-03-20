package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POINTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.drink.Drink;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Adds reward points to an existing customer in the address book.
 */
public class PointsAddCommand extends Command {

    public static final String COMMAND_WORD = "pointsadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds reward points to a customer in the address book. "
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_POINTS + "POINTS_TO_ADD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_POINTS + "100";

    public static final String MESSAGE_ADD_POINTS_SUCCESS = "Points added to %1$s: %2$d. Total points now: %3$d";
    public static final String MESSAGE_INVALID_POINTS = "Points to add must be a positive integer.";

    private final Index targetIndex;
    private final int pointsToAdd;

    /**
     * Creates a PointsAddCommand to add the specified {@code points} to customer at {@code Index}
     */
    public PointsAddCommand(Index targetIndex, int pointsToAdd) {
        requireAllNonNull(targetIndex, pointsToAdd);
        this.targetIndex = targetIndex;
        this.pointsToAdd = pointsToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (pointsToAdd <= 0) {
            throw new CommandException(MESSAGE_INVALID_POINTS);
        }

        List<Customer> lastShownList = model.getFilteredCustomerList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        Customer customerToUpdate = lastShownList.get(targetIndex.getZeroBased());
        Customer updatedCustomer = createUpdatedCustomer(customerToUpdate, pointsToAdd);

        model.setCustomer(customerToUpdate, updatedCustomer);
        model.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);

        return new CommandResult(String.format(MESSAGE_ADD_POINTS_SUCCESS,
                customerToUpdate.getName().fullName, pointsToAdd, updatedCustomer.getRewardPoints()));
    }

    /**
     * Creates and returns a {@code Customer} with the details of {@code customerToUpdate}
     * with added reward points.
     */
    private static Customer createUpdatedCustomer(Customer customerToUpdate, int pointsToAdd) {
        assert customerToUpdate != null;

        Name name = customerToUpdate.getName();
        Phone phone = customerToUpdate.getPhone();
        Email email = customerToUpdate.getEmail();
        Address address = customerToUpdate.getAddress();
        Remark remark = customerToUpdate.getRemark();
        Set<Tag> tags = customerToUpdate.getTags();
        String customerId = customerToUpdate.getCustomerId();
        int updatedRewardPoints = customerToUpdate.getRewardPoints() + pointsToAdd;
        int visitCount = customerToUpdate.getVisitCount();
        Drink favoriteItem = customerToUpdate.getFavoriteItem();
        double totalSpent = customerToUpdate.getTotalSpent();

        return new Customer(name, phone, email, address, remark, tags,
                customerId, updatedRewardPoints, visitCount, favoriteItem, totalSpent);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PointsAddCommand)) {
            return false;
        }

        PointsAddCommand otherCommand = (PointsAddCommand) other;
        return targetIndex.equals(otherCommand.targetIndex)
                && pointsToAdd == otherCommand.pointsToAdd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("pointsToAdd", pointsToAdd)
                .toString();
    }
}
