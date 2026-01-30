CREATE TABLE IF NOT EXISTS Notifications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT, -- NULL if for ALL or specific role like ADMIN
    role_target VARCHAR(20), -- 'ADMIN', 'USER', 'ALL'
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
