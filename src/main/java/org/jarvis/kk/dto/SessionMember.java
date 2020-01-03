package org.jarvis.kk.dto;

import java.io.Serializable;

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
        this.existInterest = member.getInterests().size() == 0 ? false : true;
    }
}