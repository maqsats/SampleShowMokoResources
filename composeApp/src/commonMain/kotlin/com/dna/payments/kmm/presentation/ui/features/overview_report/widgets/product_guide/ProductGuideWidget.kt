package com.dna.payments.kmm.presentation.ui.features.overview_report.widgets.product_guide

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.product_guide.ProductGuide
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

object ProductGuideWidget : Screen {
    override val key: String = "ProductGuideWidget"

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<ProductGuideViewModel>()

        val state by viewModel.uiState.collectAsState()

        val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })

        LaunchedEffect(Unit) {
            while (true) {
                delay(3.seconds)
                with(pagerState) {
                    val target = if (currentPage < pageCount - 1) currentPage + 1 else 0

                    animateScrollToPage(
                        page = target,
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            userScrollEnabled = true,
            pageContent = { pageIndex ->
                ProductGuideWidgetItem(state.productGuideList[pageIndex])
            }
        )
    }


    @Composable
    private fun ProductGuideWidgetItem(productGuide: ProductGuide) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth().zIndex(1f)
                ) {
                    Image(
                        painter = rememberImagePainter(productGuide.imageBackground),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .aspectRatio(ratio = 16f / 9f)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth().zIndex(2f).align(Alignment.BottomEnd)
                ) {
                    Image(
                        painter = rememberImagePainter(PAY_BY_LINK_IMAGE),
                        contentDescription = null,
                        modifier = Modifier
                            .aspectRatio(ratio = 9f / 4f)
                            .wrapContentSize(Alignment.CenterEnd)
                    )
                }
            }

            Column(modifier = Modifier.padding(Paddings.medium)) {
                DNAText(
                    text = stringResource(MR.strings.merchant_portal),
                    style = DnaTextStyle.Medium12Grey4,
                )

                Spacer(modifier = Modifier.height(Paddings.extraSmall))

                DNAText(
                    text = stringResource(productGuide.title),
                    style = DnaTextStyle.SemiBold20
                )

                Spacer(modifier = Modifier.height(Paddings.small))

                DNAText(
                    text = stringResource(productGuide.description),
                    style = DnaTextStyle.Normal14Grey5,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(Paddings.medium))

                Row {

                    DNAText(
                        text = stringResource(productGuide.detailLink),
                        style = DnaTextStyle.GreenMedium16
                    )

                    Spacer(modifier = Modifier.width(Paddings.extraSmall))

                    Image(
                        painter = painterResource(MR.images.product_guide_arrow),
                        contentDescription = null
                    )
                }
            }
        }
    }

    private const val PAY_BY_LINK_IMAGE =
        "https://firebasestorage.googleapis.com/v0/b/diploma-c971d.appspot.com/o/pay_by_link_guide%401x.png?alt=media&token=0bed8a17-8a88-4f13-9a2f-42c02c68d3c5"
}