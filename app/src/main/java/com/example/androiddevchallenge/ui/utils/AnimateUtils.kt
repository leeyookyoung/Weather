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

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.BoxState
import com.example.androiddevchallenge.MyPosition

enum class TransitionType {
    IN_AND_OUT1, IN_AND_OUT2, LEFT_TO_RIGHT1, LEFT_TO_RIGHT2, UP_DOWN_SLOW, UP_DOWN1, UP_DOWN2, UP_DOWN3, UP_DOWN4, ALPHA1, ALPHA2, CIRCULAR
}

@Composable
fun getInfiniteTransitionValueMap(): Map<TransitionType, Float> {
    val infiniteTransition = rememberInfiniteTransition()
    return mapOf(
        TransitionType.IN_AND_OUT1 to getTransitionValue(infiniteTransition, TransitionType.IN_AND_OUT1),
        TransitionType.IN_AND_OUT2 to getTransitionValue(infiniteTransition, TransitionType.IN_AND_OUT2),
        TransitionType.LEFT_TO_RIGHT1 to getTransitionValue(infiniteTransition, TransitionType.LEFT_TO_RIGHT1),
        TransitionType.LEFT_TO_RIGHT2 to getTransitionValue(infiniteTransition, TransitionType.LEFT_TO_RIGHT2),
        TransitionType.UP_DOWN_SLOW to getTransitionValue(infiniteTransition, TransitionType.UP_DOWN_SLOW),
        TransitionType.UP_DOWN1 to getTransitionValue(infiniteTransition, TransitionType.UP_DOWN1),
        TransitionType.UP_DOWN2 to getTransitionValue(infiniteTransition, TransitionType.UP_DOWN2),
        TransitionType.UP_DOWN3 to getTransitionValue(infiniteTransition, TransitionType.UP_DOWN3),
        TransitionType.UP_DOWN4 to getTransitionValue(infiniteTransition, TransitionType.UP_DOWN4),
        TransitionType.ALPHA1 to getTransitionValue(infiniteTransition, TransitionType.ALPHA1),
        TransitionType.CIRCULAR to getTransitionValue(infiniteTransition, TransitionType.CIRCULAR)
    )
}

@Composable
fun getTransitionValue(infiniteTransition: InfiniteTransition, type: TransitionType): Float {
    @Composable
    fun transition(initialValue: Float, targetValue: Float, durationMillis: Int, easing: Easing, repeatMode: RepeatMode): Float {
        val value by infiniteTransition.animateFloat(
            initialValue = initialValue,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis, easing = easing),
                repeatMode = repeatMode
            )
        )
        return value
    }

    return when (type) {
        TransitionType.IN_AND_OUT1 -> transition(initialValue = 0f, targetValue = 20f, durationMillis = 1000, easing = FastOutLinearInEasing, repeatMode = RepeatMode.Reverse)
        TransitionType.IN_AND_OUT2 -> transition(initialValue = 0f, targetValue = 20f, durationMillis = 2000, easing = FastOutSlowInEasing, repeatMode = RepeatMode.Reverse)
        TransitionType.LEFT_TO_RIGHT1 -> transition(initialValue = 30f, targetValue = 0f, durationMillis = 1500, easing = LinearEasing, repeatMode = RepeatMode.Reverse)
        TransitionType.LEFT_TO_RIGHT2 -> transition(initialValue = 20f, targetValue = 0f, durationMillis = 1500, easing = LinearOutSlowInEasing, repeatMode = RepeatMode.Reverse)
        TransitionType.UP_DOWN_SLOW -> transition(initialValue = 0f, targetValue = 30f, durationMillis = 1500, easing = LinearOutSlowInEasing, repeatMode = RepeatMode.Reverse)
        TransitionType.UP_DOWN1 -> transition(initialValue = 40f, targetValue = 100f, durationMillis = 1000, easing = LinearOutSlowInEasing, repeatMode = RepeatMode.Restart)
        TransitionType.UP_DOWN2 -> transition(initialValue = 50f, targetValue = 100f, durationMillis = 1000, easing = LinearEasing, repeatMode = RepeatMode.Restart)
        TransitionType.UP_DOWN3 -> transition(initialValue = 60f, targetValue = 100f, durationMillis = 1000, easing = FastOutSlowInEasing, repeatMode = RepeatMode.Restart)
        TransitionType.UP_DOWN4 -> transition(initialValue = 40f, targetValue = 100f, durationMillis = 1000, easing = LinearEasing, repeatMode = RepeatMode.Restart)
        TransitionType.ALPHA1 -> transition(initialValue = 1f, targetValue = 0f, durationMillis = 1000, easing = LinearEasing, repeatMode = RepeatMode.Restart)
        TransitionType.ALPHA2 -> transition(initialValue = 1f, targetValue = 0f, durationMillis = 500, easing = FastOutSlowInEasing, repeatMode = RepeatMode.Restart)
        TransitionType.CIRCULAR -> transition(initialValue = 0f, targetValue = 360f, durationMillis = 4000, easing = LinearEasing, repeatMode = RepeatMode.Reverse)

        else -> 0.0f
    }
}

class TransitionData(
    alpha: State<Float>,
    size: State<Dp>,
    paddingX: State<Dp>,
    paddingY: State<Dp>

) {
    val alpha by alpha
    val size by size
    val paddingX by paddingX
    val paddingY by paddingY
}

@Composable
fun updateTransitionData(boxState: BoxState, myPosition: MyPosition, maxWidth: Dp): TransitionData {
    val transition = updateTransition(boxState)
    val alpha = transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 1000,
                delayMillis = 50
            )
        }
    ) { state ->
        when (state) {
            BoxState.Collapsed -> 0f
            BoxState.Expanded -> 1f
        }
    }
    val size = transition.animateDp(
        transitionSpec = {
            tween(
                durationMillis = 1000,
                delayMillis = 50
            )
        }
    ) { state ->
        when (state) {
            BoxState.Collapsed -> 0.dp
            BoxState.Expanded -> maxWidth - 32.dp
        }
    }
    val paddingX = transition.animateDp(
        transitionSpec = {
            tween(
                durationMillis = 1000,
                delayMillis = 50
            )
        }
    ) { state ->
        when (state) {
            BoxState.Collapsed -> myPosition.padding.x
            BoxState.Expanded -> 16.dp
        }
    }
    val paddingY = transition.animateDp(
        transitionSpec = {
            tween(
                durationMillis = 1000,
                delayMillis = 50
            )
        }
    ) { state ->
        when (state) {
            BoxState.Collapsed -> myPosition.padding.y
            BoxState.Expanded -> 16.dp
        }
    }

    return remember(transition) { TransitionData(alpha, size, paddingX, paddingY) }
}
