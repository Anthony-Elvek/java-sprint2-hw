import java.util.ArrayList;
import java.util.HashMap;

public class AccountingAutomation {

    HashMap<Integer, ArrayList<MonthlyReportRecord>> monthlyReportRecords;
    ArrayList<YearlyReportRecord> yearlyReportRecords;
    HashMap<Integer, Integer> incomeRecord;
    HashMap<Integer, Integer> expenseRecord;
    MonthlyReport monthlyReport = new MonthlyReport();
    YearlyReport yearlyReport = new YearlyReport();

    AccountingAutomation() {
        monthlyReportRecords = new HashMap<>();
        yearlyReportRecords = new ArrayList<>();
        incomeRecord = new HashMap<>();
        expenseRecord = new HashMap<>();
    }

    void checkAmountOfReports() {
        int sum = 0;
        for (YearlyReportRecord yearlyReportRecord : yearlyReportRecords) {
            if (yearlyReportRecord.isExpense) {
                if (monthlyReport.getSumIncomeOrExpenseOfTheMonth(monthlyReportRecords, yearlyReportRecord.month, true) == yearlyReportRecord.amount) {
                    sum++;
                } else {
                    System.out.println("Убыток за " + monthlyReport.monthName[yearlyReportRecord.month - 1] + " несоответствует годовому отчету");
                    sum--;
                }
            } else {
                if (monthlyReport.getSumIncomeOrExpenseOfTheMonth(monthlyReportRecords, yearlyReportRecord.month, false) == yearlyReportRecord.amount) {
                    sum++;
                } else {
                    System.out.println("Доход за " + monthlyReport.monthName[yearlyReportRecord.month - 1] + " несоответствует годовому отчету");
                    sum--;
                }
            }
        }
        if (sum == yearlyReportRecords.size()) {
            System.out.println("Сверка отчетов успешно завершена...");
        }
    }

    void printMonthReport() {
        if (!monthlyReportRecords.isEmpty()) {
            for (int month : monthlyReportRecords.keySet()) {
                System.out.println("Отчет за " + monthlyReport.monthName[month - 1] + ":");
                System.out.println(monthlyReport.getMaxIncomeItemName(monthlyReportRecords, month));
                System.out.println(monthlyReport.getMaxExpenseItemName(monthlyReportRecords, month));
            }
        } else {
            System.out.println("Отчет еще не загружен...");
        }
    }

    void printYearReport() {
        if (!yearlyReportRecords.isEmpty()) {
            System.out.println("Отчет за " + yearlyReport.yearName[0] + " год:");
            yearlyReport.readYearStatistic(yearlyReportRecords, incomeRecord, expenseRecord);
            yearlyReport.getAvgExpenseAndIncomeOfTheYear(incomeRecord, expenseRecord);
        } else {
            System.out.println("Отчет еще не загружен...");
        }
    }

    void getMonthlyRecords() {
        if (monthlyReportRecords.isEmpty()) {
            monthlyReport.readMonthlyRecords(monthlyReportRecords);
            System.out.println("Месячные отчеты загружены...");
        } else {
            System.out.println("Данные уже загружены...");
        }
    }

    void getYearlyRecords() {
        if (yearlyReportRecords.isEmpty()) {
            yearlyReport.readYearlyRecords(yearlyReportRecords);
            System.out.println("Годовой отчет загружен...");
        } else {
            System.out.println("Данные уже загружены...");
        }
    }

    void printMenu() {
        System.out.println("1 -- Считать все месячные отчёты");
        System.out.println("2 -- Считать годовой отчёт");
        System.out.println("3 -- Сверить отчёты");
        System.out.println("4 -- Вывести информацию о всех месячных отчётах");
        System.out.println("5 -- Вывести информацию о годовом отчёте");
        System.out.println("0 -- Выход");
    }
}