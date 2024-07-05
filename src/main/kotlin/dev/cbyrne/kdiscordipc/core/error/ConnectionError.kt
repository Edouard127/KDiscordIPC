package dev.cbyrne.kdiscordipc.core.error

sealed class ConnectionError(reason: String) : Error(reason) {
    data object NoIPCFile :
        ConnectionError("Failed to find an IPC file. Is Discord open? Is this application allowed to access temporary files?")

    data object AlreadyConnected :
        ConnectionError("You are already connected!")

    data object NotConnected :
        ConnectionError("This socket has either been closed, or, was never connected.")

    data object Failed :
        ConnectionError("Failed to connect to socket.")

    data object Disconnected :
        ConnectionError("The discord application disconnected from the socket.")
}
