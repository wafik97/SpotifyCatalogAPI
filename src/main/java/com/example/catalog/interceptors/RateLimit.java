package com.example.catalog.interceptors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import static java.lang.System.*;


@Component
public class RateLimit implements HandlerInterceptor {

    @Value("${rate-limit.algo}")
    private String rateLimitAlgo;

    @Value("${rate-limit.rpm}")
    private int rateLimitRPM;

    @Value("${rate-limit.enabled}")
    private boolean rateLimitEnabled;



    private final long startingTime = currentTimeMillis();
    private int requestCounter = 0;
    private static final int blockTime = 60000;
    private int lastFix = 0;
    ArrayList<Long> list = new ArrayList<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

         //   System.out.println("request is check_for1: "+request);
          //  System.out.println("Response is check_for2: " + response);
        //    System.out.println("handler is check_for3: " + handler);

        if (request.getRequestURI().startsWith("/internal")|| !rateLimitEnabled) {
          //  response.setHeader("X-Rate-Limit-Remaining", "10");
           // response.setStatus(200);
            return true;
        }



        String clientIp = request.getRemoteAddr();

        if (!isAllowed(clientIp,response)) {
            // TODO your implementation ...

            response.setHeader("X-Rate-Limit-Remaining", "0");
            response.setHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(blockTime/1000));
            response.setStatus(429);

            return false;
        }

        return true;
    }




    // to do nested class



    private synchronized boolean isAllowed(String clientIp, HttpServletResponse response) {
        // TODO your implementation ...

        if (rateLimitAlgo.equals("fixed")){

           return isAllowedFixed( clientIp,response) ;
        }
        else if (rateLimitAlgo.equals("moving")){


            return isAllowedMoving( clientIp,response);
        }

        return true;
    }


    private synchronized boolean isAllowedFixed(String clientIp, HttpServletResponse response) {
        long curr = currentTimeMillis();

        if((int)((curr-startingTime)/60000)>lastFix){
            requestCounter = 0;
            lastFix=(int)((curr-startingTime)/60000);
        }

        if (requestCounter <rateLimitRPM) {
            requestCounter++;
            response.setHeader("X-Rate-Limit-Remaining", String.valueOf(rateLimitRPM - requestCounter));

            return true;
        }
        else {


            return false;
        }
    }

    private synchronized boolean isAllowedMoving(String clientIp, HttpServletResponse response) {
        list.add(currentTimeMillis());
        int counter=-1;

        for(long i:list){
            if(list.get(list.size()-1)-i>60000){
                counter++;

            }
        }
        if (counter >= 0) {
            list.subList(0, counter + 1).clear();
        }

        if(list.size()<rateLimitRPM){

            response.setHeader("X-Rate-Limit-Remaining", String.valueOf(rateLimitRPM - list.size()));
            return true;
        }
        else {
            return false;
        }




    }


}