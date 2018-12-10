import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

public class DailyTradeTest {

	static long investedFunds = 6000;
	static int tradeLimit = 7;
	static long targetProfit = 700000;

	static boolean wannaTrade = true;
	static int megaUnitCost = 5300;
	static int miniUnitCost = megaUnitCost / 10;
	static int SLPoints = 30;
	static int profitPoints = 10;
	static int lossLimitPercentage = 50;
	static int dayCount = 0;
	static long totalRealizedProfit = 0;
	static Suggestion suggestion = null;
	static List<Trade> tradeList = new ArrayList<>();
	static boolean targetMet = false;

	public static void main(String[] args) {


		Scanner scan = new Scanner(System.in);

		while (investedFunds >= 600 && !targetMet) {
			dayCount++;
			System.out.println("\n********** Welcome to Day " + dayCount + " trading *************");//+ getDateOfAchievement(dayCount-1));
			System.out.println("\n Realized profit till date :" + totalRealizedProfit);
			System.out.println("Your intial investment for today  " + investedFunds);

			if (totalRealizedProfit >= targetProfit) {
				targetMet = true;
				wannaTrade = false;
				System.out.println(" Target Achieved !!! ");
				return;
			}

			long totalFunds = investedFunds;
			int tradeCount = 0;
			long profitTillNow = 0;
			long moneyToWithDraw = 0;
			wannaTrade = true;

			while (wannaTrade) {
				if (tradeCount >= tradeLimit) {
					System.out.println("\nYou completed " + tradeCount + " trades today.Better take rest ");
					if (profitTillNow > 0) {

						moneyToWithDraw = (profitTillNow < investedFunds) ? profitTillNow : investedFunds;

						System.out.println("Your total funds " + totalFunds);
						System.out.println("Your total profit till now " + profitTillNow);
						System.out.println("********And you can withdraw " + moneyToWithDraw);
						wannaTrade = false;
						investedFunds = totalFunds - moneyToWithDraw;
						totalRealizedProfit = totalRealizedProfit + moneyToWithDraw;
					}
				} else {

					long safeFunds = (investedFunds * lossLimitPercentage) / 100;
					long tradeUnits = 0;
					long moneyToTrade = totalFunds - safeFunds;
					long estimatedProfit = 0;
					long sl = 0;
					if (moneyToTrade >= megaUnitCost) {
						tradeUnits = (totalFunds / megaUnitCost);
						sl = SLPoints * tradeUnits * 100;
						if (sl > (totalFunds - safeFunds)) {
							tradeUnits = tradeUnits - 1;
						}
						estimatedProfit = tradeUnits * 1000;
					} else {
						tradeUnits = (int) (moneyToTrade / miniUnitCost);
						sl = SLPoints * tradeUnits * 10;
						if (sl > (totalFunds - safeFunds)) {
							tradeUnits = tradeUnits - 1;
						}
						estimatedProfit = tradeUnits * 100;
					}
					if (tradeUnits > 0) {
						tradeCount++;
						System.out.println("\nYour trade number " + tradeCount);
						System.out.println("Your total funds " + totalFunds);
						System.out.println("Safe funds " + safeFunds);
						System.out.println("Your total profit till now " + profitTillNow);
						System.out.println(
								"You can safely trade for " + tradeUnits + " units for a profit of " + estimatedProfit);
						System.out.println("Please enter trade profit after trade execution...");
						// int tradeProfit = scan.nextInt();
						long tradeProfit = estimatedProfit;
						totalFunds = totalFunds + tradeProfit;
						profitTillNow = profitTillNow + tradeProfit;
					} else {
						System.out.println("\nIts not safe for you to trade anymore " + "today. Take rest.Thank you");
						System.out.println("Your total funds " + totalFunds);
						System.out.println("Your lost funds " + profitTillNow);
						wannaTrade = false;
						investedFunds = totalFunds;
					}
				}
			}
		}
		System.out.println("Not enough funds " + investedFunds);
	}

	private static String getDateOfAchievement(int dayCount) {
		
			
		Calendar calendar = Calendar.getInstance();

		Date today = new Date();
		calendar.setTime(today);
		calendar.add(Calendar.DATE, dayCount);
		
		
		if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			calendar.add(Calendar.DATE, dayCount+1);
		}else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			calendar.add(Calendar.DATE, dayCount+2);
		}else {
			calendar.add(Calendar.DATE, dayCount);
			
		}
		today = calendar.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate = format1.format(today);
		return formattedDate;
	}
}
