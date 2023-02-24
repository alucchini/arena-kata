package org.katas

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ArenaDamageCalculatorTest {
    @Test
    fun `should compute rest defender's lp one water hero vs one water hero`() {
        val attacker = Hero(HeroElement.Water, 100, 0, 0, 0, 100)
        val defender = Hero(HeroElement.Water, 100, 0, 0, 0, 100)
        val defenders = listOf(defender)
        val arenaDamageCalculator = ArenaDamageCalculator()
        val result = arenaDamageCalculator.computeDamage(attacker, defenders)
        assertEquals(0, result?.get(0)?.lp)
    }

    @Test
    fun `should compute rest defender's lp one fire hero vs one fire hero`() {
        val attacker = Hero(HeroElement.Fire, 10, 0, 0, 0, 100)
        val defender = Hero(HeroElement.Fire, 100, 0, 0, 0, 100)
        val defenders = listOf(defender)
        val arenaDamageCalculator = ArenaDamageCalculator()
        val result = arenaDamageCalculator.computeDamage(attacker, defenders)
        assertEquals(90, result?.get(0)?.lp)
    }

    @Test
    fun `should compute rest defender's lp one earth hero vs one earth hero`() {
        val attacker = Hero(HeroElement.Earth, 28, 0, 0, 0, 100)
        val defender = Hero(HeroElement.Earth, 100, 0, 0, 0, 100)
        val defenders = listOf(defender)
        val arenaDamageCalculator = ArenaDamageCalculator()
        val result = arenaDamageCalculator.computeDamage(attacker, defenders)
        assertEquals(72, result?.get(0)?.lp)
    }
}