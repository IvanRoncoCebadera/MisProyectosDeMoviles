package dev.ivanronco.inicioenrecyclerview_ivaroncocebadera.adapter

import dev.ivanronco.inicioenrecyclerview_ivaroncocebadera.models.Persona

interface PersonOnClickListener {
    fun onClickPerson(persona: Persona):Persona
}