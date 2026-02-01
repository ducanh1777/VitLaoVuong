package com.vitlaovuong.controller;

import com.vitlaovuong.dal.CategoryDAO;
import com.vitlaovuong.dal.ProductDAO;
import com.vitlaovuong.model.Category;
import com.vitlaovuong.model.Product;
import com.vitlaovuong.model.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AdminProductServlet", urlPatterns = { "/admin/products" })
public class AdminProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            response.sendRedirect("../login");
            return;
        }

        ProductDAO pDao = new ProductDAO();
        CategoryDAO cDao = new CategoryDAO();

        String action = request.getParameter("action");
        String message = request.getParameter("message");
        if (message != null) {
            try {
                message = java.net.URLDecoder.decode(message, "UTF-8");
            } catch (Exception e) {
                message = "";
            }
        } else {
            message = "";
        }

        try {
            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                pDao.deleteProduct(id);

                // Lay trang hien tai tu tham so
                int pageToDelete = 1;
                try {
                    pageToDelete = Integer.parseInt(request.getParameter("page"));
                } catch (Exception e) {
                }

                // Tinh toan lai so trang sau khi xoa
                int count = pDao.count();
                int pageSize = 5;
                int endPage = count / pageSize;
                if (count % pageSize != 0) {
                    endPage++;
                }

                // Neu trang hien tai lon hon tong so trang (vi du xoa mon cuoi cung cua trang),
                // lui ve trang truoc
                if (pageToDelete > endPage && endPage > 0) {
                    pageToDelete = endPage;
                }

                String msg = java.net.URLEncoder.encode("Xóa sản phẩm thành công!", "UTF-8");
                response.sendRedirect("products?page=" + pageToDelete + "&message=" + msg);
                return;
            } else if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Product p = pDao.getProductById(id);
                request.setAttribute("product", p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "Co loi xay ra: " + e.getMessage();
        }

        // Phan trang
        int index = 1;
        String indexPage = request.getParameter("page");
        if (indexPage != null) {
            try {
                index = Integer.parseInt(indexPage);
            } catch (NumberFormatException e) {
                index = 1;
            }
        }

        int count = pDao.count();
        int pageSize = 5; // 5 san pham moi trang
        int endPage = count / pageSize;
        if (count % pageSize != 0) {
            endPage++;
        }

        List<Product> list = pDao.getPaging(index, pageSize);
        List<Category> cats = cDao.getAllCategories();

        request.setAttribute("products", list);
        request.setAttribute("categories", cats);
        request.setAttribute("endPage", endPage);
        request.setAttribute("index", index);
        if (!message.isEmpty()) {
            request.setAttribute("message", message);
        }

        request.getRequestDispatcher("/admin_products.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ProductDAO pDao = new ProductDAO();
        CategoryDAO cDao = new CategoryDAO();

        String idStr = null;
        String name = null;
        String desc = null;
        String priceStr = null;
        String image = null;
        String catIdStr = null;
        String newCategoryName = null;

        try {
            idStr = request.getParameter("id");
            name = request.getParameter("name");
            desc = request.getParameter("description");
            priceStr = request.getParameter("price");
            image = request.getParameter("image");
            catIdStr = request.getParameter("categoryId");
            newCategoryName = request.getParameter("newCategoryName");

            // Validate basic
            if (name == null || priceStr == null || catIdStr == null) {
                throw new Exception("Thieu thong tin bat buoc");
            }

            double price = Double.parseDouble(priceStr);
            int catId = Integer.parseInt(catIdStr);

            // Handle new category
            if (catId == -1) {
                if (newCategoryName == null || newCategoryName.trim().isEmpty()) {
                    throw new Exception("Vui long nhap ten danh muc moi");
                }
                Category existingCat = cDao.getCategoryByName(newCategoryName);
                if (existingCat != null) {
                    catId = existingCat.getId();
                } else {
                    catId = cDao.insertCategory(newCategoryName);
                    if (catId == -1) {
                        throw new Exception("Khong the tao danh muc moi");
                    }
                }
            }

            String qtyStr = request.getParameter("quantity");
            String salePriceStr = request.getParameter("salePrice"); // Keep for backward compat if any, but mainly use
                                                                     // discountPercent
            String discountPercentStr = request.getParameter("discountPercent");
            double quantity = 0;
            double salePrice = 0;
            try {
                if (qtyStr != null && !qtyStr.isEmpty()) {
                    quantity = Double.parseDouble(qtyStr);
                }
                if (discountPercentStr != null && !discountPercentStr.isEmpty()) {
                    int discountPercent = Integer.parseInt(discountPercentStr);
                    if (discountPercent > 0) {
                        salePrice = price * (100 - discountPercent) / 100.0;
                    }
                } else if (salePriceStr != null && !salePriceStr.isEmpty()) {
                    // Fallback just in case
                    salePrice = Double.parseDouble(salePriceStr);
                }
            } catch (Exception e) {
            }

            Product p = new Product();
            p.setName(name);
            p.setDescription(desc);
            p.setPrice(price);
            p.setImageUrl(image);
            p.setCategoryId(catId);
            p.setQuantity(quantity);
            p.setSalePrice(salePrice);

            if (idStr == null || idStr.isEmpty()) {
                pDao.insertProduct(p);

                // Tinh trang cuoi cung
                int count = pDao.count();
                int pageSize = 5;
                int endPage = count / pageSize;
                if (count % pageSize != 0) {
                    endPage++;
                }

                String msg = java.net.URLEncoder.encode("Thêm món thành công!", "UTF-8");
                response.sendRedirect("products?page=" + endPage + "&message=" + msg);
                return;
            } else {
                p.setId(Integer.parseInt(idStr));
                pDao.updateProduct(p);
                String msg = java.net.URLEncoder.encode("Cập nhật món thành công!", "UTF-8");
                response.sendRedirect("products?message=" + msg);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Truong hop loi thi forward lai trang kem thong bao va du lieu cu
            request.setAttribute("error", "Loi khi luu san pham: " + e.getMessage());

            // Re-construct product to keep form data
            Product p = new Product();
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    p.setId(Integer.parseInt(idStr));
                } catch (Exception ex) {
                }
            }
            p.setName(name);
            p.setDescription(desc);
            try {
                p.setPrice(Double.parseDouble(priceStr));
            } catch (Exception ex) {
            }
            p.setImageUrl(image);
            try {
                p.setCategoryId(Integer.parseInt(catIdStr));
            } catch (Exception ex) {
            }

            request.setAttribute("product", p);
            doGet(request, response);
        }
    }
}
