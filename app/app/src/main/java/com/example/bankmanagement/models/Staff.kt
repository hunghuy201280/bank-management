package com.example.bankmanagement.models




 abstract class Staff(
     open val id: String,
    open val name: String,
    open val email: String,
    open val branchId: String,
){
    abstract fun getRoleName():String;
 }

data class BoardOfDirector(
    override val id: String,
   override val name: String,
   override val email: String,
   override val branchId: String,
): Staff(id,name,email,branchId) {
    override fun getRoleName(): String =StaffRole.Director.name
}

data class SupportStaff(
    override val id: String,
   override val name: String,
   override val email: String,
   override val branchId: String,
): Staff(id,name,email,branchId) {
    override fun getRoleName(): String =StaffRole.Support.name

}

data class BusinessStaff(
    override val id: String,
   override val name: String,
   override val email: String,
   override val branchId: String,
): Staff(id,name,email,branchId) {
    override fun getRoleName(): String =StaffRole.Business.name

}

data class AppraisalStaff(
    override val id: String,
   override val name: String,
   override val email: String,
   override val branchId: String,
): Staff(id,name,email,branchId) {
    override fun getRoleName(): String =StaffRole.Appraisal.name

}


enum class StaffRole(val value:Int) {

    Support(1),
    Business(2),
    Appraisal(3),
    Director(4);
    companion object {
        private val map = StaffRole.values().associateBy(StaffRole::value)
        fun fromInt(type: Int):StaffRole = map[type]!!

        fun getName():String ="";
    }


}