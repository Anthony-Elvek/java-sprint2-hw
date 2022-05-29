import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccountingAutomation accountingAutomation = new AccountingAutomation();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            accountingAutomation.printMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                accountingAutomation.getMonthlyRecords();
            } else if (command == 2) {
                accountingAutomation.getYearlyRecords();
            } else if (command == 3) {
                accountingAutomation.checkAmountOfReports();
            } else if (command == 4) {
                accountingAutomation.printMonthReport();
            } else if (command == 5) {
                accountingAutomation.printYearReport();
            } else if (command == 0) {
                break;
            }
        }
    }
}
