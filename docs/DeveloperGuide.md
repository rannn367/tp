---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# AB-3 Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

![github](images/Persona.png)
* Name: Amelia Tan
* Age: 34 years
* Gender: Female
* Business Type: Independent café owner of a single-location café "Brew Haven"
* Team Size: Small team (5–20 staff members)
* Customer Base: 100–300 regular customers
* Pain Points:
  * Struggles with scattered information (handwritten notes, spreadsheets, physical loyalty cards)
  * Finds it hard to keep track of customer preferences, allergies, birthdays, and visit history 
  * Needs a better way to manage staff shifts, roles, and contact details
  * Deals with frequent equipment breakdowns without a proper maintenance log
  * Wants to streamline supplier management and order frequency tracking
* Technical Comfort:
  * Prefers desktop apps over web or mobile solutions for stability and performance
  * Comfortable with a Command Line Interface (CLI) for fast data entry and retrieval
  * Types fast and prefers keyboard shortcuts over mouse interactions
  * Reasonably tech-savvy but values an intuitive and easy-to-learn system
* Work Style:
  * Juggles multiple roles — customer service, staff management, inventory, and maintenance
  * Values efficiency and quick access to information to avoid disruptions during peak hours
  * Wants to delegate responsibilities without losing oversight
* Goals:
  * Build strong customer loyalty by remembering personal preferences and rewarding regulars
  * Minimize downtime caused by equipment failures through proper maintenance tracking
  * Streamline supplier coordination and avoid last-minute shortages
  * Maintain a well-organized staff schedule with clear roles and availability

**Value proposition**: manage customers, staff, suppliers, and maintenance faster and more efficiently than a typical mouse/GUI-driven app, all in one centralized system tailored for a fast-paced café environment.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​     | I want to …​                                                     | So that I can…​                                                     |
|----------|-------------|------------------------------------------------------------------|---------------------------------------------------------------------|
| `* * *`  | café owner  | add a staff member's phone number                                | contact them when needed.                                           |
| `* * *`  | café owner  | delete a staff member's phone number                             | keep my contact list updated.                                       |
| `* * *`  | café owner  | view a list of all staff phone numbers                           | quickly find and reach them.                                        |
| `* * *`  | café owner  | search for a staff member by name to retrieve their phone number | quickly find the right contact.                                     |
| `* * *`  | café owner  | view a list of supplier emails                                   | map stock requests to specific emails.                              |
| `* * *`  | café owner  | view a list of current stock                                     | know when to reorder stock.                                         |
| `* * *`  | café owner  | add a repair technician's phone number                           | contact them quickly when needed.                                   |
| `* * *`  | café owner  | delete a repair technician's phone number                        | keep my contact list updated.                                       |
| `* * *`  | café owner  | add a repair technician's email address                          | reach them via email if required.                                   |
| `* * *`  | café owner  | delete a repair technician's email address                       | remove outdated contact information.                                |
| `* * *`  | café owner  | view a list of all repair technician's contact details           | find and contact them easily.                                       |
| `* * *`  | café owner  | search for a repair technician by name                           | retrieve their contact information efficiently.                     |
| `* * *`  | café owner  | tag repair technicians based on their expertise                  | easily identify the right person for a specific issue.              |
| `* * *`  | café owner  | store notes on past repair services for each technician          | track maintenance history and avoid repeated issues.                |
| `* * *`  | café owner  | set reminders for regular maintenance appointments               | proactively prevent equipment failures.                             |
| `* *`    | café owner  | set up autofill fields for frequently entered data               | avoid repetitive typing.                                            |
| `* *`    | café owner  | enable autofill for supplier names                               | avoid having to type them repeatedly when creating orders.          |
| `* *`    | café owner  | enable autofill for supplier email addresses                     | quickly send stock requests without re-entering details.            |
| `* *`    | café owner  | enable autofill for supplier phone numbers                       | contact them without needing to look up their details every time.   |
| `* *`    | café owner  | enable autofill for commonly ordered stock items                 | quickly add them to an order form.                                  |
| `* *`    | café owner  | enable autofill for standard pricing of stock items              | avoid manually entering expected costs.                             |
| `* *`    | café owner  | enable autofill for my café's business details                   | avoid having to repeatedly enter them when placing orders.          |
| `* *`    | café owner  | edit or update saved autofill fields                             | ensure information remains accurate.                                |
| `*`      | café owner  | disable autofill fields                                          | enter information manually when necessary.                          |
| `* *`    | café owner  | view a list of all autofill fields I have set up                 | manage them easily.                                                 |
| `* *`    | café owner  | delete autofill entries for outdated suppliers or items          | prevent storing unnecessary data.                                   |
| `*`      | café owner  | categorize autofill fields                                       | organize frequently entered data more efficiently.                  |
| `* *`    | café owner  | enable autofill suggestions when entering data                   | choose from previously used values instead of typing them manually. |
| `* * *`  | café owner  | add new customers                                                | track their purchase history.                                       |
| `* * *`  | café owner  | record a drink ordered by a customer in their account            | record the history the customer has with us.                        |
| `* * *`  | café owner  | track the data on the types of drinks the customer orders        | tell when a customer has a favourite drink.                         |
| `* * *`  | café owner  | add to a list of supplier emails                                 | keep track of each supplier contact.                                |
| `* * *`  | café owner  | delete from list of supplier emails                              | keep the list updated.                                              |


*{More to be added}*

### Use cases

(For all use cases below, the System is the Café Management System and the Actor is the café owner, unless specified otherwise)

**Use case: Add a Staff Member**

**MSS**

1. Café owner requests to add a new staff member. 
2. System prompts for staff details: name, phone number, and role. 
3. Café owner provides the required details. 
4. System validates the input. 
5. System adds the new staff member. 
6. System confirms the addition.
Use case ends.

**Extensions**

* 4a. Provided name format is invalid. 

  * 4a1. System shows an error message. 
  
  * 4a2. Use case resumes at step 2.

* 4b. Provided phone number is invalid.

  * 4b1. System shows an error message. 
  
  * 4b2. Use case resumes at step 2.

* 4c. Role description exceeds character limit.

  * 4c1. System shows an error message.
  
  * 4c2. Use case resumes at step 2.

* 4d. Staff member with the same name and phone number already exists. 

  * 4d1. System shows a duplication error message.

    Use case ends.

* 4e. Staff member with the same name but different phone number exists.

  * 4e1. System prompts to confirm if phone number should be updated.
  
  * 4e2. Café owner chooses not to update.
  
  Use case ends. 
  
  * 4e3. Café owner chooses to update.
  
    * System updates phone number.
    
    * System confirms update.
    
  Use case ends.

**Use case: Delete a Staff Member**

**MSS**

1. Café owner requests to delete a staff member.

2. System prompts for the staff member's name.

3. Café owner provides the staff member’s name.

4. System checks if the staff member exists.

5. System deletes the staff member.

6. System confirms the deletion.

7. Use case ends.

**Extensions**
* 4a. No staff member with the given name is found.

  * 4a1. System shows an error message.

    Use case ends.

* 4b. Multiple staff members with the same name are found.

  * 4b1. System prompts for the phone number.

  * 4b2. Café owner provides the phone number.

  * 4b3. System validates and deletes the correct entry.

  * 4b4. System confirms the deletion.

    Use case ends.

**Use case: View Staff List**

**MSS**

1. Café owner requests to view the staff list.

2. System retrieves and displays the list of staff members.

Use case ends.

**Extensions**
* 2a. The staff list is empty.

  * 2a1. System shows a message indicating no staff members are found.

    Use case ends.

**Use case: Search Staff by Name**

**MSS**

1. Café owner requests to search for a staff member by name.

2. System prompts for the staff member’s name.

3. Café owner provides the staff member’s name.

4. System searches for matching staff members.

5. System displays matching staff members.

Use case ends.

**Extensions**
* 4a. No staff members with the given name are found.

  * 4a1. System shows a message indicating no matches.

    Use case ends.

* 4b. Multiple staff members with the same name are found.

  * 4b1. System displays all matching entries.

    Use case ends.

**Use case: Define a Reward**

**MSS**

1. Café owner requests to define a new reward.

2. System prompts for reward details: reward name and points required.

3. Café owner provides the required details.

4. System validates the input.

5. System adds the new reward.

6. System confirms the addition.

Use case ends.

**Extensions**
* 4a. Provided reward name format is invalid. 

  * 4a1. System shows an error message. 

  * 4a2. Use case resumes at step 2.

* 4b. Provided points value is invalid.

  * 4b1. System shows an error message.
  
  * 4b2. Use case resumes at step 2.

* 4c. Reward with the same name already exists.

  * 4c1. System shows a duplication error message.

    Use case ends

**Use case: Edit a Reward**

**MSS**

1. Café owner requests to edit an existing reward.

2. System prompts for reward name and new points required.

3. Café owner provides the reward name and updated points.

4. System checks if the reward exists.

5. System updates the reward.

6. System confirms the update.

Use case ends.

**Extensions**
* 4a. Reward not found.

  * 4a1. System shows an error message.
  
    Use case ends.

* 4b. Provided points value is invalid.

  * 4b1. System shows an error message.
  
  * 4b2. Use case resumes at step 2.

**Use case: Track Customer Points**

**MSS**

1. Café owner requests to update customer points.

2. System prompts for customer name and points to add or deduct.

3. Café owner provides the customer name and points.

4. System validates the input.

5. System updates the customer’s points balance.

6. System confirms the update.

Use case ends.

**Extensions**
* 4a. Provided customer name format is invalid. 

  * 4a1. System shows an error message.
    
  * 4a2. Use case resumes at step 2.

* 4b. Provided points value is invalid.

  * 4b1. System shows an error message.

  * 4b2. Use case resumes at step 2.

* 4c. Customer not found.

  * 4c1. System shows an error message.
  
    Use case ends.

* 4d. Multiple customers with the same name are found.

  * 4d1. System prompts for a unique identifier (e.g., phone number).
  
  * 4d2. Café owner provides the identifier.
  
  * 4d3. System validates and updates the correct entry.
  
  * 4d4. System confirms the update.

    Use case ends.

**Use case: Redeem a Reward**

**MSS**

1. Café owner requests to redeem a reward for a customer.

2. System prompts for customer name and reward name.

3. Café owner provides the required details.

4. System checks if the customer and reward exist.

5. System verifies if the customer has enough points.

6. System deducts the required points and confirms the redemption.

Use case ends.

**Extensions**
* 4a. Customer not found.

  * 4a1. System shows an error message.

    Use case ends.

* 4b. Reward not found.

  * 4b1. System shows an error message.
  
    Use case ends.

* 5a. Customer does not have enough points.

  * 5a1. System shows an insufficient points message.
  
    Use case ends.

**Use case: View Customer Visit History**

**MSS**

1. Café owner requests to view a customer’s visit history.

2. System prompts for the customer’s name.

3. Café owner provides the customer’s name.

4. System retrieves and displays the customer’s visit history.

Use case ends.

**Extensions**
* 4a. Customer not found.

  * 4a1. System shows an error message.
  
    Use case ends.

* 4b. Customer has no recorded visits.

  * 4b1. System shows a message indicating no visit history.

    Use case ends.

**Use case: Generate Daily Sales Summary**

**MSS**

1. Café owner requests a daily sales summary for a specific date.

2. System validates the date format.

3. System retrieves sales data for the given date.

4. System compiles the sales summary, including total revenue, transaction count, and most popular item.

5. System displays the daily sales summary.

Use case ends.

**Extensions**
* 2a. Provided date format is invalid.

  * 2a1. System shows an error message.
  
    Use case ends.

* 3a. No sales data found for the given date.

  * 3a1. System shows a message indicating no recorded transactions.
  
    Use case ends.

**Use case: Generate Customer Insights Report**

**MSS**

1. Café owner requests customer insights by providing a customer name or choosing a predefined report type (e.g., top spenders, popular items).

2. System validates the input.

3. System retrieves and compiles the requested insights.

4. System displays the customer insights report.

Use case ends.

**Extensions**
* 2a. Provided customer name format is invalid.

  * 2a1. System shows an error message.
  
    Use case ends.

* 2b. No data available for the requested report.

  * 2b1. System shows a message indicating lack of data.
  
    Use case ends.

* 3a. Customer not found.

  * 3a1. System shows an error message.
  
    Use case ends.

**Use case: Monitor Stock Levels**

**MSS**

1. Café owner requests to check stock levels for a specific item or set a low-stock threshold alert.

2. System validates the input.

3. System retrieves current stock data.

4. System displays the stock levels or sets the threshold alert.

Use case ends.

**Extensions**
* 2a. Provided item name format is invalid.

  * 2a1. System shows an error message.
  
    Use case ends.

* 2b. Item not found in inventory.

  * 2b1. System shows an error message.
  
    Use case ends.

* 2c. Provided threshold value is invalid.

  * 2c1. System shows an error message.
  
    Use case ends.

* 3a. Stock level is below the threshold.

  * 3a1. System shows a low-stock alert message.

    Use case ends.

**Use case: View Customer Rewards**

**MSS**

1. Café owner requests to view a customer’s reward points.

2. System validates the customer name.

3. System retrieves and displays the customer’s reward points and eligible rewards.

Use case ends.

**Extensions**
* 2a. Provided customer name format is invalid.

  * 2a1. System shows an error message.
  
    Use case ends.

* 3a. Customer not found.

  * 3a1. System shows an error message.
  
    Use case ends.

**Use case: View Customer Order History**

**MSS**

1. Café owner requests to view a customer’s order history.

2. System validates the customer name.

3. System retrieves and displays the customer’s past orders.

Use case ends.

**Extensions**
* 2a. Provided customer name format is invalid.

  * 2a1. System shows an error message.
  
    Use case ends.

* 3a. Customer not found.

  * 3a1. System shows an error message.
  
    Use case ends.

* 3b. No past orders found for the customer.

  * 3b1. System shows a message indicating no recorded orders.

    Use case ends.

**Use case: Record Customer Feedback**

**MSS**

1. Café owner requests to add feedback for a customer.

2. System prompts for customer name, rating, and feedback message.

3. Café owner provides the required details.

4. System validates the input.

5. System records the feedback.

6. System confirms the feedback addition.

Use case ends.

**Extensions**
* 4a. Provided customer name format is invalid.

  * 4a1. System shows an error message.
  
  * 4a2. Use case resumes at step 2.

* 4b. Provided rating is out of the accepted range.

  * 4b1. System shows an error message.
  
  * 4b2. Use case resumes at step 2.

* 4c. Feedback message exceeds character limit.

  * 4c1. System shows an error message.
  
  * 4c2. Use case resumes at step 2.

* 4d. Customer not found.

  * 4d1. System shows an error message.
  
    Use case ends.

**Use case: Add a Supplier Email**

**MSS**
1. Café owner requests to add a new supplier email.

2. System prompts for the supplier's name and email.

3. Café owner provides the required details.

4. System validates the input.

5. System adds the new supplier.

6. System confirms the addition.

Use case ends.

**Extensions**
* 4a. Provided name format is invalid.

  * 4a1. System shows an error message.
  
  * 4a2. Use case resumes at step 2.
  
* 4b. Provided email format is invalid.

  * 4b1. System shows an error message.

  * 4b2. Use case resumes at step 2.

* 4c. Supplier with the same name and email already exists.

  * 4c1. System shows a duplication error message.

    Use case ends.
    
* 4d. Supplier with the same name but a different email exists.
  
  * 4d1. System prompts to confirm if email should be updated.
    
  * 4d2. Café owner chooses not to update.
    
    Use case ends.
    
  * 4d3. Café owner chooses to update.
    
    * System updates the email.
      
    * System confirms the update.
      
    Use case ends.

* 4e. Required fields are missing.
    
  * 4e1. System shows an error message.
      
  * 4e2. Use case resumes at step 2.

**Use case: Delete a Supplier Email**

**MSS**

1. Café owner requests to delete a supplier email.

2. System prompts for the supplier's name.

3. Café owner provides the supplier's name.

4. System checks if the supplier exists.

5. System deletes the supplier.

6. System confirms the deletion.

Use case ends. 

**Extensions**

* 4a. No supplier with the given name is found.

  * 4a1. System shows an error message.

    Use case ends.
  
* 4b. Multiple suppliers with the same name are found.

  * 4b1. System prompts for the email.
  
  * 4b2. Café owner provides the email.
  
  * 4b3. System validates and deletes the correct entry.
  
  * 4b4. System confirms the deletion.
  
    Use case ends.

**Use case: View Supplier Email List**
**MSS**

1. Café owner requests to view the supplier email list.

2. System retrieves and displays the list of suppliers.

Use case ends.

**Extensions**

* 2a. The supplier list is empty.

  * 2a1. System shows a message indicating no suppliers are found.
  
    Use case ends.

**Use case: View Supplier Email List**

**MSS**

1. Café owner requests to view the supplier email list.

2. System retrieves and displays the list of suppliers.

Use case ends.

**Extensions**
* 2a. The supplier list is empty.

  * 2a1. System shows a message indicating no suppliers are found.

    Use case ends.

### Non-Functional Requirements

1. Performance & Scalability

   * The application should be able to handle up to 1000 persons without noticeable sluggishness in performance for typical usage. 
   
   * The system should respond to user actions within 1 second for typical operations like adding, deleting, or searching for contacts. 
   
   * The startup time should be no more than 2 seconds on a modern consumer-grade computer. 
   
   * The application should consume no more than 200MB of RAM during normal operation with 1000 persons stored.

3. Compatibility & Portability
   
   * The application should be compatible with Java 17 or above and run on any mainstream OS (Windows, Linux, macOS, Unix).
   
   * The application should be usable on both 32-bit and 64-bit architectures.
   
   * The system should be self-contained and should not require an internet connection for core functionalities.
   
3. Usability & Accessibility

   * A user with above-average typing speed for regular English text should be able to accomplish most tasks faster using commands than using the mouse.
   
   * The user interface should be intuitive enough for users who are not IT-savvy.
   
   * The application should provide clear and actionable error messages when a user makes an incorrect input.
   
4. Reliability & Robustness

   * The system should not crash or freeze when an invalid command is entered.
   
   * User data should be persisted safely to prevent accidental loss due to application crashes.
   
   * The system should be able to recover gracefully from unexpected failures (e.g., power loss, abrupt shutdowns).
   
5. Security & Privacy

   * The system should not expose private contact details unless explicitly permitted by the user.
   
   * The application should ensure that only authorized users can access certain functionalities if applicable.

6. Maintainability & Extensibility

   * The codebase should be modular and well-structured, allowing easy addition of new features without affecting existing ones.
   
   * The system should be easily maintainable, with clear documentation for developers.

7. Compliance & Constraints

   * The application should follow standard software development best practices, ensuring reliability and efficiency. 
   
   * The application should not exceed a total package size of 100MB to ensure ease of distribution and comply with Constraint-File-Size.

### Glossary

* Mainstream OS: Operating systems commonly used by the majority of users, including Windows, Linux, Unix, and macOS.

* Private contact detail: A contact detail that is not meant to be shared with others, such as a personal phone number, home address, or private email.

* Typical usage: Standard operations performed within the application, including adding, editing, deleting, and searching for contacts, assuming a user base of up to 1000 persons.

* Command-based interaction: A method of input where users type textual commands to execute actions instead of using a graphical user interface (GUI).

* Graphical User Interface (GUI): A visual interface that allows users to interact with the system through elements like buttons, forms, and icons instead of typing commands.

* Error message: A message displayed by the system when a user enters an invalid input or an operation cannot be completed, providing clear guidance on how to correct the issue.

* Modern consumer-grade computer: A personal computer with at least a quad-core processor and 8GB of RAM, manufactured within the last five years.

* Modular codebase: A structured code design where different components can be modified, replaced, or extended independently without affecting the overall system.

* Persisted data: Information that is stored and retained across application restarts, ensuring that user data is not lost when the application is closed.

* Startup time: The time taken from launching the application to when it is fully ready for user interaction.

* Authorized user: A user who has been granted specific permissions to access certain functionalities within the system.

* Self-contained application: An application that does not require external dependencies or an internet connection for its core features to function.

* Scalability: The system’s ability to maintain performance and responsiveness even as the user base or data size increases.

* Undo/Redo functionality: A feature that allows users to reverse or reapply their last action, improving usability and error recovery.

* Extensibility: The ability to add new features or enhance existing ones without major rework of the system.

* Logging and audit trail: A system feature that records user actions and system events for security, debugging, or compliance purposes.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
