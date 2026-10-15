package com.application.digibookyproject.service.request;

import java.util.UUID;

public record PostLoanRequest(String memberEmail, UUID bookId) {
}
