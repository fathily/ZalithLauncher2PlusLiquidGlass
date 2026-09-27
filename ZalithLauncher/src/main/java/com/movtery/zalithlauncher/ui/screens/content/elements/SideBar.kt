package com.movtery.zalithlauncher.ui.screens.content.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movtery.zalithlauncher.R
import com.movtery.zalithlauncher.setting.AllSettings
import com.movtery.zalithlauncher.ui.theme.backgroundColor
import com.movtery.zalithlauncher.ui.theme.onBackgroundColor

private val MenuWidth = 264.dp
private val GlassShape = RoundedCornerShape(30.dp)
private val RowShape = RoundedCornerShape(18.dp)

@Composable
fun SideBar(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onFpsClick: () -> Unit,
    onVersionsClick: () -> Unit,
    onInfoClick: () -> Unit
) {
    if (!isVisible) return

    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 10.dp, top = 12.dp, bottom = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (expanded) {
            IosGlassMenu(
                onCollapse = { expanded = false },
                onFpsClick = onFpsClick,
                onVersionsClick = onVersionsClick,
                onInfoClick = onInfoClick
            )
        } else {
            GlassMenuHandle(
                onClick = { expanded = true }
            )
        }
    }
}

@Composable
private fun IosGlassMenu(
    onCollapse: () -> Unit,
    onFpsClick: () -> Unit,
    onVersionsClick: () -> Unit,
    onInfoClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(MenuWidth)
            .fillMaxHeight()
            .clip(GlassShape)
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.84f),
                        MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.72f),
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.80f)
                    )
                )
            )
            .border(
                1.dp,
                Brush.verticalGradient(
                    listOf(
                        Color.White.copy(alpha = 0.62f),
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.20f),
                        Color.White.copy(alpha = 0.12f)
                    )
                ),
                GlassShape
            )
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.tertiary
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "ZL",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = "ZL2+ Liq",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Liquid Glass",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            val collapseInteraction = remember { MutableInteractionSource() }
            val collapsePressed by collapseInteraction.collectIsPressedAsState()
            val collapseScale by animateFloatAsState(
                targetValue = if (collapsePressed) 0.90f else 1f,
                animationSpec = spring(),
                label = "collapseScale"
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .scale(collapseScale)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.62f)
                    )
                    .border(
                        1.dp,
                        Color.White.copy(alpha = 0.20f),
                        CircleShape
                    )
                    .clickable(
                        interactionSource = collapseInteraction,
                        indication = null,
                        onClick = onCollapse
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left_rounded),
                    contentDescription = stringResource(R.string.generic_collapse),
                    modifier = Modifier.size(22.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.72f)
                )
            }
        }

        Spacer(Modifier.height(2.dp))

        GlassSectionLabel("LAUNCHER")

        IosMenuRow(
            painter = painterResource(R.drawable.ic_video_settings),
            title = stringResource(R.string.game_menu_option_fps_settings),
            subtitle = "Graphics & performance",
            onClick = onFpsClick
        )

        IosMenuRow(
            painter = painterResource(R.drawable.ic_assignment_filled),
            title = stringResource(R.string.page_title_version_manage),
            subtitle = "Minecraft versions",
            onClick = onVersionsClick
        )

        GlassSectionLabel("MORE")

        IosMenuRow(
            painter = painterResource(R.drawable.ic_info_outlined),
            title = stringResource(R.string.about_launcher_title),
            subtitle = "About ZL2+ Liq",
            onClick = onInfoClick
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = "ZL2+ Liq • iOS-inspired",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.55f),
            style = MaterialTheme.typography.labelSmall,
            fontSize = 10.sp
        )
    }
}

@Composable
private fun GlassSectionLabel(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(start = 8.dp, top = 3.dp),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.72f),
        style = MaterialTheme.typography.labelSmall,
        fontSize = 10.sp
    )
}

@Composable
private fun IosMenuRow(
    painter: Painter,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = spring(),
        label = "iosRowScale"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clip(RowShape)
            .background(
                MaterialTheme.colorScheme.surfaceContainerHighest.copy(
                    alpha = if (pressed) 0.72f else 0.48f
                )
            )
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.10f),
                RowShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.78f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(21.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 11.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.72f)
            )
        }

        Icon(
            painter = painterResource(R.drawable.ic_arrow_right_rounded),
            contentDescription = null,
            modifier = Modifier
                .size(19.dp)
                .alpha(0.48f)
        )
    }
}

@Composable
private fun GlassMenuHandle(onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.92f else 1f,
        animationSpec = spring(),
        label = "handleScale"
    )

    Box(
        modifier = Modifier
            .scale(scale)
            .size(width = 48.dp, height = 104.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                MaterialTheme.colorScheme.surface.copy(alpha = 0.78f)
            )
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.18f),
                RoundedCornerShape(24.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                Modifier
                    .width(4.dp)
                    .height(24.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.72f))
            )
            Text(
                text = "›",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.60f),
                fontSize = 22.sp
            )
        }
    }
}
