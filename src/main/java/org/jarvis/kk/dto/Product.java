package org.jarvis.kk.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Product
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Product {

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    private String title;
    
    @Column(length = 1000)
    private String image, link;

    private Integer price, fee;
}