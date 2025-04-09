package com.example.javaspringbootnavy1.controller;

import com.example.javaspringbootnavy1.config.JwtProvider;
import com.example.javaspringbootnavy1.doman.AccountStatus;
import com.example.javaspringbootnavy1.exceptions.SellerException;
import com.example.javaspringbootnavy1.modal.Seller;
import com.example.javaspringbootnavy1.modal.SellerReport;
import com.example.javaspringbootnavy1.modal.VerificationCode;
import com.example.javaspringbootnavy1.repository.VerificationCodeRepository;
import com.example.javaspringbootnavy1.request.LoginRequest;
import com.example.javaspringbootnavy1.response.ApiResponse;
import com.example.javaspringbootnavy1.response.AuthResponse;
import com.example.javaspringbootnavy1.service.AuthService;
import com.example.javaspringbootnavy1.service.EmailService;
import com.example.javaspringbootnavy1.service.SellerService;
import com.example.javaspringbootnavy1.service.impl.SellerReportService;
import com.example.javaspringbootnavy1.utils.OtpUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;
    private final SellerReportService sellerReportService;

//    @PostMapping("/sent/login-otp")
//    public ResponseEntity<ApiResponse> sendOtpHandle(
//            @RequestBody VerificationCode req) throws Exception {
//
//        authService.sentLoginOtp(req.getEmail());
//
//        ApiResponse res = new ApiResponse();
//
//        res.setMessage("otp sent successful");
//
//
//        return  ResponseEntity.ok(res);
//    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(
            @RequestBody LoginRequest req
            ) throws Exception {

        String otp = req.getOtp();
        String email = req.getEmail();



        req.setEmail("seller_"+ email);
        System.out.println("OTP: " + otp+ "email: " + email);
        AuthResponse authResponse = authService.signing(req);
        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySellerEmail(
            @PathVariable String otp) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);

        if(verificationCode==null || !verificationCode.getOtp().equals(otp)){
            throw new Exception("Wrong otp!!!");
        }

        Seller seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);

        return new ResponseEntity<>(seller, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(
            @RequestBody Seller seller) throws Exception, MessagingException {
        Seller savedSeller = sellerService.createSeller(seller);

        String otp = OtpUtils.generateOtp();
        VerificationCode verifivationCode=new VerificationCode();
        verifivationCode.setOtp(otp);
        verifivationCode.setEmail(seller.getEmail());
        verificationCodeRepository.save(verifivationCode);

        String subject = "OTP Verification Code";
        String text = "OTP Verification Code";
        String frontend_url = "http://localhost:8080/verify-sellers/";
        emailService.sendVerificationOtpEmail(seller.getEmail(), verifivationCode.getOtp(),subject,text + frontend_url );

        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerException {
        Seller seller = sellerService.getSellerById(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerProfile(@RequestHeader("Authorization") String jwt) throws Exception{
        Seller seller = sellerService.getSellerProfile(jwt);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwt) throws Exception{
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        Seller seller = sellerService.getSellerByEmail(email);
        SellerReport report = sellerReportService.getSellerReport(seller);

        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Seller>> getallSellers(
            @RequestParam(required = false) AccountStatus status){
        List<Seller> sellers = sellerService.getAllSellers(status);
        return ResponseEntity.ok(sellers);
    }

    @PatchMapping()
    public ResponseEntity<Seller> updateSeller(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Seller seller) throws Exception{

        Seller profile = sellerService.getSellerProfile(jwt);
        Seller updatedSeller = sellerService.updateSeller(profile.getId(), seller);
        return ResponseEntity.ok(updatedSeller);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSeller(@PathVariable Long id) throws Exception{
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }

}
