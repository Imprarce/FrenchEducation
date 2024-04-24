package com.imprarce.android.feature_community.helpers

interface OnDeleteItemClickListener<T> {
    fun onDeleteItemClick(position: Int, item: T)
}
