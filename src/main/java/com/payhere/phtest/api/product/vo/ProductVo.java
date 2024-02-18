package com.payhere.phtest.api.product.vo;

import com.payhere.phtest.common.enumulation.ProductSize;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductVo {

    private String category;

    private Long sellingPrice;

    private Long costPrice;

    private String name;

    private String description;

    private String barcode;

    private LocalDate expireDate;

    private ProductSize size;

}
