package com.mao.service.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.io.Serializable

@Service
class JdbcUserDetailsService: UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        println("username: $username")
        return DefaultUserDetails()
    }

    class DefaultUserDetails: UserDetails,Serializable {

        companion object {
            const val serialVersionUID = 12345467890L
        }

        override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
            val list = ArrayList<GrantedAuthority>()
            list.add(GrantedAuthority { "system" })
            return list
        }

        override fun isEnabled(): Boolean {
            return true
        }

        override fun getUsername(): String {
            return "admin"
        }

        override fun isCredentialsNonExpired(): Boolean {
            return true
        }

        override fun getPassword(): String {
            return BCryptPasswordEncoder().encode("admin")
        }

        override fun isAccountNonExpired(): Boolean {
            return true
        }

        override fun isAccountNonLocked(): Boolean {
            return true
        }

    }

}