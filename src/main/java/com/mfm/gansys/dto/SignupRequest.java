package com.mfm.gansys.dto;

public record SignupRequest(String email, String password, String firstName, String lastName, Long roleId) {
}
