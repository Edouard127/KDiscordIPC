package dev.cbyrne.kdiscordipc.core.socket.impl

import dev.cbyrne.kdiscordipc.core.socket.RawPacket
import dev.cbyrne.kdiscordipc.core.socket.Socket
import dev.cbyrne.kdiscordipc.core.util.reverse
import java.net.UnixDomainSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel

class UnixSocket : Socket {
    private lateinit var socket: SocketChannel

    override val connected: Boolean
        get() = if (::socket.isInitialized) socket.isConnected else false

    override fun connect(file: String) {
        socket = SocketChannel.open(UnixDomainSocketAddress.of(file))
    }

    override fun read(): RawPacket {
        val opcode = readLittleEndianInt()
        val length = readLittleEndianInt()

        val data = ByteBuffer.allocate(length)
        socket.read(data)

        return RawPacket(opcode, length, data.array())
    }

    private fun readLittleEndianInt() =
        ByteBuffer.wrap(readBytes(4)).int.reverse()

    private fun readBytes(length: Int): ByteArray {
        val buffer = ByteBuffer.allocate(length)
        socket.read(buffer)

        return buffer.array()
    }

    override fun write(bytes: ByteArray) {
        socket.write(ByteBuffer.wrap(bytes))
    }

    override fun close() {
        socket.close()
    }
}
