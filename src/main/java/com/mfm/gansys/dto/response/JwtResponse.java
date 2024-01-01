package com.mfm.gansys.dto.response;

import java.util.List;

public record JwtResponse(String jwt, List<String> roles) {
}
