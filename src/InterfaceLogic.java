import java.util.ArrayList;
import java.util.HashMap;

public class InterfaceLogic {
    HashMap<Integer, ArrayList<MonthlyReportRecord>> monthlyReportRecords;
    ArrayList<YearlyReportRecord> yearlyReportRecords;
    FileReader fileReader = new FileReader();
    String[] monthName = {"январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};
    String[] yearName = {"2021"};

    InterfaceLogic() {
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
        if(yearlyReportRecords.isEmpty()) {
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

    int getSumIncomeOfTheMonth(int monthKey){
        int sumIncome = 0;
        for (MonthlyReportRecord month : monthlyReportRecords.get(monthKey)){
            if (month.isExpense){
                sumIncome += month.quantity * month.sumOfOne;
            }
        }
        return sumIncome;
    }

    int getSumExpenseOfTheMonth(int monthKey){
        int sumExpense = 0;
        for (MonthlyReportRecord month : monthlyReportRecords.get(monthKey)){
            if (!month.isExpense){
                sumExpense += month.quantity * month.sumOfOne;
            }
        }
        return sumExpense;
    }

    // to do: переделать вывод информации, чтобы после полной проверки, в конце печатал информацию о месяцах где есть несоответствие можно создать аррей лист
    boolean checkAmountOfReports() {
        boolean check = false;
        for (YearlyReportRecord yearlyReportRecord : yearlyReportRecords) {
            if (!yearlyReportRecord.isExpense) {
                if (getSumExpenseOfTheMonth(yearlyReportRecord.month) == yearlyReportRecord.amount) {
                    check = true;
                } else {
                    System.out.println("Сумма дохода за " + monthName[yearlyReportRecord.month - 1] + " не соотвутствуют годовому отчету");
                    check= false;
                }
            } else {
                if (getSumIncomeOfTheMonth(yearlyReportRecord.month) == yearlyReportRecord.amount) {
                    check = true;
                } else {
                    System.out.println("Сумма убытка за " + monthName[yearlyReportRecord.month - 1]  + " не соотвутствуют годовому отчету");
                    check= false;
                }
            }
        }
        return check;
    }

    void printCheckReport(){
        if (!checkAmountOfReports()){
            System.out.println("Считывание данных завершено...");
        }
    }

    String getMaxIncomeItemName(int monthKey){
        int maxIncome = 0;
        String item = "";
        String maxItem = "";
        int sum = 0;
        for (MonthlyReportRecord monthInfo : monthlyReportRecords.get(monthKey)) {
            if (monthInfo.isExpense) {
                sum += monthInfo.quantity + monthInfo.sumOfOne;
                item = monthInfo.itemName;
            }
        }
        if (sum > maxIncome) {
            maxIncome = sum;
            maxItem = item;
        }
        return "Прибыльный товар: " + maxItem + "\nСумма: " + maxIncome;
    }


    String getMaxExpenseItemName(int monthKey) {
        int maxExpense = 0;
        String item = "";
        String maxExpenseItem = "";
        int sum = 0;
        for (MonthlyReportRecord monthInfo : monthlyReportRecords.get(monthKey)) {
            if (!monthInfo.isExpense) {
                sum += monthInfo.quantity + monthInfo.sumOfOne;
                item = monthInfo.itemName;
            }
        }
        if (sum > maxExpense) {
            maxExpense = sum;
            maxExpenseItem = item;
        }
        return "Нерентабельный товар: " + maxExpenseItem + "\nСумма: " + maxExpense;
    }

    void printMonthReport(){
        for(int numberOfMonth : monthlyReportRecords.keySet()){
            System.out.println("Отчет за " + monthName[numberOfMonth - 1] + ":");
            System.out.println(getMaxIncomeItemName(numberOfMonth));
            System.out.println(getMaxExpenseItemName(numberOfMonth));
            System.out.println(getSumExpenseOfTheMonth(numberOfMonth));
            System.out.println(getSumIncomeOfTheMonth(numberOfMonth));

        }
    }
}