package org.jarvis.kk.domain;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;
import org.jarvis.kk.dto.BaseTimeEntity;
import org.jarvis.kk.dto.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Pick
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_pick")
public class Pick extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pno;

    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "currentPrice"))
    private Product product;

    private Integer wantedPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(columnDefinition = "bit default 0")
    private Boolean state;

    @Column(insertable = false, columnDefinition = "bit default 1")
    private Boolean receipt;

    // @OrderBy(clause = "regdate desc")
    // @ElementCollection(fetch = FetchType.LAZY)
    // @CollectionTable(name = "tbl_lowprice", joinColumns = @JoinColumn(name = "pno"))
    // private List<LowPrice> lowPrices;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private List<LowPrice> lowPrices;

    @Builder
    public Pick(Integer pno, Member member, Product product, Integer wantedPrice) {
        this.pno = pno;
        this.member = member;
        this.product = product;
        this.wantedPrice = wantedPrice;
    }

    public Pick updateReceipt(Boolean receipt) {
        this.receipt = receipt;
        return this;
    }
}