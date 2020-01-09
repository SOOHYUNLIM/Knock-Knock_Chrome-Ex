package org.jarvis.kk.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jarvis.kk.dto.BaseTimeEntity;
import org.jarvis.kk.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * LowPrice
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
// @Embeddable
@Entity
@Table(name = "tbl_lowprice")
public class LowPrice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lno;

    @Embedded
    private Product product;

}