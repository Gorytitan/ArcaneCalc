package org.tama.marketskimmer;

import java.io.IOException;

public class Arcane {
    final protected String urlName;
    protected double averagePriceMaxed;
    protected int volume;


    public Arcane(String urlName) throws IOException {
        this.urlName = urlName;
        updatePrices();
    }
    public void updatePrices() throws IOException {
        double[] arcaneStats = Market.getArcaneStats(urlName);
        averagePriceMaxed = arcaneStats[0];
        volume = (int) arcaneStats[1];
    }
}

