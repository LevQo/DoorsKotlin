package com.levqo.doorskotlin

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.content.Intent
import android.view.View


class MainActivity : AppCompatActivity() {

    private var percent = 0f
    private var percentChanged = 0f
    private var percentNotChanged = 0f

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    //Обработка кликов с передачей колчества повторов
    fun onClick(v: View) {
        when (v.id) {
            R.id.btn_100 -> calculateDoors(99)
            R.id.btn_1000 -> calculateDoors(999)
            R.id.btn_5000 -> calculateDoors(4999)
            R.id.btn_10000 -> calculateDoors(9999)
        }
    }

    private fun calculateDoors(n: Int) {

        var notChangedWins = 0
        var changedWins = 0

        val gen = Random()

        for (plays in 0..n) {
            val doors = intArrayOf(0, 0, 0)//Создание массива из трех чисел, где 0 это коза, а 1 машина
            doors[gen.nextInt(3)] = 1//Добавление единицы в рандомную дверь
            val choice = gen.nextInt(3) //Выбор рандомной двери
            var shown: Int
            //Открываются двери до тех пора пока открытая дверь не будет 1 или не будет сразу выбрана правильная дверь
            do {
                shown = gen.nextInt(3)
            } while (doors[shown] == 1 || shown == choice)

            notChangedWins += doors[choice]//Подсчет побед без изменния выбора
            changedWins += doors[3 - choice - shown] //Подсчет побед с измененным выбором
        }

        //Подсчет процентов
        percent = (notChangedWins + changedWins).toFloat()
        percentChanged = Math.round(changedWins / percent * 100.0f).toFloat()
        percentNotChanged = Math.round(notChangedWins / percent * 100.0f).toFloat()

        //Вывод данных в TextView
        changed_won_number.text = "${getString(R.string.str_changed_won_num)} $changedWins"
        changed_won_percent.text = "${getString(R.string.str_changed_won_percent)} ${String.format("%.0f", percentChanged)}%"

        not_changed_won_number.text = "${getString(R.string.str_not_changed_won_num)} $notChangedWins"
        not_changed_won_percent.text = "${getString(R.string.str_not_changed_won_percent)} ${String.format("%.0f", percentNotChanged)}%"
    }
}
