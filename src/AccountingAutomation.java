public class AccountingAutomation {
    MonthlyReport monthlyReport;
    YearlyReport yearlyReport;

    AccountingAutomation() {
        monthlyReport = new MonthlyReport();
        yearlyReport = new YearlyReport();
    }

    void checkAmountOfReports() {
        if (yearlyReport.yearlyReportRecords.isEmpty() && monthlyReport.monthlyReportRecords.isEmpty()) {
            System.out.println("Годовой и месячный отчет еще не загружены...");
        } else if (yearlyReport.yearlyReportRecords.isEmpty()) {
            System.out.println("Годовой отчет еще не загружен...");
        } else if (monthlyReport.monthlyReportRecords.isEmpty()) {
            System.out.println("Месячный отчет еще не загружен...");
        } else {
            int sum = 0;
            for (YearlyReportRecord yearlyReportRecord : yearlyReport.yearlyReportRecords) {
                if (yearlyReportRecord.isExpense) {
                    if (monthlyReport.getSumIncomeOrExpenseOfTheMonth(yearlyReportRecord.month, true) == yearlyReportRecord.amount) {
                        sum++;
                    } else {
                        System.out.println("Убыток за " + monthlyReport.monthName[yearlyReportRecord.month - 1] + " несоответствует годовому отчету");
                        sum--;
                    }
                } else {
                    if (monthlyReport.getSumIncomeOrExpenseOfTheMonth(yearlyReportRecord.month, false) == yearlyReportRecord.amount) {
                        sum++;
                    } else {
                        System.out.println("Доход за " + monthlyReport.monthName[yearlyReportRecord.month - 1] + " несоответствует годовому отчету");
                        sum--;
                    }
                }
            }
            if (sum == yearlyReport.yearlyReportRecords.size()) {
                System.out.println("Сверка отчетов успешно завершена...");
            }
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