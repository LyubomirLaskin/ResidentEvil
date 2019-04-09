package softuni.residentevil.domain.models.viewModels;

import org.springframework.format.annotation.DateTimeFormat;
import softuni.residentevil.config.validators.PastOrPresent;
import softuni.residentevil.domain.entities.enums.Magnitude;
import softuni.residentevil.domain.entities.enums.Mutation;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

public class EditVirusViewModel {

    private String name;
    private String description;
    private String sideEffects;
    private String creator;
    private boolean isDeadly;
    private boolean isCurable;
    private Mutation mutation;
    private Integer turnoverRate;
    private Integer hoursUntilTurn;
    private Magnitude magnitude;
    private Date releasedOn;
    private List<String> capitals;

    public EditVirusViewModel() {
    }


    @NotNull
    @Size(min = 3, max = 10, message = "Invalid name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(min = 5, max = 100, message = "Invalid description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Invalid side effects")
    @NotEmpty(message = "Invalid side effects")
    @Size(max = 50, message = "Invalid side effects")
    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    @Pattern(regexp = "^[Cc]orp$", message = "Invalid creator")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isDeadly() {
        return isDeadly;
    }

    public void setDeadly(boolean deadly) {
        isDeadly = deadly;
    }

    public boolean isCurable() {
        return isCurable;
    }

    public void setCurable(boolean curable) {
        isCurable = curable;
    }

    @NotNull(message = "Mutation can not be null")
    public Mutation getMutation() {
        return mutation;
    }

    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }

    @NotNull(message = "Invalid turnover rate")
    @Max(value = 100, message = "Invalid turnover rate")
    public Integer getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(Integer turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    @NotNull(message = "Invalid hours until turn")
    @Min(value = 1, message = "Invalid hours until turn")
    @Max(value = 12, message = "Invalid hours until turn")
    public Integer getHoursUntilTurn() {
        return hoursUntilTurn;
    }

    public void setHoursUntilTurn(Integer hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    @NotNull
    public Magnitude getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Magnitude magnitude) {
        this.magnitude = magnitude;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Invalid date")
    public Date getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(Date releasedOn) {
        this.releasedOn = releasedOn;
    }

    @NotNull(message = "You must select capitals")
    @NotEmpty(message = "You must select capitals")
    public List<String> getCapitals() {
        return capitals;
    }

    public void setCapitals(List<String> capitals) {
        this.capitals = capitals;
    }
}
