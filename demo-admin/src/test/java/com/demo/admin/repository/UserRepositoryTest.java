//package com.demo.admin.repository;
//
//import com.demo.admin.entity.UserInfo;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.util.List;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class UserRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    void findByUserNameIn() {
//        List<UserInfo.SelectUserName> user = userRepository.findByUserNameIn(UserInfo.SelectUserName.class,"admin");
//        Assertions.assertNotNull(user);
//    }
//}