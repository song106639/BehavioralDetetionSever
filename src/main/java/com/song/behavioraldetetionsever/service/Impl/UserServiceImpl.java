package com.song.behavioraldetetionsever.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.behavioraldetetionsever.bean.User;
import com.song.behavioraldetetionsever.mapper.UserMapper;
import com.song.behavioraldetetionsever.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
