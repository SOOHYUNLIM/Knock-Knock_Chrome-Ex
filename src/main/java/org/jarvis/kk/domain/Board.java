package org.jarvis.kk.domain;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jarvis.kk.dto.BaseTimeEntity;

import lombok.Getter;

/**
 * Q&A
 */
@Entity
@Getter
@Table(name = "tbl_board")
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bno;

    private String title;

    @Column(length = 1000)
    private String content;

    private Boolean status, notice;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno")
    private List<Reply> replies;
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "tbl_attach", joinColumns = @JoinColumn(name="bno"))
    private List<Attach> attachs;
}