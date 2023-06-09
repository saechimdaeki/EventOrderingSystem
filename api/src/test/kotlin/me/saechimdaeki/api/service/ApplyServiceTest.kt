package me.saechimdaeki.api.service

import me.saechimdaeki.api.repository.CouponRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import kotlin.concurrent.thread

@SpringBootTest
class ApplyServiceTest @Autowired constructor(
    val applyService: ApplyService,
    val couponRepository: CouponRepository
){

    @Test
    fun `쿠폰은 한번만 응모가 가능하다`() {
        applyService.applyCoupon(1L)

        val count = couponRepository.count()

        assertThat(count).isEqualTo(1)
    }


    @Test
    fun `여러 명이 응모하는 상황 테스트`(){
        val threadCount = 1000
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)

        for (i in 1..threadCount){
            executorService.execute {
                applyService.applyCoupon(i.toLong())
                latch.countDown()
            }
        }
        latch.await()

        // Thread 10 초 sleep (쿠폰 생성까지 텀이 있기에 기다림)
        Thread.sleep(10000)


        val count = couponRepository.count()

        assertThat(count).isEqualTo(100)
    }
}