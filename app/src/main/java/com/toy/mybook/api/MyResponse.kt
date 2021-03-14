package com.toy.mybook.api

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


@Xml(name="rss")
data class MyResponse(
    @Element
    val channel: Channel
)

@Xml(name="channel")
data class Channel(
    @PropertyElement
    val title: String,
    @PropertyElement
    val link: String,
    @PropertyElement
    val description: String,
    @PropertyElement
    val lastBuildDate: String,
    @PropertyElement
    val total: Int,
    @PropertyElement
    val start: Int,
    @PropertyElement
    val display: Int,
    @Element
    val item: List<Item>
)

@Xml(name="item")
data class Item(
    @PropertyElement
    val title: String?,
    @PropertyElement
    val link: String?,
    @PropertyElement
    val image: String?,
    @PropertyElement
    val author: String?,
    @PropertyElement
    val price: Int?,
    @PropertyElement
    val discount : Int?,
    @PropertyElement
    val publisher: String?,
    @PropertyElement
    val pubdate: String?,
    @PropertyElement
    val isbn: String?,
    @PropertyElement
    val description: String?
){
    constructor(): this(null,null,null,null,null,null,null,null,null,null)
}
