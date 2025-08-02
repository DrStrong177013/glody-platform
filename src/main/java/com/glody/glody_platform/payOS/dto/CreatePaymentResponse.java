package com.glody.glody_platform.payOS.dto;

import lombok.Data;

@Data
public class CreatePaymentResponse {
    private String  code;   // "00" = success
    private String  desc;
    private DataObj data;
    @Data
    public static class DataObj {
        private String checkoutUrl;  // link PayOS trả về
    }
    private String signature; // PayOS-signature
}
