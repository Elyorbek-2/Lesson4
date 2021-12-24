package u.pdp.lesson3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import u.pdp.lesson3.service.MyAuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    MyAuthService myAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //Requestdan tokenni oldik
        String token = request.getHeader("Authorization");
        //Tokenni tekshirib olyapmiz
        if (token!=null&&token.startsWith("Bearer")){
            //Tokenni bor yoqligini va bearer ni tekshiryapmiz
            boolean validationToken = jwtProvider.validationToken(token);
            token=token.substring(7);
            //tokenni validatsiyadan o'tkazdik(muddatini hack qilinmaanini)
            if (validationToken){
                String usernameFromToken = jwtProvider.getUsernameFromToken(token);
                //Username orqali userdetailsni oldik
                UserDetails userDetails = myAuthService.loadUserByUsername(usernameFromToken);
                //userdatails orqali authentication yaratib oldik
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null, userDetails.getAuthorities());
                //sistemaga kim kirganini o'rnatib oldik
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);

    }


}
