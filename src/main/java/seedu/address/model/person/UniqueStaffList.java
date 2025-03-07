package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateStaffException;
import seedu.address.model.person.exceptions.StaffNotFoundException;

/**
 * A list of staff members that enforces uniqueness between its elements and does not allow nulls.
 * A staff member is considered unique by comparing using {@code Staff#isSameStaff(Staff)}.
 * Adding and updating of staff uses {@code Staff#isSameStaff(Staff)} for equality to ensure
 * that the staff member being added or updated is unique in terms of identity in the list.
 * However, removal of a staff member uses {@code Staff#equals(Object)} to ensure that the
 * staff member with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Staff#isSameStaff(Staff)
 */
public class UniqueStaffList implements Iterable<Staff> {

    private final ObservableList<Staff> internalList = FXCollections.observableArrayList();
    private final ObservableList<Staff> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent staff member as the given argument.
     */
    public boolean contains(Staff toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameStaff);
    }

    /**
     * Adds a staff member to the list.
     * The staff member must not already exist in the list.
     */
    public void add(Staff toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStaffException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the staff member {@code target} in the list with {@code editedStaff}.
     * {@code target} must exist in the list.
     * The staff identity of {@code editedStaff} must not be the same as another existing staff member in the list.
     */
    public void setStaff(Staff target, Staff editedStaff) {
        requireAllNonNull(target, editedStaff);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new StaffNotFoundException();
        }

        if (!target.isSameStaff(editedStaff) && contains(editedStaff)) {
            throw new DuplicateStaffException();
        }

        internalList.set(index, editedStaff);
    }

    /**
     * Removes the equivalent staff member from the list.
     * The staff member must exist in the list.
     */
    public void remove(Staff toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new StaffNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code staffs}.
     * {@code staffs} must not contain duplicate staff members.
     */
    public void setStaffs(List<Staff> staffs) {
        requireAllNonNull(staffs);
        if (!staffsAreUnique(staffs)) {
            throw new DuplicateStaffException();
        }

        internalList.setAll(staffs);
    }

    /**
     * Replaces the current list with another {@code UniqueStaffList}.
     */
    public void setStaffs(UniqueStaffList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Staff> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Staff> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueStaffList)) {
            return false;
        }

        UniqueStaffList otherUniqueStaffList = (UniqueStaffList) other;
        return internalList.equals(otherUniqueStaffList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code staffs} contains only unique staff members.
     */
    private boolean staffsAreUnique(List<Staff> staffs) {
        for (int i = 0; i < staffs.size() - 1; i++) {
            for (int j = i + 1; j < staffs.size(); j++) {
                if (staffs.get(i).isSameStaff(staffs.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
