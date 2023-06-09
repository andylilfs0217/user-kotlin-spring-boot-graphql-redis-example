package com.andylilfs.userkotlinspringbootgraphqlredisexample.resolver

import com.andylilfs.userkotlinspringbootgraphqlredisexample.model.AppUser
import com.andylilfs.userkotlinspringbootgraphqlredisexample.repository.UserRepository
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class UserResolver(private val userRepository: UserRepository) {

  @QueryMapping
  fun getUsers(): List<AppUser> {
    return userRepository.findAll().toList()
  }

  @QueryMapping
  fun getUser(@Argument id: UUID): Optional<AppUser> {
    return userRepository.findById(id)
  }

  @MutationMapping
  fun createUser(
    @Argument firstName: String,
    @Argument lastName: String,
    @Argument email: String,
    @Argument username: String,
    @Argument password: String
  ): AppUser? {
    val newUser =
      AppUser(firstName = firstName, lastName = lastName, email = email, username = username, password = password)
    return userRepository.save(newUser)
  }

  @MutationMapping
  fun updateUser(
    @Argument id: UUID, @Argument firstName: String?, @Argument lastName: String?, @Argument password: String?
  ): AppUser? {
    val user = userRepository.findById(id).orElse(null) ?: return null

    firstName?.let { user.firstName = it }
    lastName?.let { user.lastName = it }
    password?.let { user.password = it }

    return userRepository.save(user)
  }

  @MutationMapping
  fun deleteUser(@Argument id: UUID): Unit {
    return userRepository.deleteById(id)
  }

  @QueryMapping
  fun login(@Argument email: String?, @Argument username: String?, @Argument password: String): Boolean {
    if (email.isNullOrEmpty() && username.isNullOrEmpty())
      return false
    val user = if (!email.isNullOrEmpty())
      userRepository.findByEmail(email)
    else
      userRepository.findByUsername(username!!)
    if (user == null || user.password != password)
      return false
    user.isLoggedIn = true
    userRepository.save(user)
    return true
  }

  @QueryMapping
  fun logout(@Argument email: String): Boolean {
    val user = userRepository.findByEmail(email) ?: return false
    user.isLoggedIn = false
    userRepository.save(user)
    return true
  }
}

data class UserDto(
  val id: UUID, val firstName: String?, val lastName: String?
)