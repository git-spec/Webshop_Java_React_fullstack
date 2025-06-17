package org.example.backend.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import org.example.backend.repository.UserRepo;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
}
