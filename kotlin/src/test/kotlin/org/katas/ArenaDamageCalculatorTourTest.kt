package org.katas

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArenaDamageCalculatorTourTest {
    private var calculator = ArenaDamageCalculator()

    @BeforeEach
    fun setup() {
        calculator = ArenaDamageCalculator()
    }

    @Test
    fun `attacker should attack enemy whom he has a bonus`() {
        val attacker = Hero(HeroElement.Water, 20, 100, 30, 30, 100)

        val results = calculator.computeDamage(attacker, listOf(
            Hero(HeroElement.Fire, 20, 100, 30, 30, 100),
            Hero(HeroElement.Earth, 20, 100, 30, 30, 100),
            Hero(HeroElement.Earth, 20, 100, 30, 30, 100)
        ))
        val fireDefender = results?.find { it.getElement() == HeroElement.Fire }
        assert(fireDefender?.lp!! < 100)
    }

    @Test
    fun `attacker should attack neutral enemy if no enemy has malus`() {
        val attacker = Hero(HeroElement.Water, 20, 100, 30, 30, 100)

        val results = calculator.computeDamage(attacker, listOf(
            Hero(HeroElement.Water, 20, 100, 30, 30, 100),
            Hero(HeroElement.Earth, 20, 100, 30, 30, 100),
            Hero(HeroElement.Earth, 20, 100, 30, 30, 100)
        ))
        val neutralDefender = results?.find { it.getElement() == HeroElement.Water }
        assert(neutralDefender?.lp!! < 100)
    }

    @Test
    fun `attacker should attack malus enemy if he has no other choice`() {
        val attacker = Hero(HeroElement.Earth, 20, 100, 30, 30, 100)

        val results = calculator.computeDamage(attacker, listOf(
            Hero(HeroElement.Water, 20, 100, 30, 30, 0),
            Hero(HeroElement.Fire, 20, 100, 30, 30, 100),
            Hero(HeroElement.Fire, 20, 100, 30, 30, 100)
        ))
        val neutralDefender = results?.find { it.lp < 100 }
        assert(neutralDefender != null)
    }

    @Test
    fun `attacker should not attack enemy with 0 lp`() {
        val attacker = Hero(HeroElement.Water, 20, 100, 30, 30, 100)

        val results = calculator.computeDamage(attacker, listOf(
            Hero(HeroElement.Fire, 20, 100, 30, 30, 0),
            Hero(HeroElement.Earth, 20, 100, 30, 30, 100),
            Hero(HeroElement.Earth, 20, 100, 30, 30, 100)
        ))
        val attacked = results?.find { it.lp in 1..99 }
        assert(attacked?.getElement() == HeroElement.Earth)
    }
}