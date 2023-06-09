package me.saechimdaeki.api.service

import me.saechimdaeki.api.domain.Coupon
import me.saechimdaeki.api.producer.CouponCreateProducer
import me.saechimdaeki.api.repository.AppliedUserRepository
import me.saechimdaeki.api.repository.CouponCountRepository
import me.saechimdaeki.api.repository.CouponRepository
import org.springframework.stereotype.Service

@Service
class ApplyService(
    private val couponRepository: CouponRepository,
    private val couponCountRepository: CouponCountRepository,
    private val couponCreateProducer: CouponCreateProducer,
    private val appliedUserRepository: AppliedUserRepository
) {

    fun applyCoupon(userId: Long) {
        // 개수에 대한 정합성이 db에 락을 건다면 쿠폰 갯수 + 쿠폰 생성까지 전부 락에 걸려있다. 따라서 성능이슈 발생
        // 핵심은 쿠폰 갯수에 대한 정합성이다. (떠오르는 생각 redis incr 명령어)
//        val count = couponRepository.count()

        // 요구사항 변경 쿠폰이 발급되었다면 return 발급 안되었다면 발급. -> redis set을 사용하자!

        val apply = appliedUserRepository.add(userId)

        // 쿠폰은 1인당 1개만 발급
        if (apply != 1L) {
            return
        }


        val count = couponCountRepository.increment() // 레디스를 통해 극복한 것 같지만 이는 rdb에 부하를 주게 됨.

        if (count > 100) {
            return
        }

//        couponRepository.save(Coupon(userId = userId)) // rdb저장

        couponCreateProducer.create(userId)
    }
}