package me.saechimdaeki.consumer.consumer

import me.saechimdaeki.consumer.domain.Coupon
import me.saechimdaeki.consumer.domain.FailEvent
import me.saechimdaeki.consumer.repository.CouponRepository
import me.saechimdaeki.consumer.repository.FailEventRepository
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CouponCreatedConsumer(
    private val couponRepository: CouponRepository,
    private val failEventRepository: FailEventRepository
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics = ["coupon_create"], groupId = "group_1")
    fun listener(userId: Long) {
        try {
            couponRepository.save(Coupon(userId = userId))
        }catch (e: Exception) {
            log.error("failed to create coupon $userId")
            failEventRepository.save(FailEvent(userId = userId))
        }
    }
}