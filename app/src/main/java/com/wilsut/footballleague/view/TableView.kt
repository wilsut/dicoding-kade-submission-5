package com.wilsut.footballleague.view

import com.wilsut.footballleague.model.Table

interface TableView {
    fun showLoading()
    fun hideLoading()
    fun showTableList(data: List<Table>)
}