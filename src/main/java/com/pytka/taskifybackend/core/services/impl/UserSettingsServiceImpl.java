package com.pytka.taskifybackend.core.services.impl;

import com.pytka.taskifybackend.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.core.models.UserSettingsEntity;
import com.pytka.taskifybackend.core.repositories.UserSettingsRepository;
import com.pytka.taskifybackend.core.services.UserSettingsService;
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
