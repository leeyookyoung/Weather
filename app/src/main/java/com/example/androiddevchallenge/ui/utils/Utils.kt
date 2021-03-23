/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R

val basicMargin = 8.dp
val intervalMargin = 16.dp
val doubleIntervalMargin = 32.dp

data class WeeklyWeatherInfo(val date: String, val date2: String, val forecast: String, val morningRid: Int, val dayRid: Int, val morningTemp: String, val dayTemp: String, val predictPercentage: String)
val weeklyWeatherInfoList = listOf(
    WeeklyWeatherInfo("Tomorrow", "3.24", "Sunny and Cloudy", R.drawable.sun, R.drawable.cloudy_sunny, "13° / 55.4℉", "14° / 57.2℉", "0% / 20%"),
    WeeklyWeatherInfo("Thursday", "3.25", "Cloudy and Rain", R.drawable.clouds, R.drawable.rain, "13° / 55.4℉", "14° / 57.2℉", "0% / 20%"),
    WeeklyWeatherInfo("Friday", "3.26", "Rain and Cloudy", R.drawable.rain, R.drawable.clouds, "13° / 55.4℉", "14° / 57.2℉", "0% / 20%"),
    WeeklyWeatherInfo("Saturday", "3.27", "Rain and Storm", R.drawable.rain, R.drawable.storm, "13° / 55.4℉", "14° / 57.2℉", "0% / 20%"),
    WeeklyWeatherInfo("Sunday", "3.28", "Snow and Rain", R.drawable.snowflake, R.drawable.rain, "13° / 55.4℉", "14° / 57.2℉", "0% / 20%"),
    WeeklyWeatherInfo("Monday", "3.29", "Wind and Wind", R.drawable.wind, R.drawable.wind, "13° / 55.4℉", "14° / 57.2℉", "0% / 20%"),
    WeeklyWeatherInfo("Tuesday", "3.30", "Sunny and Sunny", R.drawable.sun, R.drawable.sun, "13° / 55.4℉", "14° / 57.2℉", "0% / 20%"),
    WeeklyWeatherInfo("Wednesday", "3.31", "Sunny and Cloudy", R.drawable.sun, R.drawable.cloudy_sunny, "13° / 55.4℉", "14° / 57.2℉", "0% / 20%"),
)

enum class LocalName {
    Seoul, Chuncheon, Baengnyeong, Suwon, Cheongju, Gangneung, Jeonju,
    Daejeon, Andong, UlleungDokdo, Mokpo, Gwangju, Daegu, Yeosu, Busan,
    Ulsan, Jeju
}

enum class WeatherType {
    SUN, CLOUDY, CLOUDY_SUNNY, RAIN, DROP1, DROP2, SNOW, WINDY
}

data class WeatherInfo(
    val temperature: String,
    val yesterdayTemp: String = "4° / 39.2℉ ↑ than yesterday",
    val conditions: String = "Clear",
    val humidity: String = "55%",
    val windPath: String = "southwester 5m/s",
    val effectiveTemperature: String = "11°",
    val fineDust: String = "Normal",
    val ultraFineDust: String = "Good",
    val ultraviolet: String = "High"
)

data class WeatherDrawInfo(
    val localName: LocalName,
    val weatherType: WeatherType,
    val name: String,
    val info: WeatherInfo,
    val x: Dp,
    val y: Dp,
    val dstHeight: Dp,
    val dstWidth: Dp
)

val localWeatherDrawList = listOf(
    WeatherDrawInfo(LocalName.Baengnyeong, WeatherType.WINDY, "Baengnyeong", WeatherInfo("13°/ 55.4℉"), 60.dp, 90.dp, 40.dp, 40.dp),
    WeatherDrawInfo(LocalName.Seoul, WeatherType.SUN, "Seoul", WeatherInfo("13°/ 55.4℉"), 150.dp, 60.dp, 40.dp, 40.dp),
    WeatherDrawInfo(LocalName.Chuncheon, WeatherType.RAIN, "Chuncheon", WeatherInfo("13°/ 55.4℉"), 230.dp, 30.dp, 40.dp, 40.dp),
    WeatherDrawInfo(LocalName.Suwon, WeatherType.CLOUDY, "Suwon", WeatherInfo("14°/ 57.2℉"), 150.dp, 110.dp, 40.dp, 40.dp),
    WeatherDrawInfo(LocalName.Cheongju, WeatherType.CLOUDY_SUNNY, "Cheongju", WeatherInfo("15°/ 59℉"), 210.dp, 80.dp, 40.dp, 40.dp),
    WeatherDrawInfo(LocalName.Gangneung, WeatherType.SNOW, "Gangneung", WeatherInfo("5°/ 41℉"), 260.dp, 50.dp, 30.dp, 30.dp),
    WeatherDrawInfo(LocalName.Jeonju, WeatherType.WINDY, "Jeonju", WeatherInfo("12°/ 53.6℉"), 130.dp, 180.dp, 40.dp, 40.dp),
    WeatherDrawInfo(LocalName.Daejeon, WeatherType.SUN, "Daejeon", WeatherInfo("12°/ 53.6℉"), 190.dp, 165.dp, 40.dp, 40.dp),
    WeatherDrawInfo(LocalName.Andong, WeatherType.CLOUDY_SUNNY, "Andong", WeatherInfo("14°/ 57.2℉"), 270.dp, 130.dp, 40.dp, 40.dp),
    WeatherDrawInfo(LocalName.UlleungDokdo, WeatherType.SNOW, "UlleungDokdo", WeatherInfo("3°/ 37.4℉"), 300.dp, 90.dp, 30.dp, 30.dp),
    WeatherDrawInfo(LocalName.Mokpo, WeatherType.CLOUDY, "Mokpo", WeatherInfo("12°/ 53.6℉"), 95.dp, 250.dp, 40.dp, 40.dp),
    WeatherDrawInfo(LocalName.Gwangju, WeatherType.CLOUDY_SUNNY, "Gwangju", WeatherInfo("12°/ 53.6℉"), 150.dp, 240.dp, 40.dp, 40.dp),
    WeatherDrawInfo(LocalName.Daegu, WeatherType.CLOUDY_SUNNY, "Daegu", WeatherInfo("12°/ 53.6℉"), 250.dp, 200.dp, 40.dp, 40.dp),
    WeatherDrawInfo(LocalName.Yeosu, WeatherType.CLOUDY, "Yeosu", WeatherInfo("12°/ 53.6℉"), 200.dp, 250.dp, 40.dp, 40.dp),
    WeatherDrawInfo(LocalName.Busan, WeatherType.RAIN, "Busan", WeatherInfo("12°/ 53.6℉"), 270.dp, 270.dp, 40.dp, 40.dp),
    WeatherDrawInfo(LocalName.Ulsan, WeatherType.RAIN, "Ulsan", WeatherInfo("12°/ 53.6℉"), 290.dp, 220.dp, 40.dp, 40.dp),
    WeatherDrawInfo(LocalName.Jeju, WeatherType.WINDY, "Jeju", WeatherInfo("13°/ 55.4℉"), 120.dp, 340.dp, 40.dp, 40.dp),
)
