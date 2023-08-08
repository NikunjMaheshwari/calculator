package com.example.calculator2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    lateinit var tvsec: TextView
    lateinit var tvMain: TextView
    lateinit var bac: Button
    lateinit var bc: Button
    lateinit var b0: Button
    lateinit var b9: Button
    lateinit var b8: Button
    lateinit var b7: Button
    lateinit var b6: Button
    lateinit var b5: Button
    lateinit var b4: Button
    lateinit var b3: Button
    lateinit var b2: Button
    lateinit var b1: Button
    lateinit var bmul: Button
    lateinit var bminus: Button
    lateinit var bplus: Button
    lateinit var bdiv: Button
    lateinit var bequal: Button
    lateinit var bdot: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvsec = findViewById(R.id.idTVSecondary)
        tvMain = findViewById(R.id.idTVprimary)
        bac = findViewById(R.id.bac)
        bc = findViewById(R.id.bc)
        b0 = findViewById(R.id.btn_zero)
        b9 = findViewById(R.id.btn_nine)
        b8 = findViewById(R.id.btn_eight)
        b7 = findViewById(R.id.btn_seven)
        b6 = findViewById(R.id.btn_six)
        b5 = findViewById(R.id.btn_five)
        b4 = findViewById(R.id.btn_four)
        b3 = findViewById(R.id.btn_three)
        b2 = findViewById(R.id.btn_two)
        b1 = findViewById(R.id.btn_one)
        bdiv = findViewById(R.id.bdiv)
        bmul = findViewById(R.id.bmul)
        bminus = findViewById(R.id.bminus)
        bplus = findViewById(R.id.bplus)
        bequal = findViewById(R.id.bequal)
        bdot = findViewById(R.id.bdot)
        b1.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "1")
        }
        b2.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "2")
        }
        b3.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "3")
        }  
        b4.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "4")
        }
        b5.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "5")
        }
        b6.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "6")
        }
        b7.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "7")
        }
        b8.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "8")
        }
        b9.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "9")
        }
        b0.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "0")
        }
        bdot.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + ".")
        }
        bplus.setOnClickListener {
            if (!tvMain.text.contains("+")) tvMain.text = (tvMain.text.toString() + "+")
        }
        bdiv.setOnClickListener {
            if (!tvMain.text.contains("/")) tvMain.text = (tvMain.text.toString() + "/")
        }
        bminus.setOnClickListener {
            val str: String = tvMain.text.toString()
            if (!str.get(index = str.length - 1).equals("-")) {
                if (!tvMain.text.contains("-"))tvMain.text = (tvMain.text.toString() + "-")
            }
        }
        bmul.setOnClickListener {
            val str: String = tvMain.text.toString()
            if (!str.get(index = str.length - 1).equals("*")) {
                if (!tvMain.text.contains("*"))tvMain.text = (tvMain.text.toString() + "*")
            }
        }
        bequal.setOnClickListener {
            val str: String = tvMain.text.toString()
            val result: Double = evaluate(str)
            val r = result.toString()
            tvMain.setText(r)
            tvsec.text = str
        }
        bac.setOnClickListener {
            tvMain.setText("")
            tvsec.setText("")
        }
        bc.setOnClickListener {
            var str: String = tvMain.text.toString()
            if (!str.equals("")) {
                str = str.substring(0, str.length - 1)
                tvMain.text = str
            }
        }
    }

    fun evaluate(str: String): Double {
        return object : Any() {
            var pos = -1
            var ch = 0
            fun nextChar() {
                ch = if (++pos < str.length) str[pos].toInt() else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.toInt()) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    if (eat('+'.toInt())) x += parseTerm() // addition
                    else if (eat('-'.toInt())) x -= parseTerm() // subtraction
                    else return x
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (eat('*'.toInt())) x *= parseFactor()
                    else if (eat('/'.toInt())) x /= parseFactor()
                    else return x
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.toInt())) return parseFactor()
                if (eat('-'.toInt())) return -parseFactor()
                var x: Double
                val startPos = pos
                if (eat('('.toInt())) {
                    x = parseExpression()
                    eat(')'.toInt())
                } else if (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) {

                    while (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) nextChar()
                    x = str.substring(startPos, pos).toDouble()
                } else if (ch >= 'a'.toInt() && ch <= 'z'.toInt()) {
                    while (ch >= 'a'.toInt() && ch <= 'z'.toInt()) nextChar()
                    val func = str.substring(startPos, pos)
                    x = parseFactor()

                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }
                return x
            }

        }.parse()
    }
}