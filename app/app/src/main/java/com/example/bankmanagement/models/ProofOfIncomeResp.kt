package com.example.bankmanagement.models


import android.net.Uri
import com.example.bankmanagement.constants.AppConfigs
import com.google.gson.annotations.SerializedName

abstract class ProofOfIncome(
    open val imageType: IncomeType,
    open val imageID: String,
    ){
    abstract fun getPathOrUrl():Uri;

}


//data class ProofOfIncomeResp(
//    override val imageID: String,
//    override val imageType: IncomeType,
//
//    @SerializedName("_id")
//    val id: String
//
//) : ProofOfIncome(imageType = imageType, imageID = imageID) {
//
//
//    override fun getPathOrUrl(): Uri=
//       Uri.parse( "${AppConfigs.baseUrl}/$imageID")
//
//}
data class ProofOfIncomeResp(
    @SerializedName("imageId")
     val imageID: String,
    @SerializedName("imageType")
     val imageType: IncomeType,

    @SerializedName("_id")
    val id: String=""

) ;
data class ProofOfIncomeRequest(
    @SerializedName("imageId")
     val imageID: String,
    @SerializedName("imageType")
     val imageType: IncomeType,


) ;

data class ProofOfIncomeUI(
    val imageUri: Uri,
    override val imageType: IncomeType,
    override val imageID: String = "",
    ) : ProofOfIncome(imageType = imageType, imageID = imageID) {

    override fun getPathOrUrl(): Uri=
        imageUri
}


enum class IncomeType(val value: Int) {

    @SerializedName("1")
    LaborContract(1),

    @SerializedName("2")
    SalaryConfirmation(2),

    @SerializedName("3")
    HouseRentalContract(3),

    @SerializedName("4")
    CarRentalContract(4),

    @SerializedName("5")
    BusinessLicense(5),

}