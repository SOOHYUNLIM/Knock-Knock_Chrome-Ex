package org.jarvis.kk.repositories;

import java.util.Optional;

import org.jarvis.kk.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * MemberRepository
 */
public interface MemberRepository extends JpaRepository<Member, String> {


    @EntityGraph(attributePaths = "interests")
    @Query("select m from Member m where m.mid = :mid")
    public Optional<Member> findByMIdToInterest(String mid);
}