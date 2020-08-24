package in.cubestack.apps.blog.core.service;

import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class TokenAuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationService.class);

    @Inject
    @ConfigProperty(name = "jwt.secret")
    String jwtSecret;

    public String generateToken(Person person) {
        Date expireTime = Date.from(LocalDateTime.now().plusDays(10).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setSubject(person.getUsername())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .setIssuedAt(new Date())
                .claim("personId", person.getId())
                .claim("roles", person.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
                .setExpiration(expireTime)
                .compact();
    }

    @SuppressWarnings("unchecked")
    public Optional<User> fromToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();

            Long personId = null;
            Object personIdClaim = claims.get("personId");
            if (personIdClaim != null) {
                personId = Long.parseLong(personIdClaim.toString());
            }
            List<String> roles = (List<String>) claims.get("roles");

            return Optional.of(new User(personId, claims.getSubject(), roles));
        } catch (Exception exception) {
            LOGGER.error("Error decoding ", exception);
        }
        return Optional.empty();
    }
}
