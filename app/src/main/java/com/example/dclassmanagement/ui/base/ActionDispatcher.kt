package com.example.dclassmanagement.ui.base

interface ActionExecutor {
    fun dispatch()
}

class ActionDispatcher(val actionExecutor: ActionExecutor){
    fun dispatch(){
        actionExecutor.dispatch()
    }
}