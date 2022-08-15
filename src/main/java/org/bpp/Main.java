package org.bpp;

import org.bpp.integration.ChartService;
import org.bpp.integration.CoinGeckoCommunicationService;
import org.bpp.model.ChartPoint;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        CoinGeckoCommunicationService coinGeckoCommunicationService = new CoinGeckoCommunicationService();
        ChartService chartService = new ChartService();

        List<Double> bitcoinPricesForLastWeek = coinGeckoCommunicationService.getBitcoinUsdPricesForLastWeek();
        //we'll use days (1,2,3..) for our regression for simplicity, can be replaced by dates, timestamp etc..
        List<Double> daysForRegressionPrediction = IntStream.range(1, bitcoinPricesForLastWeek.size() + 1)
                .mapToDouble(i -> (double) i).boxed()
                .collect(Collectors.toList());

        SimpleRegressionUa simpleRegression = new SimpleRegressionUa(daysForRegressionPrediction, bitcoinPricesForLastWeek);
        Double predictedBitcoinPrice = simpleRegression.predictForValue(8);
        System.out.println(predictedBitcoinPrice);

        List<ChartPoint> previousPrices = IntStream.range(0, bitcoinPricesForLastWeek.size()).boxed()
                .map(i -> ChartPoint.of(daysForRegressionPrediction.get(i), bitcoinPricesForLastWeek.get(i)))
                .collect(Collectors.toList());
        List<ChartPoint> linePoints = List.of(ChartPoint.of(1, simpleRegression.predictForValue(1)),
                ChartPoint.of(8, simpleRegression.predictForValue(8)));
        chartService.generatePredictedPriceChart(previousPrices, linePoints, ChartPoint.of(8, predictedBitcoinPrice));
    }
}
