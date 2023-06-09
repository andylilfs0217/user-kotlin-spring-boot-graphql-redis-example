package com.andylilfs.userkotlinspringbootgraphqlredisexample.repository

import com.andylilfs.userkotlinspringbootgraphqlredisexample.model.AppUser
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CrudRepository<AppUser, UUID> {
  fun findByEmail(email: String): AppUser?
  fun findByUsername(username: String): AppUser?
}