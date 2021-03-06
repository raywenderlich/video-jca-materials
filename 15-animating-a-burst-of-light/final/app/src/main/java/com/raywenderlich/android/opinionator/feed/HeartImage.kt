/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.opinionator.feed

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raywenderlich.android.opinionator.R
import kotlinx.coroutines.delay

enum class HeartAnimationState {
  Hidden,
  Shown
}

@Composable
fun HeartImage(heartAnimationState: MutableState<HeartAnimationState>) {
  val transition = updateTransition(
    targetState = heartAnimationState.value,
    label = "Heart Transition"
  )
  val heartSize by transition.animateDp(
    label = "Size Animation",
    transitionSpec = {
      if (HeartAnimationState.Shown isTransitioningTo HeartAnimationState.Hidden) {
        tween(durationMillis = 300)
      } else {
        keyframes {
          durationMillis = 1600
          0.0.dp at 0 with FastOutSlowInEasing
          130.dp at 400 with FastOutSlowInEasing
          100.dp at 550 with FastOutSlowInEasing
          100.dp at 900
          130.dp at 1000 with FastOutSlowInEasing
          100.dp at 1300 with FastOutSlowInEasing
        }
      }
    }
  ) { state ->
    when (state) {
      HeartAnimationState.Hidden -> 0.dp
      HeartAnimationState.Shown -> 100.dp
    }
  }
  val outlineRadiusSize by transition.animateDp(
    label = "Outline Size Animation",
    transitionSpec = {
      tween(500)
    }
  ) { state ->
    when (state) {
      HeartAnimationState.Hidden -> 0.dp
      HeartAnimationState.Shown -> 400.dp
    }
  }
  val outlineAlpha by transition.animateFloat(
    label = "Outline Alpha Animation",
    transitionSpec = {
      tween(700)
    }
  ) { state ->
    when (state) {
      HeartAnimationState.Hidden -> 0.7f
      HeartAnimationState.Shown -> 0f
    }
  }
  val radius = LocalDensity.current.run { outlineRadiusSize.toPx() }
  if (transition.currentState == transition.targetState) {
    heartAnimationState.value = HeartAnimationState.Hidden
  }
  Box(modifier = Modifier
    .drawBehind {
      val lightExplosionColor = 0XFFF0E68C
      if (transition.currentState != HeartAnimationState.Shown) {
        drawCircle(Color(lightExplosionColor), radius = radius, alpha = outlineAlpha)
      }
    }) {
    Image(
      painter = painterResource(id = R.drawable.favorite),
      contentDescription = "Heart Animation",
      colorFilter = ColorFilter.tint(Color.Red),
      modifier = Modifier.size(heartSize)
    )
  }
}

@Preview
@Composable
private fun HeartImagePreview() {
  val state = remember { mutableStateOf(HeartAnimationState.Hidden) }
  LaunchedEffect("LaunchAnimation") {
    state.value = HeartAnimationState.Shown
  }
  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier.size(300.dp)
  ) {
    HeartImage(state)
  }
}




