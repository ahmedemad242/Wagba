package com.example.wagba.feeCalculator.tax;

public class FlatTaxCalculator implements TaxCalculator {
    private double rate;

    public FlatTaxCalculator(double rate) {
        this.rate = rate;
    }

    @Override
    public double calculateTax(double subtotal) {
        return subtotal * rate;
    }
}

