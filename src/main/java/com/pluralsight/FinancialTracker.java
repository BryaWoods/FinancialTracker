package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FinancialTracker {

    private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String fileName) {

        String line;

        try {
            BufferedReader br = new BufferedReader(new FileReader("transactions.csv"));
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                double price = Double.parseDouble(parts[4]);

                transactions.add(new Transaction(date, time, description, vendor, price));
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        // ✓This method should load transactions from a file with the given file name.
        // If the file does not exist, it should be created.
        // ✓The transactions should be stored in the `transactions` ArrayList.
        // ✓Each line of the file represents a single transaction in the following format:
        // <date>,<time>,<vendor>,<description>,<amount>
        // For example: 2023-04-29,13:45:00,Amazon,PAYMENT,29.99
        // ⭐︎⭐︎⭐︎After reading all the transactions, the file should be closed.
        // If any errors occur, an appropriate error message should be displayed.
    }

    private static void addDeposit(Scanner scanner) {

        System.out.println("\nEnter your deposit information:");
        System.out.print("Date (yyyy-MM-dd) ");
        String inputDate = scanner.nextLine();
        LocalDate date = LocalDate.parse(inputDate, DATE_FORMATTER);

        System.out.println("Enter the time (HH:mm:ss): ");
        String inputTime = scanner.nextLine();
        LocalTime time = LocalTime.parse(inputTime, TIME_FORMATTER);

        System.out.println("Enter a description of deposit: ");
        String description = scanner.nextLine();

        System.out.println("Enter the vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter the amount (amount must be positive):");
        double amount;

        do {
            while (!scanner.hasNextDouble()) {

                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }

            amount = scanner.nextDouble();

        } while (amount <= 0);


        Transaction deposit = new Transaction(date, time, description, vendor, amount);
        transactions.add(deposit);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            for (Transaction transaction : transactions) {
                String formattedTransaction = String.format("%s|%s|%s|%s|%.2f\n",
                        transaction.getDate(), transaction.getTime(),
                        transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
                writer.write(formattedTransaction);
                writer.newLine();
            }
            System.out.println("Transactions have been written to transactions.csv");
        } catch (IOException e) {
            System.err.println("Error writing transactions to file: " + e.getMessage());

        }
        scanner.close();
    }

    // ✓This method should prompt the user to enter the date, time, vendor, and amount of a deposit.
    // ✓The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
    // ✓The amount should be a positive number.
    // ✓After validating the input, a new `Deposit` object should be created with the entered values.
    // ✓The new deposit should be added to the `transactions` ArrayList.


    private static void addPayment(Scanner scanner) {

        System.out.println("\nEnter your payment information:");
        System.out.print("Date (yyyy-MM-dd) ");
        String inputDate = scanner.nextLine();
        LocalDate date = LocalDate.parse(inputDate, DATE_FORMATTER);

        System.out.println("Enter the time (HH:mm:ss): ");
        String inputTime = scanner.nextLine();
        LocalTime time = LocalTime.parse(inputTime, TIME_FORMATTER);

        System.out.println("Enter a description of payment: ");
        String description = scanner.nextLine();

        System.out.println("Enter the vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter the amount (amount must be positive):");
        double amount;

        do {
            while (!scanner.hasNextDouble()) {

                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }

            amount = scanner.nextDouble();


        } while (amount <= 0);


        Transaction payment = new Transaction(date, time, description, vendor, amount);
        transactions.add(payment);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv"))) {
            for (Transaction transaction : transactions) {
                String formattedTransaction = String.format("%s|%s|%s|%s|-%.2f\n",
                        transaction.getDate(), transaction.getTime(),
                        transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
                writer.write(formattedTransaction);
                writer.newLine();
            }
            System.out.println("Transactions have been written to transactions.csv");
        } catch (IOException e) {
            System.err.println("Error writing transactions to file: " + e.getMessage());

        }
        scanner.close();
    }

    //SUBTRACTING MONEY
    // ✓This method should prompt the user to enter the date, time, vendor, and amount of a payment.
    // ✓The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
    // ✓The amount should be a positive number.
    // ✓After validating the input, a new `Payment` object should be created with the entered values.
    // ✓The new payment should be added to the `transactions` ArrayList.


    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        System.out.println("                                    LEDGER                                            ");
        System.out.println("╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡");
        System.out.printf("| %-12s | %-12s | %-20s | %-15s | %-10s |\n",
                "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println(" °˖✧✿✧˖°        °˖✧✿✧˖°       °˖✧✿✧˖°°˖✧✿✧˖°         °˖✧✿✧˖°           °˖✧✿✧˖°        ");

        for (Transaction transaction : transactions) {
            System.out.printf("| %-12s | %-12s | %-20s | %-15s | $%-9.2f |\n",
                    transaction.getDate(), transaction.getTime(),
                    transaction.getDescription(), transaction.getVendor(),
                    transaction.getAmount());
        }

        System.out.println("╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡");
    }


    private static void displayDeposits() {

        System.out.println("                                    DEPOSITS                                            ");
        System.out.println("╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡");
        System.out.printf("| %-12s | %-12s | %-20s | %-15s | %-10s |\n",
                "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println(" °˖✧✿✧˖°        °˖✧✿✧˖°       °˖✧✿✧˖°°˖✧✿✧˖°         °˖✧✿✧˖°           °˖✧✿✧˖°        ");
        for (Transaction deposit : transactions) {
            double amount = deposit.getAmount();
            if (amount > 0)

                System.out.printf("| %-12s | %-12s | %-20s | %-15s | $%-9.2f |\n",
                        deposit.getDate(), deposit.getTime(),
                        deposit.getDescription(), deposit.getVendor(),
                        deposit.getAmount());

        }
        System.out.println("╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡");
        // This method should display a table of all deposits in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount.
    }

    private static void displayPayments() {

        System.out.println("                                    PAYMENTS                                            ");
        System.out.println("╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡");
        System.out.printf("| %-12s | %-12s | %-20s | %-15s | %-10s |\n",
                "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println(" °˖✧✿✧˖°        °˖✧✿✧˖°       °˖✧✿✧˖°°˖✧✿✧˖°         °˖✧✿✧˖°           °˖✧✿✧˖°        ");
        for (Transaction payment : transactions) {
            double amount = payment.getAmount();
            if (amount < 0)

                System.out.printf("| %-12s | %-12s | %-20s | %-15s | $%-9.2f |\n",
                        payment.getDate(), payment.getTime(),
                        payment.getDescription(), payment.getVendor(),
                        payment.getAmount());


        }
        // This method should display a table of all payments in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount.
    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();


            switch (input) {
                case "1":
                    LocalDate currentDate = LocalDate.now();
                    LocalDate startOfCurrentMonth = currentDate.withDayOfMonth(1);
                    LocalDate endOfCurrentMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
                    filterTransactionsByDate(transactions, startOfCurrentMonth, endOfCurrentMonth);
                    break;

                // Generate a report for all transactions within the current month,
                // including the date, vendor, and amount for each transaction.
                case "2":
                    LocalDate firstDayOfPreviousMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
                    LocalDate lastDayOfPreviousMonth = LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth());
                    filterTransactionsByDate(transactions, firstDayOfPreviousMonth, lastDayOfPreviousMonth);
                    break;

                // Generate a report for all transactions within the previous month,
                // including the date, vendor, and amount for each transaction.
                case "3":
                    LocalDate startOfCurrentYear = LocalDate.now().withDayOfYear(1);
                    LocalDate endOfCurrentYear = LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear());
                    filterTransactionsByDate(transactions, startOfCurrentYear, endOfCurrentYear);
                    break;
                // Generate a report for all transactions within the current year,
                // including the date, vendor, and amount for each transaction.

                case "4":
                    LocalDate startOfPreviousYear = LocalDate.now().minusYears(1).withDayOfYear(1);
                    LocalDate endOfPreviousYear = LocalDate.now().minusYears(1).withDayOfYear(LocalDate.now().minusYears(1).lengthOfYear());
                    filterTransactionsByDate(transactions, startOfPreviousYear, endOfPreviousYear);
                    break;
                // Generate a report for all transactions within the previous year,
                // including the date, vendor, and amount for each transaction.
                case "5":
                    System.out.println("Enter a Vendor: ");
                    String vendor = scanner.nextLine();
                    filterTransactionsByVendor(vendor);
                    break;
                // Prompt the user to enter a vendor name, then generate a report for all transactions
                // with that vendor, including the date, vendor, and amount for each transaction.
                case "0":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }


    private static void filterTransactionsByDate(ArrayList<Transaction> transactions, LocalDate startDate, LocalDate endDate) {
        System.out.println("Transactions between " + startDate + " and " + endDate + ":");

        boolean foundTransactions = false;
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();
            if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate)) {
                System.out.println("Date: " + transaction.getDate() +
                        " | Vendor: " + transaction.getVendor() +
                        " | Description: " + transaction.getDescription() +
                        " | Amount: " + transaction.getAmount());
                foundTransactions = true;
            }
        }

        if (!foundTransactions) {
            System.out.println("No transactions found in the specified date range.");
        }
    }


    // ✓This method filters the transactions by date and prints a report to the console.
    // ✓It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
    // ✓The method loops through the transactions list and checks each transaction's date against the date range.
    // ✓Transactions that fall within the date range are printed to the console.
    // ✓If no transactions fall within the date range, the method prints a message indicating that there are no results.


    private static void filterTransactionsByVendor(String vendor) {

        boolean foundTransactions = false;
        for (Transaction transaction : transactions) {
            if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println("Date: " + transaction.getDate() +
                        " | Vendor: " + transaction.getVendor() +
                        " | Description: " + transaction.getDescription() +
                        " | Amount: " + transaction.getAmount());

                foundTransactions = true;
            }

            if (!foundTransactions) {
                System.out.println("No transactions found in the specified date range.");
            }
        }


        // This method filters the transactions by vendor and prints a report to the console.
        // It takes one parameter: vendor, which represents the name of the vendor to filter by.
        // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
        // Transactions with a matching vendor name are printed to the console.
        // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
    }
}

