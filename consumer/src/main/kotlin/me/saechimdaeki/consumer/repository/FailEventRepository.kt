package me.saechimdaeki.consumer.repository

import me.saechimdaeki.consumer.domain.FailEvent
import org.springframework.data.jpa.repository.JpaRepository

interface FailEventRepository  : JpaRepository<FailEvent, Long>{
}