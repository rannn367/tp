---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# CaféConnect User Guide

## Welcome to CaféConnect!

Hello and welcome to **CaféConnect**! Thanks for choosing us to help manage your café. We know running a café is no easy task. We know running a café is no walk in the park; from managing customer orders, handling staff schedules, and keeping everything running smoothly, it’s easy to feel like you’re juggling too much. But with **CaféConnect**, we make it simpler, so you can spend less time stressing and more time doing what you do best—serving great food and drinks.

### Efficient Café Management
Think of **CaféConnect** like your café’s personal assistant. Whether you're adding a customer's details or checking who’s working today, everything is just a quick command away. No need to waste time scrolling through contacts or digging through papers, everything you need is right in front of you, ready to go.

### What CaféConnect Can Do for You
1. Customer Management
* **Add customer details:** Use customeradd to quickly input customer preferences, allergies, and favourite orders. You’ll remember your regulars and what they like, and they’ll appreciate the personal touch you bring every time.
* **Build customer loyalty:** The more you know about your customers, the better you can serve them. Keep track of their preferences, and they’ll keep coming back for more!
2. Staff Management
* **Effortlessly manage staff:** Use staffadd to add new staff members and store their roles and contact details. It’s all in one place, so you won’t have to worry about sifting through paper notes or trying to remember who’s working which shift.
* **Stay organised:** With all the staff details in one system, you can easily check who’s on shift and who’s available. No more confusion during the busy hours.

### Why Choose CaféConnect?

**CaféConnect** is like the swiss army knife of café management. Whether it’s adding customer info or organising staff, it’s quick, easy, and efficient. You won’t waste time clicking through complicated contact books or trying to find the right spreadsheet. Just use the command line, and you’ll get what you need in seconds. No fuss, no headache.

### Overview of Target Users

If you're a café owner who types fast and needs a simple way to manage customer details and staff, CafeConnect is for you! We know running a café can be hectic, so we designed CafeConnect to help you stay organised with just a few quick commands.


<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

## Adding a customer: `customeradd`

Adds a customer to the address book with required details such as customer ID, name, phone, email, address, reward points, visit count, favorite item, and total spent.

Format: `customeradd cid/CUSTOMER_ID n/NAME p/PHONE e/EMAIL a/ADDRESS rp/REWARD_POINTS vc/VISIT_COUNT fi/FAVORITE_ITEM ts/TOTAL_SPENT [t/TAG]…`

* `CUSTOMER_ID` should start with a 'C' followed by digits, e.g., C1001
* `NAME` should only contain alphanumeric characters and spaces, and it should not be blank
* `PHONE` should only contain digits, and it should be at least 3 digits long
* `EMAIL` must be a valid email address.
* `ADDRESS` can take any value, and it should not be blank
* `REWARD_POINTS` should only contain digits
* `VISIT_COUNT` should only contain digits
* `FAVORITE_ITEM` can take any value, and it should not be blank
* `TOTAL_SPENT` should only contain digits, representing the amount in dollars

Examples:
* `customeradd cid/C001 n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 rp/150 vc/8 fi/Cappuccino ts/120`
* `customeradd cid/C002 n/Betsy Crowe p/1234567 e/betsycrowe@example.com a/Newgate Prison rp/300 vc/15 fi/Mocha ts/250 t/vip t/regular`

These are the before and after images of the first example

<div style="display: flex; justify-content: center; align-items: flex-start; gap: 20px; flex-wrap: wrap; margin-bottom: 20px;">
  <div style="text-align: center; max-width: 48%;">
    <img src="images/UG_Ui_Images/before_customeradd.png" alt="Before adding the customer" style="max-width: 100%; height: auto;"/>
    <p><i>Before adding the customer</i></p>
  </div>
  <div style="text-align: center; max-width: 48%;">
    <img src="images/UG_Ui_Images/after_customeradd.png" alt="After adding the customer" style="max-width: 100%; height: auto;"/>
    <p><i>After adding the customer</i></p>
  </div>
</div>

## Deleting a customer: `customerdelete`

Deletes the specified customer from the address book.

Format: `customerdelete INDEX`

* Deletes the customer at the specified `INDEX`.
* The `INDEX` refers to the index number shown in the displayed customer list.
* The `INDEX` **must be a positive integer** 1, 2, 3, …
* The `INDEX` **must be a valid index number** (e.g. 5 is not valid when there is less than five applications in the displayed list).

Examples:
* `customerdelete 2` deletes the 2nd customer in the address book.

These are the before and after images of the first example

<div style="display: flex; justify-content: center; align-items: flex-start; gap: 20px; flex-wrap: wrap; margin-bottom: 20px;">
  <div style="text-align: center; max-width: 48%;">
    <img src="images/UG_Ui_Images/before_customerdelete.png" alt="Before deleting the customer" style="max-width: 100%; height: auto;"/>
    <p><i>Before deleting the customer</i></p>
  </div>
  <div style="text-align: center; max-width: 48%;">
    <img src="images/UG_Ui_Images/after_customerdelete.png" alt="After deleting the customer" style="max-width: 100%; height: auto;"/>
    <p><i>After deleting the customer</i></p>
  </div>
</div>

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

CafeConnect data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

CafeConnect data are saved automatically in the following JSON files:
- Address book data: `[JAR file location]/data/addressbook.json`
- Drink catalog data: `[JAR file location]/data/drinkcatalog.json`
- User preferences: `[JAR file location]/preferences.json`

Advanced users are welcome to update data directly by editing these data files.


<box type="warning" seamless>

**Caution:**
If your changes to any data file makes its format invalid, CafeConnect will discard all data in that file and start with an empty data file at the next run. Hence, it is recommended to take a backup of the files before editing them.<br>
Furthermore, certain edits can cause CafeConnect to behave in unexpected ways (e.g., if a value entered is outside the acceptable range for the address book, drink catalog, or user preferences). Therefore, edit the data files only if you are confident that you can update them correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
