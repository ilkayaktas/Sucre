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

package edu.metu.sucre.controller.api;


import edu.metu.sucre.model.api.Channel;
import edu.metu.sucre.model.api.FBUser;
import edu.metu.sucre.model.api.User;
import io.reactivex.Observable;

/**
 * Created by iaktas on 24/04/17.
 */

public interface IApiHelper {

    boolean login(User user, String userId, String token, String expireDate);

    boolean logout(String userId, String token);

    Observable<FBUser> getFacebookProfile();

    Observable<User> getUser(String userId);

    Observable<User> addUser(User user);

    Observable<Channel> getUserChannels(String userId);

    Observable<Channel> addChannel(Channel channel, String userToken);

    Observable<Channel> updateChannel(String id, String memberToken);

}
