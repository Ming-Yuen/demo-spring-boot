CREATE OR ALTER PROCEDURE confirmPendingUser
AS
BEGIN
    DECLARE @insert_count INT = 1;
    DECLARE @update_count INT = 1;

    WHILE @insert_count > 0 OR @update_count > 0
    BEGIN
        IF @insert_count > 0
        BEGIN
            INSERT INTO user_info (user_name, first_name, last_name, pwd, gender, email, phone, role_level, tx_version, creator, creation_time, modifier, modification_time)
            SELECT TOP 1000 latestPending.user_name, latestPending.first_name, latestPending.last_name, latestPending.pwd, latestPending.gender, latestPending.email, latestPending.phone, latestPending.role_level,
            latestPending.tx_version, latestPending.creator, latestPending.creation_time, latestPending.modifier, latestPending.modification_time
            FROM user_info_pending latestPending
            LEFT JOIN user_info info ON latestPending.user_name = info.user_name
            WHERE info.user_name IS NULL
            AND latestPending.status = 0 ;

            SET @insert_count = @@ROWCOUNT;
        END

        IF @update_count > 0
        BEGIN
            UPDATE user_info
            SET user_info.first_name = user_info_pending.first_name, user_info.last_name = user_info_pending.last_name, user_info.pwd = user_info_pending.pwd, user_info.gender = user_info_pending.gender,
            user_info.email = user_info_pending.email, user_info.phone = user_info_pending.phone, user_info.role_level = user_info_pending.role_level,
            user_info.tx_version = user_info.tx_version + 1, user_info.modifier = user_info_pending.modifier, user_info.modification_time = user_info_pending.modification_time
            FROM user_info
            INNER JOIN user_info_pending ON user_info_pending.user_name = user_info.user_name
            WHERE user_info_pending.status = 0 AND user_info_pending.modification_time > user_info.modification_time
            AND user_info.user_name IN (SELECT TOP 1000 user_name FROM user_info_pending WHERE status = 0);

            SET @update_count = @@ROWCOUNT;
        END

        UPDATE user_info_pending
        SET status = 1
        FROM user_info_pending
        INNER JOIN user_info ON user_info_pending.user_name = user_info.user_name
        WHERE user_info_pending.status = 0 AND user_info.modification_time >= user_info_pending.modification_time;
    END
END