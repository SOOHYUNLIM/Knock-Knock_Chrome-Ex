package org.jarvis.kk.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jarvis.kk.dto.BaseTimeEntity;

import lombok.Getter;

/**
 * PickHistory
 */
@Getter
@Entity
@Table(name = "tbl_PickHistory")
public class PickHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pick pick;
}