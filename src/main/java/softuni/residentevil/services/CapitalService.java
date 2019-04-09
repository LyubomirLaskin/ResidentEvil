package softuni.residentevil.services;

import softuni.residentevil.domain.models.serviceModels.CapitalServiceModel;

import java.util.List;

public interface CapitalService {

    List<CapitalServiceModel> findAllCapitals();
}
