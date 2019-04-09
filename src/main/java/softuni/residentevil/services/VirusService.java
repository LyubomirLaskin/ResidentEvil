package softuni.residentevil.services;

import softuni.residentevil.domain.models.serviceModels.VirusServiceModel;

import java.util.List;

public interface VirusService {

    boolean saveEntity(VirusServiceModel virusServiceModel);

    boolean updateEntityById(String id, VirusServiceModel virusServiceModel);

    List<VirusServiceModel> getAllViruses();

    VirusServiceModel getVirusById(String id);

    boolean deleteEntityById(String id);

}
