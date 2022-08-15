package org.bpp.integration;

import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.domain.Coins.MarketChart;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

//Service for communication with CoinGecko public API, cryptocurrency ranking website.
public class CoinGeckoCommunicationService {

    public CoinGeckoCommunicationService() {
    }

    //Returns prices for bitcoin for last seven days (including day of request), ordered by date ascending
    public List<Double> getBitcoinUsdPricesForLastWeek() {
        //excluding day 7 (today) - it is fetched by default
        int days = 6;
        MarketChart marketChart = executeSingleClientCall(client -> client.getCoinMarketChartById("bitcoin", "usd", days, "daily"));
        //prices are represented in a next way (timestamp and price): [[1659484800000, 23053.846847482677],[1659571200000, 22860.42098438317]]
        List<List<String>> timeAndPriceList = marketChart.getPrices();

        return timeAndPriceList.stream().map(timeAndPrice -> {
            String priceString = timeAndPrice.get(1);
            return Double.parseDouble(priceString);
        }).collect(Collectors.toList());

    }

    private <T> T executeSingleClientCall(Function<CoinGeckoApiClient, T> clientCallFunction) {
        CoinGeckoApiClient coinGeckoApiClient = new CoinGeckoApiClientImpl();
        T callResult = clientCallFunction.apply(coinGeckoApiClient);
        coinGeckoApiClient.shutdown();
        return callResult;
    }
}
