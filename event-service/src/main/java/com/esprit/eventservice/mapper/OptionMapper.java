package com.esprit.eventservice.mapper;

import com.esprit.eventservice.dto.OptionRequest;
import com.esprit.eventservice.dto.OptionResponse;
import com.esprit.eventservice.entity.Option;

public final class OptionMapper {

    private OptionMapper() {}

    // L'événement parent est rattaché dans le service, pas ici
    public static Option toEntity(OptionRequest request) {
        Option option = new Option();
        option.setType(request.type());
        option.setPrix(request.prix());
        option.setPrestataireId(request.prestataireId());
        return option;
    }

    public static OptionResponse toResponse(Option option) {
        return new OptionResponse(
                option.getId(),
                option.getType(),
                option.getPrix(),
                option.getPrestataireId()
        );
    }
}