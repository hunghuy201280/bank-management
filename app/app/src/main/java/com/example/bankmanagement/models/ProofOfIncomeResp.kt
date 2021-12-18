package com.example.bankmanagement.models


import android.net.Uri
import android.os.Parcelable
import com.example.bankmanagement.constants.AppConfigs
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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
@Parcelize

data class ProofOfIncomeResp(
    @SerializedName("imageId")
     val imageID: String,
    @SerializedName("imageType")
     val imageType: IncomeType,

    @SerializedName("_id")
    val id: String=""

) : Parcelable {
     fun getUrl(): Uri=
       Uri.parse( "${AppConfigs.baseUrl}images/$imageID")
}
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
    BusinessLicense(5);
    companion object {
        fun getValues():List<String> = values().map { it.getName() }
    }

    fun getName():String{
        return when(this){
            LaborContract ->"Labor Contract"
            SalaryConfirmation ->"Salary Confirmation"
            HouseRentalContract ->"House Rental Contract"
            CarRentalContract ->"Car Rental Contract"
            BusinessLicense ->"Business License"
            else->""
        }
    }
}