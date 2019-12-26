package org.jarvis.kk.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jarvis.kk.dto.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ClickHistory
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "tbl_ClickHistory")
public class ClickHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private CommunityCrawling communityCrawling;

    @Builder
    public ClickHistory(Member member, CommunityCrawling communityCrawling) {
        this.member = member;
        this.communityCrawling = communityCrawling;
    }
}