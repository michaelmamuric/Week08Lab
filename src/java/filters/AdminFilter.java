/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.UserService;

/**
 *
 * @author 799470
 */
public class AdminFilter implements Filter {
    
    private static final int ROLE_ADMIN = 1;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        UserService service = new UserService();
        HttpServletRequest req = (HttpServletRequest)request;
        HttpSession session = req.getSession();
        String username = (String)session.getAttribute("username");
        
        try {
            User authenticatedUser = service.get(username);
            int roleID = authenticatedUser.getRole().getRoleid();
            
            // Admin User
            if (roleID == ROLE_ADMIN)
                chain.doFilter(request, response);
            else {
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect("login");
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void destroy() {

    }
    
}
