@file:Suppress("DeprecatedCallableAddReplaceWith")

package kotlinx.io.core

import kotlinx.io.bits.*
import kotlinx.io.core.internal.*
import kotlinx.io.core.internal.require


@Deprecated("Use discard with Int parameter. No replacement")
fun Buffer.discard(n: Long): Long = minOf(readRemaining.toLong(), n).toInt().also { discard(it) }.toLong()

fun Buffer.discardExact(n: Int) {
    discard(n)
}

/**
 * Copy available bytes to the specified [buffer] but keep them available.
 * If the underlying implementation could trigger
 * bytes population from the underlying source and block until any bytes available
 *
 * Very similar to [readAvailable] but don't discard copied bytes.
 *
 * @return number of bytes were copied
 */
fun Buffer.peekTo(buffer: Buffer): Int {
    val size = minOf(readRemaining, buffer.writeRemaining)
    memory.copyTo(buffer.memory, readPosition, size, buffer.writePosition)
    discard(size)
    buffer.commitWritten(size)
    return size
}

/**
 * Write byte [value] repeated the specified [times].
 */
fun Buffer.fill(times: Int, value: Byte) {
    require(times >= 0) { "times shouldn't be negative: $times" }
    require(times <= writeRemaining) { "times shouldn't be greater than the write remaining space: $times > $writeRemaining" }

    memory.fill(writePosition, times, value)
    commitWritten(times)
}

/**
 * Write unsigned byte [value] repeated the specified [times].
 */
fun Buffer.fill(times: Int, value: UByte) {
    fill(times, value.toByte())
}

/**
 * Write byte [v] value repeated [n] times.
 */
@Deprecated("Use fill with n with type Int")
fun Buffer.fill(n: Long, v: Byte) {
    fill(n.toIntOrFail("n"), v)
}

/**
 * Push back [n] bytes: only possible if there were at least [n] bytes read before this operation.
 */
@Deprecated("Use rewind instead", ReplaceWith("rewind(n)"))
fun Buffer.pushBack(n: Int): Unit = rewind(n)

@Deprecated("Use duplicate instead", ReplaceWith("duplicate()"))
fun Buffer.makeView(): Buffer = duplicate()

@Deprecated("Does nothing.")
fun Buffer.flush() {
}


@Deprecated("Not supported anymore", level = DeprecationLevel.ERROR)
fun Buffer.appendChars(csq: CharArray, start: Int, end: Int): Int = TODO()

@Deprecated("Not supported anymore", level = DeprecationLevel.ERROR)
fun Buffer.appendChars(csq: CharSequence, start: Int, end: Int): Int = TODO()

@Deprecated("Not supported anymore", level = DeprecationLevel.ERROR)
fun Buffer.append(c: Char): Buffer = TODO()

@Deprecated("Not supported anymore", level = DeprecationLevel.ERROR)
fun Buffer.append(csq: CharSequence?): Buffer = TODO()

@Deprecated("Not supported anymore", level = DeprecationLevel.ERROR)
fun Buffer.append(csq: CharSequence?, start: Int, end: Int): Buffer = TODO()

@Deprecated("Not supported anymore", level = DeprecationLevel.ERROR)
fun Buffer.append(csq: CharArray, start: Int, end: Int): Buffer = TODO()

@Deprecated(
    "This is no longer supported. All operations are big endian by default. Use readXXXLittleEndian " +
        "to read primitives in little endian",
    level = DeprecationLevel.ERROR
)
var Buffer.byteOrder: ByteOrder
    get() = ByteOrder.BIG_ENDIAN
    set(newOrder) {
        if (newOrder != ByteOrder.BIG_ENDIAN) throw UnsupportedOperationException("Only BIG_ENDIAN is supported")
    }