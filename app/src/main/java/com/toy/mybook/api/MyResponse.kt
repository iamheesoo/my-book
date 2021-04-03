package com.toy.mybook.api

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable


@Root(name="rss", strict = false)
data class MyResponse @JvmOverloads constructor(
    @field:Element
    var channel: Channel
)
{
    constructor(): this(Channel())
}

@Root(name="channel")
data class Channel(
    @field:Element
    var title: String,
    @field:Element
    var link: String,
    @field:Element(required = false)
    var description: String,
    @field:Element
    var lastBuildDate: String,
    @field:Element
    var total: Int,
    @field:Element
    var start: Int,
    @field:Element
    var display: Int,
    @field:ElementList(type=Item::class, inline=true)
    var item: ArrayList<Item>
)
{
    constructor(): this("","","","",0,0,0,arrayListOf<Item>())
}

@Root(name="item")
data class Item(
    @field:Element
    var title: String?,
    @field:Element
    var link: String?,
    @field:Element(required=false)
    var image: String?,
    @field:Element
    var author: String?,
    @field:Element
    var price: Int?,
    @field:Element(required=false)
    var discount : Int?,
    @field:Element
    var publisher: String?,
    @field:Element
    var pubdate: String?,
    @field:Element
    var isbn: String?,
    @field:Element(required=false)
    var description: String?,

    var uid: String?,
    var rating: Float?
): Serializable
{
    constructor(): this(null,null,null,null,null,null,null,null,null,null,null,0f)
}