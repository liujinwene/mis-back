package com.sec.security.authentication;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import com.sec.security.model.SecRole;
import com.sec.security.model.SecUser;
import com.sec.security.service.UserService;

/**
 * An {@link AuthenticationProvider} implementation that retrieves user details from a {@link UserDetailsService}.
 * 
 * @author Ben Alex
 */
public class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	// ~ Instance fields ================================================================================================

	private PasswordEncoder passwordEncoder;
	@Resource
	private UserService userService;

	// ~ Methods ========================================================================================================

	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

		String rawPassword = authentication.getCredentials().toString();

		String encodedPassword = ((SecUser) userDetails).getPassword();
		

		if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
			logger.debug("Authentication failed: password does not match stored value");

			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

	protected void doAfterPropertiesSet() throws Exception {
		Assert.notNull(userService, "A UserDetailsService must be set");
	}

	protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		UserDetails loadedUser;

		try {
			loadedUser = userService.loadUserByUsername(username);
			if(loadedUser != null)
			{
			SecUser user = (SecUser)loadedUser;
			user.getRoles().add(SecRole.getDefaultRole());
			user.setLastLoginTime(new Date());
			userService.updateLastLoginDate(user);
			}
		} catch (UsernameNotFoundException notFound) {
			throw notFound;
		} catch (Exception repositoryProblem) {
			repositoryProblem.printStackTrace();
			throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
		}

		if (loadedUser == null) {
			throw new AuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
		}
		return loadedUser;
	}

	/**
	 * Sets the PasswordEncoder instance to be used to encode and validate passwords. If not set, the password will be compared as plain
	 * text.
	 * <p>
	 * For systems which are already using salted password which are encoded with a previous release, the encoder should be of type
	 * {@code org.springframework.security.authentication.encoding.PasswordEncoder}. Otherwise, the recommended approach is to use
	 * {@code org.springframework.security.crypto.password.PasswordEncoder}.
	 * 
	 * @param passwordEncoder
	 *            must be an instance of one of the {@code PasswordEncoder} types.
	 */
	public void setPasswordEncoder(Object passwordEncoder) {
		Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");

		if (passwordEncoder instanceof PasswordEncoder) {
			this.passwordEncoder = (PasswordEncoder) passwordEncoder;
			return;
		}

		throw new IllegalArgumentException("passwordEncoder must be a PasswordEncoder instance");
	}

	protected PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	
}
