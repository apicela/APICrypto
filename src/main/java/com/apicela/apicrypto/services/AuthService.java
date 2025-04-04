package com.apicela.apicrypto.services;

import com.apicela.apicrypto.models.UserModel;
import com.apicela.apicrypto.models.requests.AuthenticationDTO;
import com.apicela.apicrypto.models.requests.RegisterUserDTO;
import com.apicela.apicrypto.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

}