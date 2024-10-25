package com.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student
{
    private int rollNo;
    private String name;
    private String mobileNo;
    private String address;
    private String standard;
}
