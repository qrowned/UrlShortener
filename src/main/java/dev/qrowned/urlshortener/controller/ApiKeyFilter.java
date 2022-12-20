package dev.qrowned.urlshortener.controller;

import dev.qrowned.urlshortener.UrlShortenerConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public final class ApiKeyFilter extends GenericFilterBean {

    private final UrlShortenerConfig urlShortenerConfig;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest httpServletRequest)) return;

        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String header = httpServletRequest.getHeader("API-KEY");

        if (((HttpServletRequest) servletRequest).getMethod().equals("GET") || (this.urlShortenerConfig.getApiKeys().contains(header)))
            filterChain.doFilter(servletRequest, servletResponse);
        else
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please provide a valid API-KEY included in the header of your request!");
    }

}
