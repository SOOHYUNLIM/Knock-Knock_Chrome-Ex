package org.jarvis.kk.dto;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Category
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "tbl_category")
public class Category {

    @Id
    private String code;

    private String keyword;

    // @ElementCollection(fetch = FetchType.LAZY)
    // @CollectionTable(name = "tbl_subCategory", joinColumns = @JoinColumn(name="code"))
    // private List<SubCategory> subCategorys;
}