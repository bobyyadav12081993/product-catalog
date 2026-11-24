
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.productcatalog.dto.AuthUserDto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenValidationFilter extends OncePerRequestFilter {

    private final String authServiceUrl;
    private final RestClient restClient;

    public TokenValidationFilter(@Value("${auth.service.url}") String authServiceUrl, RestClient restClient) {
        this.authServiceUrl = authServiceUrl;
        this.restClient = restClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getMethod().equals("GET")) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        AuthUserDto authUserDto;
        try {
            // HttpHeaders headers = new HttpHeaders();
            // headers.set("Authorization", header);
            // ResponseEntity<AuthUserDto> answer = restTemplate.exchange(
            // this.authServiceUrl+"/auth/validateToken",
            // HttpMethod.GET,
            // new HttpEntity<>(headers),
            // AuthUserDto.class);

            authUserDto = restClient.get()
                    .uri(this.authServiceUrl + "/auth/validateToken")
                    .header("Authorization", header)
                    .retrieve()
                    .body(AuthUserDto.class);

        } catch (HttpClientErrorException.Unauthorized e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        } catch (RestClientException e) {
            response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
            return;
        }

        if (request.getMethod().equals("DELETE") && !"ADMIN".equals(authUserDto.getRole())) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        filterChain.doFilter(request, response);
    }

}
