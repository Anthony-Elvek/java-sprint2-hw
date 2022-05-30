import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    FileReader fileReader = new FileReader();
    MonthlyReport monthlyReport = new MonthlyReport();
    String[] yearName = {"2021"};

    void readYearlyRecords(ArrayList<YearlyReportRecord> yearlyReportRecords) {
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
    }

    void readYearStatistic(ArrayList<YearlyReportRecord> yearlyReportRecords, HashMap<Integer, Integer> incomeRecord, HashMap<Integer, Integer> expenseRecord) {
        for (YearlyReportRecord record : yearlyReportRecords) {
            if (record.isExpense) {
                expenseRecord.put(record.month, record.amount);
            } else {
                incomeRecord.put(record.month, record.amount);
            }
            if (!(expenseRecord.get(record.month) == null) && !(incomeRecord.get(record.month) == null)) {
                int profitOfTheMonth;
                profitOfTheMonth = incomeRecord.get(record.month) - expenseRecord.get(record.month);
                if (profitOfTheMonth < 0) {
                    System.out.println("Убыток в " + monthlyReport.monthName[record.month - 1] + " месяце составил: " + profitOfTheMonth + " руб.");
                } else {
                    System.out.println("Прибыль в " + monthlyReport.monthName[record.month - 1] + " месяце составила: " + profitOfTheMonth + " руб.");
                }
            }
        }
    }


    void getAvgExpenseAndIncomeOfTheYear(HashMap<Integer, Integer> incomeRecord, HashMap<Integer, Integer> expenseRecord) {
        int sumIncome = 0;
        for (Integer record : incomeRecord.values()) {
            sumIncome += record;
        }
        int avgIncome = sumIncome / incomeRecord.size();
        System.out.println("Средняя прибыль за год составила: " + avgIncome + " руб.");

        int sumExpense = 0;
        for (Integer record : expenseRecord.values()) {
            sumExpense += record;
        }
        int avgExpense = sumExpense / expenseRecord.size();
        System.out.println("Средняя прибыль за год составила: " + avgExpense + " руб.");
    }
}