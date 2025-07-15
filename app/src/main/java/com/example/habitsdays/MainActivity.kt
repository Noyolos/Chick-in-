package com.example.habitsdays

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitsdays.ui.theme.HabitsDaysTheme
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotApplyResult
import androidx.compose.ui.draw.scale
import androidx.compose.ui.modifier.ModifierLocalReadScope
import kotlinx.coroutines.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitsDaysTheme {
                val navController = rememberNavController()
                val habitList = remember { mutableStateListOf<String>() }
                val count = remember {  mutableStateOf(0) }



                NavHost(navController, startDestination = "main") {
                    composable("main") { mainScreen(onNavigate={navController.navigate("second")},
                       count=count) }
                    composable("second") { secondScreen(onBack={navController.navigate("main")},
                        gothird={navController.navigate("third")},
                        habitList=habitList)}
                    composable("third") { thirdScreen(gothird={navController.navigate("third")},
                        onBack={navController.navigate("second")},
                        habitList=habitList)}
                }
            }
        }
    }
}

@Composable
fun mainScreen(onNavigate: () -> Unit,count:MutableState<Int>) {
    var showSucces by remember { mutableStateOf(false) }
    val scale = remember { Animatable(1f) }

    LaunchedEffect(showSucces) {
        if (showSucces) {
            scale.snapTo(2.5f)
            scale.animateTo(1f, tween(durationMillis = 300))
            delay(3000)
            showSucces = false
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {

            Text(
                text = "Chick-in🐥",
                style = MaterialTheme.typography.labelLarge,
                fontSize = 50.sp,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y =20.dp)
            )

            Button(
                onClick = {showSucces=true
                          count.value+=1},
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .offset(y=-40.dp)
            ) {
                Text(
                    text = "Click me",
                    fontSize = 50.sp,
                )
            }
            Text(text="You've checked in ${count.value}times🔥",
                fontSize  =30.sp,
                modifier = Modifier.align(Alignment.Center))



            if(showSucces==true){
                Text(text="✔Check-in Succesfully🔥😘",
                    fontSize = 25.sp,
                    modifier = Modifier
                        .scale(scale.value)
                        .align(Alignment.Center)
                        .offset(y=-250.dp))





        }
        Button(onClick = onNavigate,
            modifier=Modifier
                .align(Alignment.BottomCenter)
                .offset(y =-200.dp))
        {
            Text(text = "next")
        }
    }
}

@Composable
fun secondScreen(onBack: () -> Unit
                 ,gothird: () -> Unit,
                 habitList: List<String>){
    Box (modifier = Modifier.fillMaxSize()){
        Text(text = "" ,
            fontSize = 50.sp,
            modifier=Modifier
                .align(Alignment.Center))

        Button(onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(y=20.dp)
            ) {
            Text(text = "back",
                modifier = Modifier)
        }
        Button(onClick = gothird,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y=20.dp)) {
            Text(text = "Cusstomize Your Habits",)
        }
        Column(modifier = Modifier
            .align(Alignment.TopStart)
            .padding(16.dp)
            .offset(y = 60.dp)) {

            Text("Habit Tracking：", fontSize = 24.sp)
            habitList.forEach { habit ->
                Text(text = "✅ $habit", fontSize = 20.sp, modifier = Modifier.padding(4.dp))
            }
            }

    }
}


@Composable
fun thirdScreen(gothird:()->Unit,
                onBack: () -> Unit,
                habitList:MutableList<String>){

    var customHabit by remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "Habits Names:",
            fontSize = 35.sp,
            modifier = Modifier
            .align(Alignment.TopStart)
                .offset(y =100.dp))

        Button(onClick = onBack,
            modifier =Modifier
            .align(Alignment.TopStart)
                .offset(y=30.dp)) {
            Text("Back")
        }

        TextField(
            value=customHabit,
            onValueChange = {customHabit=it},
            label = { Text("Enter Your Habits")},
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(y=160.dp)
        )
        
        Button(onClick = { if (customHabit.isNotBlank())
            habitList.add(customHabit)
            customHabit=""
        }, modifier = Modifier
            .align(Alignment.CenterEnd)
            .offset(x=-0.dp)
            .offset(y=-270.dp)
            .padding(16.dp)

        ) {
            Text("Add It!",
                fontSize = 20.sp,
                modifier = Modifier)

        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    HabitsDaysTheme {
        val fakeCount = remember { mutableStateOf(0) }
        mainScreen(onNavigate={},count = fakeCount)
    }
}


