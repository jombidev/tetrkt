package dev.jombi.tetris

class Timer(tps: Float, private var lastMs: Long) {
    var partialTick = 0f
    var tickDelta = 0f
    private val msPerTick = 1000.0f / tps

    fun advanceTime(currentMs: Long): Int {
        tickDelta = (currentMs - lastMs).toFloat() / msPerTick
        lastMs = currentMs
        partialTick += tickDelta
        val partialTick = partialTick.toInt()
        this.partialTick -= partialTick.toFloat()
        return partialTick
    }
}