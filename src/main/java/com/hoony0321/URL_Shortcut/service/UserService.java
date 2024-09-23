package com.hoony0321.URL_Shortcut.service;

import com.hoony0321.URL_Shortcut.domain.User;
import com.hoony0321.URL_Shortcut.form.CreateUserForm;
import com.hoony0321.URL_Shortcut.form.UpdateUserPasswordForm;
import com.hoony0321.URL_Shortcut.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;  // 비밀번호 암호화

    public List<User> findAllUser(){
        return userRepository.findAll();
    }

    public User findUser(Long id){
        User entity = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return entity;
    }

    public User findUserByEmail(String email){
        User entity = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        return entity;
    }

    @Transactional
    public void createUser(CreateUserForm form){
        userRepository.findByEmail(form.getEmail()).ifPresent(user -> {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        });

        form = CreateUserForm.builder()
                .name(form.getName())
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .build();

        User entity = User.createEntity(form);
        userRepository.save(entity);
    }

    @Transactional
    public void changeUserPassword(UpdateUserPasswordForm form){
        User user = userRepository.findById(form.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if(!user.getEmail().equals(form.getEmail())){
            throw new IllegalStateException("이메일이 일치하지 않습니다.");
        }

        user.changePassword(form.getNewPassword());
    }

    @Transactional
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public void loginUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if(!user.getPassword().equals(password)){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }
}
