import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.text.DecimalFormat;

interface Observer<T> {
	public void update(T t);
}

interface Subject<T> {
	public void register(Observer<T> o, T t);
	public void unregister(Observer<T> o, T t);
	public void notify(T t);
}

class Stock {
	private final String ticker;
	private List<Double> prices = new ArrayList<Double>();
	
	public Stock(String stockTicker, Double initialPrice) {
		ticker = stockTicker;
		prices.add(initialPrice);
	}

	public String getTicker() {
		return ticker;
	}
	
	public Double getPrice() {
		return prices.get(prices.size() - 1);
	}
	
	public void updatePrice(Double delta) {
		Double currentPrice = getPrice();
		DecimalFormat df = new DecimalFormat("#.##");
		Double newPrice = Double.valueOf(df.format(currentPrice + delta));
		
		prices.add(newPrice);
		//TODO: limit price history to a certain number of inputs
		//TODO: have different price frames to maintain a larger history view (way overthinking)
	}
}

class Trader implements Observer<Stock> {
	private static int traderIDTracker = 0;
	private int traderID = ++traderIDTracker;
	
	public void update(Stock updatedStock) {
		System.out.println("Trader " + traderID + ":\n" + updatedStock.getTicker() + ": " + updatedStock.getPrice());
	}
}

class StockExchange implements Subject<Stock>, Runnable {
	private Map<String, List<Observer<Stock>>> traders = new HashMap<>();
	private List<Stock> stocks = new ArrayList<Stock>();
	private Random randomNumberGenerator = new Random();

	public void register(Observer<Stock> o, Stock stock) {
		String ticker = stock.getTicker();
		List<Observer<Stock>> stockObservers = traders.get(ticker);
		if (stockObservers == null) {
			stockObservers = new ArrayList<>();
			traders.put(ticker, stockObservers);
		}
		//TODO: should make sure that the observer is not already in the list
		stockObservers.add(o);
	}
	
	public void unregister(Observer<Stock> o, Stock stock) {
		String ticker = stock.getTicker();
		List<Observer<Stock>> stockObservers = traders.get(ticker);
		if (stockObservers == null) {
			stockObservers = new ArrayList<>();
			traders.put(ticker, stockObservers);
		}

		int observerIndex = stockObservers.indexOf(o);
		if (observerIndex > -1) {
			traders.remove(observerIndex);
		}
	}

	public void notify(Stock stock) {
		String ticker = stock.getTicker();
		List<Observer<Stock>> stockObservers = traders.get(ticker);
		if (stockObservers == null) {
			stockObservers = new ArrayList<>();
			traders.put(ticker, stockObservers);
		}

		for (Observer<Stock> trader: stockObservers) {
			trader.update(stock);
		}
	}
	
	public void initialPublicOffering(Stock stock) {
		stocks.add(stock);
	}
	
	@Override
	public void run() {
		for(int i = 0; i < 20; ++i) {
			try {
				Thread.sleep(2000);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			int stockIndex = randomNumberGenerator.nextInt(stocks.size());
			Stock stock = stocks.get(stockIndex);
			double delta = (Math.random() * 0.06) - 0.03;
			stock.updatePrice(delta);
			notify(stock);
		}
	}
}

public class StockMarket {
	public static void main(String[] args) {
		Stock pinterest = new Stock("PINS", 74.14);
		Stock etsy = new Stock("ETSY", 169.82);
		Stock pfizer = new Stock("PFE", 38.81);

		Stock astraZeneca = new Stock("AZN", 8382.38);
		Stock barclays = new Stock("BARC", 176.38);
		Stock glencore = new Stock("GLEN", 297.55);
		
		StockExchange nasdaq = new StockExchange();
		StockExchange lse =  new StockExchange(); // stands for London Stock Exhange not Large Scale Event (LoL)
		
		nasdaq.initialPublicOffering(pinterest);
		nasdaq.initialPublicOffering(etsy);
		nasdaq.initialPublicOffering(pfizer);

		lse.initialPublicOffering(astraZeneca);
		lse.initialPublicOffering(barclays);
		lse.initialPublicOffering(glencore);
		
		/* For experimentation purposes, I will dual list pfizer and astraZeneca
		 * We can imagine them being added across markets after Corona pandemic
		 */
		
		nasdaq.initialPublicOffering(pfizer);
		lse.initialPublicOffering(astraZeneca);

		Trader alice = new Trader();
		Trader bob = new Trader();
		Trader charlie = new Trader();
		
		
		nasdaq.register(alice, pinterest);
		lse.register(alice, glencore);
		
		nasdaq.register(bob, pfizer);
		lse.register(bob, pfizer);
		
		nasdaq.register(charlie, astraZeneca);
		nasdaq.register(charlie, etsy);
		lse.register(charlie, barclays);
		
		new Thread(nasdaq).start();
		new Thread(lse).start();
	}
}
