package com.dra.event_management_system.config.filter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RateLimitFilter implements Filter {

    private final int capacity;
    private final int durationMinutes;
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public RateLimitFilter(int capacity, int durationMinutes){
        this.capacity = capacity;
        this.durationMinutes = durationMinutes;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String ip = httpRequest.getRemoteAddr();
        Bucket bucket = buckets.computeIfAbsent(ip, key -> this.createNewBucket(key));

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setStatus(429);
            httpResponse.getWriter().write("Too many requests - try again later.");
        }
        
    }

    private Bucket createNewBucket(String key) {
        Bandwidth limit = Bandwidth.builder()
                            .capacity(this.capacity)
                            .refillIntervally(this.capacity, Duration.ofMinutes(this.durationMinutes))
                            .build();
        return Bucket.builder().addLimit(limit).build();

    }

}
