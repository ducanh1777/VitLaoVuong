package com.vitlaovuong.controller;

import com.vitlaovuong.dal.ProductDAO;
import com.vitlaovuong.model.Cart;
import com.vitlaovuong.model.OrderDetail;
import com.vitlaovuong.model.Product;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CartServlet", urlPatterns = { "/cart" })
public class CartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        if (action == null)
            action = "view";

        ProductDAO pDao = new ProductDAO();

        if (action.equals("add")) {
            int pid = Integer.parseInt(request.getParameter("pid"));
            double quantity = 1.0;
            try {
                quantity = Double.parseDouble(request.getParameter("quantity"));
            } catch (NumberFormatException e) {
                quantity = 1.0;
            }

            Product product = pDao.getProductById(pid);
            if (product != null) {
                OrderDetail item = new OrderDetail();
                item.setProductId(pid);
                item.setProduct(product);
                item.setPrice(product.getPrice());
                item.setQuantity(quantity);
                cart.addItem(item);
            }

            String ajax = request.getParameter("ajax");
            if ("true".equals(ajax)) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter()
                        .write("{\"cartSize\": " + cart.getItems().size() + ", \"message\": \"Thêm thành công!\"}");
                return;
            }

            response.sendRedirect("menu"); // Or back to where they were
            return;
        } else if (action.equals("remove")) {
            int pid = Integer.parseInt(request.getParameter("pid"));
            cart.removeItem(pid);
            response.sendRedirect("cart");
            return;
        } else if (action.equals("update")) {
            int pid = Integer.parseInt(request.getParameter("pid"));
            double quantity = Double.parseDouble(request.getParameter("quantity"));
            cart.updateQuantity(pid, quantity);
            response.sendRedirect("cart");
            return;
        }

        session.setAttribute("cart", cart); // Push back just in case references changed inside (not needed for object
                                            // but good practice)
        request.setAttribute("totalMoney", cart.getTotalMoney());
        request.getRequestDispatcher("cart.jsp").forward(request, response);
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
