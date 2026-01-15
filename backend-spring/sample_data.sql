-- ============================================
-- DRUGSTORE DATABASE - SAMPLE DATA
-- ============================================
-- Dữ liệu mẫu cho hệ thống nhà thuốc
-- Sử dụng relative paths cho hình ảnh
-- ============================================

-- 1. USERS (Mật khẩu: "123456" đã được mã hóa bằng BCrypt)
INSERT INTO users (name, email, password, is_admin, created_at, updated_at) VALUES
('Admin User', 'admin@drugstore.com', '$2a$10$VUS8veWKAvr9Si80fJy3Ye3ocGdAWfdTWKIGzWiT2QEqAbrh3xt22', true, NOW(), NOW()),
('Nguyễn Văn A', 'nguyenvana@gmail.com', '$2a$10$VUS8veWKAvr9Si80fJy3Ye3ocGdAWfdTWKIGzWiT2QEqAbrh3xt22', false, NOW(), NOW()),
('Trần Thị B', 'tranthib@gmail.com', '$2a$10$VUS8veWKAvr9Si80fJy3Ye3ocGdAWfdTWKIGzWiT2QEqAbrh3xt22', false, NOW(), NOW()),
('Lê Văn C', 'levanc@gmail.com', '$2a$10$VUS8veWKAvr9Si80fJy3Ye3ocGdAWfdTWKIGzWiT2QEqAbrh3xt22', false, NOW(), NOW());

-- 2. CATEGORIES (Danh mục sản phẩm)
INSERT INTO category (name, description, is_show, is_parent, parent_category_id, created_at, updated_at) VALUES
-- Parent Categories
('Thuốc kê đơn', 'Các loại thuốc cần có đơn của bác sĩ', true, true, NULL, NOW(), NOW()),
('Thuốc không kê đơn', 'Thuốc OTC - Over The Counter', true, true, NULL, NOW(), NOW()),
('Thực phẩm chức năng', 'Vitamin, khoáng chất, thực phẩm bổ sung', true, true, NULL, NOW(), NOW()),
('Chăm sóc cá nhân', 'Sản phẩm vệ sinh, làm đẹp', true, true, NULL, NOW(), NOW()),
('Thiết bị y tế', 'Máy đo huyết áp, nhiệt kế, băng gạc', true, true, NULL, NOW(), NOW()),

-- Sub Categories
('Thuốc giảm đau', 'Thuốc giảm đau, hạ sốt', true, false, 2, NOW(), NOW()),
('Thuốc kháng sinh', 'Kháng sinh điều trị nhiễm khuẩn', true, false, 1, NOW(), NOW()),
('Vitamin & Khoáng chất', 'Bổ sung vitamin, khoáng chất', true, false, 3, NOW(), NOW()),
('Sản phẩm cho mẹ và bé', 'Sữa, tã, đồ dùng cho bé', true, false, 4, NOW(), NOW()),
('Thiết bị đo đường huyết', 'Máy đo đường huyết, que thử', true, false, 5, NOW(), NOW());

-- 3. PRODUCTS (Lưu relative path)
INSERT INTO product (ma, name, image, description, category_id, rating, num_reviews, price, count_in_stock, loan_price, is_bought, created_at, updated_at) VALUES
-- Thuốc giảm đau
('P001', 'Paracetamol 500mg', '/uploads/products/1.png', 'Thuốc giảm đau, hạ sốt hiệu quả. Hộp 100 viên nén.', 6, 4.5, 125, 25000, 500, 0, false, NOW(), NOW()),
('P002', 'Ibuprofen 400mg', '/uploads/products/2.png', 'Thuốc giảm đau, chống viêm. Hộp 60 viên.', 6, 4.3, 89, 45000, 300, 0, false, NOW(), NOW()),
('P003', 'Aspirin 100mg', '/uploads/products/3.png', 'Thuốc giảm đau, ngăn ngừa đông máu. Hộp 30 viên.', 6, 4.2, 67, 35000, 250, 0, false, NOW(), NOW()),

-- Thuốc kháng sinh
('P004', 'Amoxicillin 500mg', '/uploads/products/4.png', 'Kháng sinh điều trị nhiễm khuẩn đường hô hấp. Hộp 20 viên.', 7, 4.6, 156, 85000, 200, 0, false, NOW(), NOW()),
('P005', 'Cefixime 200mg', '/uploads/products/5.png', 'Kháng sinh thế hệ mới. Hộp 10 viên.', 7, 4.4, 92, 120000, 150, 0, false, NOW(), NOW()),
('P006', 'Azithromycin 250mg', '/uploads/products/6.png', 'Kháng sinh điều trị nhiễm khuẩn. Hộp 6 viên.', 7, 4.5, 78, 95000, 180, 0, false, NOW(), NOW()),

-- Vitamin & Khoáng chất
('P007', 'Vitamin C 1000mg', '/uploads/products/7.png', 'Tăng cường sức đề kháng. Hộp 30 viên sủi.', 8, 4.7, 234, 150000, 400, 0, false, NOW(), NOW()),
('P008', 'Vitamin D3 5000IU', '/uploads/products/8.png', 'Bổ sung vitamin D, tốt cho xương khớp. Lọ 60 viên.', 8, 4.6, 189, 280000, 300, 0, false, NOW(), NOW()),
('P009', 'Omega-3 Fish Oil', '/uploads/products/9.png', 'Dầu cá Omega-3, tốt cho tim mạch. Hộp 100 viên.', 8, 4.8, 312, 450000, 250, 0, false, NOW(), NOW()),
('P010', 'Multivitamin Daily', '/uploads/products/1.png', 'Vitamin tổng hợp hàng ngày. Hộp 60 viên.', 8, 4.5, 167, 320000, 350, 0, false, NOW(), NOW()),

-- Sản phẩm cho mẹ và bé
('P011', 'Sữa Enfamil A+ 900g', '/uploads/products/2.png', 'Sữa công thức cho trẻ 0-6 tháng tuổi.', 9, 4.9, 456, 520000, 100, 0, false, NOW(), NOW()),
('P012', 'Tã Pampers Newborn', '/uploads/products/3.png', 'Tã dán cho trẻ sơ sinh. Gói 84 miếng.', 9, 4.7, 289, 380000, 200, 0, false, NOW(), NOW()),
('P013', 'Nước muối sinh lý 0.9%', '/uploads/products/4.png', 'Rửa mũi cho bé. Hộp 30 ống x 5ml.', 9, 4.6, 178, 85000, 300, 0, false, NOW(), NOW()),

-- Thiết bị y tế
('P014', 'Máy đo huyết áp Omron', '/uploads/products/5.png', 'Máy đo huyết áp điện tử tự động.', 10, 4.8, 234, 1250000, 50, 0, false, NOW(), NOW()),
('P015', 'Nhiệt kế điện tử', '/uploads/products/6.png', 'Nhiệt kế đo nhiệt độ nhanh chóng.', 5, 4.5, 156, 180000, 150, 0, false, NOW(), NOW()),
('P016', 'Máy đo đường huyết Accu-Chek', '/uploads/products/7.png', 'Máy đo đường huyết chính xác. Kèm 25 que thử.', 10, 4.7, 198, 850000, 80, 0, false, NOW(), NOW()),

-- Chăm sóc cá nhân
('P017', 'Kem chống nắng SPF50+', '/uploads/products/8.png', 'Kem chống nắng phổ rộng. Tuýp 50ml.', 4, 4.6, 267, 280000, 200, 0, false, NOW(), NOW()),
('P018', 'Nước súc miệng Listerine', '/uploads/products/9.png', 'Nước súc miệng diệt khuẩn. Chai 500ml.', 4, 4.4, 189, 95000, 250, 0, false, NOW(), NOW()),
('P019', 'Dầu gội trị gàu Head & Shoulders', '/uploads/products/1.png', 'Dầu gội trị gàu hiệu quả. Chai 650ml.', 4, 4.5, 234, 145000, 300, 0, false, NOW(), NOW()),
('P020', 'Kem dưỡng da Cetaphil', '/uploads/products/2.png', 'Kem dưỡng ẩm cho da nhạy cảm. Tuýp 100g.', 4, 4.7, 312, 320000, 180, 0, false, NOW(), NOW());

-- 4. BANNERS (Cột: link_img, link_page, is_show)
INSERT INTO banner (link_img, link_page, is_show, created_at, updated_at) VALUES
('/uploads/banners/banner.jpg', '/products', true, NOW(), NOW()),
('/uploads/banners/banner-mb.jpg', '/promotions', true, NOW(), NOW()),
('/uploads/banners/banner.jpg', '/new-arrivals', true, NOW(), NOW());

-- ============================================
-- NOTES:
-- - Mật khẩu users: 123456
-- - Hình ảnh: relative paths (/uploads/...)
-- - Frontend sẽ build full URL khi hiển thị
-- ============================================
