package com.example.restaurantapps


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.restaurantapps.data.entities.Restaurant
import com.example.restaurantapps.data.remote.ApiClient
import com.example.restaurantapps.ui.theme.RestaurantAppsTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : ComponentActivity() {
    //private lateinit var restaurants: List<Restaurant>
    var restaurants by mutableStateOf(listOf<Restaurant>())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRestaurants()
        setContent {
            RestaurantAppsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    RestaurantList(restaurants)
                }
            }
        }
    }
    private fun loadRestaurants(){
        val restaurantInterface = ApiClient.build()
        val fetchRestaurants = restaurantInterface?.fetchRestaurants()
        fetchRestaurants?.enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(
                call: Call<List<Restaurant>>,
                response: Response<List<Restaurant>>
            ) {
                restaurants = response.body()!!
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RestaurantAppsTheme {
    }
}


@Composable
fun RestaurantList(restaurants:List<Restaurant>){
    LazyColumn {
        items(restaurants){restaurant ->
            RestaurantRow(restaurant)
        }
    }
}

@Composable
fun RestaurantRow(restaurant: Restaurant){
    Row {
        //Image starts
        Image(
            painter = rememberImagePainter(
                data = restaurant.poster,
                builder = {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = "Restaurant photo",
            modifier = Modifier.size(80.dp)
        )
        //Image ends
        Spacer(Modifier.width(20.dp))
        Column{
            Text(text = restaurant.name, fontWeight = FontWeight.Bold)
            Text(text = restaurant.district)
        }
    }

}


@Composable
fun CardDemo(restaurant: Restaurant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable{ },
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                buildAnnotatedString {
                    append(restaurant.name)
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                    ) {
                        append(restaurant.district)
                    }
                }
            )
            Text(
                buildAnnotatedString {
                    append("Now you are in the ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                        append("Card")
                    }
                    append(" section")
                }
            )
        }
    }
}


@Composable
fun RestauranftList(restaurants:List<Restaurant>){
    LazyColumn {
        items(restaurants){restaurant ->
           CardDemo(restaurant)
        }
    }
}