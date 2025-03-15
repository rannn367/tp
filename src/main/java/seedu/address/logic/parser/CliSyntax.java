package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Prefix definitions for Staff */
    public static final Prefix PREFIX_STAFF_ID = new Prefix("sid/"); // Staff ID
    public static final Prefix PREFIX_ROLE = new Prefix("role/"); // Job role
    public static final Prefix PREFIX_SHIFT_TIMING = new Prefix("shift/"); // Shift timing
    public static final Prefix PREFIX_HOURS_WORKED = new Prefix("hours/"); // Total hours worked
    public static final Prefix PREFIX_PERFORMANCE_RATING = new Prefix("rating/"); // Performance rating

    /* Prefix definitions for Customer */
    // Add these to your existing CliSyntax.java file
    public static final Prefix PREFIX_CUSTOMER_ID = new Prefix("cid/");
    public static final Prefix PREFIX_REWARD_POINTS = new Prefix("rp/");
    public static final Prefix PREFIX_VISIT_COUNT = new Prefix("vc/");
    public static final Prefix PREFIX_FAVORITE_ITEM = new Prefix("fi/");
    public static final Prefix PREFIX_TOTAL_SPENT = new Prefix("ts/");

    // Add this to your CliSyntax class
    public static final Prefix PREFIX_INDEX = new Prefix("ind/");
    public static final Prefix PREFIX_POINTS = new Prefix("pts/");
}
