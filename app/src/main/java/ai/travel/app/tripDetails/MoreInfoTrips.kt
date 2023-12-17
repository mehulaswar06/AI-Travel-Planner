package ai.travel.app.tripDetails

import ai.travel.app.R
import ai.travel.app.home.HomeViewModel
import ai.travel.app.home.base64ToByteArray
import ai.travel.app.home.ui.convertImageByteArrayToBitmap
import ai.travel.app.ui.theme.CardBackground
import ai.travel.app.ui.theme.appGradient
import ai.travel.app.ui.theme.lightText
import ai.travel.app.ui.theme.textColor
import ai.travel.app.utils.dashedBorder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Card
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreInfoTrips(viewModel: HomeViewModel, paddingValues: PaddingValues) {
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {

                        Spacer(modifier = Modifier.height(40.dp))
                        Spacer(modifier = Modifier.height(40.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Card(modifier = Modifier.clip(RoundedCornerShape(15.dp))) {
                                    Text(
                                        text = "Hotels",
                                        modifier = Modifier.padding(all = 10.dp)
                                    )
                                }

                                Card(modifier = Modifier.clip(RoundedCornerShape(15.dp))) {
                                    Text(
                                        text = "Restaurants",
                                        modifier = Modifier.padding(all = 10.dp)
                                    )
                                }
                                Card(modifier = Modifier.clip(RoundedCornerShape(15.dp))) {
                                    Text(
                                        text = "Fuels",
                                        modifier = Modifier.padding(all = 10.dp)
                                    )
                                }


                            }
                        }



                        Spacer(modifier = Modifier.height(30.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .zIndex(5f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {


                            Card(
                                modifier = Modifier
                                    .height(240.dp)
                                    .width(165.dp)
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {

                                    Image(
                                        painter = painterResource(id = R.drawable.cu5),
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
                                                .align(Alignment.BottomCenter)
                                        ) {
                                            Card(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(15.dp))
                                                    .fillMaxWidth(),
                                                shape = RoundedCornerShape(
                                                    topStart = 0.dp,
                                                    topEnd = 0.dp,
                                                    bottomEnd = 8.dp,
                                                    bottomStart = 8.dp
                                                )
                                            ) {

                                                Column(
                                                    modifier = Modifier.padding(
                                                        horizontal = 8.dp,
                                                        vertical = 4.dp
                                                    )
                                                ) {
                                                    Text(
                                                        text = "Gallery Cafe",
                                                        fontSize = 18.sp,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                    Row(
                                                        modifier = Modifier,
                                                        horizontalArrangement = Arrangement.Center,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Text(text = "2.9")
                                                        Row(
                                                            modifier = Modifier,
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Default.Star,
                                                                contentDescription = null,
                                                                modifier = Modifier
                                                                    .padding(1.dp)
                                                                    .size(18.dp),
                                                                tint = Color.Yellow
                                                            )
                                                            Icon(
                                                                imageVector = Icons.Default.Star,
                                                                contentDescription = null,
                                                                modifier = Modifier
                                                                    .padding(1.dp)
                                                                    .size(18.dp),
                                                                tint = Color.Yellow
                                                            )
                                                            Icon(
                                                                imageVector = Icons.Default.Star,
                                                                contentDescription = null,
                                                                modifier = Modifier
                                                                    .padding(1.dp)
                                                                    .size(18.dp),
                                                                tint = Color.Yellow
                                                            )
                                                            Icon(
                                                                imageVector = Icons.Default.Star,
                                                                contentDescription = null,
                                                                modifier = Modifier
                                                                    .padding(1.dp)
                                                                    .size(18.dp),

                                                                )
                                                            Icon(
                                                                imageVector = Icons.Default.Star,
                                                                contentDescription = null,
                                                                modifier = Modifier
                                                                    .padding(1.dp)
                                                                    .size(18.dp),

                                                                )

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }


                            }

                        }



                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                                .padding(end = 15.dp, top = 15.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Gallery Cafe", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Icon(
                                imageVector = Icons.Outlined.Bookmark,
                                contentDescription = null,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp)
                        ) {
                            Text(
                                text = "Indian, Asian, Healthy, International," +
                                        " Vegetarian Friendly, Vegan Options",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp)
                        ) {
                            Text(
                                text = "A nice buffet place with many options, I personally loved the dessert section it has so many options to choose from my fav was their motichoor ladoo and gulab Jamun.The vibe is really good and has a classy interior.\n" +
                                        "\n" +
                                        "I would recommend you to go on weekdays to avoid rush.",
                                fontSize = 14.sp
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(25.dp)
                            )
                            Text(
                                text = "Mumbai, Maharashtra 400070",
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Column(modifier = Modifier.padding(top = 10.dp)) {
                            Text(text = "Ratings", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "2.9")
                                Spacer(modifier = Modifier.width(10.dp))
                                Row(
                                    modifier = Modifier,
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        modifier = Modifier.padding(8.dp),
                                        tint = Color.Yellow
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        modifier = Modifier.padding(8.dp),
                                        tint = Color.Yellow
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        modifier = Modifier.padding(8.dp),

                                        )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {


                            Image(
                                painter = painterResource(id = R.drawable.makemytrip),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(100.dp)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.goibibo),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(100.dp)
                            )


                        }

                        Spacer(modifier = Modifier.height(40.dp))


                    }

                }

            }
        }
    }
}