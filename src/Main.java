public class Main {
    public static void main(String[] args) {
        MonthlyReportLogic monthlyReportLogic = new MonthlyReportLogic();
        monthlyReportLogic.getMonthlyRecords();
        System.out.println(monthlyReportLogic.monthlyReportIncome());
        System.out.println(monthlyReportLogic.monthlyReportExpense());
    }
}
