package DataReports;

public class MonthlyReportRecord {
    String itemName;
    boolean isExpense;
    int quantity;
    int simOfOne;

    public MonthlyReportRecord(String itemName, boolean isExpense, int quantity, int simOfOne) {
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.simOfOne = simOfOne;
    }


}
