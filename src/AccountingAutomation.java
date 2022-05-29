import java.util.ArrayList;
import java.util.HashMap;

public class AccountingAutomation {
    HashMap<Integer, ArrayList<MonthlyReportRecord>> monthlyReportRecords;
    ArrayList<YearlyReportRecord> yearlyReportRecords;
    FileReader fileReader = new FileReader();
    String[] monthName = {"январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};
    String[] yearName = {"2021"};

    AccountingAutomation() {
        monthlyReportRecords = new HashMap<>();
        yearlyReportRecords = new ArrayList<>();
    }

    void getMonthlyRecords() {
        if (monthlyReportRecords.isEmpty()) {
            for (int month = 1; month <= 3; month++) {
                ArrayList<MonthlyReportRecord> recordMonth = new ArrayList<>();
                String monthRecord = fileReader.readFileContentsOrNull("resources/m.20210" + month + ".csv");
                String[] lines = monthRecord.split("\n");
                for (int i = 1; i < lines.length; i++) {
                    String line = lines[i];
                    String[] parts = line.split(",");
                    String itemName = parts[0];
                    boolean isExpense = Boolean.parseBoolean(parts[1]);
                    int quantity = Integer.parseInt(parts[2]);
                    int sumOfOne = Integer.parseInt(parts[3]);
                    MonthlyReportRecord monthlyRecord = new MonthlyReportRecord(itemName, isExpense, quantity, sumOfOne);
                    recordMonth.add(monthlyRecord);
                    monthlyReportRecords.put(month, recordMonth);
                }
            }
            System.out.println("Месячные отчеты загружены...");
        } else {
            System.out.println("Данные уже загружены...");
        }
    }

    void getYearlyRecords() {
        if (yearlyReportRecords.isEmpty()) {
            String yearRecord = fileReader.readFileContentsOrNull("resources/y.2021.csv");
            String[] lines = yearRecord.split("\n");
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                String[] parts = line.split(",");
                int month = Integer.parseInt(parts[0]);
                int amount = Integer.parseInt(parts[1]);
                boolean isExpense = Boolean.parseBoolean(parts[2]);
                YearlyReportRecord yearlyReportRecord = new YearlyReportRecord(month, amount, isExpense);
                yearlyReportRecords.add(yearlyReportRecord);
            }
            System.out.println("Годовой отчет загружен...");
        } else {
            System.out.println("Данные уже загружены...");
        }
    }

    int getSumIncomeOfTheMonth(int monthKey) {
        int sumIncome = 0;
        for (MonthlyReportRecord month : monthlyReportRecords.get(monthKey)) {
            if (!month.isExpense) {
                sumIncome += month.quantity * month.sumOfOne;
            }
        }
        return sumIncome;
    }

    int getSumExpenseOfTheMonth(int monthKey) {
        int sumExpense = 0;
        for (MonthlyReportRecord month : monthlyReportRecords.get(monthKey)) {
            if (month.isExpense) {
                sumExpense += month.quantity * month.sumOfOne;
            }
        }
        return sumExpense;
    }

    void checkAmountOfReports() {
        int sum = 0;
        for (YearlyReportRecord yearlyReportRecord : yearlyReportRecords) {
            if (yearlyReportRecord.isExpense) {
                if (getSumExpenseOfTheMonth(yearlyReportRecord.month) == yearlyReportRecord.amount) {
                    sum++;
                } else {
                    System.out.println("Убыток за " + monthName[yearlyReportRecord.month - 1] + " несоответствует годовому отчету");
                    sum--;
                }
            } else {
                if (getSumIncomeOfTheMonth(yearlyReportRecord.month) == yearlyReportRecord.amount) {
                    sum++;
                } else {
                    System.out.println("Доход за " + monthName[yearlyReportRecord.month - 1] + " несоответствует годовому отчету");
                    sum--;
                }
            }
        }
        if (sum == yearlyReportRecords.size()) {
            System.out.println("Сверка отчетов успешно завершена...");
        }
    }

    String getMaxIncomeItemName(int monthKey) {
        int maxIncome = 0;
        String item;
        String maxItem = "";
        int sum;
        for (MonthlyReportRecord monthInfo : monthlyReportRecords.get(monthKey)) {
            if (!monthInfo.isExpense) {
                sum = monthInfo.quantity * monthInfo.sumOfOne;
                item = monthInfo.itemName;
                if (sum > maxIncome) {
                    maxIncome = sum;
                    maxItem = item;
                }
            }
        }
        return "Прибыльный товар: " + maxItem + "\nСумма: " + maxIncome;
    }


    String getMaxExpenseItemName(int monthKey) {
        int maxExpense = 0;
        String item;
        String maxExpenseItem = "";
        int sum;
        for (MonthlyReportRecord monthInfo : monthlyReportRecords.get(monthKey)) {
            if (monthInfo.isExpense) {
                sum = monthInfo.quantity * monthInfo.sumOfOne;
                item = monthInfo.itemName;
                if (sum > maxExpense) {
                    maxExpense = sum;
                    maxExpenseItem = item;
                }
            }
        }
        return "Нерентабельный товар: " + maxExpenseItem + "\nСумма: " + maxExpense;
    }

    void getProfitOfTheMonth() {
        for (int month : monthlyReportRecords.keySet()) {
            int profitOfTheMonth = getSumIncomeOfTheMonth(month) - getSumExpenseOfTheMonth(month);
            if (profitOfTheMonth < 0) {
                System.out.println("Убыток в " + month + " месяце составил: " + profitOfTheMonth + " руб.");
            } else {
                System.out.println("Прибыль в " + month + " месяце составила: " + profitOfTheMonth + " руб.");
            }
        }
    }

    int getAvgProfitOfTheYear() {
        int avgSum = 0;
        for (int month : monthlyReportRecords.keySet()) {
            avgSum += getSumIncomeOfTheMonth(month);
        }
        return avgSum / monthlyReportRecords.keySet().size();
    }

    int getAvgExpenseOfTheYear() {
        int avgSum = 0;
        for (int month : monthlyReportRecords.keySet()) {
            avgSum += getSumExpenseOfTheMonth(month);
        }
        return avgSum / monthlyReportRecords.keySet().size();
    }

    void printMonthReport() {
        for (int month : monthlyReportRecords.keySet()) {
            System.out.println("Отчет за " + monthName[month - 1] + ":");
            System.out.println(getMaxIncomeItemName(month));
            System.out.println(getMaxExpenseItemName(month));
        }
    }

    void printYearReport() {
        System.out.println("Отчет за " + yearName[0] + " год:");
        getProfitOfTheMonth();
        System.out.println("Средний расход за все месяцы в году: " + getAvgExpenseOfTheYear() + " руб.");
        System.out.println("Средний доход за все месяцы в году: " + getAvgProfitOfTheYear() + " руб.");
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