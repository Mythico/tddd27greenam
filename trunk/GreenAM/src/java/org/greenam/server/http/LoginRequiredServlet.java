package org.greenam.server.http;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LoginRequieredServlet is a servlet that will handle login and logout
 * request to an OpenId provider and then redirect back to the requesting
 * page.
 * @author Emil
 * @author Michael
 */

@SuppressWarnings("serial")
public class LoginRequiredServlet extends HttpServlet {

    private static final Map<String, String> openIdProviders;
    static {
        openIdProviders = new HashMap<String, String>();
        openIdProviders.put("Google", "www.google.com/accounts/o8/id");
        openIdProviders.put("Yahoo", "yahoo.com");
        openIdProviders.put("MySpace", "myspace.com");
        openIdProviders.put("AOL", "aol.com");
        openIdProviders.put("MyOpenId.com", "myopenid.com");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser(); // or req.getUserPrincipal()

        resp.setContentType("text/html");
        
        String query = req.getQueryString();
        if(!query.isEmpty()){ //if the is a query, add a ? infront of it.
            query = "?" + query;
        }

        if (user != null) {            
            resp.sendRedirect(userService.createLogoutURL("/" + query));
        } else {
            resp.sendRedirect(userService.createLoginURL("/" + query));
        }
    }
}