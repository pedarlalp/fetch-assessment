package com.example.receipt_processor.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Getter
@Setter
public class Receipt {
    @NotNull
    @Pattern(regexp = "^[\\w\\s\\-&]+$")
    private String retailer;

    @NotNull
    @Pattern(regexp = "^(?:19|20)\\d{2}-(?:0[1-9]|1[0-2])-(?:0[1-9]|[12][0-9]|3[01])$", message = "Invalid date format. Expected format: yyyy-MM-dd")
    private String purchaseDate;

    @NotNull
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):([0-5][0-9])$", message = "Invalid time format. Expected format: HH:mm")
    private String purchaseTime;

    @NotNull
    @Size(min = 1, message = "At least one item is required")
    @Valid
    private List<Item> items;

    @NotNull
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String total;
}
