package com.toy.mybook.DTO

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name="item")
@XmlAccessorType(XmlAccessType.FIELD)
data class SearchBookDTO(
    var title:String
)