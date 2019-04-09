package softuni.residentevil.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.residentevil.domain.entities.Role;
import softuni.residentevil.domain.entities.User;
import softuni.residentevil.domain.models.serviceModels.UserServiceModel;
import softuni.residentevil.repositories.RoleRepository;
import softuni.residentevil.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }


    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        this.seedRolesInDB();


        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(encoder.encode(userServiceModel.getPassword()));
        this.giveRolesToUser(user);

        try {
            this.userRepository.saveAndFlush(user);

            return true;

        }catch (Exception e){
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public List<UserServiceModel> getAllUsers() {

        return this.userRepository
                .findAll()
                .stream()
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public void setUserRole(String userId, String userRole) {

        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("No user found with this ID"));

        user.getAuthorities().clear();

        switch (userRole){
            case "USER":
                user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
                break;
            case "MODERATOR":
                user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
                user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_MODERATOR"));
                break;
            case "ADMIN":
                user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
                user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_MODERATOR"));
                user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_ADMIN"));
                break;
        }
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private void seedRolesInDB(){

        if (this.roleRepository.count() == 0){

            Role root = new Role();
            root.setAuthority("ROLE_ROOT");

            Role admin = new Role();
            admin.setAuthority("ROLE_ADMIN");

            Role moderator = new Role();
            moderator.setAuthority("ROLE_MODERATOR");

            Role user = new Role();
            user.setAuthority("ROLE_USER");

            this.roleRepository.saveAndFlush(root);
            this.roleRepository.saveAndFlush(admin);
            this.roleRepository.saveAndFlush(moderator);
            this.roleRepository.saveAndFlush(user);
        }
    }

    private void giveRolesToUser(User user){

        if (this.userRepository.count() == 0){
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_ROOT"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_ADMIN"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_MODERATOR"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
        }else{
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
        }
    }
}
