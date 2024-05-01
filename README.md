# Financial Tracker Application 💰

## Description of the Project

The Financial Tracker Java console application is designed to help users manage their financial 
transactions effectively. Its purpose is to provide a convenient way for users to track their deposits, 
payments (debits), and overall account activity. The intended users of this application are individuals 
or small businesses who want to maintain a clear record of their financial transactions.

#### Main Functionality:

Home Screen:
Users are presented with options to add deposits, make payments (debits), view the ledger, generate reports, or exit the application.

    Add Deposit (D) and Make Payment (P):
Users can input deposit or payment information, including date, time, description, vendor, and amount. The application then saves this information to a CSV file, ensuring a record of all financial activities.

    Ledger (L):
The ledger screen displays all financial entries. Users can choose to view all entries, only deposits, or only payments (debits).

    Reports (R):
- Users can generate pre-defined reports for specific time periods such as Month To Date, Previous Month, Year To Date, Previous Year, and Vendor. 
Additionally, they can search for transactions by Custom Search, 
this feature allows users to perform a detailed search based on specific criteria, including start date, end date, description, vendor, and amount. 
The application filters entries based on the entered criteria, offering a customized view of financial transactions.
The application will also log all custom searches and save them to a SearchLog.csv.


#### Purpose and Problem Solving:

The Financial Tracker application aims to solve the problem of manual financial tracking and organization. 
It provides a structured platform for users to record deposits, payments, and other financial activities seamlessly. 
By maintaining an accurate ledger and offering reporting functionalities, the application empowers users to gain 
insights into their financial status, identify spending trends, and make informed decisions about budgeting and financial planning. 
Overall, this Java console application simplifies financial management for individuals and small businesses, ensuring clarity 
and efficiency in tracking financial transactions.


![Class Diagram](path/to/your/class_diagram.png)

## User Stories


- As a user, I want to be able to manage my financial transactions conveniently, so I can keep track of my deposits and payments accurately.

- As a user, I want to view my financial transactions in an organized manner, so I can easily track my account activity.

- As a user, I want to generate reports based on specific time periods or vendor names, so I can analyze my spending and income trends.

- As a user, I want to perform a custom search on my financial transactions, so I can filter entries based on specific criteria.
The application should filter entries based on the entered values. If a value is not entered for a field, the application should not filter based on that field.

## Setup

Instructions on how to set up and run the project using IntelliJ IDEA.

### Prerequisites

- IntelliJ IDEA: Ensure you have IntelliJ IDEA installed, which you can download from [here](https://www.jetbrains.com/idea/download/).
- Java SDK: Make sure Java SDK is installed and configured in IntelliJ.

### Running the Application in IntelliJ

Follow these steps to get your application running within IntelliJ IDEA:

1. Open IntelliJ IDEA.
2. Select "Open" and navigate to the directory where you cloned or downloaded the project.
3. After the project opens, wait for IntelliJ to index the files and set up the project.
4. Find the main class with the `public static void main(String[] args)` method.
5. Right-click on the file and select 'Run 'YourMainClassName.main()'' to start the application.

## Technologies Used

- Java: Mention the version you are using.
- Any additional libraries or frameworks used in the project.

## Demo

Include screenshots or GIFs that show your application in action. Use tools like [Giphy Capture](https://giphy.com/apps/giphycapture) to record a GIF of your application.

![Application Screenshot](path/to/your/screenshot.png)

## Future Work

Outline potential future enhancements or functionalities you might consider adding:

- Additional feature to be developed.
- Improvement of current functionalities.

## Resources

List resources such as tutorials, articles, or documentation that helped you during the project.

- [Java Programming Tutorial](https://www.example.com)
- [Effective Java](https://www.example.com)

## Team Members

- **Aisha** - Specific contributions or roles.
- **Pratick** - Specific contributions or roles.
- **Ben** - Specific contributions or roles.

## Thanks

Express gratitude towards those who provided help, guidance, or resources:

- Thank you to Raymound Maroun for continuous support and guidance.
- A special thanks to all teammates for their dedication and teamwork.
