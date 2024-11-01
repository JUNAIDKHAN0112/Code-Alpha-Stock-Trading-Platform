import java.util.*;

class Stock {
    String symbol;
    double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    @Override
    public String toString() {
        return symbol + " - $" + price;
    }
}

class Portfolio {
    private Map<String, Integer> stocksOwned;
    private double totalInvested;

    public Portfolio() {
        this.stocksOwned = new HashMap<>();
        this.totalInvested = 0.0;
    }

    public void buyStock(Stock stock, int quantity) {
        stocksOwned.put(stock.symbol, stocksOwned.getOrDefault(stock.symbol, 0) + quantity);
        totalInvested += stock.price * quantity;
    }

    public void sellStock(Stock stock, int quantity) {
        if (stocksOwned.getOrDefault(stock.symbol, 0) < quantity) {
            System.out.println("Not enough shares to sell.");
            return;
        }
        stocksOwned.put(stock.symbol, stocksOwned.get(stock.symbol) - quantity);
        totalInvested -= stock.price * quantity;
    }

    public void viewPortfolio(List<Stock> market) {
        System.out.println("\nPortfolio:");
        double currentValue = 0.0;

        for (Stock stock : market) {
            if (stocksOwned.containsKey(stock.symbol) && stocksOwned.get(stock.symbol) > 0) {
                int quantity = stocksOwned.get(stock.symbol);
                double value = quantity * stock.price;
                System.out.printf("%s: %d shares, Current Value: $%.2f\n", stock.symbol, quantity, value);
                currentValue += value;
            }
        }

        System.out.printf("\nTotal Invested: $%.2f\n", totalInvested);
        System.out.printf("Current Portfolio Value: $%.2f\n", currentValue);
    }
}

class StockMarket {
    private List<Stock> stocks;

    public StockMarket() {
        this.stocks = new ArrayList<>();
        stocks.add(new Stock("AAPL", 150.0));
        stocks.add(new Stock("GOOGL", 2800.0));
        stocks.add(new Stock("AMZN", 3300.0));
        stocks.add(new Stock("TSLA", 700.0));
    }

    public List<Stock> getMarketData() {
        return stocks;
    }

    public Stock getStock(String symbol) {
        for (Stock stock : stocks) {
            if (stock.symbol.equalsIgnoreCase(symbol)) {
                return stock;
            }
        }
        return null;
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StockMarket market = new StockMarket();
        Portfolio portfolio = new Portfolio();

        while (true) {
            System.out.println("\n--- Stock Trading Platform ---");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nMarket Data:");
                    for (Stock stock : market.getMarketData()) {
                        System.out.println(stock);
                    }
                    break;

                case 2:
                    System.out.print("Enter Stock Symbol to Buy: ");
                    String buySymbol = scanner.next();
                    System.out.print("Enter Quantity: ");
                    int buyQuantity = scanner.nextInt();
                    Stock buyStock = market.getStock(buySymbol);

                    if (buyStock != null) {
                        portfolio.buyStock(buyStock, buyQuantity);
                        System.out.println("Bought " + buyQuantity + " shares of " + buySymbol);
                    } else {
                        System.out.println("Invalid stock symbol.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Stock Symbol to Sell: ");
                    String sellSymbol = scanner.next();
                    System.out.print("Enter Quantity: ");
                    int sellQuantity = scanner.nextInt();
                    Stock sellStock = market.getStock(sellSymbol);

                    if (sellStock != null) {
                        portfolio.sellStock(sellStock, sellQuantity);
                        System.out.println("Sold " + sellQuantity + " shares of " + sellSymbol);
                    } else {
                        System.out.println("Invalid stock symbol.");
                    }
                    break;

                case 4:
                    portfolio.viewPortfolio(market.getMarketData());
                    break;

                case 5:
                    System.out.println("Exiting the platform.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
