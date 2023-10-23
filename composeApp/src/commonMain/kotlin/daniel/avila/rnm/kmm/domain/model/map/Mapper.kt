package daniel.avila.rnm.kmm.domain.model.map

abstract class Mapper<M, P> {
    abstract fun map(model: M): P

    fun map(values: List<M>): List<P> = values.map { map(it) }
}