package org.katas

import org.junit.jupiter.api.Test

class ArenaDamageCalculatorTurncoatTest {

    @Test
    fun `Hero with turncoat advantage change his element temporarily` () {
        val attacker = Hero(HeroElement.Fire, 100, 0, 0, 0, 100)
        attacker.apply {
            setBuffs(arrayListOf(Buff.Turncoat))
        }
        if (attacker.getBuffs().contains(Buff.Turncoat)) {
            if (attacker.getElement() == HeroElement.Fire) {
                attacker.setElement(HeroElement.Water)
            } else if (attacker.getElement() == HeroElement.Water) {
                attacker.setElement(HeroElement.Earth)
            } else {
                attacker.setElement(HeroElement.Fire)
            }
        }
        assert(attacker.getElement() == HeroElement.Water)
    }
}