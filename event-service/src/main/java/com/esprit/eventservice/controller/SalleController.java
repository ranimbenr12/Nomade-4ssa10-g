package com.esprit.eventservice.controller;

import com.esprit.eventservice.dto.SalleRequest;
import com.esprit.eventservice.dto.SalleResponse;
import com.esprit.eventservice.service.SalleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salles")
@RequiredArgsConstructor
public class SalleController {

    private final SalleService salleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalleResponse creer(@Valid @RequestBody SalleRequest request) {
        return salleService.creer(request);
    }

    @GetMapping
    public List<SalleResponse> lister() {
        return salleService.lister();
    }

    @GetMapping("/{id}")
    public SalleResponse trouver(@PathVariable Long id) {
        return salleService.trouver(id);
    }
}