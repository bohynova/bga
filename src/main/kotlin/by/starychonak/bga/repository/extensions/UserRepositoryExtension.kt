package by.starychonak.bga.repository.extensions

import by.starychonak.bga.repository.UserRepository

fun UserRepository.findByUserNameOrThrow(username: String) = findByUsername(username) ?: throw Exception("Cannot find user by username = $username")

fun UserRepository.checkUniqueUsername(username: String) = if (existsUserByUsername(username)) throw Exception("User with username = $username already exists") else true

fun UserRepository.checkUniqueEmail(email: String) = if (existsUserByEmail(email)) throw Exception("User with email = $email already exists") else true