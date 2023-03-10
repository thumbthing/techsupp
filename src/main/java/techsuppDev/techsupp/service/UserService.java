package techsuppDev.techsupp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techsuppDev.techsupp.domain.User;
import techsuppDev.techsupp.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(User user) {
//        String userName = user.getUserName();
//        String userEmail = user.getUserEmail();
        // 사용자 비밀번호 암호화.
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
//        String userPassword = user.getUserPassword();
//        String userPhone = user.getUserPhone();
//        user = user.createUser(userName, userEmail, userPassword, userPhone);
        validateDuplicateUser(user);        // 회원 중복 검증
        userRepository.save(user);
        return user.getUserId();
    }

    // 이메일 중복 검증
    private void validateDuplicateUser(User user) {
        Optional<User> findUserEmail = userRepository.findByUserEmail(user.getUserEmail());
        if (!findUserEmail.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    // 이메일 중복 검증
    public String checkId(String email, String type) {
        if (type.equals("email")) {
            Optional<User> users = userRepository.findByUserEmail(email);
            if(users.isEmpty()) {
                return "0";
            }
            return "1";
        }
        return "0";
    }


    // 회원 전체 조회
    public List<User> findUser() {
        return userRepository.findAll();
    }

    // 회원 한명 조회
    public User findOne(Long userId) {
        return userRepository.getOne(userId);
    }


    public User getUserByEmail(String userEmail) {
        Optional<User> users = userRepository.findByUserEmail(userEmail);
        if (users != null) {
            return users.get();
        }
        return null;
    }


    // 로그인
    public User login(String email, String password) {
        User user = getUserByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getUserPassword())) {
            return user;
        }
        return null;
    }

    // user 이메일 찾기
//    public String getFindId(String userName, String userPhone) {
//        List<User> AllUser = userRepository.findAll();
//
//    }

}


