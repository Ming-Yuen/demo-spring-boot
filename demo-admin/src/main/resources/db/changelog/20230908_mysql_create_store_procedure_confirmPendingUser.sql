CREATE OR REPLACE PROCEDURE confirmPendingUser()
BEGIN

    WHILE EXISTS (select 1 from user_info_pending where status = 0 limit 1) DO

    INSERT INTO user_info (user_name, first_name, last_name, pwd, gender, email, phone, role_level, tx_version, creator, creation_time, modifier, modification_time)

    SELECT latestPending.user_name, latestPending.first_name, latestPending.last_name, latestPending.pwd, latestPending.gender, latestPending.email, latestPending.phone, latestPending.role_level,
    latestPending.tx_version, latestPending.creator, latestPending.creation_time, latestPending.modifier, latestPending.modification_time
    FROM user_info_pending latestPending
    inner join (select user_name, max(modification_time) as modification_time, max(id) as id
    from user_info_pending where status = 0
    group by user_name LIMIT 1000
    )
    pending on pending.user_name = latestPending.user_name and pending.modification_time = latestPending.modification_time
    left join user_info info on latestPending.user_name = info.user_name
    WHERE latestPending.status = 0 and info.user_name is null and latestPending.id >= pending.id;

    UPDATE user_info
    JOIN user_info_pending ON user_info_pending.user_name = user_info.user_name
    SET user_info.first_name = user_info_pending.first_name, user_info.last_name = user_info_pending.last_name, user_info.pwd = user_info_pending.pwd, user_info.gender = user_info_pending.gender,
    user_info.email = user_info_pending.email, user_info.phone = user_info_pending.phone, user_info.role_level = user_info_pending.role_level,
    user_info.tx_version = user_info.tx_version + 1, user_info.modifier = user_info_pending.modifier, user_info.modification_time = user_info_pending.modification_time
    WHERE user_info_pending.status = 0 and user_info_pending.modification_time > user_info.modification_time
    LIMIT 1000;

    update user_info_pending
    join user_info ON user_info_pending.user_name = user_info.user_name and user_info_pending.modification_time <= user_info.modification_time
    set user_info_pending.status = 1;
    END WHILE;
end/