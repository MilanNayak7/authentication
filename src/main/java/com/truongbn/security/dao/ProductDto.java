package com.truongbn.security.dao;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto{
    Long id;
    String pName;
    String pImage;
    String pCategory;
    String pPrice;
}
