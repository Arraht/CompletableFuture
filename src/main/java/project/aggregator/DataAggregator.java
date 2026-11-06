package project.aggregator;

import project.dto.ProductInfoDto;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class DataAggregator {
    private final Random random = new Random();

    public ProductInfoDto aggregateProductInfo(String productName) {
        CompletableFuture<Double> priceFuture = CompletableFuture
                .supplyAsync(() -> fetchPrice(productName))
                .exceptionally(ex -> 0.0);

        CompletableFuture<String> descriptionFuture = CompletableFuture
                .supplyAsync(() -> fetchDescription(productName))
                .exceptionally(ex -> "Нет данных");

        CompletableFuture<Double> ratingFuture = CompletableFuture
                .supplyAsync(() -> fetchRating(productName))
                .exceptionally(ex -> 0.0);

        return priceFuture
                .thenCombine(descriptionFuture, (price, desc)
                        -> new Object[] { price, desc })
                .thenCombine(ratingFuture, (combined, rating) -> {
                    double price = (Double) combined[0];
                    String desc = (String) combined[1];
                    return new ProductInfoDto(productName, price, desc, rating);
                })
                .join();
    }

    private double fetchPrice(String name) {
        simulateDelayAndFailure();
        return 899.99;
    }

    private String fetchDescription(String name) {
        simulateDelayAndFailure();
        return "Мощный игровой ноутбук с отличным экраном";
    }

    private double fetchRating(String name) {
        simulateDelayAndFailure();
        return 4.7;
    }

    private void simulateDelayAndFailure() {
        try {
            Thread.sleep(1000 + random.nextInt(2000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (random.nextInt(100) < 20) {
            throw new RuntimeException("Ошибка сервиса");
        }
    }
}