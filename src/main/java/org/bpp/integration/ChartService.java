package org.bpp.integration;

import io.quickchart.QuickChart;
import org.bpp.model.ChartConfig;
import org.bpp.model.ChartPoint;
import org.bpp.model.constant.ChartType;
import org.bpp.utils.JsonMapper;
import java.util.List;


import static org.bpp.model.ChartConfig.ChartConfigData;
import static org.bpp.model.ChartConfig.ChartConfigData.ChartConfigDataDataset;

public class ChartService {

    public ChartService() {
    }

    public void generatePredictedPriceChart(List<ChartPoint> historicalPrices,
                                            List<ChartPoint> regressionLinePoints,
                                            ChartPoint predictedPricePoint) {
        QuickChart quickChart = new QuickChart();
        quickChart.setHeight(500);
        quickChart.setWidth(1000);

        String configString = JsonMapper.mapObject(new ChartConfig(ChartType.scatter, new ChartConfigData(List.of(
                new ChartConfigDataDataset("Previous prices", false, 0, 0.1, "blue", historicalPrices, false),
                new ChartConfigDataDataset("Regression line", true, 0, 0.5, "red", regressionLinePoints, false),
                new ChartConfigDataDataset("Predicted price", false, 0, 1, "yellow", List.of(predictedPricePoint), false)))));
        quickChart.setConfig(configString);
        System.out.println("Chart link: " + quickChart.getUrl());
    }
}
