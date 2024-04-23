package WooJJam.mintspot.filter;

import WooJJam.mintspot.session.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] whiteList = {"/", "/user/*", "/css/*"};
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestURI = httpServletRequest.getRequestURI();
        log.info("Request URI = {}", requestURI);
        try {
            log.info("인증 체크 필터 {}", httpServletRequest);

            if (isLoginRequired(requestURI)) {
                log.info("인증 체크 로직 {}", requestURI);

                HttpSession session = httpServletRequest.getSession(false);

                if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
                    log.info("미인증 사용자 {}", requestURI);
                    httpServletResponse.sendRedirect("/user/login");
                    return;
                }
            }

            chain.doFilter(request, response);
        }catch (Exception e) {
            log.error("인증 체크 필터 오류", e);
            throw e;
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    private boolean isLoginRequired(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
