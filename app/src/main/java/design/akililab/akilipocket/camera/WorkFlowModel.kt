package design.akililab.akilipocket.camera

import android.app.Application
import android.content.Context
import androidx.annotation.MainThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import design.akililab.akilipocket.objectdetection.DetectedObject
import design.akililab.akilipocket.productsearch.Product
import design.akililab.akilipocket.productsearch.SearchedObject
import design.akililab.akilipocket.settings.PreferenceUtils

/** View model for handling application workflow based on camera preview.  */
class WorkflowModel(application: Application) : AndroidViewModel(application) {

    val detectedBarcode = MutableLiveData<FirebaseVisionBarcode>()
    val workflowState = MutableLiveData<WorkflowState>()
    val objectToSearch = MutableLiveData<DetectedObject>()
    val searchedObject = MutableLiveData<SearchedObject>()


    private val objectIdsToSearch = HashSet<Int>()


    private var confirmedObject: DetectedObject? = null

    var isCameraLive = false
        private set


    private val context: Context
        get() = getApplication<Application>().applicationContext


    /**
     * State set of the application workflow.
     */
    enum class WorkflowState {
        NOT_STARTED,
        DETECTING,
        DETECTED,
        CONFIRMING,
        CONFIRMED,
        SEARCHING,
        SEARCHED
    }


    @MainThread
    fun setWorkflowState(workflowState: WorkflowState) {

        this.workflowState.value = workflowState
    }


    fun markCameraLive() {
        isCameraLive = true
    }

    fun markCameraFrozen() {
        isCameraLive = false
    }

    @MainThread
    fun confirmingObject(confirmingObject: DetectedObject, progress: Float) {
        val isConfirmed = java.lang.Float.compare(progress, 1f) == 0
        if (isConfirmed) {
            confirmedObject = confirmingObject
            if (PreferenceUtils.isAutoSearchEnabled(context)) {
                setWorkflowState(WorkflowState.SEARCHING)
                triggerSearch(confirmingObject)
            } else {
                setWorkflowState(WorkflowState.CONFIRMED)
            }
        } else {
            setWorkflowState(WorkflowState.CONFIRMING)
        }
    }

    @MainThread
    fun onSearchButtonClicked() {
        confirmedObject?.let {
            setWorkflowState(WorkflowState.SEARCHING)
            triggerSearch(it)
        }
    }


    private fun triggerSearch(detectedObject: DetectedObject) {
        val objectId = detectedObject.objectId ?: throw NullPointerException()
        if (objectIdsToSearch.contains(objectId)) {
            // Already in searching.
            return
        }
        objectIdsToSearch.add(objectId)
        objectToSearch.value = detectedObject
    }




    fun onSearchCompleted(detectedObject: DetectedObject, products: List<Product>) {
        val lConfirmedObject = confirmedObject
        if (detectedObject != lConfirmedObject) {
            // Drops the search result from the object that has lost focus.
            return
        }

        objectIdsToSearch.remove(detectedObject.objectId)
        setWorkflowState(WorkflowState.SEARCHED)

        searchedObject.value = SearchedObject(context.resources, lConfirmedObject, products)
    }
}