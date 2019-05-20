package design.akililab.akilipocket.qrcode.camera

import android.graphics.RectF
import android.util.Size

data class QRCodeObject(val boundingBox: RectF, val sourceSize: Size, val sourceRotationDegrees: Int)