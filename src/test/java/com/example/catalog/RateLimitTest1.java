//package com.example.catalog;
//
//import com.example.catalog.interceptors.RateLimit;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpServletResponse;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class RateLimitTest1 {
//
//    @Autowired
//    private RateLimit rateLimit;
//
//    private MockHttpServletRequest request;
//    private MockHttpServletResponse response;
//
//    @BeforeEach
//    void setUp() {
//        rateLimit = new RateLimit();
//        rateLimit.setRateLimitAlgo("moving");
//        rateLimit.setRateLimitRPM(15);
//        rateLimit.setRateLimitEnabled(true);
//
//        request = new MockHttpServletRequest();
//        response = new MockHttpServletResponse();
//    }
//
//
//
//    @Test
//    void shouldSetRetryAfterHeaderWhenBlocked() throws Exception {
//        request.setRemoteAddr("172.16.0.3");
//
//        for (int i = 0; i < 15; i++) {
//            rateLimit.preHandle(request, response, null);
//        }
//
//        rateLimit.preHandle(request, response, null);
//
//        String retryAfter = response.getHeader("X-Rate-Limit-Retry-After-Seconds");
//        assertNotNull(retryAfter, "Retry-After header should be set");
//        assertTrue(Integer.parseInt(retryAfter) > 0, "Retry-After header should have a positive value");
//    }
//
//    @Test
//    void shouldExcludeInternalEndpointsFromRateLimiting() throws Exception {
//        request.setRequestURI("/internal");
//        request.setRemoteAddr("10.0.0.6");
//
//        boolean isAllowed = rateLimit.preHandle(request, response, null);
//        assertTrue(isAllowed, "Internal endpoints should not be rate-limited");
//
//        assertNull(response.getHeader("X-Rate-Limit-Remaining"), "Headers should not include rate limit info for internal endpoints");
//    }
//
//    @Test
//    void shouldAllowRequestsAfterTimeFrameResets_MovingWindow() throws Exception {
//        rateLimit.setRateLimitAlgo("moving");
//        request.setRemoteAddr("192.168.0.4");
//
//        for (int i = 0; i < 15; i++) {
//            rateLimit.preHandle(request, response, null);
//        }
//
//        Thread.sleep(60000);
//
//        boolean isAllowed = rateLimit.preHandle(request, response, null);
//        assertTrue(isAllowed, "Requests should be allowed after sliding window reset");
//
//        String remaining = response.getHeader("X-Rate-Limit-Remaining");
//        assertEquals(14, Integer.parseInt(remaining), "Remaining requests should reflect the reset state");
//    }
//
//    @Test
//    void shouldAllowFewerRequestsWhenAlgoChanges() throws Exception {
//        rateLimit.setRateLimitAlgo("fixed");
//        rateLimit.setRateLimitRPM(5);
//        request.setRemoteAddr("10.0.0.7");
//
//        for (int i = 0; i < 5; i++) {
//            boolean isAllowed = rateLimit.preHandle(request, response, null);
//            assertTrue(isAllowed, "Requests within the new limit should be allowed");
//
//            String remaining = response.getHeader("X-Rate-Limit-Remaining");
//            assertEquals(5 - (i + 1), Integer.parseInt(remaining));
//        }
//
//        boolean isBlocked = rateLimit.preHandle(request, response, null);
//        assertFalse(isBlocked, "Requests beyond the new limit should be blocked");
//    }
//}
