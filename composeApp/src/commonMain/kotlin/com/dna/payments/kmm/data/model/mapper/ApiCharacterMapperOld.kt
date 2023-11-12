package com.dna.payments.kmm.data.model.mapper

import com.dna.payments.kmm.data.model.ApiCharacter
import com.dna.payments.kmm.domain.model.Character
import com.dna.payments.kmm.domain.model.Gender
import com.dna.payments.kmm.domain.model.Status
import com.dna.payments.kmm.domain.model.map.MapperOld

class ApiCharacterMapperOld : MapperOld<ApiCharacter, Character>() {
    override fun map(model: ApiCharacter): Character = model.run {
        Character(
            id, name, when (status) {
                "Alive" -> Status.ALIVE
                "Dead" -> Status.DEAD
                else -> Status.UNKNOWN
            }, species, when (gender) {
                "Male" -> Gender.MALE
                "Female" -> Gender.FEMALE
                "Genderless" -> Gender.GENDERLESS
                else -> Gender.UNKNOWN
            }, origin.name, location.name, image
        )
    }
}