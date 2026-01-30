package com.vitlaovuong.controller;

import com.vitlaovuong.dal.CategoryDAO;
import com.vitlaovuong.dal.ProductDAO;
import com.vitlaovuong.model.Category;
import com.vitlaovuong.model.Product;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MenuServlet", urlPatterns = { "/menu" })
public class MenuServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO pDao = new ProductDAO();
        CategoryDAO cDao = new CategoryDAO();

        String cateIdRaw = request.getParameter("cid");
        List<Product> products;

        // Pagination setup
        int index = 1;
        String indexPage = request.getParameter("page");
        if (indexPage != null) {
            try {
                index = Integer.parseInt(indexPage);
            } catch (NumberFormatException e) {
                // Ignore invalid page param
            }
        }

        // Note: For now, pagination is applied to "All Products".
        // If sorting by category, we show all (or implement paginateByCategory later if
        // needed)

        if (cateIdRaw != null && !cateIdRaw.isEmpty()) {
            int cid = Integer.parseInt(cateIdRaw);
            int count = pDao.countByCategoryId(cid);
            int pageSize = 6;
            int endPage = count / pageSize;
            if (count % pageSize != 0) {
                endPage++;
            }
            products = pDao.getProductsByCategoryId(cid, index, pageSize);
            request.setAttribute("cid", cid);
            request.setAttribute("endPage", endPage);
            request.setAttribute("index", index);
        } else {
            // Paginate All Products
            int count = pDao.count();
            int pageSize = 6; // 6 products per page for Menu
            int endPage = count / pageSize;
            if (count % pageSize != 0) {
                endPage++;
            }

            products = pDao.getPaging(index, pageSize);
            request.setAttribute("endPage", endPage);
            request.setAttribute("index", index);
        }

        List<Category> categories = cDao.getAllCategories();

        request.setAttribute("products", products);
        request.setAttribute("categories", categories);

        request.getRequestDispatcher("menu.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
