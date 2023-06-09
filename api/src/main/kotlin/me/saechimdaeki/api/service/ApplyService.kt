package me.saechimdaeki.api.service

import me.saechimdaeki.api.domain.Coupon
import me.saechimdaeki.api.repository.CouponRepository
import org.springframework.stereotype.Service

@Service
class ApplyService(
    private val couponRepository: CouponRepository
) {

    fun applyCoupon(userId: Long) {

        val count = couponRepository.count()

        if (count > 100) {
            return
        }
        couponRepository.save(Coupon(userId = userId))
    }
}