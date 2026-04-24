package com.pluralsight.conference.controller;

import com.pluralsight.conference.model.Speaker;
import com.pluralsight.conference.service.SpeakerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class SpeakerController {

    private SpeakerService speakerService;

    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    //no se ha puesto a nivel de controlador la ruta porque lo mismo controlamos mas recursos con este controller
    @PostMapping("/speaker")
    public Speaker createSpeaker(@RequestBody Speaker speaker) {

        System.out.println("Name: "+speaker.getName());
        return speakerService.create(speaker);
    }

    @GetMapping("/speaker")
    public List<Speaker> getSpeakers() {
        return speakerService.findAll();
    }

    @GetMapping("/speaker/{id}")
    public Speaker getSpeaker(@PathVariable int id) {
        return speakerService.getSpeaker(id);
    }

    @PutMapping("/speaker")
    public Speaker updateSpeaker(@RequestBody Speaker speaker) {

        System.out.println("Name: "+speaker.getName());
        return speakerService.update(speaker);
    }
    
}
