package com.university.back.service;

import com.university.back.config.MemberDetails;
import com.university.back.model.Member;
import com.university.back.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository repository;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Member member = repository.findMemberByLogin(login);
        if (member == null)
            return null;
        return new MemberDetails(member);
    }
}
