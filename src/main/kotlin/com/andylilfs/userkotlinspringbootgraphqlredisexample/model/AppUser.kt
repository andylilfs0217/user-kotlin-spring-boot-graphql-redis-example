package com.andylilfs.userkotlinspringbootgraphqlredisexample.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.util.UUID

@RedisHash("AppUser")
data class AppUser(
  @Id
  val id: UUID = UUID.randomUUID(),
  var firstName: String,
  var lastName: String,
  val email: String,
  val username: String,
  var password: String,
  var isLoggedIn: Boolean = false
)
