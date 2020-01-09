package org.jarvis.kk.repositories;

import java.util.List;
import java.util.Optional;

import org.jarvis.kk.domain.Member;
import org.jarvis.kk.domain.Pick;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * PickRepository
 */
public interface PickRepository extends JpaRepository<Pick, Integer>{

    @EntityGraph(attributePaths = "lowPrices")
    @Query("select p from Pick p where p.receipt = true and p.state = true")
    public List<Pick> getAllPickList();
    
    @EntityGraph(attributePaths = "lowPrices")
    @Query("select p from Pick p where p.member = :member and p.receipt = true")
    public List<Pick> getPickList(Member member);

    @Query("select p from Pick p where p.member = :#{#pick.member} and p.receipt = true and p.product.title = :#{#pick.product.title}")
    public Optional<Pick> duplicationCheck(@Param("pick") Pick pick);

}