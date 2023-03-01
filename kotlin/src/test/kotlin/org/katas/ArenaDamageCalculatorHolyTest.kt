package org.katas

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
class ArenaDamageCalculatorHolyTest {

    @Test
    fun `hero not affected by advantage or disadvantage versus his adversary, deals 20% less damage, but ignores the opponent's defense` () {
        val defenderLP = 100
        val attacker = Hero(HeroElement.Fire, 100, 0, 0, 0, 100).apply {
            setCounters(mutableListOf(Counter.Holy))
        }
        val defender = Hero(HeroElement.Fire, 20, 0, 0, 0, defenderLP).apply {
            setBuffs(mutableListOf(Buff.Defense))
        }
        val result = ArenaDamageCalculator().computeDamage(attacker, listOf(defender))
        assertEquals(20, result[0].lp)
    }

    @Test
    fun `hero choose what adversary he want` () {
        val attacker = Hero(HeroElement.Fire, 100, 0, 0, 0, 100).apply {
            setCounters(mutableListOf(Counter.Holy))
        }
        val defenders = listOf<Hero>(
            Hero(HeroElement.Fire, 20, 75, 0, 0, 200),
            Hero(HeroElement.Water, 20, 7500, 0, 0, 40),
            Hero(HeroElement.Earth, 20, 0, 0, 0, 500)
        )
        ArenaDamageCalculator().computeDamage(attacker, defenders, defenders[1])
        assertEquals(0, defenders[1].lp)
    }
}