package mx.unam.dgtic.security.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 28/11/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {
    private String token;
    private String tokenType;
    private String userId;
    private String userName;
    private Long expiryDuration;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtRequest(String token, String userId, String userName, Long expiryDuration,
                      Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.userId = userId;
        this.userName = userName;
        this.expiryDuration = expiryDuration;
        this.authorities = authorities;
        this.tokenType = "Bearer";
    }
}