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

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue

@Composable
@ExperimentalPagerApi
fun ImagePager(images: List<Int>) {
  if (images.isNotEmpty()) {
    val pagerState = rememberPagerState(pageCount = images.size)
    Column {
      HorizontalPager(
        state = pagerState,
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp)
          .padding(top = 16.dp)
      ) { page ->
        Image(
          painter = painterResource(id = images[page]),
          contentDescription = "Post Image $page",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp)
            .animatePageChange(this, page)
        )
      }
      HorizontalPagerIndicator(
        pagerState = pagerState,
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .padding(4.dp)
      )
    }
  }
}

@ExperimentalPagerApi
private fun Modifier.animatePageChange(pagerScope: PagerScope, page: Int):
    Modifier {
  return this.graphicsLayer {
    Log.d("Experiment", "Offset: ${pagerScope.calculateCurrentOffsetForPage(page)}")
    val pageOffset = pagerScope.calculateCurrentOffsetForPage(page).absoluteValue
    val scale = lerp(
      start = 0.85f,
      stop = 1f,
      fraction = 1f - pageOffset
    )
    val updatedAlpha = lerp(
      start = 0.5f,
      stop = 1f,
      fraction = 1f - pageOffset
    )
    scaleX = scale
    scaleY = scale
    alpha = updatedAlpha
  }
}





