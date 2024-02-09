package com.university.back.repository;

import com.university.back.model.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {
    void deleteMemberByLogin(String login);
    Member findMemberByLogin(String login);
    @Query("select m.name, m.surname from Member m where m.group = :group")
    List<Object[]> findAllByGroup(String group);
    @Query("select m from Member m")
    List<Member> findAllMembers();
    Member findMemberBySessionId(String sessionId);

    @Modifying
    @Transactional
    @Query("update Member m set m.login = :#{#member.login}," +
            "m.password = :#{#member.password}," +
            "m.group = :#{#member.group}," +
            "m.name = :#{#member.name}," +
            "m.surname = :#{#member.surname} where m.login = :login")
    void updateMemberByLogin(@Param("login") String login, @Param("member") Member member);
}
