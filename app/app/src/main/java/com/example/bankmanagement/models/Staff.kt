package com.example.bankmanagement.models


data class Staff(
    val id: String,
    val role: StaffRole,
    val name: String,
    val email: String,
    val branchId: String,
)

enum class StaffRole(val value:Int) {
    Support(1),
    Business(2),
    Appraisal(3),
    Director(4);
    companion object {
        private val map = StaffRole.values().associateBy(StaffRole::value)
        fun fromInt(type: Int):StaffRole = map[type]!!
    }


}