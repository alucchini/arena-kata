package org.katas

import org.junit.jupiter.api.Test

class ArenaDamageCalculatorTurncoatTest {

    @Test
    fun `Hero with turncoat advantage change his element temporarily` () {
        val attacker = Hero(HeroElement.Fire, 100, 0, 0, 0, 100).apply {
            setCounters(arrayListOf(Counter.Turncoat))
        }
        val defender = Hero(HeroElement.Fire, 100, 0, 0, 0, 100)
        ArenaDamageCalculator().computeDamage(attacker, listOf(defender))
        assert(attacker.getElement() == HeroElement.Water)
    }
}