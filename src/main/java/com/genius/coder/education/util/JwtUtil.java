package com.genius.coder.education.util;

import com.genius.coder.education.user.form.AdminUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/16
 */
@Component
@Slf4j
public class JwtUtil {
    public static final String TOKEN_HEADER = "jwt-token";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String SUBJECT = "congge";

    public static final int EXPIRITION = 24 * 60 * 60 * 7;

    public static final String APPSECRET_KEY = "congge_secret";

    private static final String ROLE_CLAIMS = "role";
    private static final String CLAIM_KEY_NAME = "name";
    private static final String CLAIM_KEY_USERNAME = "username";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_USER_ID = "id";
    private static final String CLAIM_KEY_AUTHORITIES = "roles";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    private Map<String, String> tokenMap = new ConcurrentHashMap<>(32);
    /***
     * 从token中获取用户信息
     * @param token
     * @return
     */
    public AdminUserDetail getUserFromToken(String token) {
        AdminUserDetail user = null;
        try{
            final Claims claims = getClaimsFromToken(token);
            String id = String.valueOf(claims.get(CLAIM_KEY_USER_ID));
            String username = String.valueOf(claims.get(CLAIM_KEY_USERNAME));
            List<String> authorities = (List<String>) claims.get(CLAIM_KEY_AUTHORITIES);
            String name = String.valueOf(claims.get(CLAIM_KEY_NAME));
            user = new AdminUserDetail(id,username,name,authorities);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return user;
    }

    /***
     * 获取当前登录用户
     * @return
     */
    public AdminUserDetail getCurrentUser(){
        return (AdminUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 生成token
     * @param user
     * @return
     */
    public String createToken(AdminUserDetail user) {
        Map<String, Object> claims = generateClaims(user);
        claims.put(CLAIM_KEY_AUTHORITIES, authoritiesToArray(user.getAuthorities()));
        String accessToken = generateToken(user.getUsername(), claims);
        //存储token
        putToken(user.getUsername(), accessToken);
        return accessToken;
    }

    /**
     * 获取过期时间
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isExpiration(token));
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            refreshedToken = generateToken(claims.getSubject(), claims);
            //存储token
            putToken(claims.getSubject(), refreshedToken);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 校验token是否失效
     *
     * @param token
     * @return
     */
    public Boolean validateToken(String token) {
        return (!isExpiration(token));
    }


    public void putToken(String userName, String token) {
        tokenMap.put(userName, token);
    }

    public void deleteToken(String userName) {
        tokenMap.remove(userName);
    }

    public boolean containToken(String userName, String token) {
        if (userName != null && tokenMap.containsKey(userName) && tokenMap.get(userName).equals(token)) {
            return true;
        }
        return false;
    }

    /**
     * 生成token
     * @param username
     * @param role
     * @return
     */
    public static String createToken(String username,String role) {
        Map<String,Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);

        return  Jwts
                .builder()
                .setSubject(username)
                .setClaims(map)
                .claim("username",username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * EXPIRITION))
                .signWith(SIGNATURE_ALGORITHM, APPSECRET_KEY)
                .compact();
    }

    /**
     * 获取用户名
     * @param token
     * @return
     */
    public String getUsername(String token){
        Claims claims = getClaimsFromToken(token);
        return claims.get("username").toString();
    }

    /**
     * 获取用户角色
     * @param token
     * @return
     */
    public String getUserRole(String token){
        Claims claims = getClaimsFromToken(token);
        return claims.get(ROLE_CLAIMS).toString();
    }

    /**
     * 获取token创建时间
     * @param token
     * @return
     */
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = claims.getIssuedAt();
        } catch (Exception e) {
            created = null;
        }
        return created;
    }
    /**
     * 是否过期
     * @param token
     * @return
     */
    public boolean isExpiration(String token){
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration().before(new Date());
    }

    private  Claims getClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
    }

    private  Map<String, Object> generateClaims(AdminUserDetail userDetail) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USERNAME, userDetail.getUsername());
        claims.put(CLAIM_KEY_NAME, userDetail.getName());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_USER_ID, userDetail.getId());
        claims.put(CLAIM_KEY_AUTHORITIES,userDetail.getAuthorities());
        return claims;
    }

    private  Set authoritiesToArray(Collection<? extends GrantedAuthority> authorities) {
        Set<String> list = new HashSet<>();
        for (GrantedAuthority ga : authorities) {
            list.add(ga.getAuthority());
        }
        return list;
    }

    private String generateToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate(EXPIRITION))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SIGNATURE_ALGORITHM, APPSECRET_KEY)
                .compact();
    }

    private Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration);
    }
}
