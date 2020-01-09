package org.jarvis.kk.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jarvis.kk.dto.BaseTimeEntity;
import org.jarvis.kk.dto.Product;

import lombok.Getter;

/**
 * CommunityCrawling
 */
@Getter
@Entity
@Table(name = "tbl_CommunityCrawling")
public class CommunityCrawling extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @Embedded
    private Product product;

    private boolean lastCrawling;
}