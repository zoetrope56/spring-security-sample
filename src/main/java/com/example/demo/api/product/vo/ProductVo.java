package com.example.demo.api.product.vo;

import com.example.demo.common.enumulation.ProductSize;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductVo {

    private Long productNo;

    private String category;

    private Long sellingPrice;

    private Long costPrice;

    private String name;

    private String description;

    private String barcode;

    private LocalDate expireDate;

    private ProductSize size;

}
