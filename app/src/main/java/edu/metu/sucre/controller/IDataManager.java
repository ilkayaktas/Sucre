/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package edu.metu.sucre.controller;


import edu.metu.sucre.model.api.*;
import edu.metu.sucre.model.app.BloodSugar;
import io.reactivex.Observable;

import java.util.List;

/**
 * Created by iaktas on 27/01/17.
 */

public interface IDataManager {

    Observable<FBUser> getFacebookProfile();

    String getFCMToken();

    String getUserId();

    Observable<User> getMe();

    void subscribeToTopic(String topic);

    Observable<Channel>  createChannel(String channelName);

    Observable<List<Channel>> getUserChannels();

    List<BloodSugar> getBloodSugar();

    void saveBloodSugar(BloodSugar bloodSugar);

    void deleteBloodSugar(String uuid);

    Observable<User> saveUser(User user);

    Observable<Channel> addUserToChannel(String dialogId, String email);

    Observable<Channel> getChannel(String channelId);

    Observable<User> getUser(String userId);

    Observable<Void> sendMessage(Message message);

    Observable<List<Message>> getMessages(String channelId);

    Observable<HealthData> saveHealthData(HealthData healthData);

    Observable<List<HealthData>> getHealthData(String userId, String healthDataTypeId);

    Observable<BloodSugarData> saveBloodSugarToServer(BloodSugarData bloodSugar);

    Observable<List<BloodSugarData>> getBloodSugarFromServer(String userId, String sugarMeasurementType);
}
