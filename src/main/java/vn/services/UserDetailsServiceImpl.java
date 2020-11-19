
package vn.services;

import vn.model.User;
import vn.security.UserPrinciple;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    UserService userRepository;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        User user = userRepository.findBySSO(username);
        if(user == null ) {
            throw new UsernameNotFoundException("User Not Found with -> username or email : " + username);
        }
        Hibernate.initialize(user.getUserProfiles());
        return UserPrinciple.build(user);
    }
}
