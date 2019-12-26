package org.jarvis.kk.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.auto.value.AutoValue.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Category
 */
@Getter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_category")
public class Category {

    @Id
    private String code;

    private String keyword;
}