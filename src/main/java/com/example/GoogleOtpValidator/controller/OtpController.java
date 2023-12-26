package com.example.GoogleOtpValidator.controller;

import com.example.GoogleOtpValidator.dto.OtpKeyGenerateResultDto;
import com.example.GoogleOtpValidator.service.OtpService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class OtpController {

    private final OtpService otpService;

    @RequestMapping("login")
    public String hello(){
        return "login";
    }


    /**
     * OTP GENERATE
     * @param vo
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("otpGenerate")
    public OtpKeyGenerateResultDto otpGenerate(HttpServletRequest request, String userId) {
        var session = request.getSession();
        //구글 otp key
        var result = otpService.otpKeyGenerate(
                (String) session.getAttribute("otpKey"),
                (String) session.getAttribute("qrCdUrl"),
                request.getScheme()+"://"+request.getServerName(),
                userId);

        if(!result.isResult()) {
            session.setAttribute("qrCdUrl", result.getQrCdUrl());
            session.setAttribute("otpKey", result.getOtpKey());
        }

        return result;
    }

    /**
     * OTP CERT
     * @param code
     */
    @ResponseBody
    @RequestMapping("otpCert")
    public boolean otpCert(HttpServletRequest request, String userCode){
        var session = request.getSession();
        return otpService.otpCert(
                (String) session.getAttribute("otpKey"),
                userCode);
    }
}
