package me.saechimdaeki.api.repository

import me.saechimdaeki.api.domain.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<Coupon, Long> {
}