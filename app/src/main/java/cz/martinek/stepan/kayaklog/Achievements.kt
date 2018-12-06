package cz.martinek.stepan.kayaklog


object Achievements {

    private val HOUR = 60*60
    private val KILOMETER = 1000


    val speed = listOf<Pair<Int,Float>>(Pair(0,10f),Pair(1,20f),Pair(2,30f))
    val distance = listOf<Pair<Int,Float>>(Pair(3,5f*KILOMETER),Pair(4,30f*KILOMETER),Pair(5,50f*KILOMETER))
    val duration = listOf<Pair<Int,Int>>(Pair(6,1*HOUR),Pair(7,2*HOUR), Pair(8,3*HOUR))

}
