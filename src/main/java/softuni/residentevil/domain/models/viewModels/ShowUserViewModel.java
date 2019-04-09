package softuni.residentevil.domain.models.viewModels;

import softuni.residentevil.domain.entities.Role;

public class ShowUserViewModel {

    private String id;
    private String username;
    private String email;
    private String authority;

    public ShowUserViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
