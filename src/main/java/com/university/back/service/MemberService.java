package com.university.back.service;

import com.university.back.model.Member;
import com.university.back.model.MemberFile;
import com.university.back.repository.FileRepository;
import com.university.back.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class MemberService{
    private MemberRepository repository;
    private FileRepository files;

    public HashMap<String, String> saveMember(Member member) {
        HashMap<String, String> response = new HashMap<>();
        member.setDefaults();
        if (repository.findMemberByLogin(member.getLogin()) == null) {
            member.setSessionId(RequestContextHolder.currentRequestAttributes().getSessionId());
            response.put("sessionId", member.getSessionId());
            repository.save(member);
        }
        else
            response.put("error_login", "Данный логин уже занят");
        return response;
    }
    public HashMap<String, String> checkMember(String login, String password) {
        HashMap<String, String> response = new HashMap<>();
        var member = repository.findMemberByLogin(login);
        if (member == null) {
            response.put("error_login", "Неправильный логин");
            return response;
        }
        if (member.getPassword().equals(password)) {
            member.increaseVisitCounter();
            member.setSessionId(RequestContextHolder.currentRequestAttributes().getSessionId());
            repository.save(member);
            response.put("sessionId", member.getSessionId());
        }
        else
            response.put("error_password", "Неправильный пароль");
        return response;
    }
    public void deleteMember(String login) {
        repository.deleteMemberByLogin(login);
        files.deleteAllByLogin(login);
    }

    public HashMap<String, String> updateMember(String login, Member newMember, MultipartFile file) throws IOException {
        HashMap<String, String> response = new HashMap<>();
        var member = repository.findMemberByLogin(login);
        newMember.refactor();
        if (!newMember.getRole().equals("student"))
            newMember.setGroup("");
        newMember.setVisitCounter(member.getVisitCounter());
        newMember.setSessionId(member.getSessionId());
        if (file != null)
            newMember.setImg(file.getBytes());
        else
            newMember.setImg(member.getImg());
        if (login.equals(newMember.getLogin())) {
            repository.save(newMember);
            response.put("updated", "true");
        }
        else {
            if (repository.findMemberByLogin(newMember.getLogin()) != null)
                response.put("error_login", "Данный логин уже занят");
            else {
                repository.updateMemberByLogin(login, newMember);
                response.put("updated", "true");
            }
        }
        return response;
    }

    public Member getMemberBySessionId(String sessionId) {
        return repository.findMemberBySessionId(sessionId);
    }
    public List<Object[]> getStudentsByGroup(String group) {
        return repository.findAllByGroup(group);
    }
    public List<Member> getAllMembers(String sessionId) {
        var member = repository.findMemberBySessionId(sessionId);
        if (member == null)
            return null;
        if (!member.getRole().equals("admin"))
            return null;
        return repository.findAllMembers();
    }

    public HashMap<String, String> getMemberData(Member member) throws IOException {
        HashMap<String, String> response = new HashMap<>();
        response.put("name", member.getName());
        response.put("surname", member.getSurname());
        response.put("login", member.getLogin());
        response.put("group", member.getGroup());
        response.put("role", member.getRole());
        response.put("password", member.getPassword());
        response.put("counter", String.valueOf(member.getVisitCounter()));
        if (member.getImg() != null)
            response.put("img", Base64.getEncoder().encodeToString(member.getImg()));
        else
            response.put("img", null);
        return response;
    }

    public void saveFile(MultipartFile fileData, String login) throws IOException {
        MemberFile file = new MemberFile();
        file.setLogin(login);
        file.setTitle(fileData.getOriginalFilename());
        file.setType(fileData.getContentType());
        file.setData(Base64.getEncoder().encodeToString(fileData.getBytes()));
        files.save(file);
    }

    public void deleteFile(String title) {
        files.deleteByTitle(title);
    }

    public List<MemberFile> getAllFiles(String login) {
        return files.findAllByLogin(login);
    }
}