package com.apicela.apicrypto.infra.sec;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class BearerToken  extends AbstractAuthenticationToken {
    final private String token;
    public BearerToken(final String token) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.token = token;
    }
    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }
}
