package org.katas

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
class ArenaDamageCalculatorHolyTest {

    @Test
    fun `hero not affected by advantage or disadvantage versus his adversary` () {
        val attacker = Hero(HeroElement.Fire, 100, 0, 0, 0, 100)
        attacker.apply {
            setCounters(mutableListOf(Counter.Holy))
        }
        val defender = Hero(HeroElement.Fire, 20, 0, 0, 0, 100)
        defender.apply {
            setBuffs(mutableListOf(Buff.Defense))
        }
        val result = ArenaDamageCalculator().computeDamage(attacker, listOf(defender))
        assertEquals(25, result[0].lp)
    }
}