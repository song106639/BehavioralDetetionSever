package com.song.behavioraldetetionsever.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class R<T>{
    private int code;
    private boolean state;
    private T data;
}
