package com.example.catalog.interceptors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.*;


@Component
public class RateLimit implements HandlerInterceptor {

    @Value("${rate-limit.algo}")
    private String rateLimitAlgo;

    @Value("${rate-limit.rpm}")
    private int rateLimitRPM;

    @Value("${rate-limit.enabled}")
    private boolean rateLimitEnabled;


    private static final int blockTime = 60000;

    private final HashMap<String, Ips> ipMap = new HashMap<>();


//    private final long startingTime = currentTimeMillis();
//    private int requestCounter = 0;
//
//    private int lastFix = 0;
//    ArrayList<Long> list = new ArrayList<>();

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

        if(!ipMap.containsKey(clientIp)){
            Ips ip=new Ips(clientIp );
            ipMap.put(clientIp,ip);
        }

        if (!isAllowed(ipMap.get(clientIp),response)) {
            // TODO your implementation ...

            response.setHeader("X-Rate-Limit-Remaining", "0");
            response.setHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(blockTime/1000));
            response.setStatus(429);

            return false;
        }

        return true;
    }




    // to do nested class

    public static class Ips {
        private String IP;
        private long startingTime = currentTimeMillis();
        private int requestCounter = 0;
        private int lastFix = 0;
        ArrayList<Long> list = new ArrayList<>();


        // Constructor
        public Ips(String ip ) {
            this.IP = ip;
            this.startingTime = currentTimeMillis();;

        }
    }


    private synchronized boolean isAllowed(Ips clientIp, HttpServletResponse response) {
        // TODO your implementation ...

        if (rateLimitAlgo.equals("fixed")){

           return isAllowedFixed( clientIp,response) ;
        }
        else if (rateLimitAlgo.equals("moving")){


            return isAllowedMoving( clientIp,response);
        }

        return true;
    }


    private synchronized boolean isAllowedFixed(Ips clientIp, HttpServletResponse response) {
        long curr = currentTimeMillis();

        if((int)((curr- clientIp.startingTime )/60000)>clientIp.lastFix){
            clientIp.requestCounter = 0;
            clientIp.lastFix=(int)((curr-clientIp.startingTime)/60000);
        }

        if (clientIp.requestCounter <rateLimitRPM) {
            clientIp.requestCounter++;
            response.setHeader("X-Rate-Limit-Remaining", String.valueOf(rateLimitRPM - clientIp.requestCounter));

            return true;
        }
        else {


            return false;
        }
    }

    private synchronized boolean isAllowedMoving(Ips clientIp, HttpServletResponse response) {
        clientIp.list.add(currentTimeMillis());
        int counter=-1;

        for(long i:clientIp.list){
            if(clientIp.list.get(clientIp.list.size()-1)-i>60000){
                counter++;

            }
        }
        if (counter >= 0) {
            clientIp.list.subList(0, counter + 1).clear();
        }

        if(clientIp.list.size()<rateLimitRPM){

            response.setHeader("X-Rate-Limit-Remaining", String.valueOf(rateLimitRPM - clientIp.list.size()));
            return true;
        }
        else {
            return false;
        }




    }


}