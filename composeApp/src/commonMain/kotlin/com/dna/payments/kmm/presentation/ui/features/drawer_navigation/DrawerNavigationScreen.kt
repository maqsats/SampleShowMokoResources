package com.dna.payments.kmm.presentation.ui.features.drawer_navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.ui.common.DNAText
import dev.icerock.moko.resources.compose.painterResource

class DrawerNavigationScreen : Screen {

    @Composable
    override fun Content() {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerShape = RectangleShape,
                    drawerContainerColor = Color.White,
                    drawerContentColor = Color.Black,
                    drawerTonalElevation = 0.dp,
                ) {

                }
            },
        ) {
            DnaCollapsingToolbar(
                drawerState = drawerState,
                headerContent = {
                    Box(
                        Modifier.fillMaxWidth().background(Color(0xFFF8F9F9)),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        DNAText(
                            modifier = Modifier.padding(
                                vertical = 10.dp,
                                horizontal = 16.dp
                            ),
                            text = "Online Payments",
                            style = DnaTextStyle.Bold22,
                        )
                    }
                },
                filterContent = {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        alignment = Alignment.CenterStart,
                        painter = painterResource(MR.images.header),
                        contentDescription = null
                    )
                },
                content = {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth().background(Color(0xFFF8F9F9))
                    ) {
                        items(100) {
                            Text(
                                text = "Item $it",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            )
        }
    }
}