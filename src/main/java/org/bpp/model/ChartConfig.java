package org.bpp.model;

import org.bpp.model.constant.ChartType;

import java.util.List;

public class ChartConfig {

    private final ChartType type;
    private final ChartConfigData data;

    public ChartConfig(ChartType type, ChartConfigData data) {
        this.type = type;
        this.data = data;
    }

    public ChartType getType() {
        return type;
    }

    public ChartConfigData getData() {
        return data;
    }

    public static class ChartConfigData {
        private final List<ChartConfigDataDataset> datasets;

        public ChartConfigData(List<ChartConfigDataDataset> datasets) {
            this.datasets = datasets;
        }

        public List<ChartConfigDataDataset> getDatasets() {
            return datasets;
        }

        public static class ChartConfigDataDataset {
            private final String label;
            private final boolean showLine;
            private final int lineTension;
            private final double borderWidth;
            private final String borderColor;
            private final List<ChartPoint> data;

            private final boolean fill;

            public ChartConfigDataDataset(String label, boolean showLine, int lineTension, double borderWidth, String borderColor, List<ChartPoint> data, boolean fill) {
                this.label = label;
                this.showLine = showLine;
                this.lineTension = lineTension;
                this.borderWidth = borderWidth;
                this.borderColor = borderColor;
                this.data = data;
                this.fill = fill;
            }

            public boolean isFill() {
                return fill;
            }

            public String getLabel() {
                return label;
            }

            public boolean isShowLine() {
                return showLine;
            }

            public int getLineTension() {
                return lineTension;
            }

            public double getBorderWidth() {
                return borderWidth;
            }

            public String getBorderColor() {
                return borderColor;
            }

            public List<ChartPoint> getData() {
                return data;
            }
        }

    }
}
