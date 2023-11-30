package com.demo.admin.service.impl;

import com.demo.admin.UserMapper;
import com.demo.admin.dto.UserQueryRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.common.entity.UserInfo;
import com.demo.common.entity.enums.RoleLevelEnum;
import com.demo.admin.service.UserService;
import com.demo.admin.dao.UserInfoDao;
import com.demo.admin.dao.UserRoleDao;
import com.demo.admin.util.JwtManager;
import com.demo.common.entity.BaseEntity;
import com.demo.common.util.LambdaUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ArrayUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private JPAQueryFactory queryFactory;
    @Autowired
    private UserInfoDao userDao;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private JwtManager jwt;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedissonClient redissonClient;
    @Override
    public void register(List<UserRegisterRequest> request){
        UserInfo[] users = request.parallelStream().map(dto->{
            return userMapper.userRegisterRequestToUser(dto);
        }).toArray(UserInfo[] :: new);
        saveUser(users);
    }
//    public static ConcurrentHashMap<String, Long> map = new ConcurrentHashMap();
    @Override
    public void saveUser(UserInfo... userInfoRecords) {
        userInfoRecords = Arrays.stream(userInfoRecords).parallel().filter(LambdaUtil.distinctByKey(UserInfo::getUserName)).toArray(UserInfo[]::new);
        List<UserInfo> usersToInsert = new ArrayList<>();
        List<UserInfo> usersToUpdate = new ArrayList<>();
        for(UserInfo userInfo : userInfoRecords){
            if(findByUserName(userInfo.getUserName()) == null){
                usersToInsert.add(userInfo);
            }else{
                usersToUpdate.add(userInfo);
            }
        }
        userDao.persistAll(usersToInsert);
        userDao.mergeAll(usersToUpdate);
//        int startIndexInclusive = 0;
//        try{
//            for(int index = 0; index < userInfoRecords.length; index++) {
//                if(!map.containsKey(userInfoRecords[index].getUserName())){
//                    map.put(userInfoRecords[index].getUserName(), Thread.currentThread().getId());
//                }else{
//                    int endIndexExclusive = Math.max(startIndexInclusive, --index);
//                    if(endIndexExclusive > startIndexInclusive){
//                        userDao.saveAll(Arrays.asList(ArrayUtils.subarray(userInfoRecords, startIndexInclusive, endIndexExclusive)));
//                        Iterator<Map.Entry<String, Long>> iterator = map.entrySet().iterator();
//                        while (iterator.hasNext()) {
//                            Map.Entry<String, Long> entry = iterator.next();
//                            if (entry.getValue().equals(Thread.currentThread().getId())) {
//                                iterator.remove();
//                            }
//                        }
//                    }
//                }
//            }
//            if(startIndexInclusive < userInfoRecords.length){
//                userDao.saveAll(Arrays.asList(ArrayUtils.subarray(userInfoRecords, startIndexInclusive, userInfoRecords.length)));
//            }
//        } finally{
//            Iterator<Map.Entry<String, Long>> iterator = map.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, Long> entry = iterator.next();
//                if (entry.getValue().equals(Thread.currentThread().getId())) {
//                    iterator.remove();
//                }
//            }
//        }


//        LinkedList<RLock> locks = new LinkedList<>();
//        userInfoRecords = Arrays.stream(userInfoRecords).parallel().filter(LambdaUtil.distinctByKey(UserInfo::getUserName)).toArray(UserInfo[]::new);
//        int startIndexInclusive = 0;
//        try{
//            for(int index = 0; index < userInfoRecords.length; index++) {
//                RLock lock = redissonClient.getLock("lock." + userInfoRecords[index].getUserName());
//                if(lock.tryLock(50, TimeUnit.MILLISECONDS)){
//                    locks.add(lock);
//                }else{
//                    int endIndexExclusive = Math.max(startIndexInclusive, --index);
//                    if(endIndexExclusive > startIndexInclusive){
//                        userDao.saveAll(Arrays.asList(ArrayUtils.subarray(userInfoRecords, startIndexInclusive, endIndexExclusive)));
//                        locks.forEach(rLock->{
//                            if(rLock.isLocked()){
//                                rLock.unlock();
//                            }
//                        });
//                        locks.clear();
//                    }
//                }
//            }
//            if(startIndexInclusive < userInfoRecords.length){
//                userDao.saveAll(Arrays.asList(ArrayUtils.subarray(userInfoRecords, startIndexInclusive, userInfoRecords.length)));
//            }
//        }catch(java.lang.InterruptedException e){
//            throw new RuntimeException(e);
//        }finally{
//            locks.forEach(rLock->{
//                if(rLock.isLocked()){
//                    rLock.unlock();
//                }
//            });
//        }
    }

//    @Cacheable(value = "userInfoCache", key = "#userName", condition="#userName!=null")
    @Cacheable("User_Info:userName")
    @Override
    public UserInfo findByUserName(String username){
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