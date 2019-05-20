package design.akililab.akilipocket.qrcode.camera



open class QRCodeTracker() {

    private val trackingListeners = mutableListOf<QRCodeTrackingListener>()
    private var pipedTracked: QRCodeTracker? = null

    fun addTrackingListener(listener: QRCodeTrackingListener): QRCodeTracker {
        trackingListeners.add(listener)
        return this
    }

    fun addTrackingListener(listener: (QRCodeObject?) -> Unit): QRCodeTracker {
        addTrackingListener(object : QRCodeTrackingListener {
            override fun onQrCodeTracked(qrCodeObject: QRCodeObject?) {
                listener(qrCodeObject)
            }
        })
        return this
    }

    fun removeTrackingListener(listener: QRCodeTrackingListener) = trackingListeners.remove(listener)

    fun pipe(otherTracker: QRCodeTracker): QRCodeTracker {
        pipedTracked = otherTracker
        return otherTracker
    }

    fun clearListenersAndPipe() {
        trackingListeners.clear()
        pipedTracked?.clearListenersAndPipe()
        pipedTracked = null
    }

    open fun processObject(qrCodeObject: QRCodeObject?) {
        publish(qrCodeObject)
    }

    protected fun publish(qrCodeObject: QRCodeObject?) {
        // Pipe
        pipedTracked?.processObject(qrCodeObject)

        // Notify
        trackingListeners.forEach {
            it.onQrCodeTracked(qrCodeObject)
        }
    }

    interface QRCodeTrackingListener {
        fun onQrCodeTracked(qrCodeObject: QRCodeObject?)
    }
}