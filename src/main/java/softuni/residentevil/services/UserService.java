package softuni.residentevil.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import softuni.residentevil.domain.models.serviceModels.UserServiceModel;

import java.util.List;

public interface UserService extends UserDetailsService {

    boolean registerUser(UserServiceModel userServiceModel);

    List<UserServiceModel> getAllUsers();

    void setUserRole(String userId, String userRole);
}
