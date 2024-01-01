package com.mfm.gansys.dto.response;

public record SigninResponse(Long status, String message, JwtResponse jwtResponse) {
}
