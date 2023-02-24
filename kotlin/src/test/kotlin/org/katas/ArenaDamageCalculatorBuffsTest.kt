package org.katas

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArenaDamageCalculatorBuffsTest {
    private var calculator = ArenaDamageCalculator()

    @BeforeEach
    fun setup() {
        calculator = ArenaDamageCalculator()
    }

    @Test
    fun `attacker with attack buff should inflict 25 percent more damage`() {
        val attackerPower = 20
        val lpDefender = 100
        val attacker = Hero(HeroElement.Fire, attackerPower, 100, 0, 0, 100).apply {
            setBuffs(arrayListOf(Buff.Attack))
        }
        val defenders = calculator.computeDamage(
            attacker,
            listOf(Hero(HeroElement.Fire, 20, 0, 0, 0, lpDefender))
        )
        assert(defenders.first().lp.toDouble() == (lpDefender - attackerPower * 1.25))
    }

    @Test
    fun `defender with defend buff should suffer 25 percent less damage`() {
        val attackerPower = 20
        val lpDefender = 100
        val attacker = Hero(HeroElement.Fire, attackerPower, 100, 0, 0, 100)
        val defender = Hero(HeroElement.Fire, 20, 0, 0, 0, lpDefender).apply {
            setBuffs(arrayListOf(Buff.Defense))
        }
        val defenders = calculator.computeDamage(attacker, listOf(defender))
        assert(defenders.first().lp.toDouble() == (lpDefender - attackerPower * 0.75))
    }
}