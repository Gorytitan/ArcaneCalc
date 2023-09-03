package org.tama.marketskimmer;

import org.joda.time.DateTime;

public class Order implements Comparable<Order> {
    protected int platinum;
    protected int modrank;
    protected boolean wtb;
    protected boolean online;
    protected DateTime lastUpdated;

    public Order(int p, int mr, boolean wtb, boolean online, DateTime lastUpdated) {
        platinum = p;
        modrank = mr;
        this.wtb = wtb;
        this.online = online;
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "\nplatinum: " + platinum
                + "\nrank: " + modrank
                + "\nwtb: " + wtb
                + "\nonline: " + online
                + "\nlastUpdate: " + lastUpdated;
    }

    @Override
    public int compareTo(Order o) {
        return this.platinum - o.platinum;
    }
}

