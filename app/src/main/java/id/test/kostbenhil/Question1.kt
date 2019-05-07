package id.test.kostbenhil

import java.util.*

class Question1 {

    fun printQ2(max: Int) {

        val scanner  = Scanner(System.`in`)
        val max = scanner.nextInt()
        println("Inputan : $max")

        for (i in 0 until max) {
            print(i + 1)
            val c = (i + 1) % 5
            if (c === 2) {
                print("#")
            } else if (c === 0) {
                print("*")
            }
        }
    }

}