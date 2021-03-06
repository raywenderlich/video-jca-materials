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

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
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
        tween(durationMillis = 1000)
      }
    }
  ) { state ->
    when (state) {
      HeartAnimationState.Hidden -> 0.dp
      HeartAnimationState.Shown -> 100.dp
    }
  }
  if (transition.currentState == transition.targetState) {
    heartAnimationState.value = HeartAnimationState.Hidden
  }
  Image(
    painter = painterResource(id = R.drawable.favorite),
    contentDescription = "Heart Animation",
    colorFilter = ColorFilter.tint(Color.Red),
    modifier = Modifier.size(heartSize)
  )
}
