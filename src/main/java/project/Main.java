package project;

import project.aggregator.DataAggregator;
import project.dto.ProductInfoDto;

public class Main {
    public static void main(String[] args) {
        DataAggregator aggregator = new DataAggregator();

        System.out.println("Начинаем сбор данных по товару...");
        long start = System.currentTimeMillis();

        ProductInfoDto result = aggregator.aggregateProductInfo("Ноутбук");

        long duration = System.currentTimeMillis() - start;
        System.out.println(result);
        System.out.println("Время выполнения: " + (duration / 1000.0) + " сек.");
    }
}