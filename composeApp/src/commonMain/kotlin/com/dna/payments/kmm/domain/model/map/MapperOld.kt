package com.dna.payments.kmm.domain.model.map

abstract class MapperOld<M, P> {
    abstract fun map(model: M): P

    fun map(values: List<M>): List<P> = values.map { map(it) }
}