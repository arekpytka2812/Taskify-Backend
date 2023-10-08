package com.pytka.taskifybackend.core.service.impl;

import com.pytka.taskifybackend.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.core.model.UserSettingsEntity;
import com.pytka.taskifybackend.core.repository.UserSettingsRepository;
import com.pytka.taskifybackend.core.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSettingsServiceImpl implements UserSettingsService {

    private final UserSettingsRepository userSettingsRepository;

    @Override
    public void createUserSettingsRecordByUser(Long userID){

        UserSettingsEntity userSettings = UserSettingsEntity.builder()
                .userID(userID)
                .build();

        try{
            this.userSettingsRepository.save(userSettings);
        }
        catch(DataAccessException e){
            throw new DataCouldNotBeSavedException(UserSettingsEntity.class, e);
        }
    }
}
