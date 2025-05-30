package com.example.quizlingo

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_rock_paper_scissor.*
import nl.dionsegijn.konfetti.core.*
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Size
import nl.dionsegijn.konfetti.xml.KonfettiView
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class RockPaperScissor : AppCompatActivity() {


    var yourScore=0
    var aiScore=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rock_paper_scissor)
        lateinit var viewKonfetti: KonfettiView
        rock.setOnClickListener()
        {
            check("Rock")

            you_chosed.text="    Rock"
        }
        paper.setOnClickListener() {
            check("Paper")
            you_chosed.text="    Paper"
        }
        scissor.setOnClickListener() {
            check("Scissor")
            you_chosed.text ="   Scissor"
        }

        resetButton.setOnClickListener()
        {
            if(yourScore>aiScore) {



                konfettiView.setVisibility(View.VISIBLE)
//                KonfettiView.build()
//                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
//                    .setDirection(0.0, 359.0)
//                    .setSpeed(1f, 5f)
//                    .setFadeOutEnabled(true)
//                    .setTimeToLive(2000L)
//                    .addShapes(Shape.RECT, Shape.CIRCLE)
//                    .addSizes(Size(12))
//                    .setPosition(-50f, KonfettiView.width + 50f, -50f, -50f)
//                    .streamFor(300, 5000L)

                val dialogueBinding = layoutInflater.inflate(R.layout.box, null)

                val myDialog = Dialog(this)
                myDialog.setContentView(dialogueBinding)

                myDialog.setCancelable(true)
                myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                myDialog.show()

                val cancelbtn = dialogueBinding.findViewById<Button>(R.id.cancel)
                cancelbtn.setOnClickListener {
                    finish()
                }
                val againbtn = dialogueBinding.findViewById<Button>(R.id.again)
                againbtn.setOnClickListener {
                    myDialog.dismiss()
                }

                viewKonfetti = findViewById(R.id.konfettiView)
                viewKonfetti.start(Presets.festive())
                viewKonfetti.start(Presets.explode())
                viewKonfetti.start(Presets.parade())
                viewKonfetti.start(Presets.rain())
            }
            else
            {
                val dialogueBinding = layoutInflater.inflate(R.layout.boxlose, null)

                val myDialog = Dialog(this)
                myDialog.setContentView(dialogueBinding)

                myDialog.setCancelable(true)
                myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                myDialog.show()

                val cancelbtn = dialogueBinding.findViewById<Button>(R.id.cancel)
                cancelbtn.setOnClickListener {
                    finish()
                }
                val againbtn = dialogueBinding.findViewById<Button>(R.id.again)
                againbtn.setOnClickListener {
                    myDialog.dismiss()
                }
            }
            yourScore=0
            aiScore=0
            your_score.text=yourScore.toString()
            ai_score.text=aiScore.toString()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun check(a:String)
    {
        var cpu_choice=""
        val random :Int = Random.nextInt(3)
        if(random==0)
        {
            cpu_choice="Rock"
            ai_chosed.text="     Rock"
        }
        if( random ==1)
        {
            cpu_choice="Paper"
            ai_chosed.text="     Paper"
        }
        if(random==2) {
            cpu_choice = "Scissor"
            ai_chosed.text="    Scissor"
        }

        if (cpu_choice=="Rock" && a=="Scissor")
        {
            result.text = "You Lose !!!"
            aiScore += 1
            ai_score.text=aiScore.toString()
        }
        else if (cpu_choice=="Rock" && a=="Paper")
        {
            result.text = "You Won !!!"
            yourScore += 1
            your_score.text=yourScore.toString()
        }
        else if (cpu_choice=="Paper" && a=="Rock")
        {
            result.text = "You Lose !!!"
            aiScore += 1
            ai_score.text=aiScore.toString()
        }
        else if (cpu_choice=="Paper" && a=="Scissor")
        {
            result.text = "You Won !!!"
            yourScore += 1
            your_score.text=yourScore.toString()
        }
        else if (cpu_choice=="Scissor" && a=="Rock")
        {
            result.text = "You Won !!!"
            yourScore += 1
            your_score.text=yourScore.toString()
        }
        else if (cpu_choice=="Scissor" && a=="Paper")
        {
            result.text = getString(R.string.lose)
            aiScore += 1
            ai_score.text=aiScore.toString()
        }
        else if (cpu_choice==a)
        {
            result.text = "      TIE !!!"
        }

    }


}
class Presets {
    companion object {
        fun festive(): List<Party> {
            val party = Party(
                speed = 30f,
                maxSpeed = 50f,
                damping = 0.9f,
                angle = Angle.TOP,
                spread = 45,
                size = listOf(Size.SMALL, Size.LARGE),
                timeToLive = 3000L,
                rotation = Rotation(),
                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(30),
                position = Position.Relative(0.5, 1.0)
            )

            return listOf(
                party,
                party.copy(
                    speed = 55f,
                    maxSpeed = 65f,
                    spread = 10,
                    emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(10),
                ),
                party.copy(
                    speed = 50f,
                    maxSpeed = 60f,
                    spread = 120,
                    emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(40),
                ),
                party.copy(
                    speed = 65f,
                    maxSpeed = 80f,
                    spread = 10,
                    emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(10),
                )
            )
        }

        fun explode(): List<Party> {
            return listOf(
                Party(
                    speed = 0f,
                    maxSpeed = 30f,
                    damping = 0.9f,
                    spread = 360,
                    colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                    emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
                    position = Position.Relative(0.5, 0.3)
                )
            )
        }

        fun parade(): List<Party> {
            val party = Party(
                speed = 10f,
                maxSpeed = 30f,
                damping = 0.9f,
                angle = Angle.RIGHT - 45,
                spread = Spread.SMALL,
                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                emitter = Emitter(duration = 5, TimeUnit.SECONDS).perSecond(30),
                position = Position.Relative(0.0, 0.5)
            )

            return listOf(
                party,
                party.copy(
                    angle = party.angle - 90, // flip angle from right to left
                    position = Position.Relative(1.0, 0.5)
                ),
            )
        }

        fun rain(): List<Party> {
            return listOf(
                Party(
                    speed = 0f,
                    maxSpeed = 15f,
                    damping = 0.9f,
                    angle = Angle.BOTTOM,
                    spread = Spread.ROUND,
                    colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                    emitter = Emitter(duration = 5, TimeUnit.SECONDS).perSecond(100),
                    position = Position.Relative(0.0, 0.0).between(Position.Relative(1.0, 0.0))
                )
            )
        }
    }
}