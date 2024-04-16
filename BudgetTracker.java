import java.util.*;

class Expense {
    String category;
    double amount;

    Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }
}

class Budget {
    String category;
    double amount;

    Budget(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }
}

class User {
    String username;
    String password;
    List<Expense> expenses;
    List<Budget> budgets;
    List<String> notifications;
    static int idCounter = 1;  // user id

    User(String username, String password) {
        this.username = username;
        this.password = password;
        this.expenses = new ArrayList<>();
        this.budgets = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    User(String name) {
        this.username = generateUsername(name);
        this.password = generatePassword();
        this.expenses = new ArrayList<>();
        this.budgets = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    // generate username 
    private String generateUsername(String name) {
        return name.toLowerCase().replace(" ", "") + idCounter++;
    }

    // generate random password
    private String generatePassword() {
        return UUID.randomUUID().toString().replaceAll("-","").substring(0, 8);
    }
}

public class BudgetTracker {
    static Scanner scanner = new Scanner(System.in);
    static User currentUser;
    public static void main(String[] args) {
        showMenu();
    }

    static void showMenu() {
        while (true) {
            System.out.println("\n*** Budget Tracking System ***");
            System.out.println("1. Login");
            System.out.println("2. Categorize Expenses");
            System.out.println("3. Exit");
            System.out.print("Enter a number (1-3): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    if (currentUser != null) {
                        enterExpenseDatabase();
                    } else {
                        System.out.println("Please log in first.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void login() {
        System.out.println("Are you registered? (Yes/No)");
        String registeredChoice = scanner.next().toLowerCase();

        if (registeredChoice.equals("yes")) {
            System.out.println("Enter your username:");
            String username = scanner.nextLine();
            System.out.println("Enter your password:");
            String password = scanner.nextLine();

            if (currentUser != null && currentUser.username.equals(username) && currentUser.password.equals(password)) {
                System.out.println("Login successful!");
                enterExpenseDatabase();
            } else {
                System.out.println("Invalid username or password. Returning to the main menu...");
            }
        } else if (registeredChoice.equals("no")) {
            System.out.print("Enter your name:");
            String name = scanner.next();
            currentUser = new User(name);
            System.out.println("Sign up successful! Your username is: " + currentUser.username + " and your password is: " + currentUser.password);
            enterExpenseDatabase();
        } else {
            System.out.println("Invalid choice. Returning to the main menu...");
        }
    }

    static void enterExpenseDatabase() {
        while (true) {
            System.out.println("\n*** Expense Database ***");
            System.out.println("1. Create Notification");
            System.out.println("2. Create Budget");
            System.out.println("3. Input Expenses");
            System.out.println("4. Generate Report");
            System.out.println("5. Back to Main Menu");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createNotification();
                    break;
                case 2:
                    createBudget();
                    break;
                case 3:
                    inputExpenses();
                    break;
                case 4:
                    generateReport();
                    break;
                case 5:
                    System.out.println("Returning to the main menu...");
                    return; // Return to the main menu without exiting the program
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void createNotification() {
        System.out.println("Enter your notification message:");
        String message = scanner.next();
        System.out.println("Select a category for notification:");
        String category = scanner.next();
        // Store the notification for later use
        currentUser.notifications.add("Category: " + category + ", Message: " + message);
        System.out.println("Notification created!");
    }

    static void createBudget() {
        System.out.println("Enter the category for the budget:");
        String category = scanner.nextLine();
        System.out.println("Enter the budget amount:");
        double amount = scanner.nextDouble();
        // Store the budget for later use
        currentUser.budgets.add(new Budget(category, amount));
        System.out.println("Budget created!");
    }

    static void inputExpenses() {
        System.out.println("Enter the category for the expense:");
        String category = scanner.nextLine();
        System.out.println("Enter the expense amount:");
        double amount = scanner.nextDouble();
        // Store the expense in the user's expense list
        currentUser.expenses.add(new Expense(category, amount));
        System.out.println("Expense added!");
    }

    static void generateReport() {
        System.out.println("\n*** Financial Report ***");
        System.out.println("Username: " + currentUser.username);
        // Display user's expenses
        System.out.println("\nExpenses:");
        for (Expense expense : currentUser.expenses) {
            System.out.println("Category: " + expense.category + ", Amount: $" + expense.amount);
        }
        // Display user's budget
        System.out.println("\nBudgets:");
        for (Budget budget : currentUser.budgets) {
            System.out.println("Category: " + budget.category + ", Amount: $" + budget.amount);
        }
        // Display user notification
        System.out.println("\nNotifications:");
        for (String notification : currentUser.notifications) {
            System.out.println(notification);
        }
    }
}
