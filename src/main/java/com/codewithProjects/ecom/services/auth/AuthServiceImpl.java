package com.codewithProjects.ecom.services.auth;

import com.codewithProjects.ecom.dto.SignupRequest;
import com.codewithProjects.ecom.dto.UserDto;
import com.codewithProjects.ecom.entity.Order;
import com.codewithProjects.ecom.entity.User;
import com.codewithProjects.ecom.enums.OrderStatus;
import com.codewithProjects.ecom.enums.UserRole;
import com.codewithProjects.ecom.repository.OrderRepository;
import com.codewithProjects.ecom.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements com.codewithProjects.ecom.services.auth.AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;




    @Autowired
    private OrderRepository orderRepository;

    public UserDto createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        //user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        User createdUser= userRepository.save(user);

        Order order = new Order();
        order.setAmount(0L);
        order.setTotalAmount(0L);
        order.setDiscount(0L);
        order.setUser(createdUser);
        order.setOrderStatus(OrderStatus.Pending);
        orderRepository.save(order);



        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());
        return userDto;
    }


    public Boolean hasUserWithEmail(String email){
        return userRepository.findFirstByEmail(email).isPresent();
    }
    @PostConstruct
    public void createAdminAccount(){
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if(null == adminAccount){
            User user =new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);

        }
    }
}