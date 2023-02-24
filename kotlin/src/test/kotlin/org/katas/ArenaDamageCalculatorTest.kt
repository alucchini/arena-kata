package org.katas

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ArenaDamageCalculatorTest {
    @Test
    fun `should compute rest defender's lp for neutral fight`() {
        val attacker = Hero(HeroElement.Water, 100, 0, 0, 0, 100)
        val defender = Hero(HeroElement.Water, 100, 0, 0, 0, 100)
        val defenders = listOf(defender)
        val arenaDamageCalculator = ArenaDamageCalculator()
        val result = arenaDamageCalculator.computeDamage(attacker, defenders)
        assertEquals(0, result?.get(0)?.lp)
    }

    @Test
    fun `should compute rest defender's lp for a fight to the advantage of the defender`() {
        val attacker = Hero(HeroElement.Water, 100, 0, 0, 0, 100)
        val defender = Hero(HeroElement.Earth, 100, 0, 0, 0, 100)
        val defenders = listOf(defender)
        val arenaDamageCalculator = ArenaDamageCalculator()
        val result = arenaDamageCalculator.computeDamage(attacker, defenders)
        assertEquals(20, result?.get(0)?.lp)
    }

    @Test
    fun `should compute rest defender's lp for a fight to the advantage of the attacker`() {
        val attacker = Hero(HeroElement.Water, 50, 0, 0, 0, 100)
        val defender = Hero(HeroElement.Fire, 100, 0, 0, 0, 100)
        val defenders = listOf(defender)
        val arenaDamageCalculator = ArenaDamageCalculator()
        val result = arenaDamageCalculator.computeDamage(attacker, defenders)
        assertEquals(40, result?.get(0)?.lp)
    }
}