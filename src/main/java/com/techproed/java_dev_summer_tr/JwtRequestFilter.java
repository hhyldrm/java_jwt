package com.techproed.java_dev_summer_tr;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
@Component
public class JwtRequestFilter extends OncePerRequestFilter //this class will filter the requests, every request should have conditions
//we will create a container because in my request user it's very important
//we will create an object from service to access everything
{
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	throws ServletException, IOException {
			
			final String authorizationHeader = request.getHeader("Authorization");
			 String username = null;
		        String jwt = null;
		        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
		            jwt = authorizationHeader.substring(7);
		            username = jwtUtil.extractUsername(jwt);
		        }
		        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
		            if (jwtUtil.validateToken(jwt, userDetails)) {
		                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		            }
		        }
		        filterChain.doFilter(request, response);
				
			}
			
//in the first condition if we look at the authorization header, if there is token we get it and we use it no need to create a token
//in the second condition if there is no token => if it's null but username is not null
//by using the username i'm getting the user details.
//if the authentication is true it will set authentications and create token
//and creating a filter by comparing request with response
}