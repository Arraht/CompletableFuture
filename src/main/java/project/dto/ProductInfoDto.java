package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductInfoDto {
    private final String name;
    private final double price;
    private final String description;
    private final double rating;
}