package com.pontodosjogos.pontodosjogos.config;

import lombok.Builder;

@Builder
public record JWTUserData(Long id, String password, String email) {
}
