package com.bci.microservice.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginUsuarioRequest {
    @Schema(example = "gussvill@gmail.com")
    private String email;
    @Schema(example = "Guss2020")
    private String password;
}

