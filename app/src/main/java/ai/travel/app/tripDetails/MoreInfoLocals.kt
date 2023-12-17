package ai.travel.app.tripDetails

import ai.travel.app.R
import ai.travel.app.home.HomeViewModel
import ai.travel.app.home.base64ToByteArray
import ai.travel.app.home.ui.MumbaiImages
import ai.travel.app.home.ui.convertImageByteArrayToBitmap
import ai.travel.app.home.ui.image
import ai.travel.app.ui.theme.appGradient
import ai.travel.app.ui.theme.lightText
import ai.travel.app.ui.theme.textColor
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreInfoLocals(viewModel: HomeViewModel, paddingValues: PaddingValues) {
    val cardData1 = listOf(
        GridCardData(
            topText = "Day",
            bottomText = viewModel.currentDay.value,
            icon = Icons.Filled.Public
        ),
        GridCardData(
            topText = "Time",
            bottomText = viewModel.currentTimeOfDay.value,
            icon = Icons.Filled.Public
        ),
    )
    val coroutineScope = rememberCoroutineScope()
    val modalSheetStates = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            initialValue = SheetValue.Hidden,
            skipPartiallyExpanded = false
        )
    )
    val isLocalVisible = remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .fillMaxHeight(0.95f)
            .background(appGradient)
            .padding(paddingValues)

    ) {

        println("MoreInfoTripssss: trips.value = ${viewModel.currentNewDestination.value}")

        var dayTrips =
            viewModel.getMoreInfo(
                destination = viewModel.currentNewDestination.value
            ).collectAsState(initial = emptyList())



        if (dayTrips.value.isEmpty()) {
            CircularProgressIndicator()
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
                dayTrips.value[0]?.photoBase64?.let {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.34f),
                        shape = RoundedCornerShape(0.dp),
                        elevation = CardDefaults.cardElevation(7.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        convertImageByteArrayToBitmap(base64ToByteArray(it))?.asImageBitmap()
                            ?.let { it1 ->
                                Image(
                                    bitmap = it1,
                                    contentDescription = "some useful description",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .drawWithCache {
                                            val gradient = Brush.verticalGradient(
                                                colors = listOf(
                                                    Color.Transparent,
                                                    Color.Black.copy(0.8f)
                                                ),
                                                startY = size.height / 5.5f,
                                                endY = size.height
                                            )
                                            onDrawWithContent {
                                                drawContent()
                                                drawRect(
                                                    gradient,
                                                    blendMode = BlendMode.Multiply
                                                )
                                            }
                                        },
                                    contentScale = ContentScale.Crop
                                )
                            }
                    }

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.padding(8.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                modifier = Modifier.padding(8.dp)
                            )

                        }
                        Spacer(modifier = Modifier.height(145.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 40.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = viewModel.currentDestination.value,
                                color = textColor,
                                fontSize = 45.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    top = 20.dp,
                                    bottom = 8.dp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(40.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {


                            Card(
                                modifier = Modifier.width(100.dp),
                                shape = RoundedCornerShape(
                                    topEnd = 0.dp,
                                    topStart = 15.dp,
                                    bottomStart = 15.dp,
                                    bottomEnd = 0.dp
                                ),
                                colors = CardDefaults.cardColors(
                                    if (isLocalVisible.value) Color.Gray else Color.Black
                                ),


                                ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Local",
                                        modifier = Modifier
                                            .padding(all = 10.dp)
                                            .clickable {
                                                isLocalVisible.value = true
                                            }
                                    )
                                }

                            }
                            Spacer(modifier = Modifier.width(3.dp))
                            Card(
                                modifier = Modifier.width(100.dp),
                                shape = RoundedCornerShape(
                                    topEnd = 15.dp,
                                    topStart = 0.dp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 15.dp
                                ), colors = CardDefaults.cardColors(
                                    if (!isLocalVisible.value) Color.Gray else Color.Black
                                )
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Others",
                                        modifier = Modifier
                                            .padding(all = 10.dp)
                                            .clickable {
                                                isLocalVisible.value = false
                                            }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                        }
                        AnimatedVisibility(
                            visible = isLocalVisible.value,
                            enter = slideInHorizontally(initialOffsetX = {
                                -it
                            }), exit = slideOutHorizontally(targetOffsetX = {
                                -it
                            })
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Spacer(modifier = Modifier.height(35.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp, horizontal = 14.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row {
                                        Icon(
                                            imageVector = Icons.Filled.LocalDining,
                                            contentDescription = null,
                                            modifier = Modifier,
                                            tint = lightText
                                        )
                                        Spacer(modifier = Modifier.width(5.dp))

                                        Text(
                                            text = "Famous FoodJoints",
                                            fontSize = 20.sp,
                                            color = textColor
                                        )


                                    }
                                }

                                LazyRow {
                                    items(cuisines) { icon ->

                                        Spacer(modifier = Modifier.width(15.dp))


                                        Card(
                                            modifier = Modifier
                                                .height(250.dp)
                                                .width(170.dp)
                                        ) {
                                            Box(modifier = Modifier.fillMaxSize()) {

                                                Image(
                                                    painter = painterResource(id = icon.image),
                                                    contentDescription = "",
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentScale = ContentScale.Crop
                                                )
                                                Box(
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentAlignment = Alignment.BottomCenter
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .align(Alignment.BottomCenter),
                                                        horizontalArrangement = Arrangement.Center,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Card(
                                                            modifier = Modifier,
                                                            shape = RoundedCornerShape(
                                                                topStart = 0.dp,
                                                                topEnd = 0.dp,
                                                                bottomStart = 10.dp,
                                                                bottomEnd = 10.dp
                                                            )

                                                        ) {
                                                            Row(
                                                                modifier = Modifier
                                                                    .fillMaxWidth()
                                                                    .padding(
                                                                        vertical = 8.dp,
                                                                        horizontal = 10.dp
                                                                    ),
                                                                horizontalArrangement = Arrangement.Center,
                                                                verticalAlignment = Alignment.CenterVertically,
                                                            ) {

                                                                Text(
                                                                    text = icon.name,
                                                                    fontSize = 14.sp,

                                                                    )
                                                            }

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                // 1 ends


                                Spacer(modifier = Modifier.height(35.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp, horizontal = 14.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row {
                                        Icon(
                                            imageVector = Icons.Default.Accessibility,
                                            contentDescription = null,
                                            modifier = Modifier,
                                            tint = lightText
                                        )
                                        Spacer(modifier = Modifier.width(5.dp))

                                        Text(
                                            text = "Famous Garments",
                                            fontSize = 20.sp,
                                            color = textColor
                                        )


                                    }
                                }

                                LazyRow {
                                    items(garments) { icon ->

                                        Spacer(modifier = Modifier.width(15.dp))


                                        Card(
                                            modifier = Modifier
                                                .height(250.dp)
                                                .width(170.dp)
                                        ) {
                                            Box(modifier = Modifier.fillMaxSize()) {

                                                Image(
                                                    painter = painterResource(id = icon.image),
                                                    contentDescription = "",
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentScale = ContentScale.Crop
                                                )
                                                Box(
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentAlignment = Alignment.BottomCenter
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .align(Alignment.BottomCenter),
                                                        horizontalArrangement = Arrangement.Center,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Card(
                                                            modifier = Modifier,
                                                            shape = RoundedCornerShape(
                                                                topStart = 0.dp,
                                                                topEnd = 0.dp,
                                                                bottomStart = 10.dp,
                                                                bottomEnd = 10.dp
                                                            )

                                                        ) {
                                                            Row(
                                                                modifier = Modifier
                                                                    .fillMaxWidth()
                                                                    .padding(
                                                                        vertical = 8.dp,
                                                                        horizontal = 10.dp
                                                                    ),
                                                                horizontalArrangement = Arrangement.Center,
                                                                verticalAlignment = Alignment.CenterVertically,
                                                            ) {

                                                                Text(
                                                                    text = icon.name,
                                                                    fontSize = 14.sp,

                                                                    )
                                                            }

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                //2 ends


                                Spacer(modifier = Modifier.height(35.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp, horizontal = 14.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row {
                                        Icon(
                                            imageVector = Icons.Default.Forest,
                                            contentDescription = null,
                                            modifier = Modifier,
                                            tint = lightText
                                        )
                                        Spacer(modifier = Modifier.width(5.dp))

                                        Text(
                                            text = "Nearby Places",
                                            fontSize = 20.sp,
                                            color = textColor
                                        )


                                    }
                                }

                                LazyRow {
                                    items(image) { icon ->

                                        Spacer(modifier = Modifier.width(15.dp))


                                        Card(
                                            modifier = Modifier
                                                .height(250.dp)
                                                .width(170.dp)
                                        ) {
                                            Box(modifier = Modifier.fillMaxSize()) {

                                                AsyncImage(
                                                   model = icon.image,
                                                    contentDescription = "",
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentScale = ContentScale.Crop
                                                )
                                                Box(
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentAlignment = Alignment.BottomCenter
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .align(Alignment.BottomCenter),
                                                        horizontalArrangement = Arrangement.Center,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Card(
                                                            modifier = Modifier,
                                                            shape = RoundedCornerShape(
                                                                topStart = 0.dp,
                                                                topEnd = 0.dp,
                                                                bottomStart = 10.dp,
                                                                bottomEnd = 10.dp
                                                            )

                                                        ) {
                                                            Row(
                                                                modifier = Modifier
                                                                    .fillMaxWidth()
                                                                    .padding(
                                                                        vertical = 8.dp,
                                                                        horizontal = 10.dp
                                                                    ),
                                                                horizontalArrangement = Arrangement.Center,
                                                                verticalAlignment = Alignment.CenterVertically,
                                                            ) {

                                                                Text(
                                                                    text = icon.name,
                                                                    fontSize = 14.sp,

                                                                    )
                                                            }

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                //3 ends


                                Spacer(modifier = Modifier.height(100.dp))
                            }
                        }
                        AnimatedVisibility(
                            visible = !isLocalVisible.value,
                            enter = slideInHorizontally(
                                initialOffsetX = {
                                    it
                                },
                                animationSpec = tween(300, 150)
                            ), exit = slideOutHorizontally(targetOffsetX = {
                                it
                            }, animationSpec = tween(300, 150))
                        ) {
                            MoreInfoTrips(viewModel, paddingValues)
                        }


                    }

                }


            }
        }
    }
}


data class Cuisines(
    val image: Int,
    val name: String,
)



val cuisines = listOf(
    Cuisines(
        R.drawable.cu1,
        "Pondichéry Café",

        ),
    Cuisines(
        R.drawable.cu2,
        "Hitchki BKC",

        ),
    Cuisines(
        R.drawable.cu3,
        "Masala Library",

        ),
    Cuisines(
        R.drawable.cu4,
        "Yauatcha Mumbai",

        ),
    Cuisines(
        R.drawable.cu5,
        "Tuskers",

        ),
    Cuisines(
        R.drawable.cu6,
        "Gallery Cafe",
    ),

    )



data class Garments(
    val image: Int,
    val name: String,
)



val garments = listOf(
    Garments(
        R.drawable.sh6,
        "Zaveri Bazaar",
    ),
    Garments(
        R.drawable.sh3,
        "Crawford Market",

        ),
    Garments(
        R.drawable.sh1,
        "Colaba Causeway",

        ),
    Garments(
        R.drawable.sh2,
        "Linking Road",

        ),
    Garments(
        R.drawable.sh4,
        "Lalbaug Market",

        ),
    Garments(
        R.drawable.sh5,
        "Hill Road",

        ),


    )

