package org.bpp;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.bpp.integration.ChartService;
import org.bpp.integration.CoinGeckoCommunicationService;
import org.bpp.model.ChartPoint;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        //fetching btc prices from API
        CoinGeckoCommunicationService coinGeckoCommunicationService = new CoinGeckoCommunicationService();
        List<Double> bitcoinPricesForLastWeek = coinGeckoCommunicationService.getBitcoinUsdPricesForLastWeek();

        //we'll use days (1,2,3..) for our regression for simplicity - can be replaced by dates, timestamp etc..
        List<Double> daysForRegressionPrediction = IntStream.range(1, bitcoinPricesForLastWeek.size() + 1)
                .mapToDouble(i -> (double) i).boxed()
                .collect(Collectors.toList());

        //creating regression and loading data
        SimpleRegression simpleRegression1 = new SimpleRegression();
        IntStream.range(0, bitcoinPricesForLastWeek.size())
                .forEach(i -> simpleRegression1.addData(daysForRegressionPrediction.get(i), bitcoinPricesForLastWeek.get(i)));


        //predicting price
        double predictedBitcoinPrice = simpleRegression1.predict(8);
        System.out.println("Predicted price: " + predictedBitcoinPrice);

        //creating chart points and url for the chart
        List<ChartPoint> historicalPrices = IntStream.range(0, bitcoinPricesForLastWeek.size()).boxed()
                .map(i -> ChartPoint.of(daysForRegressionPrediction.get(i), bitcoinPricesForLastWeek.get(i)))
                .collect(Collectors.toList());
        List<ChartPoint> regressionLinePoints = List.of(ChartPoint.of(1, simpleRegression1.predict(1)),
                ChartPoint.of(8, simpleRegression1.predict(8)));
        ChartPoint predictedPricePoint = ChartPoint.of(8, predictedBitcoinPrice);

        ChartService chartService = new ChartService();
        chartService.generatePredictedPriceChart(historicalPrices, regressionLinePoints, predictedPricePoint);
    }
}
