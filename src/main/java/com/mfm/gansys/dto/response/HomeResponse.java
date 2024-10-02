package com.mfm.gansys.dto.response;

import java.util.List;

public record HomeResponse(String firstName, String lastName, List<String> role) {
}
