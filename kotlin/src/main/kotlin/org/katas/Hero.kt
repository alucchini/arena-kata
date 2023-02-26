package org.katas

class Hero(element: HeroElement, pow: Int, def: Int, leth: Int, crtr: Int, lp: Int) {
    private var element: HeroElement
    var pow: Int
    var def: Int
    var leth: Int
    var crtr: Int
    var lp: Int
    private var buffs: MutableList<Buff>
    private var counters: MutableList<Counter>

    init {
        this.element = element
        this.pow = pow
        this.def = def
        this.leth = leth
        this.crtr = crtr
        this.lp = lp
        buffs = mutableListOf()
        counters = mutableListOf()
    }

    fun getElement(): HeroElement {
        return element
    }

    fun setElement(element: HeroElement) {
        this.element = element
    }

    fun getBuffs(): MutableList<Buff> {
        return buffs
    }

    fun setBuffs(buffs: MutableList<Buff>) {
        this.buffs = buffs
    }

    fun getCounters(): MutableList<Counter> {
        return counters
    }

    fun setCounters(counters: MutableList<Counter>) {
        this.counters = counters
    }
}