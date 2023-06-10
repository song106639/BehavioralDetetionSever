package com.song.behavioraldetetionsever.bean;

import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
public class User {
    private Integer usrId;
    private String name;
    private String pwd;
    private Integer identity;
    private String email;
}
