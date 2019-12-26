package org.jarvis.kk.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.jarvis.kk.dto.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Wanted
 */
@Getter
@NoArgsConstructor
@Embeddable
public class Wanted {

    @Embedded
    private Product product;

    private Boolean state;

    @Builder
    public Wanted(Product product, Boolean state) {
        this.product = product;
        this.state = state;
    }

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private LocalDateTime regdate;
}