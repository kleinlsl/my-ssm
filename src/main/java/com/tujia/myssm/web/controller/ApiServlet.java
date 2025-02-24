package com.tujia.myssm.web.controller;

/**
 * @author: songlinl
 * @create: 2025/2/24 17:09
 */
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/*")
public class ApiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = "{"
                + "\"status\": \"success\","
                + "\"method\": \"" + request.getMethod() + "\","
                + "\"path\": \"" + request.getRequestURI() + "\","
                + "\"timestamp\": " + System.currentTimeMillis()
                + "}";

        response.getWriter().write(jsonResponse);
    }
}