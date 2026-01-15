package com.nhom91.drugstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddressDTO {
    private Long id;
    private String address;
    private String city;
    private String postalCode;
    private String country;
}