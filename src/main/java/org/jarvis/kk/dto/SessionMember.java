package org.jarvis.kk.dto;

import java.io.Serializable;
import java.util.List;

import org.jarvis.kk.domain.Interest;
import org.jarvis.kk.domain.Member;

import lombok.Getter;

/**
 * SessionMember
 */
@Getter
public class SessionMember implements Serializable{

	private static final long serialVersionUID = 1L;

    private final String mid;

    private final boolean existInterest;

    public SessionMember(Member member){
        this.mid = member.getMid();
        List<Interest> interests = member.getInterests();
        this.existInterest = interests == null ? false : (interests.size() == 0 ? false : true);
    }
}