package com.example.safetech.data.network.models

import com.example.safetech.view.theme.models.UiObjectChecklist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ObjectChecklist(
    @SerialName("Номер")
    val number: String,
    @SerialName("СтруктурноеПодразделение")
    val structuralUnit: String,
    @SerialName("ОтветственныйЗаОбъект")
    val responsibleForObject: String,
    @SerialName("ЧекЛист")
    val checklist: List<ControlItem>,
    @SerialName("Сотрудники")
    val employees: List<Employee>
)

fun ObjectChecklist.toUiObjectChecklist(): UiObjectChecklist {
    return UiObjectChecklist(
        number = this.number,
        structuralUnit = this.structuralUnit,
        responsibleForObject = this.responsibleForObject,
        checklist = this.checklist.map { it.toUiControlItem() },
        employees = this.employees,
        isChecked = false,
        violationsList = mutableListOf()
    )
}