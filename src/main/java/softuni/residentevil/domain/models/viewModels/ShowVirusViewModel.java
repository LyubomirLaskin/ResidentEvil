package softuni.residentevil.domain.models.viewModels;

import softuni.residentevil.domain.entities.enums.Magnitude;

import java.util.Date;

public class ShowVirusViewModel {

    private String id;
    private String name;
    private Magnitude magnitude;
    private Date releasedOn;

    public ShowVirusViewModel() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Magnitude getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Magnitude magnitude) {
        this.magnitude = magnitude;
    }

    public Date getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(Date releasedOn) {
        this.releasedOn = releasedOn;
    }
}
