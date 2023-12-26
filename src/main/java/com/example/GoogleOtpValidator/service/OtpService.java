package com.example.GoogleOtpValidator.service;
import com.example.GoogleOtpValidator.dto.OtpKeyGenerateResultDto;
import com.example.GoogleOtpValidator.util.GoogleOtp;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;


@Service
public class OtpService {

    /**
     * OTP KEY Generate
     * @param request
     * @param vo
     * @return
     */
    public OtpKeyGenerateResultDto otpKeyGenerate(String otpKey, String qrCdUrl, String serverUrl, String userId) {
        //구글 otp key
        HashMap<String, String> otpGenerate = null;

        if(!StringUtils.isEmptyOrWhitespace(otpKey) && !StringUtils.isEmptyOrWhitespace(qrCdUrl)) {
            return OtpKeyGenerateResultDto.builder()
                    .otpKey(otpKey)
                    .qrCdUrl(qrCdUrl)
                    .result(true)
                    .build();
        }else {
            //OTP KEY 생성 및 QR CODE URL 생성
            otpGenerate = GoogleOtp.generate(userId, serverUrl);

            return OtpKeyGenerateResultDto.builder()
                    .otpKey(otpGenerate.get("encodedKey"))
                    .qrCdUrl(otpGenerate.get("url"))
                    .result(false)
                    .build();
        }
    }

    /**
     * OTP CERT
     * @param request
     * @param vo
     * @return
     */
    public boolean otpCert(String otpKey, String userCode) {
        boolean certChk = false;
        if(!"".equals(GoogleOtp.replaceNull(otpKey)) && !"".equals(GoogleOtp.replaceNull(userCode))) {
            certChk = GoogleOtp.checkCode(userCode, otpKey);
        }
        return certChk;
    }
}
