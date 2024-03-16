package com.fady.venuevoyage.presentation.utils.common

class NetworkState(var status: Status?, var msg: String?) {
    enum class Status {
        INIT_RUNNING, RUNNING, SUCCESS, FAILED
    }
}
