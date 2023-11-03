package com.demo.admin.service.impl;

import com.demo.admin.dao.UsersPendingDao;
import com.demo.admin.dto.UserQueryRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.*;
import com.demo.admin.entity.enums.StatusEnum;
import com.demo.common.entity.QUserInfo;
import com.demo.common.entity.UserInfo;
import com.demo.common.entity.enums.RoleLevelEnum;
import com.demo.admin.mapper.UsersPendingMapper;
import com.demo.admin.service.UserService;
import com.demo.admin.dao.UserInfoDao;
import com.demo.admin.dao.UserRoleDao;
import com.demo.admin.util.JwtManager;
import com.demo.common.entity.BaseEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    @Autowired
    private JPAQueryFactory queryFactory;
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
        List<UserInfoPending> users = request.parallelStream().map(dto->{
            return userMapper.userRegisterRequestToUser(dto);
        }).collect(Collectors.toList());
        saveUserPending(users);
    }
    @Override
    public void saveUserPending(List<? extends UserInfoPending> users){
        users = users.parallelStream()
                .filter(distinctByKey(UserInfoPending::getUserName))
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
//        QUserInfoPending userInfoPending = QUserInfoPending.userInfoPending;
//        QUserInfo qUserInfo = QUserInfo.userInfo;
//
//        queryFactory.insert(qUserInfo)
//                    .columns( qUserInfo.userName, qUserInfo.firstName, qUserInfo.lastName, qUserInfo.pwd, qUserInfo.gender, qUserInfo.email, qUserInfo.phone, qUserInfo.roleLevel,
//                              qUserInfo.version, qUserInfo.createdBy, qUserInfo.createdAt, qUserInfo.updatedBy, qUserInfo.updatedAt)
//                    .select(
//                            queryFactory.select(userInfoPending.userName, userInfoPending.firstName, userInfoPending.lastName,
//                             userInfoPending.pwd, userInfoPending.gender, userInfoPending.email, userInfoPending.phone,
//                             userInfoPending.roleLevel, userInfoPending.version, userInfoPending.createdBy,
//                             userInfoPending.createdAt, userInfoPending.updatedBy, userInfoPending.updatedAt)
//                    .from(userInfoPending)
//                    .leftJoin(QUserInfo.userInfo).on(userInfoPending.userName.eq(qUserInfo.userName))
//                    .where(qUserInfo.userName.isNull()
//                            .and(userInfoPending.status.eq(StatusEnum.PENDING))
//                            .and(userInfoPending.batchId.eq(batchId)))
//                            .limit(1000)
//                ).execute();
//        queryFactory.update(userInfoPending)
//                .join(qUserInfo).on(userInfoPending.userName.eq(qUserInfo.userName))
//                .set(userInfoPending.status, 1)
//                .set(userInfoPending.version, userInfoPending.version.add(1))
//                .where(userInfoPending.status.eq(StatusEnum.PENDING)
//                        .and(qUserInfo.updatedAt.goe(userInfoPending.updatedAt))
//                        .and(userInfoPending.batchId.eq(batchId)))
//                .execute();
//
//        queryFactory.update(qUserInfoPending)
//                .set(qUserInfoPending.status, 1)
//                .set(qUserInfoPending.version, qUserInfoPending.version.add(1))
//                .where(qUserInfoPending.status.eq(0)
//                        .and(qUserInfo.modificationTime.goe(qUserInfoPending.modificationTime))
//                        .and(qUserInfoPending.batchId.eq(batchId)))
//                .execute();
    }
    @Override
    public void confirmUserPending(List<? extends UserInfoPending> users){

    }

//    @Cacheable(value = "userInfoCache", key = "#userName", condition="#userName!=null")
    @Cacheable("userInfoCache.userName")
    @Override
    public UserInfo findByUserName(String username){
        log.info("goto findByUserName");
        return userDao.findByUserName(username);
    }
    @Override
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
    @Override
    public List<UserInfo> query(UserQueryRequest request) {
        return userDao.findByUserNameIn(request.getUserNameList());
    }
}