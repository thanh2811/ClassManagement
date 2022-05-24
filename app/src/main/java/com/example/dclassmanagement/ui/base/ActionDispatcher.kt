package com.example.dclassmanagement.ui.base

import com.example.dclassmanagement.data.model.ActionWrapper

interface ActionExecutor {
    fun dispatch(actionWrapper: ActionWrapper?)
}

class ActionDispatcher(private val actionExecutor: ActionExecutor){
    fun dispatch(actionWrapper: ActionWrapper? = null){
        actionExecutor.dispatch(actionWrapper)
    }
}