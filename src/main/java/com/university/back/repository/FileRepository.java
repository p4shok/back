package com.university.back.repository;

import com.university.back.model.MemberFile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<MemberFile, String> {
    void deleteAllByLogin(String login);
    List<MemberFile> findAllByLogin(String login);
    void deleteByTitle(String title);
}
