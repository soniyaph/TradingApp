import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

public class TradingApp {

    HashMap<String, Trade> tradeHashMap = new HashMap<>();

    public void addTradeItemToStore(Trade trade){

        if(tradeHashMap.containsKey(trade.getTradeId())){
            checkLowerTradeVersion(trade);
        }else {
            checkMaturityDate(trade);
        }

    }

    private void checkLowerTradeVersion(Trade trade) {
        Optional<Trade> t = Optional.ofNullable(tradeHashMap.get(trade.getTradeId()));
        if(t.isPresent() && trade.getVersion() < t.get().getVersion()){
            throw new InvalidTradeVersionException(trade.getTradeId());
        } else {
            tradeHashMap.replace(trade.getTradeId(),trade);
        }
    }

    private void checkMaturityDate(Trade trade) {
        Date currentDate = new Date();
        if (currentDate.compareTo(trade.getMaturityDate()) < 0) {
            trade.setExpired('N');
            tradeHashMap.put(trade.getTradeId(),trade);
        } else {
            System.out.println("Maturity date less then today's date");
        }
    }

    public HashMap<String, Trade> getTradeHashMap() {
        return tradeHashMap;
    }

    public Trade retrieveTradeItemFromStore(String tradeId){
       return tradeHashMap.get(tradeId);
    }

    public void displayTradeItemFromStore(){
        for(Map.Entry<String, Trade> tradeItem: tradeHashMap.entrySet()){
            String tradeId = tradeItem.getKey();
            Trade trade = tradeItem.getValue();
            Formatter f = new Formatter();
            f.format("%15s %15s %15s %15s %15s %15s %15s\n", "Trade Id", "Version", "Counter-Party Id",
                    "Book-Id", "Maturity Date", "Created Date", "Expired");
            f.format("%15s %15s %15s %15s %15s %15s %15s\n", tradeId, trade.getVersion(), trade.getCounterPartyId(),
                    trade.getBookId(), trade.getMaturityDate(), trade.getCreationDate(), trade.getExpired());
            System.out.println(f);

        }
    }

    public void checkExpiredFlag() {
        Date currentDate=new Date();
        for(String tradeId : tradeHashMap.keySet() ){
            if(currentDate.compareTo(tradeHashMap.get(tradeId).getMaturityDate())> 0)
            {
                Trade t = tradeHashMap.get(tradeId);
                t.setExpired('Y');
                tradeHashMap.replace(tradeId, t);
            }
        }
    }
}
