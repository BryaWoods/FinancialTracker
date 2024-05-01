package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
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
            scanner.nextLine();

        } while (amount <= 0);


        Transaction deposit = new Transaction(date, time, description, vendor, amount);
        transactions.add(deposit);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {

                String formattedDeposit = String.format("%s|%s|%s|%s|%.2f",
                        deposit.getDate(), deposit.getTime(),
                        deposit.getDescription(), deposit.getVendor(), deposit.getAmount());
                writer.write(formattedDeposit);
                //writer.newLine();

            System.out.println("Thank you! Your deposit has been added.");
        } catch (IOException e) {
            System.err.println("Error writing transactions to file: " + e.getMessage());

        }

    }



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

        System.out.println("Enter the amount (must be positive):");
        double amount;

        do {
            while (!scanner.hasNextDouble()) {

                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }

            amount = scanner.nextDouble();



        } while (amount <= 0);

        scanner.nextLine();


        Transaction payment = new Transaction(date, time, description, vendor, -amount);
        transactions.add(payment);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv",true))) {

                String formattedPayment = String.format("%s|%s|%s|%s|%.2f",
                        payment.getDate(), payment.getTime(),
                        payment.getDescription(), payment.getVendor(), payment.getAmount());
                writer.write(formattedPayment);
                //writer.newLine();

            System.out.println("Thank you! Your payment has been added.");
        } catch (IOException e) {
            System.err.println("Error writing transactions to file: " + e.getMessage());

        }
    }



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
        System.out.println(" °˖✧✿✧˖°        °˖✧✿✧˖°       °˖✧✿✧˖✿˖✧✿✧˖°         °˖✧✿✧˖°           °˖✧✿✧˖°        ");

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
        System.out.println(" °˖✧✿✧˖°        °˖✧✿✧˖°       °˖✧✿✧˖✿˖✧✿✧˖°         °˖✧✿✧˖°           °˖✧✿✧˖°        ");
        for (Transaction deposit : transactions) {
            double amount = deposit.getAmount();
            if (amount > 0)

                System.out.printf("| %-12s | %-12s | %-20s | %-15s | $%-9.2f |\n",
                        deposit.getDate(), deposit.getTime(),
                        deposit.getDescription(), deposit.getVendor(),
                        deposit.getAmount());

        }
        System.out.println("╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡°˖✧✿✧˖°╞══✿══|°˖✧✿✧˖°╞══✿══╡");

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
            System.out.println("6) Custom Search ");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();


            switch (input) {
                case "1":
                    LocalDate currentDate = LocalDate.now();
                    LocalDate startOfCurrentMonth = currentDate.withDayOfMonth(1);
                    LocalDate endOfCurrentMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
                    filterTransactionsByDate(transactions, startOfCurrentMonth, endOfCurrentMonth);
                    break;


                case "2":
                    LocalDate firstDayOfPreviousMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
                    LocalDate lastDayOfPreviousMonth = LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth());
                    filterTransactionsByDate(transactions, firstDayOfPreviousMonth, lastDayOfPreviousMonth);
                    break;


                case "3":
                    LocalDate startOfCurrentYear = LocalDate.now().withDayOfYear(1);
                    LocalDate endOfCurrentYear = LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear());
                    filterTransactionsByDate(transactions, startOfCurrentYear, endOfCurrentYear);
                    break;


                case "4":
                    LocalDate startOfPreviousYear = LocalDate.now().minusYears(1).withDayOfYear(1);
                    LocalDate endOfPreviousYear = LocalDate.now().minusYears(1).withDayOfYear(LocalDate.now().minusYears(1).lengthOfYear());
                    filterTransactionsByDate(transactions, startOfPreviousYear, endOfPreviousYear);
                    break;

                case "5":
                    System.out.println("Enter a Vendor: ");
                    String vendor = scanner.nextLine();
                    filterTransactionsByVendor(vendor);
                    break;

                case "6":
                    customSearch(scanner);

                    break;
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
                        " | Time: " + transaction.getTime() +
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



    private static void filterTransactionsByVendor(String vendor) {

        boolean foundTransactions = false;
        for (Transaction transaction : transactions) {
            if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println("Date: " + transaction.getDate() +
                        " | Time: " + transaction.getTime() +
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


    private static void customSearch(Scanner scanner) {
        System.out.println("Custom Search");

        System.out.println("Enter Start Date (yyyy-MM-dd): ");
        String startDateStr = scanner.nextLine().trim();
        LocalDate startDate = startDateStr.isEmpty() ? null : LocalDate.parse(startDateStr, DATE_FORMATTER);

        System.out.println("Enter End Date (yyyy-MM-dd): ");
        String endDateStr = scanner.nextLine().trim();
        LocalDate endDate = endDateStr.isEmpty() ? null : LocalDate.parse(endDateStr, DATE_FORMATTER);

        System.out.println("Enter Description: ");
        String description = scanner.nextLine().trim();

        System.out.println("Enter Vendor: ");
        String vendor = scanner.nextLine().trim();

        System.out.println("Enter Amount (positive number): ");
        String amountStr = scanner.nextLine().trim();
        Double amount = amountStr.isEmpty() ? null : Double.parseDouble(amountStr);

        System.out.println("Search Results:");

        LocalTime searchTime = LocalTime.now();

        boolean foundTransactions = false;
        for (Transaction transaction : transactions) {
            boolean matchesCriteria =
                    (startDate == null || transaction.getDate().isAfter(startDate)) &&
                            (endDate == null || transaction.getDate().isBefore(endDate)) &&
                            (description.isEmpty() || transaction.getDescription().equalsIgnoreCase(description)) &&
                            (vendor.isEmpty() || transaction.getVendor().equalsIgnoreCase(vendor)) &&
                            (amount == null || Double.compare(transaction.getAmount(), amount) == 0);

            if (matchesCriteria) {
                System.out.println("Date: " + transaction.getDate() +
                        " | Time: " + transaction.getTime() +
                        " | Vendor: " + transaction.getVendor() +
                        " | Description: " + transaction.getDescription() +
                        " | Amount: " + transaction.getAmount());
                foundTransactions = true;
            }
        }

        if (!foundTransactions) {
            System.out.println("No transactions found matching the search criteria.");
        }

        writeToSearchLog(startDate, endDate, searchTime, description, vendor, amount, foundTransactions);

    }

    private static void writeToSearchLog(LocalDate startDate, LocalDate endDate, LocalTime searchTime, String description, String vendor, Double amount, boolean foundTransactions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("search-log.csv", true))) {

            writer.write("Search Time: " + searchTime + "\n");
            writer.write("Search Criteria:\n");
            writer.write("Start Date: " + startDate + "\n");
            writer.write("End Date: " + endDate + "\n");
            writer.write("Description: " + description + "\n");
            writer.write("Vendor: " + vendor + "\n");
            writer.write("Amount: " + amount + "\n");

            writer.write("Search Results:\n");
            if (foundTransactions) {
                for (Transaction transaction : transactions) {
                    boolean matchesCriteria =
                            (startDate == null || transaction.getDate().isAfter(startDate)) &&
                                    (endDate == null || transaction.getDate().isBefore(endDate)) &&
                                    (description.isEmpty() || transaction.getDescription().equalsIgnoreCase(description)) &&
                                    (vendor.isEmpty() || transaction.getVendor().equalsIgnoreCase(vendor)) &&
                                    (amount == null || Double.compare(transaction.getAmount(), amount) == 0);

                    if (matchesCriteria) {
                        writer.write("Date: " + transaction.getDate() +
                                " | Time: " + transaction.getTime() +
                                " | Vendor: " + transaction.getVendor() +
                                " | Description: " + transaction.getDescription() +
                                " | Amount: " + transaction.getAmount() + "\n");
                    }
                }
            } else {
                writer.write("No transactions found matching the search criteria.\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to search log file: " + e.getMessage());
        }
    }
}



