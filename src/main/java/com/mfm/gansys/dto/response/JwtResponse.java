package com.mfm.gansys.dto.response;

import java.util.List;

public record JwtResponse(String firstName, String lastName, String jwt, List<String> roles) {
}
