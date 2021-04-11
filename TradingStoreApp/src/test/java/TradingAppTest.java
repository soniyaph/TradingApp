import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import static org.junit.Assert.*;


public class TradingAppTest {

    TradingApp tradingApp = new TradingApp();
    private Date currentDate = new Date();
    private SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");


    @Test
    public void addTradeItemToStore() throws Exception
    {
        Date maturityDate=sd.parse("20/05/2021");
        Trade t1=new Trade("T1",1,"CP-1","B1",maturityDate, currentDate, 'N');
        tradingApp.addTradeItemToStore(t1);
        assertTrue(tradingApp.getTradeHashMap().containsKey("T1"));
    }

    @Test
    public void testDisplayTradeItemFromStore() throws Exception
    {
        Date maturityDate=sd.parse("20/05/2021");
        Trade t1=new Trade("T2",2,"CP-2","B2",maturityDate, currentDate, 'N');
        tradingApp.addTradeItemToStore(t1);
        tradingApp.displayTradeItemFromStore();
        assertTrue(tradingApp.getTradeHashMap().size() > 0);
    }

    @Test
    public void testCheckMaturityDate() throws Exception
    {
        Date maturityDate=sd.parse("20/05/2014");
        Trade t2=new Trade("T2",2,"CP-2","B2",maturityDate, currentDate, 'N');
        tradingApp.addTradeItemToStore(t2);
        assertFalse(tradingApp.getTradeHashMap().containsKey(t2.getTradeId()));
    }

    @Test(expected = InvalidTradeVersionException.class)
    public void testcheckLowerTradeVersion() throws Exception
    {
            Date maturityDate = sd.parse("20/05/2025");
            Trade t2Old = new Trade("T2", 2, "CP-2", "B2", maturityDate, currentDate, 'N');
            tradingApp.addTradeItemToStore(t2Old);
            Trade t2New = new Trade("T2", 1, "CP-2", "B2", maturityDate, currentDate, 'N');
            tradingApp.addTradeItemToStore(t2New);
    }

    @Test
    public void testSameTradeVersion() throws Exception
    {
            Date maturityDate1 = sd.parse("20/05/2025");
            Trade t2Old = new Trade("T2", 2, "CP-2", "B2", maturityDate1, currentDate, 'N');
            tradingApp.addTradeItemToStore(t2Old);
            Date maturityDate2 = sd.parse("20/05/2021");
            Trade t2New = new Trade("T2", 2, "CP-2", "B2", maturityDate2, currentDate, 'N');
            tradingApp.addTradeItemToStore(t2New);
            assertTrue(2 == tradingApp.getTradeHashMap().get("T2").getVersion());
    }

    @Test
    public void testCheckExpiredFlag() throws Exception
    {
        Date maturityDate = sd.parse("20/05/2021");
        Trade t2 = new Trade("T2", 2, "CP-2", "B2", maturityDate, currentDate, 'N');
        tradingApp.addTradeItemToStore(t2);
        HashMap<String, Trade> tradeHashMap = tradingApp.getTradeHashMap();
        Trade trade = tradeHashMap.get("T2");
        trade.setMaturityDate(sd.parse("20/05/2014"));
        tradeHashMap.replace(trade.getTradeId(), trade);
        tradingApp.checkExpiredFlag();
        assertEquals( t2.getExpired(),'Y');
    }

    @Test
    public void testRetrieveTradeItemFromStore() throws Exception
    {
        Date maturityDate = sd.parse("20/05/2021");
        Trade t2 = new Trade("T2", 2, "CP-2", "B2", maturityDate, currentDate, 'N');
        tradingApp.addTradeItemToStore(t2);
        Trade trade = tradingApp.retrieveTradeItemFromStore("T2");
        assertNotNull(trade);
    }

}
