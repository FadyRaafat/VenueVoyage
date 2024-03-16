package com.fady.venuevoyage.presentation.utils.common

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.fady.venuevoyage.BR
class ToolbarData : BaseObservable() {

    fun handleHome(showBack: Boolean = false) {
        showTitle = false
        showLogo = true
        this.showBack = showBack
        showNav = true

    }

    fun handleSplash() {
        showBar = false
        showNav = false
    }

    fun handleNormal(title: String = "", showNav: Boolean = true) {
        showTitle = true
        showBack = true
        this.title = title
        this.showNav = showNav
    }

    @get:Bindable
    var title: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }


    @get:Bindable
    var showTitle: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showTitle)
        }

    @get:Bindable
    var showLogo: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.showLogo)
        }

    @get:Bindable
    var showBack: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showBack)
        }



    @get:Bindable
    var showBar: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.showBar)
        }

    @get:Bindable
    var showNav: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.showNav)
        }

}