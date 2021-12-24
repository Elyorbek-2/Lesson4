package u.pdp.lesson3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import u.pdp.lesson3.entity.User;
import u.pdp.lesson3.security.JwtProvider;
import u.pdp.lesson3.service.MyAuthService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MyAuthService myAuthService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping
    public ResponseEntity<?> get(@RequestBody User user){
        try {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUserName(),
                user.getPassword()));
            String token = jwtProvider.generateToken(user.getUserName());
            return ResponseEntity.status(400).body("OK");

        }catch (BadCredentialsException e){
            return ResponseEntity.status(400).body("Parol yoki login xato");
        }

    }
}
