package com.example.Excel_Spring_Task2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    private String name;
    private String email;
    private String data; // New field for 'Data'
    private List<String> fileNames; // Updated to handle multiple files
}