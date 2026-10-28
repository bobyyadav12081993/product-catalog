package com.example.productcatalog.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class GroqPitchResponseDto {
    private List<Choice> choices;
}
