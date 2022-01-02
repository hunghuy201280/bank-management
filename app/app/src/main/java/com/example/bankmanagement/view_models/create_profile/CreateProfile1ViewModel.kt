package com.example.bankmanagement.view_models.create_profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.models.customer.Customer
import com.example.bankmanagement.repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CreateProfile1ViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
) : BaseUiViewModel<BaseUserView>() {
    private val TAG: String = "CreateProfile1ViewModel";

    val customers = MutableLiveData<ArrayList<Customer>>(arrayListOf())

    val isEmpty = Transformations.map(customers) {
        return@map it.isEmpty()
    }

    fun onSearch(query: String) {
        if (query.isEmpty()) return;
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result =
                    mainRepo.searchCustomers(name = query, isStartWith = true)
                customers.postValue(result);


            } catch (e: HttpException) {
                customers.postValue(arrayListOf())
                Log.d(TAG, "Error happened: ${e.response()?.errorBody()?.string()} ");

            } catch (e: Exception) {
                customers.postValue(arrayListOf())
                Log.d(TAG, "Error happened: $e ");
            }
        }
    }


}