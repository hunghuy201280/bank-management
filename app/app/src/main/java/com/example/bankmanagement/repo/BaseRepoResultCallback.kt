package com.example.bankmanagement.repo

/**
 * T is Class Resp
 * ex: TableResp
 *
 * @param <T> author: congnt
</T> */
interface BaseRepoResultCallback<T> : BaseRepoCallback {
    fun onGetDataSuccess(result: T)
}