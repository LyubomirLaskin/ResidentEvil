package softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.residentevil.domain.models.bindingModels.UserRegisterBindingModel;
import softuni.residentevil.domain.models.serviceModels.UserServiceModel;
import softuni.residentevil.domain.models.viewModels.ShowUserViewModel;
import softuni.residentevil.repositories.RoleRepository;
import softuni.residentevil.services.UserService;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController extends BaseController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(){
        return super.view("register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute("model") UserRegisterBindingModel model, ModelAndView modelAndView){
        if (!model.getPassword().equals(model.getConfirmPassword())){
            super.redirect("/register");
        }

        this.userService.registerUser(this.modelMapper.map(model, UserServiceModel.class));

        return super.view("/login", modelAndView);
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(@RequestParam(required = false) String error, Model model){
        if (error != null){
            model.addAttribute("error", "Error");
        }

        return super.view("login");
    }

    @GetMapping("/unauthorized")
    public String unauthorized(){
        return "unauthorized";
    }

    @GetMapping("/admin/showUsers")
    @PreAuthorize("hasRole('ROLE_ROOT') or hasRole('ROLE_ADMIN')")
    public ModelAndView showUsers(ModelAndView modelAndView){

        modelAndView.addObject("users", showUserViewModels());

        return super.view("show-users", modelAndView);
    }

    @PostMapping("/admin/showUsers/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT') or hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id){

        this.userService.setUserRole(id, "ADMIN");

        return super.redirect("/admin/showUsers");
    }

    @PostMapping("/admin/showUsers/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT') or hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable String id){

        this.userService.setUserRole(id, "MODERATOR");

        return super.redirect("/admin/showUsers");
    }

    @PostMapping("/admin/showUsers/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT') or hasRole('ROLE_ADMIN')")
    public ModelAndView setUser(@PathVariable String id){

        this.userService.setUserRole(id, "USER");

        return super.redirect("/admin/showUsers");
    }

    private List<ShowUserViewModel> showUserViewModels(){

        List<ShowUserViewModel> viewModels = new ArrayList<>();
        List<UserServiceModel> serviceModels = this.userService.getAllUsers();

        for (UserServiceModel serviceModel : serviceModels) {

            ShowUserViewModel viewModel = this.modelMapper.map(serviceModel, ShowUserViewModel.class);

            if (serviceModel.getAuthorities().contains(this.roleRepository.findByAuthority("ROLE_ROOT"))){
                viewModel.setAuthority("ROOT");
            }else if (serviceModel.getAuthorities().contains(this.roleRepository.findByAuthority("ROLE_ADMIN"))){
                viewModel.setAuthority("ADMIN");
            }else if(serviceModel.getAuthorities().contains(this.roleRepository.findByAuthority("ROLE_MODERATOR"))){
                viewModel.setAuthority("MODERATOR");
            }else{
                viewModel.setAuthority("USER");
            }
            viewModels.add(viewModel);
        }

        return viewModels;
    }

}
