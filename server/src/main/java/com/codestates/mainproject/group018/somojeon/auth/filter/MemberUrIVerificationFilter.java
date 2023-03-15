package com.codestates.mainproject.group018.somojeon.auth.filter;//package com.group5.stackoverflow.auth.filter;
//
//import com.group5.stackoverflow.utils.Checker;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.net.URI;
//
//@Slf4j
//public class MemberUrIVerificationFilter extends OncePerRequestFilter {  // (1)
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // API URL에 따라 권한 체크
//        AntPathMatcher antPathMatcher = new AntPathMatcher();
//        URI uri = URI.create(request.getRequestURI());
//        String method = request.getMethod();
//
//        request.setAttribute("ok", false);
//
//        // 특정 멤버를 타겟으로 하는 경우
//        if(Checker.checkAdmin()){
//            request.setAttribute("ok", true);
//            filterChain.doFilter(request, response);
//            return;
//
//        }
//
//        if( uri.getPath().contains("?")){
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//
//        filterChain.doFilter(request, response);
//    }
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return request.getRequestURI().startsWith("/members");
//    }
//
//}
