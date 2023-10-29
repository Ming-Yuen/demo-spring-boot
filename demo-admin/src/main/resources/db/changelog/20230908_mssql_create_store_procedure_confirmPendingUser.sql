CREATE OR ALTER PROCEDURE confirmPendingUser
AS
BEGIN
    DECLARE @insert_count INT = 1;
    DECLARE @update_count INT = 1;

    WHILE @insert_count > 0 OR @update_count > 0
    BEGIN
        IF @insert_count > 0
        BEGIN
            INSERT INTO user_info (user_name, first_name, last_name, pwd, gender, email, phone, role_level, version, created_by, created_at, update_by, updated_at)
            SELECT TOP 1000 latestPending.user_name, latestPending.first_name, latestPending.last_name, latestPending.pwd, latestPending.gender, latestPending.email, latestPending.phone, latestPending.role_level,
            latestPending.version, latestPending.created_by, latestPending.created_at, latestPending.update_by, latestPending.updated_at
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
            user_info.version = user_info.version + 1, user_info.update_by = user_info_pending.update_by, user_info.updated_at = user_info_pending.updated_at
            FROM user_info
            INNER JOIN user_info_pending ON user_info_pending.user_name = user_info.user_name
            WHERE user_info_pending.status = 0 AND user_info_pending.updated_at > user_info.updated_at
            AND user_info.user_name IN (SELECT TOP 1000 user_name FROM user_info_pending WHERE status = 0);

            SET @update_count = @@ROWCOUNT;
        END

        UPDATE user_info_pending
        SET status = 1
        FROM user_info_pending
        INNER JOIN user_info ON user_info_pending.user_name = user_info.user_name
        WHERE user_info_pending.status = 0 AND user_info.updated_at >= user_info_pending.updated_at;
    END
END