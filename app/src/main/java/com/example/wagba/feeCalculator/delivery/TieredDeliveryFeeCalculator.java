package com.example.wagba.feeCalculator.delivery;

import java.util.Map;

public class TieredDeliveryFeeCalculator implements DeliveryFeeCalculator {
    private Map<Double, Double> tiers;

    public TieredDeliveryFeeCalculator(Map<Double, Double> tiers) {
        this.tiers = tiers;
    }

    @Override
    public double calculateDeliveryFee(double subtotal) {
        double fee = 0;
        for (Map.Entry<Double, Double> entry : tiers.entrySet()) {
            double threshold = entry.getKey();
            double rate = entry.getValue();
            if (subtotal > threshold) {
                fee += rate;
            }
        }
        return fee;
    }
}

