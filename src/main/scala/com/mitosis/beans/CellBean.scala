package com.mitosis.beans

import CellType._

import scala.beans.{BeanProperty, BooleanBeanProperty}

//remove if not needed
import scala.collection.JavaConversions._

class CellBean {

  @BeanProperty
  var name: String = _

  @BeanProperty
  var `type`: String = _

}