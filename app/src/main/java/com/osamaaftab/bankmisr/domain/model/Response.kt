package com.osamaaftab.bankmisr.domain.model

interface Response {
    val success: Boolean
    val error : ApiErrorModel?
}