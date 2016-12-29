package net.freedomserg.restaurant.userLayer.web.security;

import net.freedomserg.restaurant.core.model.entity.User;
import net.freedomserg.restaurant.core.model.entity.UserProfile;
import net.freedomserg.restaurant.core.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class RestaurantUserDetailsService implements UserDetailsService {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String ssoId) throws UsernameNotFoundException {
        User user = userService.getBySso(ssoId);
        if (user == null){
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getSsoId(), user.getPassword(),
                user.getState().equals("Active"), true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(UserProfile userProfile : user.getUserProfiles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+userProfile.getType()));
        }
        return authorities;
    }
}
