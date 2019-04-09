package softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.residentevil.domain.entities.Capital;
import softuni.residentevil.domain.models.bindingModels.VirusAddBindingModel;
import softuni.residentevil.domain.models.serviceModels.VirusServiceModel;
import softuni.residentevil.domain.models.viewModels.CapitalViewModel;
import softuni.residentevil.domain.models.viewModels.EditVirusViewModel;
import softuni.residentevil.domain.models.viewModels.ShowVirusViewModel;
import softuni.residentevil.services.CapitalService;
import softuni.residentevil.services.VirusService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/viruses")
public class VirusController extends BaseController{

    private final CapitalService capitalService;
    private final VirusService virusService;
    private final ModelMapper modelMapper;

    @Autowired
    public VirusController(CapitalService capitalService, VirusService virusService, ModelMapper modelMapper) {
        this.capitalService = capitalService;
        this.virusService = virusService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView addVirus(ModelAndView modelAndView,
                                 @ModelAttribute(name = "bindingModel") VirusAddBindingModel bindingModel){

        modelAndView.addObject("bindingModel", bindingModel)
                .addObject("capitals", capitals());

        return super.view("add-virus", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView confirmVirus(@Valid @ModelAttribute(name = "bindingModel") VirusAddBindingModel bindingModel,
                                     BindingResult bindingResult, ModelAndView modelAndView){

        if (bindingResult.hasErrors() || bindingModel == null) {
            modelAndView.addObject("bindingModel", bindingModel)
                    .addObject("capitals", capitals());

            return super.view("add-virus", modelAndView);
        }

        VirusServiceModel virusServiceModel = this.modelMapper.map(bindingModel, VirusServiceModel.class);

        if (!this.virusService.saveEntity(virusServiceModel)){
            throw new IllegalArgumentException("No such virus");
        }

        return redirect("/viruses/");
    }

    @GetMapping("/")
    public ModelAndView showVirus(){

        return super.view("show-virus");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editVirus(ModelAndView modelAndView,
                                  @ModelAttribute(name = "editViewModel") EditVirusViewModel editVirusViewModel,
                                  @PathVariable("id") String id,
                                  HttpSession session){

        editVirusViewModel = this.modelMapper.map(this.virusService.getVirusById(id), EditVirusViewModel.class);

        if (editVirusViewModel == null){
            throw new IllegalArgumentException("No such virus");
        }

        modelAndView.addObject("editViewModel", editVirusViewModel)
                .addObject("capitals", capitals());
        session.setAttribute("id", id);

        return super.view("edit-virus", modelAndView);

    }

    @PostMapping("/edit/{id}")
    public ModelAndView confirmEditVirus(ModelAndView modelAndView,
                                         @Valid @ModelAttribute("editViewModel") EditVirusViewModel editVirusViewModel,
                                         BindingResult bindingResult,
                                         @PathVariable("id") String id){

        if (bindingResult.hasErrors() || editVirusViewModel == null){
            modelAndView.addObject("editViewModel", editVirusViewModel)
            .addObject("capitals", capitals());

            return super.view("edit-virus", modelAndView);
        }

        VirusServiceModel virusServiceModel = this.modelMapper.map(editVirusViewModel, VirusServiceModel.class);

        if (!this.virusService.updateEntityById(id, virusServiceModel)){

            throw new IllegalArgumentException("No such virus");
        }

        return redirect("/viruses/");

    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteVirus(@PathVariable("id") String id, ModelAndView modelAndView){

        this.virusService.deleteEntityById(id);

        return redirect("/viruses/");
    }

    @GetMapping(value = "/showViruses", produces = "application/json")
    @ResponseBody
    public Object fetchVirusesData(){

        return this.virusService
                .getAllViruses()
                .stream()
                .map(virus -> this.modelMapper.map(virus, ShowVirusViewModel.class))
                .collect(Collectors.toList());

    }
    @GetMapping(value = "/showCapitals", produces = "application/json")
    @ResponseBody
    public Object fetchCapitalsData(){

        List<CapitalViewModel> capitals = capitals();

        return capitals;
    }







    private List<CapitalViewModel> capitals() {
        return this.capitalService
                .findAllCapitals()
                .stream()
                .map(capital -> this.modelMapper.map(capital, CapitalViewModel.class))
                .collect(Collectors.toList());
    }

}
