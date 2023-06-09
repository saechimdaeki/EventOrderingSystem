package me.saechimdaeki.consumer.repository

import me.saechimdaeki.consumer.domain.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<Coupon, Long> {
}