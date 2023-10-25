package com.demo.admin.service.impl;

import com.demo.admin.dao.UsersPendingDao;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.*;
import com.demo.admin.entity.enums.RoleLevelEnum;
import com.demo.admin.mapper.UsersPendingMapper;
import com.demo.admin.service.UserService;
import com.demo.admin.dao.UserInfoDao;
import com.demo.admin.dao.UserRoleDao;
import com.demo.admin.util.JwtManager;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.demo.common.util.LambdaUtil.distinctByKey;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
//    @Autowired
//    private JPAQueryFactory queryFactory;
    @Autowired
    private UserInfoDao userDao;
    @Autowired
    private UsersPendingDao usersPendingDao;
    @Autowired
    private UsersPendingMapper userMapper;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private JwtManager jwt;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void register(List<UserRegisterRequest> request){
        List<UserPending> users = request.parallelStream().map(dto->{
            return userMapper.userRegisterRequestToUser(dto);
        }).collect(Collectors.toList());
        saveUserPending(users);
    }
    @Override
    public void saveUserPending(List<? extends UserPending> users){
        users = users.parallelStream()
                .filter(distinctByKey(UserPending::getUserName))
//                .map(user->{
//                        user.setPassword(passwordEncoder.encode(user.getPassword()));
//                        return user;
//                    })
                .collect(Collectors.toList());
        usersPendingDao.saveAll(users);
//        queryFactory.select(qUserInfo.userName).from(qUserInfo).where(qUserInfo.userName.in(x->UserPending::getUserName))
    }
    @Override
    public void confirmPendingUserInfo(String batchId) {
        usersPendingDao.confirmPendingUser(batchId);
//        QUserPending userInfoPending = QUserPending.userPending;
//        QUserInfo qUserInfo = QUserInfo.userInfo;
//
//        queryFactory.insert(qUserInfo)
//                    .columns( qUserInfo.userName, qUserInfo.firstName, qUserInfo.lastName, qUserInfo.pwd, qUserInfo.gender, qUserInfo.email, qUserInfo.phone, qUserInfo.roleLevel,
//                              qUserInfo.txVersion, qUserInfo.creator, qUserInfo.creationTime, qUserInfo.modifier, qUserInfo.modificationTime)
//                    .select(
//                            queryFactory.select(userInfoPending.userName, userInfoPending.firstName, userInfoPending.lastName,
//                             userInfoPending.pwd, userInfoPending.gender, userInfoPending.email, userInfoPending.phone,
//                             userInfoPending.roleLevel, userInfoPending.txVersion, userInfoPending.creator,
//                             userInfoPending.creationTime, userInfoPending.modifier, userInfoPending.modificationTime)
//                    .from(QUserPending.userPending)
//                    .leftJoin(QUserInfo.userInfo).on(userInfoPending.userName.eq(qUserInfo.userName))
//                    .where(qUserInfo.userName.isNull()
//                            .and(userInfoPending.status.eq(StatusEnum.PENDING))
//                            .and(userInfoPending.batchId.eq(batchId)))
//                            .limit(1000)
//                ).execute();
    }
    @Override
    public void confirmUserPending(List<? extends UserPending> users){

    }
    @Override
    public UserInfo findByUserName(String username){
        return userDao.findByUserName(username);
    }
    @Override
    @Cacheable()
    public Collection<Long> getManageRoles(RoleLevelEnum role_level){
        return userRoleDao.findByRoleLevelGreaterThanEqual(role_level).stream().map(BaseEntity::getId).collect(Collectors.toList());
    }
    @Override
    public String login(String username, String password) {
        UserInfo user = userDao.findByUserName(username);
        if(user == null || !passwordMatch(password, user)){
            throw new IllegalArgumentException("Incorrect password");
        }

        return jwt.generateToken(user.getUserName(), user.getPwd());
    }
    public boolean passwordMatch(String password, UserInfo admin){
        return passwordEncoder.matches(password, admin.getPwd());
    }
    @Override
    public String passwordEncode(String password){
        return passwordEncoder.encode(password);
    }
}