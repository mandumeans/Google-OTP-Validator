package com.example.GoogleOtpValidator.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OtpKeyGenerateResultDto {
    boolean result;
    String otpKey;
    String qrCdUrl;
}
