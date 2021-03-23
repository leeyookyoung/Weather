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
package com.example.androiddevchallenge

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.amber100
import com.example.androiddevchallenge.ui.theme.amber900
import com.example.androiddevchallenge.ui.theme.lightBlue100
import com.example.androiddevchallenge.ui.theme.lightBlue400
import com.example.androiddevchallenge.ui.theme.lightBlue900
import com.example.androiddevchallenge.ui.theme.lightGreen100
import com.example.androiddevchallenge.ui.theme.lightGreen900
import com.example.androiddevchallenge.ui.utils.CanvasUtils
import com.example.androiddevchallenge.ui.utils.TransitionType
import com.example.androiddevchallenge.ui.utils.WeatherDrawInfo
import com.example.androiddevchallenge.ui.utils.WeatherInfo
import com.example.androiddevchallenge.ui.utils.WeatherType
import com.example.androiddevchallenge.ui.utils.WeeklyWeatherInfo
import com.example.androiddevchallenge.ui.utils.basicMargin
import com.example.androiddevchallenge.ui.utils.doubleIntervalMargin
import com.example.androiddevchallenge.ui.utils.getImageBitmaps
import com.example.androiddevchallenge.ui.utils.getInfiniteTransitionValueMap
import com.example.androiddevchallenge.ui.utils.intervalMargin
import com.example.androiddevchallenge.ui.utils.localWeatherDrawList
import com.example.androiddevchallenge.ui.utils.updateTransitionData
import com.example.androiddevchallenge.ui.utils.weeklyWeatherInfoList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun WeatherScreen(darkMode: Boolean) {
    var expandedWeatherDetail by remember { mutableStateOf(false) }
    val composableScope = rememberCoroutineScope()
    val myPosition by remember { mutableStateOf(MyPosition(DpOffset(0.dp, 0.dp))) }
    var myWeatherDrawInfo by remember { mutableStateOf(localWeatherDrawList[0]) }
    val interactionSource = remember { MutableInteractionSource() }
    var screenMaxWidth: Dp
    val infiniteTransitionValueMap = getInfiniteTransitionValueMap()

    BoxWithConstraints {
        screenMaxWidth = maxWidth
        Surface(
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .fillMaxSize()
        ) {
            ConstraintLayout {
                val (title, title2, map, canvas, bottomColumn) = createRefs()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .constrainAs(title) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Column(modifier = Modifier.padding(basicMargin)) {
                        Text(
                            text = "",
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
                Surface(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(top = basicMargin)
                        .fillMaxWidth()
                        .height(400.dp)
                        .constrainAs(map) {
                            top.linkTo(title.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.korea_map),
                        contentDescription = "background of Korea map's",
                        contentScale = ContentScale.Fit,
                    )
                }

                ConstraintLayout(
                    modifier = Modifier
                        .padding(start = intervalMargin, end = intervalMargin)
                        .fillMaxWidth()
                        .constrainAs(title2) {
                            top.linkTo(title.bottom)
                            start.linkTo(parent.start)
                        }
                ) {
                    val (text1, text2) = createRefs()
                    Text(
                        text = "Korea's Today Weather",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier
                            .constrainAs(text1) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            }
                    )
                    Text(
                        text = "Mon 3.24.2021",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .constrainAs(text2) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            }
                    )
                }

                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .padding(top = basicMargin)
                        .constrainAs(canvas) {
                            top.linkTo(title.bottom)
                            start.linkTo(map.start)
                            end.linkTo(map.end)
                            bottom.linkTo(map.bottom)
                        }
                ) {
                    for (weatherDrawInfo in localWeatherDrawList) {
                        WeatherIconCanvas(
                            weatherDrawInfo = weatherDrawInfo, infiniteTransitionValueMap,
                            (expandedWeatherDetail && myPosition.padding.x - 20.dp == weatherDrawInfo.x && myPosition.padding.y - 20.dp == weatherDrawInfo.y)
                        )

                        Box(
                            modifier = Modifier
                                .padding(start = weatherDrawInfo.x, top = weatherDrawInfo.y)
                                .size(weatherDrawInfo.dstWidth)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                    onClick = {
                                        if (!expandedWeatherDetail) {
                                            myWeatherDrawInfo = weatherDrawInfo
                                            myPosition.padding =
                                                DpOffset(
                                                    weatherDrawInfo.x + 20.dp,
                                                    weatherDrawInfo.y + 20.dp
                                                )

                                            // AnimatingBox has delay to animate MyPosition's position is applied lately
                                            // when user once clicks a weather icon then after the other weather icon
                                            // so I added coroutine to make delay to apply changed myPosition padding values.
                                            composableScope.launch {
                                                delay(550)
                                                expandedWeatherDetail = !expandedWeatherDetail
                                            }
                                        }
                                    }
                                )
                        )
                    }
                    AnimatingBox(
                        if (expandedWeatherDetail) BoxState.Expanded else BoxState.Collapsed,
                        myPosition, screenMaxWidth, myWeatherDrawInfo, infiniteTransitionValueMap, darkMode
                    ) { expandedWeatherDetail = !expandedWeatherDetail }
                }
                Surface(
                    Modifier
                        .padding(0.dp)
                        .background(color = MaterialTheme.colors.background)
                        .constrainAs(bottomColumn) {
                            top.linkTo(canvas.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    elevation = 2.dp
                ) {
                    Row(
                        Modifier
                            .horizontalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.width(intervalMargin))
                        for (weeklyWeatherInfo in weeklyWeatherInfoList) {
                            WeeklyWeatherForecastCard(weeklyWeatherInfo)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeeklyWeatherForecastCard(weeklyWeatherInfo: WeeklyWeatherInfo) {
    Card(
        modifier = Modifier
            .padding(top = intervalMargin, bottom = intervalMargin, end = intervalMargin)
            .width(150.dp)
            .height(200.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(intervalMargin))
            weeklyWeatherInfo.apply {
                Text(
                    text = date,
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = date2,
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.height(basicMargin))
                Row {
                    Image(
                        painter = painterResource(id = morningRid),
                        contentDescription = forecast,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.width(basicMargin))
                    Image(
                        painter = painterResource(id = dayRid),
                        contentDescription = forecast,
                        modifier = Modifier.size(50.dp)
                    )
                }
                Spacer(modifier = Modifier.height(basicMargin))
                Column {
                    Text(text = morningTemp, style = MaterialTheme.typography.subtitle2)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = dayTemp, style = MaterialTheme.typography.subtitle2)
                    Spacer(modifier = Modifier.height(basicMargin))
                    Text(
                        text = predictPercentage,
                        style = MaterialTheme.typography.body2,
                        color = lightBlue400
                    )
                }
            }
        }
    }
}

enum class BoxState { Collapsed, Expanded }
data class MyPosition(var padding: DpOffset)

@Composable
fun AnimatingBox(
    boxState: BoxState,
    myPosition: MyPosition,
    maxWidth: Dp,
    weatherDrawInfo: WeatherDrawInfo,
    infiniteTransitionValueMap: Map<TransitionType, Float>,
    darkMode: Boolean,
    onClick: () -> Unit
) {
    val transitionData = updateTransitionData(boxState, myPosition, maxWidth)
    val interactionSource = remember { MutableInteractionSource() }
    Surface(
        Modifier
            .padding(start = transitionData.paddingX, top = transitionData.paddingY)
            .background(color = if (darkMode) MaterialTheme.colors.background else Color.Transparent)
            .alpha(transitionData.alpha)
    ) {
        Card(
            modifier = Modifier
                .size(transitionData.size)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                ),
            elevation = 4.dp
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (canvas, column) = createRefs()

                Box(
                    modifier = Modifier
                        .padding(top = intervalMargin)
                        .fillMaxWidth()
                        .constrainAs(canvas) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    WeatherDetailCanvas(maxWidth, weatherDrawInfo, infiniteTransitionValueMap)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = intervalMargin)
                        .fillMaxWidth()
                        .constrainAs(column) {
                            top.linkTo(canvas.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    weatherDrawInfo.info.apply {
                        Text(
                            text = "$yesterdayTemp / $conditions",
                            style = MaterialTheme.typography.subtitle1
                        )
                        Spacer(modifier = Modifier.height(basicMargin))
                        Row {
                            DetailCardBasicInfo("Humidity", humidity)
                            DetailCardBasicInfo("", windPath)
                            DetailCardBasicInfo("E-T", effectiveTemperature)
                        }
                        Spacer(modifier = Modifier.height(doubleIntervalMargin))
                        Row {
                            @Composable
                            fun DetailCardAdvancedInfo(title: String, value: String, backgroundColor: Color, color: Color) {
                                Spacer(modifier = Modifier.width(basicMargin))
                                Card(
                                    modifier = Modifier.weight(0.33f),
                                    shape = RoundedCornerShape(4.dp),
                                    elevation = 1.dp,
                                    backgroundColor = backgroundColor
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Spacer(modifier = Modifier.height(basicMargin))
                                        Text(
                                            text = title,
                                            style = MaterialTheme.typography.subtitle2
                                        )
                                        Spacer(modifier = Modifier.height(basicMargin))
                                        Text(
                                            text = value,
                                            color = color,
                                            style = MaterialTheme.typography.subtitle1
                                        )
                                        Spacer(modifier = Modifier.height(basicMargin))
                                    }
                                }
                            }
                            DetailCardAdvancedInfo(title = "Fine Dust", value = fineDust, lightGreen100, lightGreen900)
                            DetailCardAdvancedInfo(title = "Ultra Fine Dust", value = ultraFineDust, lightBlue100, lightBlue900)
                            DetailCardAdvancedInfo(title = "Ultraviolet", value = ultraviolet, amber100, amber900)
                            Spacer(modifier = Modifier.width(basicMargin))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailCardBasicInfo(title: String, value: String) {
    Text(
        text = "$title $value",
        style = MaterialTheme.typography.subtitle2
    )
    Spacer(modifier = Modifier.width(basicMargin))
}

data class DrawInfo(val dstHeight: Float, val dstWidth: Float, val x: Float, val y: Float, val name: String, val info: WeatherInfo, val mulValue: Int = 1)
@Composable
fun WeatherDetailCanvas(maxWidth: Dp, weatherDrawInfo: WeatherDrawInfo, infiniteTransitionValueMap: Map<TransitionType, Float>) {
    val imageBitmaps = getImageBitmaps()
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {

        val dstHeight = weatherDrawInfo.dstHeight.value * 2 * density
        val dstWidth = weatherDrawInfo.dstWidth.value * 2 * density
        val x = maxWidth.value * density / 2 - dstWidth
        val y = dstHeight / 4
        val name = weatherDrawInfo.name
        val info = weatherDrawInfo.info

        drawWeatherDetailIconAndText(this, imageBitmaps, DrawInfo(dstHeight, dstWidth, x, y, name, info, 2), weatherDrawInfo, infiniteTransitionValueMap, false) {
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    name, x + dstWidth + dstWidth / 4, y + dstHeight / 2,
                    CanvasUtils.paint2
                )
                it.nativeCanvas.drawText(
                    info.temperature, x + dstWidth + dstWidth / 4, y + dstHeight / 2 + (dstHeight / 2.5).toInt(),
                    CanvasUtils.paint2
                )
            }
        }
    }
}

fun drawWeatherDetailIconAndText(
    onDraw: DrawScope,
    imageBitmaps: Map<WeatherType, ImageBitmap>,
    drawInfo: DrawInfo,
    weatherDrawInfo: WeatherDrawInfo,
    infiniteTransitionValueMap: Map<TransitionType, Float>,
    clickWeather: Boolean,
    localInfo: () -> Unit
) {
    val (dstHeight, dstWidth, x, y, _, _, mulValue) = drawInfo
    fun drawMain(img: ImageBitmap, intSize: IntSize, mainOffset: IntOffset = IntOffset(x.toInt(), y.toInt())) {
        onDraw.drawImage(
            image = img,
            dstOffset = mainOffset,
            dstSize = intSize,
            alpha = if (clickWeather) infiniteTransitionValueMap[TransitionType.ALPHA1]!! else 1.0f
        )
    }

    when (weatherDrawInfo.weatherType) {
        WeatherType.SUN -> {
            val inOut = infiniteTransitionValueMap[TransitionType.IN_AND_OUT1]!!.toInt()
            drawMain(
                imageBitmaps[WeatherType.SUN]!!,
                IntSize(
                    height = dstHeight.toInt() + inOut,
                    width = dstWidth.toInt() + inOut
                )
            )
        }
        WeatherType.RAIN -> {
            val leftRight = infiniteTransitionValueMap[TransitionType.LEFT_TO_RIGHT2]!!.toInt()
            drawMain(
                imageBitmaps[WeatherType.RAIN]!!,
                IntSize(
                    height = dstHeight.toInt() + leftRight,
                    width = dstWidth.toInt() + + leftRight
                )
            )

            val dropSize = dstHeight.toInt() / 5
            val dropXPosition = dstWidth.toInt() / 4

            fun dropDraw(img: ImageBitmap, psX: Int, psY: Int) {
                onDraw.drawImage(
                    image = img,
                    dstOffset = IntOffset(psX, psY),
                    dstSize = IntSize(height = dropSize, width = dropSize),
                    alpha = infiniteTransitionValueMap[TransitionType.ALPHA1]!!
                )
            }
            val drop2ImageBitmap = imageBitmaps[WeatherType.DROP2]!!
            dropDraw(drop2ImageBitmap, x.toInt(), y.toInt() + infiniteTransitionValueMap[TransitionType.UP_DOWN1]!!.toInt() * mulValue)
            dropDraw(imageBitmaps[WeatherType.DROP1]!!, x.toInt() + dropXPosition, y.toInt() + infiniteTransitionValueMap[TransitionType.UP_DOWN2]!!.toInt() * mulValue)
            dropDraw(drop2ImageBitmap, x.toInt() + dropXPosition * 2, y.toInt() + infiniteTransitionValueMap[TransitionType.UP_DOWN3]!!.toInt() * mulValue)
            dropDraw(drop2ImageBitmap, x.toInt() + dropXPosition * 3, y.toInt() + infiniteTransitionValueMap[TransitionType.UP_DOWN4]!!.toInt() * mulValue)
        }
        WeatherType.WINDY -> {
            drawMain(
                imageBitmaps[WeatherType.WINDY]!!,
                IntSize(
                    height = dstHeight.toInt() + infiniteTransitionValueMap[TransitionType.IN_AND_OUT1]!!.toInt(),
                    width = dstWidth.toInt() + infiniteTransitionValueMap[TransitionType.LEFT_TO_RIGHT2]!!.toInt()
                ),
                IntOffset(x.toInt() + infiniteTransitionValueMap[TransitionType.LEFT_TO_RIGHT1]!!.toInt(), y.toInt())
            )
        }
        WeatherType.CLOUDY -> {
            drawMain(
                imageBitmaps[WeatherType.CLOUDY]!!, IntSize(height = dstHeight.toInt(), width = dstWidth.toInt()),
                IntOffset(
                    x.toInt() + infiniteTransitionValueMap[TransitionType.LEFT_TO_RIGHT1]!!.toInt(),
                    y.toInt() + infiniteTransitionValueMap[TransitionType.UP_DOWN_SLOW]!!.toInt()
                )
            )
        }
        WeatherType.SNOW -> {
            onDraw.rotate(infiniteTransitionValueMap[TransitionType.CIRCULAR]!!, Offset(x + dstWidth / 2, y + dstWidth / 2)) {
                drawMain(imageBitmaps[WeatherType.SNOW]!!, IntSize(height = dstHeight.toInt(), width = dstWidth.toInt()))
            }
        }
        WeatherType.CLOUDY_SUNNY -> {
            val leftRight = infiniteTransitionValueMap[TransitionType.IN_AND_OUT2]!!.toInt()
            drawMain(
                imageBitmaps[WeatherType.CLOUDY_SUNNY]!!,
                IntSize(
                    height = dstHeight.toInt() + leftRight,
                    width = dstWidth.toInt() + + leftRight
                )
            )
        }
        else -> {
        }
    }
    localInfo()
}

@Composable
fun WeatherIconCanvas(weatherDrawInfo: WeatherDrawInfo, infiniteTransitionValueMap: Map<TransitionType, Float>, clickWeather: Boolean) {
    val imageBitmaps = getImageBitmaps()
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        val x = weatherDrawInfo.x.value * density
        val y = weatherDrawInfo.y.value * density
        val dstHeight = weatherDrawInfo.dstHeight.value * density
        val dstWidth = weatherDrawInfo.dstWidth.value * density
        val name = weatherDrawInfo.name
        val info = weatherDrawInfo.info
        drawWeatherDetailIconAndText(this, imageBitmaps, DrawInfo(dstHeight, dstWidth, x, y, name, info), weatherDrawInfo, infiniteTransitionValueMap, clickWeather) {
            CanvasUtils.paint.alpha = if (clickWeather) (infiniteTransitionValueMap[TransitionType.ALPHA1]!! * 255).toInt() else 255
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    name, x, (y + dstHeight),
                    CanvasUtils.paint
                )
                it.nativeCanvas.drawText(
                    info.temperature, x, (y + dstHeight + (dstHeight / 3)),
                    CanvasUtils.paint
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}
