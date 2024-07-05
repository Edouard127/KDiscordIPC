package dev.cbyrne.kdiscordipc.core.socket

data class RawPacket(val opcode: Int, val length: Int, val data: ByteArray)
