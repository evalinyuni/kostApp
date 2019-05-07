package id.test.kostbenhil.model.networking

import eu.amirs.JSON
import id.test.kostbenhil.model.StatusResponse

interface DelegateAPIRequest {
    fun onCallSuccess(response: JSON)
    fun onCallFailed(statusResponse: StatusResponse)
}