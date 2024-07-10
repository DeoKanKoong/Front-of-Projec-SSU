package com.example.softproject1.service;

import com.example.softproject1.dto.JoinDTO;
import com.example.softproject1.entity.UserEntity;
import com.example.softproject1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) {
        // db에 이미 동일한 username을 가진 회원이 존재하는지 확인
        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if (isUser) {
            return;  // 동일한 username이 이미 존재하면 가입 처리 중단
        }

        // UserEntity 객체 생성 및 필드 설정
        UserEntity data = new UserEntity();
        data.setUsername(joinDTO.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        data.setName(joinDTO.getName());
        data.setDepartment(joinDTO.getDepartment());
        data.setGrade(joinDTO.getGrade());
        data.setPhoneNumber(joinDTO.getPhoneNumber());
        data.setRole("ROLE_USER");

        // UserEntity 객체를 데이터베이스에 저장
        userRepository.save(data);
    }
}
