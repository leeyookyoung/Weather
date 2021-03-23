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

import android.graphics.Paint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.example.androiddevchallenge.R

class CanvasUtils {
    companion object {
        val paint = Paint()
        val paint2 = Paint()
        init {
            paint.textSize = 40f
            paint.color = 0xff000000.toInt()

            paint2.textSize = 55f
            paint2.isFakeBoldText = true
            paint2.color = 0xff000000.toInt()
        }
    }
}

@Composable
fun getImageBitmap(id: Int): ImageBitmap = ImageBitmap.imageResource(id)

@Composable
fun getImageBitmaps(): Map<WeatherType, ImageBitmap> {
    return mapOf(
        WeatherType.SUN to getImageBitmap(R.drawable.sun),
        WeatherType.RAIN to getImageBitmap(
            R.drawable.rain
        ),
        WeatherType.DROP1 to getImageBitmap(R.drawable.water_drops),
        WeatherType.DROP2 to getImageBitmap(
            R.drawable.drop
        ),
        WeatherType.WINDY to getImageBitmap(R.drawable.wind), WeatherType.CLOUDY to getImageBitmap(R.drawable.clouds),
        WeatherType.SNOW to getImageBitmap(R.drawable.snowflake),
        WeatherType.CLOUDY_SUNNY to getImageBitmap(
            R.drawable.cloudy_sunny
        ),
    )
}
