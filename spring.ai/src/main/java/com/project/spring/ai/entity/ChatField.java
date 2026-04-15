package com.project.spring.ai.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatField {
    private String title;
    private String category;
    private String subcategory;
    private String experienceLevel;
}
