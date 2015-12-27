package spring.service;

/**
 * Created by dozac on 15/04/2015.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.StaticConstant;
import spring.dao.UserDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.context.FacesContext;

    @Service
    @Transactional(readOnly=true)
    public class CustomUserDetailsService implements UserDetailsService {

        @Autowired
        private UserDAO userDAO;

        public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

            spring.model.User domainUser = userDAO.getUser(login);
            

            
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userid", domainUser.getId());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("roleid", domainUser.getRole().getId());

            
            
            
            System.out.println( "login user id  : " + domainUser.getId());
            System.out.println( "login user role  : " + domainUser.getRole().getId());
            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

            return new User(
                    domainUser.getLogin(),
                    domainUser.getPassword(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    getAuthorities(domainUser.getRole().getId())
            );
        }

        public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
            List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
            return authList;
        }

        public List<String> getRoles(Integer role) {

            List<String> roles = new ArrayList<String>();

            if (role.intValue() == 1) {
                roles.add("ROLE_MODERATOR");
                roles.add("ROLE_ADMIN");
//                roles.add("ROLE_USER");
            } else if (role.intValue() == 2) {
                roles.add("ROLE_MODERATOR");
                roles.add("ROLE_USER");
            }else if (role.intValue() == 3) {
                roles.add("ROLE_USER");
                roles.add("ROLE_MODERATOR");
            }
            return roles;
        }

        public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            return authorities;
        }

    }

