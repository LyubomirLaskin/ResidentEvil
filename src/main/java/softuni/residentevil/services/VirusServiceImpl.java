package softuni.residentevil.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.residentevil.domain.entities.Capital;
import softuni.residentevil.domain.entities.Virus;
import softuni.residentevil.domain.models.serviceModels.VirusServiceModel;
import softuni.residentevil.repositories.CapitalRepository;
import softuni.residentevil.repositories.VirusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VirusServiceImpl implements VirusService {

    private final VirusRepository virusRepository;
    private final CapitalRepository capitalRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VirusServiceImpl(VirusRepository virusRepository, CapitalRepository capitalRepository, ModelMapper modelMapper) {
        this.virusRepository = virusRepository;
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean saveEntity(VirusServiceModel virusServiceModel) {

        Virus virus = this.modelMapper.map(virusServiceModel, Virus.class);

        if (virus == null) {
            return false;
        }

        virus.setCapitals(bindCapitals(virusServiceModel));

        this.virusRepository.saveAndFlush(virus);

        return true;
    }

    @Override
    public boolean updateEntityById(String id, VirusServiceModel virusServiceModel) {

        Virus virus = this.modelMapper.map(virusServiceModel, Virus.class);

        if (virus == null) {
            return false;
        }

        virus.setId(id);
        virus.setCapitals(bindCapitals(virusServiceModel));

        this.virusRepository.saveAndFlush(virus);

        return true;

    }

    @Override
    public List<VirusServiceModel> getAllViruses() {

        return this.virusRepository
                .findAll()
                .stream()
                .map(virus -> this.modelMapper.map(virus, VirusServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public VirusServiceModel getVirusById(String id) {

        Virus virus = this.virusRepository.findById(id).orElse(null);

        if (virus == null) {
            return null;
        }

        return this.modelMapper.map(virus, VirusServiceModel.class);
    }

    @Override
    public boolean deleteEntityById(String id) {

        try{
            this.virusRepository.deleteById(id);

            return true;

        }catch (Exception e){
            e.printStackTrace();

            return false;
        }

    }

    private List<Capital> bindCapitals(VirusServiceModel virusServiceModel){

        List<Capital> capitals = new ArrayList<>();

        virusServiceModel.getCapitals().forEach(capital -> capitals.add(this.capitalRepository.findById(capital).orElse(null)));

        return capitals;

    }


}
