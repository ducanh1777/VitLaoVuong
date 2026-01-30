-- File: update_schema.sql
-- Script nay dung de cap nhat CSDL ma khong lam mat du lieu cu.

USE vitlaovuong_db;

-- 1. Them cot 'quantity' (DA CO ROI THI BO QUA DONG NAY)
-- -- ALTER TABLE Products ADD COLUMN quantity INT DEFAULT 0;  <-- Dong nay gay loi vi da co cot nay roi.

-- 2. Cap nhat so luong mac dinh cho cac san pham hien co
-- Tat che do an toan (Safe Update Mode) de cho phep update nhieu dong
SET SQL_SAFE_UPDATES = 0;
UPDATE Products SET quantity = 100 WHERE quantity = 0 OR quantity IS NULL;
SET SQL_SAFE_UPDATES = 1;

-- 3. Them cot 'note' vao bang 'Orders' (QUAN TRONG CHO ADMIN)
-- Chay dong nay de sua loi huy don hang
ALTER TABLE Orders ADD COLUMN note TEXT;

-- 4. Them cot 'account_id' vao bang 'Orders' (QUAN TRONG CHO LICH SU DON HANG)
ALTER TABLE Orders ADD COLUMN account_id INT;

-- Kiem tra ket qua
SELECT 'Cap nhat thanh cong!' AS Status;
