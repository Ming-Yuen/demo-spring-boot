CREATE or replace PROCEDURE confirmPendingUser()
begin
	DECLARE insert_count INT DEFAULT 1;
	DECLARE update_count INT DEFAULT 1;

    WHILE insert_count > 0 or insert_count > 0 DO
        if insert_count > 0 then
            INSERT INTO user_info (user_name, first_name, last_name, pwd, gender, email, phone, role_level, tx_version, creator, creation_time, modifier, modification_time)
            SELECT latestPending.user_name, latestPending.first_name, latestPending.last_name, latestPending.pwd, latestPending.gender, latestPending.email, latestPending.phone, latestPending.role_level,
            latestPending.tx_version, latestPending.creator, latestPending.creation_time, latestPending.modifier, latestPending.modification_time
            FROM user_info_pending latestPending
            left join user_info info on latestPending.user_name = info.user_name
            where info.user_name is null
            and  latestPending.status = 0 limit 1000 ;
            SET insert_count = ROW_COUNT();
        end if;

        if update_count > 0 then
            UPDATE user_info
            JOIN user_info_pending ON user_info_pending.user_name = user_info.user_name
            SET user_info.first_name = user_info_pending.first_name, user_info.last_name = user_info_pending.last_name, user_info.pwd = user_info_pending.pwd, user_info.gender = user_info_pending.gender,
            user_info.email = user_info_pending.email, user_info.phone = user_info_pending.phone, user_info.role_level = user_info_pending.role_level,
            user_info.tx_version = user_info.tx_version + 1, user_info.modifier = user_info_pending.modifier, user_info.modification_time = user_info_pending.modification_time
            WHERE user_info_pending.status = 0 and user_info_pending.modification_time > user_info.modification_time
            LIMIT 1000;

            SET update_count = ROW_COUNT();
        end if;

        update user_info_pending
            join user_info ON user_info_pending.user_name = user_info.user_name
            set user_info_pending.status = 1
            where user_info_pending.status = 0 and user_info.modification_time >= user_info_pending.modification_time;
    END WHILE;


end/