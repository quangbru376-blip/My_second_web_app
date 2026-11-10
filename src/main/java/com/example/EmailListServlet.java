package com.example;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class EmailListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String url = "/index.html";

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";  // default action
        }

        // perform action and set URL to appropriate page
        if (action.equals("join")) {
            url = "/index.html";    // the "join" page
        } else if (action.equals("add")) {
            // get parameters from the request
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String dateOfBirth = request.getParameter("dateOfBirth");

            String ways = request.getParameter("ways");
            String howToHeardAbout;
            if ("search".equals(ways)) {
                howToHeardAbout = "Search Engine";
            } else if ("mouth".equals(ways)) {
                howToHeardAbout = "Word of Mouth";
            } else if ("socialMedia".equals(ways)) {
                howToHeardAbout = "Social Media";
            } else {
                howToHeardAbout = "Other";
            }

            String[] announcements = request.getParameterValues("announcements");
            String announcementsString = "None";
            if (announcements != null && announcements.length > 0) {
                announcementsString = String.join(", ", announcements);
            }

            String contactMethod = request.getParameter("methods");

            // store data in User and Survey objects
            User user = new User(firstName, lastName, email, dateOfBirth);
            Survey survey = new Survey(user, howToHeardAbout, contactMethod, announcementsString);

            // set User and Survey objects in request object and set URL
            request.setAttribute("user", user);
            request.setAttribute("survey", survey);
            url = "/thanks.jsp";   // the "thanks" page
        }

        // forward request and response objects to specified URL
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
