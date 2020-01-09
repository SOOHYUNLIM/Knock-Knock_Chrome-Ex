package org.jarvis.kk.repositories;

import java.util.List;

import org.jarvis.kk.domain.ClickHistory;
import org.jarvis.kk.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * ClickHistoryRepository
 */
public interface ClickHistoryRepository extends JpaRepository<ClickHistory, Integer> {

    @Query("select c.product.category.code from ClickHistory h left join h.communityCrawling c where h.member = :member group by c.product.category order by count(h) desc")
    public List<String> groupByCategoryCount(Member member);

}