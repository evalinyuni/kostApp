package id.test.kostbenhil.model

data class StatusResponse(var isSuccess: Boolean = true,
                          var message: String = "",
                          var errorCode: String = "")