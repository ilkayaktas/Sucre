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

package edu.metu.sucre.di.components;


import dagger.Component;
import edu.metu.sucre.di.annotations.PerActivity;
import edu.metu.sucre.di.modules.ActivityModule;
import edu.metu.sucre.views.activities.base.BaseActivity;
import edu.metu.sucre.views.activities.channels.HealthChannelsActivity;
import edu.metu.sucre.views.activities.home.MainActivity;
import edu.metu.sucre.views.activities.login.LoginActivity;
import edu.metu.sucre.views.activities.messages.HealthChannelMessageActivity;
import edu.metu.sucre.views.activities.splash.SplashScreenActivity;
import edu.metu.sucre.views.activities.sugarlevel.SugarLevelActivity;
import edu.metu.sucre.views.fragments.listfragment.ListFragment;
import edu.metu.sucre.views.fragments.statisticsfragment.StatisticsFragment;

/**
 * Created by iaktas on 24/04/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);

    void inject(MainActivity activity);
    
    void inject(SplashScreenActivity activity);
    
    void inject(SugarLevelActivity activity);

    void inject(ListFragment fragment);

    void inject(StatisticsFragment fragment);

    void inject(LoginActivity loginActivity);

    void inject(HealthChannelMessageActivity healthChannelMessageActivity);

    void inject(HealthChannelsActivity healthChannelsActivity);
}
