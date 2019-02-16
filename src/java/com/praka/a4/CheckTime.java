/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.praka.a4;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author praka
 */
public class CheckTime extends HttpServlet {

        protected void doGet(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = getServletContext().getInitParameter("email");
        String phone = getServletContext().getInitParameter("phone");
        
        String DateOpenConfig = getServletConfig().getInitParameter("Date");
        String[] DateOpen = DateOpenConfig.split(",");
                         
        String open = getServletConfig().getInitParameter("openTime");
        
        String[] openTime = open.split(":");
        Calendar openCalendar = Calendar.getInstance(); // return วันที่และเวลาปัจจุบัน ถ้าไม่ set จะเป็นเวลาตอนรัน
        openCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(openTime[0])); //set ชั่วโมง 10
        openCalendar.set(Calendar.MINUTE, Integer.parseInt(openTime[1])); // นาที 00
        openCalendar.set(Calendar.SECOND, Integer.parseInt(openTime[2])); // วินาที 00
        
        String close = this.getInitParameter("closeTime");
        String[] closeTime = close.split(":");
        Calendar closeCalendar = Calendar.getInstance();
        closeCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(closeTime[0]));
        closeCalendar.set(Calendar.MINUTE, Integer.parseInt(closeTime[1]));
        closeCalendar.set(Calendar.SECOND, Integer.parseInt(closeTime[2]));
        
        Calendar now = Calendar.getInstance();
        String[] dateNow = now.getTime().toString().split(" "); // Sat Feb 16 19:34:27 ICT 2019 ใช้แค่ วันอย่างเดียว dateNow[0]
        boolean checkDate = false;
        
        for (String dateConfig : DateOpen) {
            if(dateConfig.equalsIgnoreCase(dateNow[0])) { //เช็กว่า วันที่ของ dateNow มีใน config หรือไม่ ถ้าไม่มีก็ให้ close ไป
                checkDate = true;
                break;
            }
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>"); 
            out.println("</head>");
            out.println("<body>");
            
            if (now.after(openCalendar) && now.before(closeCalendar) && checkDate){
                out.println("<form action='./LoginServlet' method='POST'>");
                out.println("username: <input type='text' name='username'><br/>")   ;
                out.println("password: <input type='password' name='password'><br/>");
                out.println("<input type='submit' name='submit' value='Login'><br/>");
                out.println("</form>");
                out.println("</div>");
            }
            else{ //close
                out.println("<h2>We are closing.</h2>");
                out.println("<table><tbody>");

                for (int i = 0; i < DateOpen.length; i++) {
                    out.println("<tr>");
                    for (int j = 0; j < 2; j++) {
                        out.println("<td>");
                        if(j%2 == 0) {
                            out.println("OPEN: "+dayFullName(DateOpen[i]));
                        } else {
                            out.println(open+" - "+close);
                        }
                        out.println("</td>");
                    }
                    out.println("</tr>");
                }
                
                out.println("</tbody></table>");
                
            }
            out.println("<br/><div style='background-color:gray;padding:5px;width:30%;font-size:70%;'>Contact: " + email + ", Phone: " + phone + "</div>");
            out.println("</body>");
            out.println("</html>");
        }
        

    }

    private static String dayFullName(String day) {
        String fullname = "";
        switch (day) {
            case "SUN":  fullname = "Sunday";
                     break;
            case "MON":  fullname = "Monday";
                     break;
            case "TUE":  fullname = "Tueday";
                     break;
            case "WED":  fullname = "Wednesday";
                     break;
            case "THU":  fullname = "Thursday";
                     break;
            case "FRI":  fullname = "Friday";
                     break;
            case "SAT":  fullname = "Saturday";
                     break;
        }
        return fullname;
    }

}
