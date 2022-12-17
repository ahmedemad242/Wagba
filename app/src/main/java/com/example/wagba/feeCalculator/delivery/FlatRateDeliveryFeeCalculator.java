package com.example.wagba.feeCalculator.delivery;

public class FlatRateDeliveryFeeCalculator implements DeliveryFeeCalculator {
    private double fee;

    public FlatRateDeliveryFeeCalculator(double fee) {
        this.fee = fee;
    }

    @Override
    public double calculateDeliveryFee(double subtotal) {
        return fee;
    }
}
