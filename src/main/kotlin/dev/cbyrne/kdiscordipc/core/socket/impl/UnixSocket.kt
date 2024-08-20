package dev.cbyrne.kdiscordipc.core.socket.impl

import dev.cbyrne.kdiscordipc.core.socket.RawPacket
import dev.cbyrne.kdiscordipc.core.socket.Socket
import dev.cbyrne.kdiscordipc.core.util.reverse
import java.io.DataInputStream
import java.net.UnixDomainSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel

class UnixSocket : Socket {
    private lateinit var socket: SocketChannel

    override val connected: Boolean
        get() = socket.isConnected

    override fun connect(file: String) {
        socket = SocketChannel.open(UnixDomainSocketAddress.of(file))
        socket.configureBlocking(false)
    }

    override fun read(): RawPacket {
        val opcode = readLittleEndianInt()
        val length = readLittleEndianInt()

        val stream = DataInputStream(socket.socket().getInputStream())
        val data = ByteArray(length)
        stream.readFully(data)

        return RawPacket(opcode, length, data)
    }

    private fun readLittleEndianInt() =
        ByteBuffer.wrap(readBytes(4)).int.reverse()

    private fun readBytes(length: Int): ByteArray {
        val array = ByteArray(length)
        socket.read(ByteBuffer.wrap(array))

        return array
    }

    override fun write(bytes: ByteArray) {
        socket.write(ByteBuffer.wrap(bytes))
    }

    override fun close() {
        socket.close()
    }
}
