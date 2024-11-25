package com.example.receipt_processor.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class Item {
    @NotNull
    @Pattern(regexp = "^[\\w\\s\\-]+$")
    private String shortDescription;

    @NotNull
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String price;
}
