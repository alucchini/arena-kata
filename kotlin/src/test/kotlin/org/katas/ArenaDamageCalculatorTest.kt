package org.katas

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ArenaDamageCalculatorTest {
    @Test
    fun `should compute rest defender's lp for neutral fight`() {
        val attacker = Hero(HeroElement.Water, 100, 0, 0, 0, 100)
        val defender = Hero(HeroElement.Water, 100, 75, 0, 0, 100)
        assertEquals(1, getDefenderLp(attacker, defender))
    }

    @Test
    fun `should compute rest defender's lp for a fight to the advantage of the defender`() {
        val attacker = Hero(HeroElement.Water, 100, 0, 0, 0, 100)
        val defender = Hero(HeroElement.Earth, 100, 150, 0, 0, 100)
        assertEquals(22, getDefenderLp(attacker, defender))
    }

    @Test
    fun `should compute rest defender's lp for a fight to the advantage of the attacker`() {
        val attacker = Hero(HeroElement.Water, 50, 0, 500, 100, 100)
        val defender = Hero(HeroElement.Fire, 100, 300, 0, 0, 100)
        assertEquals(8, getDefenderLp(attacker, defender))
    }

    private fun getDefenderLp(attacker: Hero, defender: Hero): Int? {
        val defenders = listOf(defender)
        val arenaDamageCalculator = ArenaDamageCalculator()
        val result = arenaDamageCalculator.computeDamage(attacker, defenders)
        return result?.get(0)?.lp
    }
}