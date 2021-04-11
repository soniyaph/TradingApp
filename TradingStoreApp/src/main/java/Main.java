import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();
        Date todaysDate = new Date();
        TradingApp tradingApp = new TradingApp();

        Date maturityDate=formatter.parse("20/05/2021");
        Trade t1=new Trade("T1",1,"CP-1","B1",maturityDate, todaysDate, 'N');
        tradingApp.addTradeItemToStore(t1);

        maturityDate=formatter.parse("20/05/2021");
        Trade t2=new Trade("T2",2,"CP-2","B1",maturityDate, todaysDate, 'N');
        tradingApp.addTradeItemToStore(t2);

        try {
            Trade t2new = new Trade("T2", 1, "CP-4", "B1", maturityDate, todaysDate, 'N');
            tradingApp.addTradeItemToStore(t2new);
        }catch(InvalidTradeVersionException ex){
            System.out.println("Trade version lower then incoming trade version");
        }

        maturityDate=formatter.parse("20/05/2014");
        Trade t3=new Trade("T3",3,"CP-3","B2",maturityDate, todaysDate, 'N');
        tradingApp.addTradeItemToStore(t3);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Checking for the expired flag for trading app");
                tradingApp.checkExpiredFlag();
            };
        };
        timer.schedule(timerTask, 1000, 10000);

        tradingApp.displayTradeItemFromStore();
    }

}
