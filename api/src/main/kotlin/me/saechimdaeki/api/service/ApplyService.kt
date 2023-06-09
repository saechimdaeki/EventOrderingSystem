package me.saechimdaeki.api.service

import me.saechimdaeki.api.domain.Coupon
import me.saechimdaeki.api.repository.CouponCountRepository
import me.saechimdaeki.api.repository.CouponRepository
import org.springframework.stereotype.Service

@Service
class ApplyService(
    private val couponRepository: CouponRepository,
    private val couponCountRepository: CouponCountRepository
) {

    fun applyCoupon(userId: Long) {
        // 개수에 대한 정합성이 db에 락을 건다면 쿠폰 갯수 + 쿠폰 생성까지 전부 락에 걸려있다. 따라서 성능이슈 발생
        // 핵심은 쿠폰 갯수에 대한 정합성이다. (떠오르는 생각 redis incr 명령어)
//        val count = couponRepository.count()

        val count = couponCountRepository.increment()

        if (count > 100) {
            return
        }
        couponRepository.save(Coupon(userId = userId))
    }
}