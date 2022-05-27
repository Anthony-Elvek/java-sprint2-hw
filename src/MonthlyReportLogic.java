import java.util.ArrayList;

public class MonthlyReportLogic {
    ArrayList<MonthlyReportRecord> monthlyReportRecords;
    FileReader fileReader = new FileReader();

    MonthlyReportLogic(){
        monthlyReportRecords = new ArrayList<>();
    }

    void getMonthlyRecords() {
        for (int month = 1; month <= 3; month++) {
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
                monthlyReportRecords.add(monthlyRecord);
            }
        }
    }

    int monthlyReportIncome(){
        int sum = 0;
        for (MonthlyReportRecord month : monthlyReportRecords){
            if (!month.isExpense){
                sum += month.quantity * month.sumOfOne;
            }
        }
        return sum;
    }

    int monthlyReportExpense(){
        int sum = 0;
        for (MonthlyReportRecord month : monthlyReportRecords){
            if (month.isExpense){
                sum += month.quantity * month.sumOfOne;
            }
        }
        return sum;
    }
}