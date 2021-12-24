package u.pdp.lesson3.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    //tokenni amal qilish muddati
    long expireTime=36000000;
    //asosiy kalit soz
    String key="XechKimBilishiKerakEmas";


    //tokenni generatsiya qilish uchun metood
    public String generateToken(String username){
        Date expireDate=new Date(System.currentTimeMillis()+expireTime);
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return token;
    }

    public boolean validationToken(String token){
        try {
            Jwts
                    .parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    //token orqali qaysi user tizimga kirayotganligini aniqlash
    //va user ismini qaytaruvchi metod
    public String getUsernameFromToken(String token){
            String username = Jwts
                    .parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return username;

    }

}
