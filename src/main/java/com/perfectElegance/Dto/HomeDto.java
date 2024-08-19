package com.perfectElegance.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class HomeDto {
    private Integer id;
    private String name;
    private Date DOB;
    private String relegion;
    private String education;
    private String profession;
    private String homeLocation;
    private boolean isOnline;
    private String image;

}
