package com.mtitek.spring.security;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {
	@Value("${app.security.keystore.path}")
	private String keystorePath;

	@Value("${app.security.keystore.password}")
	private String keystorePassword;

	@Value("${app.security.keystore.alias}")
	private String keystoreAlias;

	@Value("${app.security.keystore.keyPassword}")
	private String keyPassword;

	@Bean
	public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
		ClientSettings clientSettings = ClientSettings.builder().requireAuthorizationConsent(true)
				.requireProofKey(false).build();

		// id: random unique identifier
		// client id: ~ user name
		// client secret: ~ password
		// authorization grant type: oauth 2 grant types to be supported (authorization code, refresh token)
		// redirect uri: registered uris that the authorization server can redirect to after authorization has been granted (registration id: mtitek-registration-id)
		// scope: oauth 2 scopes for the client (addAppProfile, updateAppProfile, deleteAppProfile)
		// client settings: custom client seettings (user consent, proof key)
		RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("mtitek-client-id").clientSecret(passwordEncoder.encode("mtitek-client-secret"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUri("http://mtitekauthclient:8080/login/oauth2/code/mtitek-registration-id")
				.scope("addAppProfile").scope("updateAppProfile").scope("deleteAppProfile").scope(OidcScopes.OPENID)
				.clientSettings(clientSettings).build();
		return new InMemoryRegisteredClientRepository(registeredClient);
	}

	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource() throws Exception {
	    RSAKey rsaKey = loadRsaKey();
	    JWKSet jwkSet = new JWKSet(rsaKey);
	    return new ImmutableJWKSet<>(jwkSet);
	}

	private RSAKey loadRsaKey() throws Exception {
	    KeyStore keyStore = KeyStore.getInstance("JKS");
	    try (InputStream is = getClass().getClassLoader().getResourceAsStream(keystorePath)) {
	        keyStore.load(is, keystorePassword.toCharArray());
	    }
	    return RSAKey.load(keyStore, keystoreAlias, keyPassword.toCharArray());
	}
}
