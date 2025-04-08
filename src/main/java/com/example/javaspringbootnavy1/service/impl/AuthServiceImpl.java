package com.example.javaspringbootnavy1.service.impl;

import com.example.javaspringbootnavy1.config.JwtProvider;
import com.example.javaspringbootnavy1.doman.User_Role;
import com.example.javaspringbootnavy1.modal.Cart;
import com.example.javaspringbootnavy1.modal.Seller;
import com.example.javaspringbootnavy1.modal.User;
import com.example.javaspringbootnavy1.modal.VerificationCode;
import com.example.javaspringbootnavy1.repository.CartRepository;
import com.example.javaspringbootnavy1.repository.SellerRepository;
import com.example.javaspringbootnavy1.repository.UserReponstitory;
import com.example.javaspringbootnavy1.repository.VerificationCodeRepository;
import com.example.javaspringbootnavy1.request.LoginRequest;
import com.example.javaspringbootnavy1.response.AuthResponse;
import com.example.javaspringbootnavy1.response.SignupRequest;
import com.example.javaspringbootnavy1.service.AuthService;
import com.example.javaspringbootnavy1.service.EmailService;
import com.example.javaspringbootnavy1.utils.OtpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserReponstitory userReponstitory;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartReponstitory;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final CustomerUserServiceImpl customerUserService;
    private final SellerRepository sellerRepository;

    @Override
    public void sentLoginOtp(String email, User_Role role) throws Exception {
        String SIGNING_PREFIX="signing_";

            if (email.startsWith(SIGNING_PREFIX)) {
            email=email.substring(SIGNING_PREFIX.length());

            if(role.equals(User_Role.Role_Seller)){
                Seller seller = sellerRepository.findByEmail(email);
                if(seller==null){
                    throw new Exception("Seller không tồn tại");
                }
            }
            else{
                System.out.println("email "+ email);
                User user=userReponstitory.findByEmail(email);
                if(user==null){
                    throw new Exception("user not exist with provided email");
                }
            }
        }
            VerificationCode isExist= verificationCodeRepository.findByEmail(email);
            if(isExist!=null){
                verificationCodeRepository.delete(isExist);
            }
            String otp = OtpUtils.generateOtp();
            VerificationCode verifivationCode=new VerificationCode();
            verifivationCode.setOtp(otp);
            verifivationCode.setEmail(email);
            verificationCodeRepository.save(verifivationCode);

            String subject = "login/signup otp";
            String text= "your login/signup otp is - "+ otp;

            emailService.sendVerificationEmail(email,otp,subject,text);
    }

    @Override
    public String createUser(SignupRequest req) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());

        if (verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())) {
            throw new Exception("wrong otp!");
        }

        User user = userReponstitory.findByEmail(req.getEmail());

        if (user == null) {
            User createUser = new User();
            createUser.setEmail(req.getEmail());
            createUser.setFullName(req.getFullName());
            createUser.setRole(User_Role.Role_Customer);
            createUser.setPhone("0888291733");
            createUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user= userReponstitory.save(createUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cartReponstitory.save(cart);
        }
        List<GrantedAuthority> authorities= new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(User_Role.Role_Customer.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        return jwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse signing(LoginRequest req) throws Exception {
        String username= req.getEmail();
        String otp= req.getOtp();

        Authentication authentication = authenticate(username, otp);
        String tooken = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(tooken);
        authResponse.setMessage("Login successful");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName= authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        authResponse.setRole(User_Role.valueOf(roleName));

        return authResponse;
    }

    private Authentication authenticate(String username, String otp) throws Exception {
        UserDetails userDetails = customerUserService.loadUserByUsername(username);

//        username=user.substring(SIGNING_PREFIX.length());
        String SELLER_PREFIX = "seller_";

        if(username.startsWith(SELLER_PREFIX)){
            username=username.substring(SELLER_PREFIX.length());
        }

        if(userDetails==null){
            throw new BadCredentialsException("invalid username");
        }
        VerificationCode verifivationCode = verificationCodeRepository.findByEmail(username);

        if(verifivationCode==null || !verifivationCode.getOtp().equals(otp)){
            throw new Exception("wrong otp");
        }
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }
}
