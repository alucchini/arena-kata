package org.katas

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
class ArenaDamageCalculatorHolyTest {

    @Test
    fun `hero not affected by advantage or disadvantage versus his adversary` () {
        val defenderLP = 100
        val attacker = Hero(HeroElement.Fire, 100, 0, 0, 0, 100)
        attacker.apply {
            setCounters(mutableListOf(Counter.Holy))
        }
        val defender = Hero(HeroElement.Fire, 20, 0, 0, 0, defenderLP)
        defender.apply {
            setBuffs(mutableListOf(Buff.Defense))
        }
        val result = ArenaDamageCalculator().computeDamage(attacker, listOf(defender))
        if (attacker.getCounters().first() == Counter.Holy) {
            defender.lp = (defenderLP - attacker.pow * 0.80).toInt()
        }
        assertEquals(20, result[0].lp)
    }

    @Test
    fun `hero deals 20% less damage, but ignores the opponent's defense` () {
        val defenderLP = 100
        val attacker = Hero(HeroElement.Fire, 100, 0, 0, 0, 100)
        attacker.apply {
            setCounters(mutableListOf(Counter.Holy))
        }
        val defender = Hero(HeroElement.Fire, 20, 750, 0, 0, defenderLP)
        val result = ArenaDamageCalculator().computeDamage(attacker, listOf(defender))
        if (attacker.getCounters().first() == Counter.Holy) {
            defender.lp = (defenderLP - attacker.pow * 0.80).toInt()
        }
        assertEquals(20, result[0].lp)
    }
}