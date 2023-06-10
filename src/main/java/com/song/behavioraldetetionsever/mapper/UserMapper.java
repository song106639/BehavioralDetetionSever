package com.song.behavioraldetetionsever.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.song.behavioraldetetionsever.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
