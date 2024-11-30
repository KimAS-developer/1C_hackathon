package com.example.safetech.view.theme.models

import com.example.safetech.data.network.models.Employee
import com.example.safetech.data.network.models.ViolationsOnObject

data class UiObjectChecklist (
    val number: String,
    val structuralUnit: String,
    val responsibleForObject: String,
    val checklist: List<UiControlItem>,
    val employees: List<Employee>,
    var isChecked: Boolean = false,
    val violationsList: MutableList<ViolationsOnObject>
)